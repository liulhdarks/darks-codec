package darks.codec.type;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.Encoder;
import darks.codec.helper.ByteHelper;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;

public class OCBytes extends OCType<byte[]>
{
    
    /**
     * ×¢ÊÍÄÚÈÝ
     */
    private static final long serialVersionUID = -3809172632938626001L;
    
    //private static final Logger log = Logger.getLogger(OCBytes.class);
    
    public OCBytes()
    {
        
    }
    
    public OCBytes(OCInteger lenType)
    {
        super(lenType);
    }
    
    public OCBytes(byte[] bytes)
    {
        super(bytes);
    }
    
    public OCBytes(byte[] bytes, OCInteger lenType)
    {
        super(bytes, lenType);
    }
    
    public OCBytes(byte[] bytes, int len)
    {
        super(bytes, len);
    }
    
    @Override
    public void writeObject(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws IOException
    {
        if (getValue() == null)
        {
            return;
        }
        writeAutoLength(encoder, out, param);
        byte[] bytes = getValue(new byte[0]);
        super.writeBytes(encoder, out, bytes, param);
    }
    
    @Override
    public void readObject(Decoder decoder, BytesInputStream in,
            CodecParameter param) throws IOException
    {
        readAutoLength(decoder, in, param);
        if (isDynamicLength())
        {
            int length = getLenType().getValue();
            setLength(length);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[length];
            if (in.available() > 0)
            {
                int len = in.read(buf);
                if (len == length)
                {
                    baos.write(buf, 0, len);
                }
                else
                {
                    throw new IOException(
                        "Read data length is not matched. Require:" + length
                            + " but:" + len);
                }
            }
            setValue(baos.toByteArray());
        }
        else
        {
            int len = in.available();
            setLength(len);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            while (in.available() > 0)
            {
                len = in.read(buf);
                if (len > 0)
                {
                    baos.write(buf, 0, len);
                }
            }
            setValue(baos.toByteArray());
        }
    }
    
    @Override
    public String toString()
    {
        return "OCBytes [getValue()=" + ByteHelper.toHexString(getValue())
            + ", getLength()=" + getLength() + "]";
    }
    
}
