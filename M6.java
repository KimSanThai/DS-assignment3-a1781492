public class M6
{
    static int ID = 6;
    static int port = 9006;
    static int[] ports = {9001,9002,9003,9004,9005,9007,9008,9009};

    public static void main (String[] args)
    {
        if(args.length == 0)
        {
            Acceptor M6_acceptor = new Acceptor(ID, port, ports, false);
            M6_acceptor.start();
        }
        else if (args.length == 1)
        {
            if(args[0].equals("true"))
            {
                Acceptor M6_acceptor = new Acceptor(ID, port, ports, true);
                M6_acceptor.start();
            }
            else if(args[0].equals("false"))
            {
                Acceptor M6_acceptor = new Acceptor(ID, port, ports, false);
                M6_acceptor.start();
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