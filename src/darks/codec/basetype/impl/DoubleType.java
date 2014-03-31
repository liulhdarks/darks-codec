package darks.codec.basetype.impl;

import java.io.IOException;

import darks.codec.CodecParameter;
import darks.codec.basetype.BaseType;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;
import darks.codec.logs.Logger;

public class DoubleType extends BaseType
{
    
    private static Logger log = Logger.getLogger(DoubleType.class);
    
    @Override
    public void encode(BytesOutputStream out, Object obj, CodecParameter param)
    {
        Double v = (Double)obj;
        try
        {
            out.writeDouble(v);
        }
        catch (IOException e)
        {
            log.error(e.getMessage(), e);
        }
    }
    
    @Override
    public Object decode(BytesInputStream in, Object obj, CodecParameter param)
    {
        try
        {
            return in.readDouble();
        }
        catch (IOException e)
        {
            log.error(e.getMessage(), e);
        }
        return 0;
    }
    
}
