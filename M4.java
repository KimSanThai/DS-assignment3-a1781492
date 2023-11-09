public class M4
{
    static int ID = 4;
    static int port = 9004;
    static int[] ports = {9001,9002,9003,9005,9006,9007,9008,9009};

    public static void main (String[] args)
    {
        if(args.length == 0)
        {
            Acceptor M4_acceptor = new Acceptor(ID, port, ports, false);
            M4_acceptor.start();
        }
        else if (args.length == 1)
        {
            if(args[0].equals("true"))
            {
                Acceptor M4_acceptor = new Acceptor(ID, port, ports, true);
                M4_acceptor.start();
            }
            else if(args[0].equals("false"))
            {
                Acceptor M4_acceptor = new Acceptor(ID, port, ports, false);
                M4_acceptor.start();
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