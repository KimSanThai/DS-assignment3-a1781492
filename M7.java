public class M7
{
    static int ID = 7;
    static int port = 9007;
    static int[] ports = {9001,9002,9003,9004,9005,9006,9008,9009};

    public static void main (String[] args)
    {
        if(args.length == 0)
        {
            Acceptor M7_acceptor = new Acceptor(ID, port, false);
            M7_acceptor.start();
        }
        else if (args.length == 1)
        {
            if(args[0].equals("true"))
            {
                Acceptor M7_acceptor = new Acceptor(ID, port, true);
                M7_acceptor.start();
            }
            else if(args[0].equals("false"))
            {
                Acceptor M7_acceptor = new Acceptor(ID, port, false);
                M7_acceptor.start();
            }
            else
            {
                System.out.println("Usage: java M7 <true|false>");
            }
        }
        else
        {
            System.out.println("Usage: java M7 <true|false>");
        }
    }
}