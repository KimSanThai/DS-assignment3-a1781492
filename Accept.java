import java.io.Serializable;

public class Accept implements Serializable
{
    public int acceptID;
    public int proposalNumber;
    public String val;

    public Accept(int ID, int propNum, String v)
    {
        this.acceptID = ID;
        this.proposalNumber = propNum;
        this.val = v;
    }
}
