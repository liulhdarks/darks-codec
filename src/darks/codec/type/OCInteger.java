package darks.codec.type;

import java.io.IOException;

import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.Encoder;
import darks.codec.helper.ByteHelper;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;

public class OCInteger extends OCType<Integer>
{
    
    /**
     * ×¢ÊÍÄÚÈÝ
     */
    private static final long serialVersionUID = -5799245199331542476L;
    
    public static final int BIT8_LEN = 1;
    
    public static final int BIT16_LEN = 2;
    
    public static final int BIT32_LEN = 4;
    
    public OCInteger()
    {
        
    }
    
    public OCInteger(int val)
    {
        super(val);
    }
    
    public OCInteger(int val, int len)
    {
        super(val, len);
    }
    
    public OCInteger(OCInteger lenType)
    {
        super(lenType);
    }
    
    @Override
    public void writeObject(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws IOException
    {
        byte[] bytes = null;
        int len = getLength();
        switch (len)
        {
        case BIT8_LEN:
            bytes = ByteHelper.convertInt8(getValue(0));
            break;
        case BIT16_LEN:
            bytes = ByteHelper.convertInt16(getValue(0), param.isLittleEndian());
            break;
        case BIT32_LEN:
            bytes = ByteHelper.convertInt32(getValue(0), param.isLittleEndian());
            break;
        }
        super.writeBytes(encoder, out, bytes, param);
    }
    
    @Override
    public void readObject(Decoder decoder, BytesInputStream in,
            CodecParameter param) throws IOException
    {
        byte[] bytes = ByteHelper.readBytes(in, getLength(), param.isLittleEndian());

        int len = getLength();
        switch (len)
        {
        case BIT8_LEN:
            setValue(ByteHelper.convertToInt8(bytes));
            break;
        case BIT16_LEN:
            setValue(ByteHelper.convertToInt16(bytes));
            break;
        case BIT32_LEN:
            setValue(ByteHelper.convertToInt32(bytes));
            break;
        }
    }
    
}
