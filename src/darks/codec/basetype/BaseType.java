package darks.codec.basetype;

import java.io.IOException;

import darks.codec.CodecParameter;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;

public abstract class BaseType
{
    public abstract void encode(BytesOutputStream out, Object obj, CodecParameter param);
    
    public abstract Object decode(BytesInputStream in, Object obj, CodecParameter param);
    
    protected void writeAutoLength(BytesOutputStream out, int length, CodecParameter param) throws IOException
    {
        if (param.isAutoLength())
        {
            out.writeInt(length);
        }
    }
    
    protected int readAutoLength(BytesInputStream in, CodecParameter param) throws IOException
    {
        if (param.isAutoLength() && in.available() >= 4)
        {
            return in.readInt();
        }
        return -1;
    }
}
