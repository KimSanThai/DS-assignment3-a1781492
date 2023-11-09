import java.io.Serializable;

public class Prepare implements Serializable
{
    public int prepareID;
    public int proposalNumber;

    public Prepare(int ID, int pNum)
    {
        this.prepareID = ID;
        this.proposalNumber = pNum;
    }
}
