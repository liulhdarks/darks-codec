package darks.codec.type;

import java.io.IOException;

import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.Encoder;
import darks.codec.helper.ByteHelper;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;

public class OCString extends OCType<String>
{
    
    /**
     * ×¢ÊÍÄÚÈÝ
     */
    private static final long serialVersionUID = -956003176522540628L;
    
    public OCString()
    {
        super();
    }
    
    public OCString(String value, int length)
    {
        super(value, length);
    }
    
    public OCString(String value)
    {
        super(value);
    }
    
    public OCString(OCInteger lenType)
    {
        super(lenType);
    }
    
    @Override
    public void writeObject(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws IOException
    {
        writeAutoLength(encoder, out, param);
        byte[] bytes = ByteHelper.convertString(getValue());
        super.writeBytes(encoder, out, bytes, param);
    }
    
    @Override
    public void readObject(Decoder decoder, BytesInputStream in,
            CodecParameter param) throws IOException
    {
        readAutoLength(decoder, in, param);
        byte[] bytes = null;
        if (!isDynamicLength())
        {
            int len = in.available();
            setLength(len);
            bytes = ByteHelper.readBytes(in, len, false);
        }
        else
        {
            int len = getLenType().getValue();
            setLength(len);
            bytes = ByteHelper.readBytes(in, len, false);
        }
        setValue(ByteHelper.convertToString(bytes));
    }
}
