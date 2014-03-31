package darks.codec.type;

public class OCInt32 extends OCInteger
{
    
    /**
     * ×¢ÊÍÄÚÈÝ
     */
    private static final long serialVersionUID = 4098042740891005945L;
    
    public OCInt32()
    {
        super(0, 4);
    }
    
    public OCInt32(int val)
    {
        super(val, 4);
    }
    
    public OCInt32(OCInteger lenType)
    {
        super(lenType);
        setLength(4);
        lenType.setValue(4);
    }
}
