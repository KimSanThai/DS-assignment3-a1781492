public class M1
{
    static int ID = 1;
    static int port = 9001;
    static int[] ports = {9002,9003,9004,9005,9006,9007,9008,9009};
    static String value = "M1 is the winner";

    public static void main (String[] args)
    {
        if(args.length == 0)
        {
            Acceptor M1_acceptor = new Acceptor(ID, port, ports, false);
            M1_acceptor.start();

            Proposer M1_proposer = new Proposer(ID, ports, value);
            M1_proposer.start();
        }
        else if (args.length == 1)
        {
            if(args[0].equals("true"))
            {
                Acceptor M1_acceptor = new Acceptor(ID, port, ports, true);
                M1_acceptor.start();

                Proposer M1_proposer = new Proposer(ID, ports, value);
                M1_proposer.start();
            }
            else if(args[0].equals("false"))
            {
                Acceptor M1_acceptor = new Acceptor(ID, port, ports, false);
                M1_acceptor.start();

                Proposer M1_proposer = new Proposer(ID, ports, value);
                M1_proposer.start();
            }
            else
            {
                System.out.println("Usage: java M1 <true|false>");
            }
        }
        else
        {
            System.out.println("Usage: java M1 <true|false>");
        }
    }
}