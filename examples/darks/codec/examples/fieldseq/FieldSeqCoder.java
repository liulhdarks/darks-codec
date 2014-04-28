package darks.codec.examples.fieldseq;

import darks.codec.CodecConfig.EndianType;
import darks.codec.ObjectCoder;
import darks.codec.helper.ByteHelper;
import darks.codec.type.OCInt16;
import darks.codec.type.OCInt8;
import darks.codec.wrap.IdentifyWrapper;

public class FieldSeqCoder
{

    public static void main(String[] args) throws Exception
    {
        codeFieldMsg();
        codeFieldCodecMsg();
    }
    
    private static void codeFieldMsg() throws Exception
    {
        ObjectCoder coder = new ObjectCoder();
        coder.getCodecConfig().setEndianType(EndianType.LITTLE);
        coder.getCodecConfig().addWrap(new IdentifyWrapper(new OCInt16(0xfafb), new OCInt8(0xFF)));
        FieldMsg msg = new FieldMsg();
        msg.id = 32;
        msg.version = 1;
        msg.command = "running";
        byte[] bytes = coder.encode(msg);
        System.out.println(ByteHelper.toHexString(bytes));
        
        FieldMsg result = new FieldMsg();
        coder.decode(bytes, result);
        System.out.println("ID:" + result.id);
        System.out.println("VERSION:" + result.version);
        System.out.println("COMMAND:" + result.command);
    } 
    
    private static void codeFieldCodecMsg() throws Exception
    {
        ObjectCoder coder = new ObjectCoder();
        coder.getCodecConfig().setEndianType(EndianType.LITTLE);
        coder.getCodecConfig().addWrap(new IdentifyWrapper(new OCInt16(0xfafb), new OCInt8(0xFF)));
        FieldCodecMsg msg = new FieldCodecMsg();
        msg.id = 32;
        msg.version = 1;
        msg.command.setValue("running");
        byte[] bytes = coder.encode(msg);
        System.out.println(ByteHelper.toHexString(bytes));
        FieldCodecMsg result = new FieldCodecMsg();
        coder.decode(bytes, result);
        System.out.println("ID:" + result.id);
        System.out.println("VERSION:" + result.version);
        System.out.println("COMMAND:" + result.command.getValue());
    } 
    
}
