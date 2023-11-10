public class M2
{
    static int ID = 2;
    static int port = 9002;
    static int[] ports = {9001,9003,9004,9005,9006,9007,9008,9009};
    static String value = "M2 is the winner";

    public static void main (String[] args)
    {
        if(args.length == 0)
        {
            Acceptor M2_acceptor = new Acceptor(ID, port, false);
            M2_acceptor.start();

            Proposer M2_proposer = new Proposer(ID, ports, value);
            M2_proposer.start();
        }
        else if (args.length == 1)
        {
            if(args[0].equals("true"))
            {
                Acceptor M2_acceptor = new Acceptor(ID, port, true);
                M2_acceptor.start();

                Proposer M2_proposer = new Proposer(ID, ports, value);
                M2_proposer.start();
            }
            else if(args[0].equals("false"))
            {
                Acceptor M2_acceptor = new Acceptor(ID, port, false);
                M2_acceptor.start();

                Proposer M2_proposer = new Proposer(ID, ports, value);
                M2_proposer.start();
            }
            else if(args[0].equals("fail"))
            {
                Acceptor M2_acceptor = new Acceptor(ID, port, false);
                M2_acceptor.start();

                Proposer_Modified M2_proposer = new Proposer_Modified(ID, ports, value);
                M2_proposer.start();
            }
            else
            {
                System.out.println("Usage: java M2 <true|false>");
            }
        }
        else
        {
            System.out.println("Usage: java M2 <true|false>");
        }
    }
}