package darks.codec.basetype.impl;

import java.io.IOException;

import darks.codec.CodecParameter;
import darks.codec.basetype.BaseType;
import darks.codec.helper.ByteHelper;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;
import darks.codec.logs.Logger;

public class StringType extends BaseType
{
    
    private static Logger log = Logger.getLogger(StringType.class);
    
    @Override
    public void encode(BytesOutputStream out, Object obj, CodecParameter param)
    {
        String s = (String)obj;
        try
        {
            byte[] bytes = s.getBytes();
            writeAutoLength(out, bytes.length, param);
            out.write(bytes);
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
            int len = in.available();
            if (len <= 0)
            {
                return null;
            }
            len = readAutoLength(in, param);
            int ioLen = in.available();
            len = (len <= 0 || ioLen < len) ? ioLen : len;
            byte[] bytes = ByteHelper.readBytes(in, len, false);
            return new String(bytes);
        }
        catch (IOException e)
        {
            log.error(e.getMessage(), e);
            return null;
        }
    }
    
}
