import java.io.Serializable;

public class Propose implements Serializable
{
    public int proposeID;
    public int proposalNumber;
    public String val;

    public Propose(int ID, int propNum, String v)
    {
        this.proposeID = ID;
        this.proposalNumber = propNum;
        this.val = v;
    }
}
