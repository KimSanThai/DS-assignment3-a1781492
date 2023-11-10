import java.io.Serializable;

public class Consensus implements Serializable
{
    public int consensusPropNum;
    public String consensusVal;

    public Consensus(int p, String v)
    {
        this.consensusPropNum = p;
        this.consensusVal = v;
    }
}
