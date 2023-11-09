import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Proposer extends Thread
{
    //initializing variables
    private int ID;
    private String value;
    private int currentProposalNumber = 0;
    private int[] Ports;
    private ConcurrentHashMap<Integer, Promise> PromiseHashMap = new ConcurrentHashMap<Integer, Promise>();

    //parameterized constructor
    public Proposer(int ID, int[] ports, String val)
    {
        this.ID = ID;
        this.Ports = ports;
        this.value = val;
    }

    public void run()
    {
        try
        {
            while(true)
            {
                propose(value);

                //wait 15 seconds before sending next round of propose
                Thread.sleep(15000);
            }
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    //Function to start sending proposal values
    public void propose(String valueToPropose) throws InterruptedException
    {
        value = valueToPropose;

        this.currentProposalNumber++;

        //Setup threads so that messsage can be sent to all simultaneously
        Thread[] prepareThreads = new Thread[Ports.length];

        for(int i = 0; i < Ports.length; i++)
        {
            int p = Ports[i];

            prepareThreads[i] = new Thread(() -> 
            {
            Promise r = sendPrepare(p, currentProposalNumber);

            //if connection fails or response is not proper type
            if(r != null)
            {
                //Add promise response to hashmap with sender's ID as key
                PromiseHashMap.put(r.promiseID, r);
            }
            });
            prepareThreads[i].start();
        }

        //Wait for threads to finish execution
        for (Thread thread : prepareThreads)
        {
            try
            {
                thread.join();
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        //check if majority of has sent back response
        if(PromiseHashMap.size() > 4)
        {
            int tmp = 0;

            //Check if there is censensus value and selects the largest one
            for(Map.Entry<Integer, Promise> v : PromiseHashMap.entrySet())
            {
                if(v.getValue().value != "" & v.getValue().AcceptedProposalNum > tmp)
                {
                    tmp = v.getValue().AcceptedProposalNum;
                    value = v.getValue().value;
                }
            }

            System.out.println("Majority promised value is: " + value);

            //Sends propose with new consensus value
            for(int p : Ports)
            {
                sendPropose(p, currentProposalNumber, value);
            }
        }
    }

    private void sendPropose(int port, int ProposalNumber, String val)
    {
        try(Socket s = new Socket("localhost", port))
        {
            //setting socket timeout
            s.setSoTimeout(10000);

            //Send request
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            out.writeUnshared(new Propose(ID ,ProposalNumber, val));
            out.flush();
        }
        catch(IOException e)
        {
            System.out.println("Message unable to be sent to port: " + port);
        }
    }

    private Promise sendPrepare(int port, int ProposalNumber)
    {
        try(Socket s = new Socket("localhost", port))
        {
            //setting socket timeout
            s.setSoTimeout(10000);

            //Send request
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            out.writeUnshared(new Prepare(ID, ProposalNumber));
            out.flush();
            
            //Recieve response
            try (ObjectInputStream in = new ObjectInputStream(s.getInputStream()))
            {
                Promise response = (Promise) in.readObject();
                return response;
            }
            catch(java.net.SocketTimeoutException e)
            {
                System.out.println("Timeout while waiting for response from port: "+ port);
                return null;
            }
        }
        catch(IOException | ClassNotFoundException e)
        {
            System.out.println("Message unable to be sent to port: " + port);
            return null;
        }
    }
}