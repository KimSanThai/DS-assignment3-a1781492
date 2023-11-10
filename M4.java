public class M4
{
    static int ID = 4;
    static int port = 9004;

    public static void main (String[] args)
    {
        if(args.length == 0)
        {
            Acceptor M4_acceptor = new Acceptor(ID, port, false);
            M4_acceptor.start();
        }
        else if (args.length == 1)
        {
            if(args[0].equals("true"))
            {
                Acceptor M4_acceptor = new Acceptor(ID, port, true);
                M4_acceptor.start();
            }
            else if(args[0].equals("false"))
            {
                Acceptor M4_acceptor = new Acceptor(ID, port, false);
                M4_acceptor.start();
            }
            else
            {
                System.out.println("Usage: java M4 <true|false>");
            }
        }
        else
        {
            System.out.println("Usage: java M4 <true|false>");
        }
    }
}