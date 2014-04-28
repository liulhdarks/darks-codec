package darks.codec.test.serial;

import java.io.IOException;
import java.io.Serializable;

import darks.codec.ObjectCoder;
import darks.codec.CodecConfig.CacheType;
import darks.codec.CodecConfig.EndianType;
import darks.codec.CodecConfig.TotalLengthType;
import darks.codec.helper.ByteHelper;
import darks.codec.serial.ObjectSerial;
import darks.codec.wrap.IdentifyWrapper;
import darks.codec.wrap.VerifyWrapper;

public class TestSerial
{

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws Exception
    {
        testLength();
        testSpeed();
    }
    
    private static void testLength() throws Exception
    {
        ObjectCoder oc = new ObjectCoder();
        oc.getCodecConfig().setEndianType(EndianType.LITTLE);
        oc.getCodecConfig().setTotalLengthType(TotalLengthType.HEAD_BODY);
        oc.getCodecConfig().setAutoLength(true);
        oc.getCodecConfig().setCacheType(CacheType.NONE);
        oc.getCodecConfig().addWrap(new IdentifyWrapper((short)0xfafb));
        oc.getCodecConfig().addWrap(VerifyWrapper.CRC16());
        
        SerialBean bean = new SerialBean();
        bean.id = 1;
        bean.name = "123";
        byte[] bytes = ObjectSerial.encode(oc, bean);
        System.out.println(bean.getClass().getName().length());
        
        Object obj = ObjectSerial.decode(oc, bytes);
        System.out.println(obj);
        SerialBean ret = (SerialBean)obj;
        System.out.println(ret.id);
        System.out.println(ret.name);
        
        bytes = ByteHelper.objectToBytes(bean);
        System.out.println("serial len:" + bytes.length);
        System.out.println(ByteHelper.toHexString(bytes));
        obj = ByteHelper.bytesToObject(bytes);
    }

    
    private static void testSpeed() throws Exception
    {
        ObjectCoder oc = new ObjectCoder();
        oc.getCodecConfig().setEndianType(EndianType.LITTLE);
        oc.getCodecConfig().setTotalLengthType(TotalLengthType.HEAD_BODY);
        oc.getCodecConfig().setAutoLength(true);
        oc.getCodecConfig().setCacheType(CacheType.LOCAL);
        oc.getCodecConfig().addWrap(new IdentifyWrapper((short)0xfafb));
        oc.getCodecConfig().addWrap(VerifyWrapper.CRC16());
        
        SerialBean bean = new SerialBean();
        bean.id = 1;
        bean.name = "123";
        long st = System.currentTimeMillis();
        for (int i = 0; i < 200000; i++)
        {
            byte[] bytes = ObjectSerial.encode(oc, bean);
            ObjectSerial.decode(oc, bytes);
        }
        long et = System.currentTimeMillis();
        System.out.println("darks codec cost:" + (et - st));
        
        st = System.currentTimeMillis();
        for (int i = 0; i < 200000; i++)
        {
            byte[] bytes = ByteHelper.objectToBytes(bean);
            ByteHelper.bytesToObject(bytes);
        }
        et = System.currentTimeMillis();
        System.out.println("java codec cost:" + (et - st));
    }
}

class SerialBean implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 5565617730283369460L;
    
    int id;
    String name;
}
