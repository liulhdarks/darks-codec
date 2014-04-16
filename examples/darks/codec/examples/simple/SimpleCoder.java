package darks.codec.examples.simple;

import java.io.IOException;

import darks.codec.CodecConfig.EndianType;
import darks.codec.CodecConfig.TotalLengthType;
import darks.codec.ObjectCoder;
import darks.codec.helper.ByteHelper;
import darks.codec.type.OCInt16;
import darks.codec.type.OCInt8;
import darks.codec.wrap.IdentifyWrapper;

public class SimpleCoder
{
    
    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException
    {
        simpleCode();
        simpleCodeTotalHeadBody();
        simpleCodeAutoLengtth();
    }
    
    private static void simpleCode() throws IOException
    {
        ObjectCoder coder = new ObjectCoder();
        coder.getCodecConfig().setEndianType(EndianType.LITTLE);
        coder.getCodecConfig().addWrap(new IdentifyWrapper(new OCInt16(0xfafb), new OCInt8(0xFF)));
        SimpleMsg msg = new SimpleMsg();
        msg.id = 32;
        msg.version = 1;
        msg.command = "running";
        byte[] bytes = coder.encode(msg);
        System.out.println(ByteHelper.toHexString(bytes));
        
        SimpleMsg result = new SimpleMsg();
        coder.decode(bytes, result);
        System.out.println("ID:" + result.id);
        System.out.println("VERSION:" + result.version);
        System.out.println("COMMAND:" + result.command);
    }
    
    private static void simpleCodeTotalHeadBody() throws IOException
    {
        ObjectCoder coder = new ObjectCoder();
        coder.getCodecConfig().setEndianType(EndianType.LITTLE);
        coder.getCodecConfig().setTotalLengthType(TotalLengthType.HEAD_BODY);
        coder.getCodecConfig().addWrap(new IdentifyWrapper(new OCInt16(0xFAFB)));
        SimpleMsg msg = new SimpleMsg();
        msg.id = 32;
        msg.version = 1;
        msg.command = "running";
        byte[] bytes = coder.encode(msg);
        System.out.println(ByteHelper.toHexString(bytes));
    }

    private static void simpleCodeAutoLengtth() throws IOException
    {
        ObjectCoder coder = new ObjectCoder();
        coder.getCodecConfig().setEndianType(EndianType.LITTLE);
        coder.getCodecConfig().setTotalLengthType(TotalLengthType.HEAD_BODY);
        coder.getCodecConfig().setAutoLength(true);
        coder.getCodecConfig().addWrap(new IdentifyWrapper(new OCInt16(0xFAFB)));
        SimpleMsg msg = new SimpleMsg();
        msg.id = 32;
        msg.version = 1;
        msg.command = "running";
        byte[] bytes = coder.encode(msg);
        System.out.println(ByteHelper.toHexString(bytes));
    }
}
