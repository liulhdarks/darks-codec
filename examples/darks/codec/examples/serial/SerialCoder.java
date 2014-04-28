package darks.codec.examples.serial;

import darks.codec.CodecConfig.CacheType;
import darks.codec.CodecConfig.EndianType;
import darks.codec.CodecConfig.TotalLengthType;
import darks.codec.ObjectCoder;
import darks.codec.serial.ObjectSerial;
import darks.codec.wrap.IdentifyWrapper;
import darks.codec.wrap.VerifyWrapper;

public class SerialCoder
{

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception
    {
        ObjectCoder coder = new ObjectCoder();
        coder.getCodecConfig().setEndianType(EndianType.LITTLE);
        coder.getCodecConfig().setTotalLengthType(TotalLengthType.HEAD_BODY);
        coder.getCodecConfig().setAutoLength(true);
        coder.getCodecConfig().setCacheType(CacheType.LOCAL);
        coder.getCodecConfig().addWrap(new IdentifyWrapper((short)0xfafb));
        coder.getCodecConfig().addWrap(VerifyWrapper.CRC16());
        
        SerialMainBean bean = new SerialMainBean();
        bean.id = 128;
        bean.version = 1;
        bean.subSerial = new SerialSubBean();
        bean.subSerial.code = 10;
        bean.subSerial.content = "running";
        bean.subSerial.equip = "2014";
        
        byte[] bytes = ObjectSerial.encode(coder, bean);
        Object result = ObjectSerial.decode(coder, bytes);
        System.out.println(result);
        System.out.println(bytes.length);
    }

}
