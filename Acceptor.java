import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Acceptor extends Thread
{
    //initialize variables
    private int ID;
    private int port;
    private boolean proposal_accepted = false;
    private int HighestProposalNumber = 0;
    private int accepted_propNum;
    private String accepted_val;
    private boolean delay_on = false;

    public Acceptor(int ID, int port, boolean delay)
    {
        this.ID = ID;
        this.port = port;
        this.delay_on = delay;
    }

    public void run()
    {
        //Open socket to listen for messages
        try
        {
            ServerSocket ss = new ServerSocket(port);
            System.out.println("Waiting for response on port: " + port);

            while(true)
            {
                Socket s = ss.accept();
                ObjectInputStream in = new ObjectInputStream(s.getInputStream());
                Object o = in.readObject();

                //Add delay
                delay();
                
                //if got sent Prepare message
                if(o instanceof Prepare)
                {
                    Prepare r = (Prepare) o;
                    System.out.println("Received prepare message from ID: " + r.prepareID);
                    
                    //check prepare proposal ID and only respond if Proposal Number is higher than highest received proposal number
                    if(r.proposalNumber > HighestProposalNumber)
                    {
                        System.out.println("Proposal number is " + r.proposalNumber + " which is bigger than " + HighestProposalNumber);
                        System.out.println("Prepare accepted");

                        //save highest proposal number
                        HighestProposalNumber = r.proposalNumber;
                        
                        //Check if proposal has been previously accepted
                        System.out.println("Sending promise");
                        if(proposal_accepted)
                        {
                            //send Promise message
                            Promise p = new Promise(ID, HighestProposalNumber, accepted_propNum, accepted_val);
                            sendPromise(p, s);
                        }
                        else
                        {
                            //send Promise message
                            Promise p = new Promise(ID, HighestProposalNumber);
                            sendPromise(p, s);
                        }
                    }
                    else
                    {
                        System.out.println("Proposal number is " + r.proposalNumber + " which is not bigger than " + HighestProposalNumber);
                        System.out.println("Prepare ignored");
                    }
                }
                else if (o instanceof Propose)
                {
                    Propose r = (Propose) o;

                    System.out.println("Received propose message from ID: " + r.proposeID);

                    //Checks if propose message is has the same number as the highest proposal number
                    if(r.proposalNumber == HighestProposalNumber)
                    {
                        System.out.println("Proposal number received is correct");
                        System.out.println("Propose accepted");

                        proposal_accepted = true;
                        accepted_propNum = r.proposalNumber;
                        accepted_val = r.val;

                        //sends accept message to everyone else
                        System.out.println("Sending accept messages out");
                        Accept a = new Accept(ID, accepted_propNum, accepted_val);
                        sendAccept(a, s);

                        //Print message and close thread
                        System.out.println("Concensus value is: " + accepted_val);
                    }
                    else
                    {
                        System.out.println("Proposal number is" + r.proposalNumber + " which is not equal to " + HighestProposalNumber);
                        System.out.println("Propose ignored");
                    }
                }
            }
        }
        catch(InterruptedException e)
        {
            System.out.println("Delay function failed");
        }
        catch(IOException | ClassNotFoundException e)
        {
            System.out.println("Unable to start connection on port: " + port);
        }
    }

    //Function to add random delay after accepting messages before sending back response
    public void delay() throws InterruptedException
    {
        //Checks if delay is on
        if(delay_on)
        {
            int randDelay;
            //no delays for M1
            if(ID == 1)
            {
                return;
            }

            //responds very late for M2 or extremely fast (0-10 seconds)
            else if(ID == 2)
            {
                randDelay = (int)(Math.random()*10000);
                Thread.sleep(randDelay);
                return;
            }

            //not as responsive as M1 but not as late as M2 (3-6 seconds)
            else if(ID == 3)
            {
                randDelay = (int)(Math.random()*6000+3000);
                Thread.sleep(randDelay);
                return;
            }

            //M4-M9 have random response time (0-5 seconds)
            else
            {
                randDelay = (int)(Math.random()*5000);
                Thread.sleep(randDelay);
                return;
            }
        }
        else
        {
            return;
        }
    }

    //sends Accept message to the port specified
    public void sendAccept(Accept a, Socket s)
    {
        try(ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream()))
        {
            out.writeUnshared(a);
            out.flush();
        }
        catch (IOException e)
        {
            System.out.println("Unable to send accept back");
        }
    }

    //sends promise message back to proposer
    public void sendPromise(Promise p, Socket s)
    {
        try(ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream()))
        {
            out.writeUnshared(p);
            out.flush();
        }
        catch (IOException e)
        {
            System.out.println("Unable to send promise back");
        }
    }
}