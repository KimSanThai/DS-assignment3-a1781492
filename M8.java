public class M8
{
    static int ID = 8;
    static int port = 9008;
    static int[] ports = {9001,9002,9003,9004,9005,9006,9007,9009};

    public static void main (String[] args)
    {
        if(args.length == 0)
        {
            Acceptor M8_acceptor = new Acceptor(ID, port, false);
            M8_acceptor.start();
        }
        else if (args.length == 1)
        {
            if(args[0].equals("true"))
            {
                Acceptor M8_acceptor = new Acceptor(ID, port, true);
                M8_acceptor.start();
            }
            else if(args[0].equals("false"))
            {
                Acceptor M8_acceptor = new Acceptor(ID, port, false);
                M8_acceptor.start();
            }
            else
            {
                System.out.println("Usage: java M8 <true|false>");
            }
        }
        else
        {
            System.out.println("Usage: java M8 <true|false>");
        }
    }
}