package darks.codec.type;

public class OCInt8 extends OCInteger
{
    
    /**
     * ×¢ÊÍÄÚÈÝ
     */
    private static final long serialVersionUID = 8680326158431049622L;
    
    public OCInt8()
    {
        super(0, 1);
    }
    
    public OCInt8(int val)
    {
        super(val, 1);
    }
    
    public OCInt8(OCInteger lenType)
    {
        super(lenType);
        setLength(1);
        lenType.setValue(1);
    }
    
}
