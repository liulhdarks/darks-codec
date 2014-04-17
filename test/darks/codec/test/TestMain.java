package darks.codec.test;

import java.io.IOException;

import darks.codec.CodecConfig.CacheType;
import darks.codec.CodecConfig.EndianType;
import darks.codec.CodecConfig.TotalLengthType;
import darks.codec.ObjectCoder;
import darks.codec.type.OCBytes;
import darks.codec.type.OCInt16;
import darks.codec.type.OCInt32;
import darks.codec.type.OCInt8;
import darks.codec.type.OCList;
import darks.codec.type.OCObject;
import darks.codec.wrap.IdentifyWrapper;
import darks.codec.wrap.VerifyWrapper;

public class TestMain
{

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException
    {
        ObjectCoder oc = new ObjectCoder();
        oc.getCodecConfig().setEndianType(EndianType.LITTLE);
        oc.getCodecConfig().setTotalLengthType(TotalLengthType.HEAD_BODY);
        oc.getCodecConfig().setAutoLength(false);
        oc.getCodecConfig().setCacheType(CacheType.NONE);
        oc.getCodecConfig().addWrap(new IdentifyWrapper(new OCInt16(0xfafb), new OCInt8(0xFF)));
        oc.getCodecConfig().addWrap(VerifyWrapper.CRC16());
        //oc.getCodecConfig().addWrap(CipherWrapper.AES("darks"));
        //oc.getCodecConfig().addWrap(ZipWrapper.JZLIB());
        //oc.getCodecConfig().getWrapChain().add(ZipWrapper.JDK_GZIP());
        //oc.getCodecConfig().getWrapChain().add(ZipWrapper.COMMON_COMPRESS(CompressorStreamFactory.SNAPPY_FRAMED));
        //oc.getCodecConfig().setIgnoreObjectAutoLength(true);
        long st = System.currentTimeMillis();
        for (int i = 0; i < 1; i++)
        {
            MainMsg msg = new MainMsg();
            msg.id = 10;
            msg.subMsg.number=16;
            msg.subMsg.list.add("abc");
            msg.subMsg.list.add("123");
            msg.subMsg.listInt.add(128);
            msg.subMsg.listInt.add(256);
            msg.subMsg.listLong.add(1280l);
            msg.subMsg.listLong.add(2560l);
            byte[] bytes = oc.encode(msg);
            
            MainMsg msg2 = new MainMsg();
            oc.decode(bytes, msg2);
            //System.out.println(msg2);
        }
        long dt = System.currentTimeMillis() - st;
        System.out.println("cost time:" + dt);
        
        Test1 test = new Test1();
        test.id = 4;
       // test.msg = "123";
        test.msg2 = "456";
        test.bytes.int32(128, true);
        byte[] bytes = oc.encode(test);
        
        Test1 test2 = new Test1();
        oc.decode(bytes, test2);
        System.out.println(test2);
    }

}

class Test1
{
    public int id;
    
    //public String msg;
    
    //public String msg2;
    
    public OCBytes bytes = new OCBytes(4);
    
    public String msg2;

    @Override
    public String toString()
    {
        return "Test1 [id=" + id
                + ", bytes=" + bytes+ ", msg2=" + msg2 + "]";
    }
    
}

class MainMsg extends OCObject
{
    public int id;
    
    public SubMsg subMsg = new SubMsg();
    
    public MainMsg()
    {
    }

    @Override
    public String toString()
    {
        return "MainMsg [id=" + id + ", \nsubMsg=" + subMsg + "]";
    }
    
    
}

class SubMsg
{
    public int number;
    
    public OCInt32 count = new OCInt32();
    
    public OCList<String> list = new OCList<String>(count);
    
    public OCList<Integer> listInt = new OCList<Integer>();
    
    public OCList<Long> listLong = new OCList<Long>();

    @Override
    public String toString()
    {
        return "SubMsg [number=" + number + ", count=" + count + ", list="
                + list + ", listInt=" + listInt + ", listLong=" + listLong
                + "]";
    }

}

