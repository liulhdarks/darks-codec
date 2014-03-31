package darks.codec.type;

public class OCInt16 extends OCInteger
{
    
    /**
     * ×¢ÊÍÄÚÈÝ
     */
    private static final long serialVersionUID = -1401660273226090239L;
    
    public OCInt16()
    {
        setLength(2);
    }
    
    public OCInt16(int val)
    {
        super(val, 2);
    }
    
    public OCInt16(OCInteger lenType)
    {
        super(lenType);
        setLength(2);
        lenType.setValue(2);
    }
    
}
