package darks.codec.basetype.impl;

import java.io.IOException;

import darks.codec.CodecParameter;
import darks.codec.basetype.BaseType;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;
import darks.codec.logs.Logger;

public class IntegerType extends BaseType
{
    
    private static Logger log = Logger.getLogger(IntegerType.class);
    
    @Override
    public void encode(BytesOutputStream out, Object obj, CodecParameter param)
    {
        Integer v = (Integer)obj;
        try
        {
            out.writeInt(v);
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
//            byte[] bytes = ByteHelper.readBytes(in, 4, param.isLittleEndian());
//            int value = ByteHelper.convertToInt32(bytes);
            return in.readInt();
        }
        catch (IOException e)
        {
            log.error(e.getMessage(), e);
        }
        return 0;
    }
    
}
