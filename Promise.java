import java.io.Serializable;

public class Promise implements Serializable
{
    public int promiseID;
    public int HighestProposalNumber;
    public int AcceptedProposalNum;
    public String value;

    public Promise(int ID, int highestPropNum,int Accepted_Prop, String v)
    {
        this.promiseID = ID;
        this.HighestProposalNumber = highestPropNum;
        this.AcceptedProposalNum = Accepted_Prop;
        this.value = v;
    }
    
    public Promise(int ID,int h)
    {
        this.promiseID = ID;
        this.HighestProposalNumber = h;
    }
}