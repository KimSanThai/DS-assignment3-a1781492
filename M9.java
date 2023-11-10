public class M9
{
    static int ID = 9;
    static int port = 9009;
    static int[] ports = {9001,9002,9003,9004,9005,9006,9007,9008};

    public static void main (String[] args)
    {
        if(args.length == 0)
        {
            Acceptor M9_acceptor = new Acceptor(ID, port, false);
            M9_acceptor.start();
        }
        else if (args.length == 1)
        {
            if(args[0].equals("true"))
            {
                Acceptor M9_acceptor = new Acceptor(ID, port, true);
                M9_acceptor.start();
            }
            else if(args[0].equals("false"))
            {
                Acceptor M9_acceptor = new Acceptor(ID, port, false);
                M9_acceptor.start();
            }
            else
            {
                System.out.println("Usage: java M9 <true|false>");
            }
        }
        else
        {
            System.out.println("Usage: java M9 <true|false>");
        }
    }
}