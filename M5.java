public class M5
{
    static int ID = 5;
    static int port = 9005;
    static int[] ports = {9001,9002,9003,9004,9006,9007,9008,9009};

    public static void main (String[] args)
    {
        if(args.length == 0)
        {
            Acceptor M5_acceptor = new Acceptor(ID, port, ports, false);
            M5_acceptor.start();
        }
        else if (args.length == 1)
        {
            if(args[0].equals("true"))
            {
                Acceptor M5_acceptor = new Acceptor(ID, port, ports, true);
                M5_acceptor.start();
            }
            else if(args[0].equals("false"))
            {
                Acceptor M5_acceptor = new Acceptor(ID, port, ports, false);
                M5_acceptor.start();
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