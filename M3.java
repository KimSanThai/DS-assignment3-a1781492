public class M3
{
    static int ID = 3;
    static int port = 9003;
    static int[] ports = {9001,9002,9004,9005,9006,9007,9008,9009};
    static String value = "M3 is the winner";

    public static void main (String[] args)
    {
        if(args.length == 0)
        {
            Acceptor M3_acceptor = new Acceptor(ID, port, false);
            M3_acceptor.start();

            Proposer M3_proposer = new Proposer(ID, ports, value);
            M3_proposer.start();
        }
        else if (args.length == 1)
        {
            if(args[0].equals("true"))
            {
                Acceptor M3_acceptor = new Acceptor(ID, port, true);
                M3_acceptor.start();

                Proposer M3_proposer = new Proposer(ID, ports, value);
                M3_proposer.start();
            }
            else if(args[0].equals("false"))
            {
                Acceptor M3_acceptor = new Acceptor(ID, port, false);
                M3_acceptor.start();

                Proposer M3_proposer = new Proposer(ID, ports, value);
                M3_proposer.start();
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