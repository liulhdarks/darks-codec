package darks.codec.test.map;

import java.io.IOException;

import darks.codec.CodecConfig.CacheType;
import darks.codec.CodecConfig.EndianType;
import darks.codec.CodecConfig.TotalLengthType;
import darks.codec.ObjectCoder;
import darks.codec.type.OCBytes;
import darks.codec.type.OCMap;
import darks.codec.wrap.IdentifyWrapper;
import darks.codec.wrap.VerifyWrapper;

public class TestMap
{
    
    public static void main(String[] args) throws IOException
    {
        ObjectCoder oc = new ObjectCoder();
        oc.getCodecConfig().setEndianType(EndianType.LITTLE);
        oc.getCodecConfig().setTotalLengthType(TotalLengthType.HEAD_BODY);
        oc.getCodecConfig().setAutoLength(true);
        oc.getCodecConfig().setIgnoreObjectAutoLength(true);
        oc.getCodecConfig().setCacheType(CacheType.NONE);
        oc.getCodecConfig().addWrap(new IdentifyWrapper((short)0xfafb));
        oc.getCodecConfig().addWrap(VerifyWrapper.CRC16());
        
        testIntMap(oc);
        testStringIntMap(oc);
        testIntStringMap(oc);
        testIntBytesMap(oc);
        testIntObjectMap(oc);
    }
    
    private static void testIntMap(ObjectCoder oc) throws IOException
    {
        MapIntIntMsg msg1 = new MapIntIntMsg();
        msg1.code = "1234567890";
        msg1.map.put(0x0001, 123);
        msg1.map.put(0x0002, 456);
        msg1.map.put(0x0003, 789);
        byte[] bytes = oc.encode(msg1);
        MapIntIntMsg ret = new MapIntIntMsg();
        oc.decode(bytes, ret);
        System.out.println(ret);
    }
    
    private static void testStringIntMap(ObjectCoder oc) throws IOException
    {
        MapStringIntMsg msg1 = new MapStringIntMsg();
        msg1.code = "1234567890";
        msg1.map.put("0x0001", 123);
        msg1.map.put("0x0002", 456);
        msg1.map.put("0x0003", 789);
        byte[] bytes = oc.encode(msg1);
        MapStringIntMsg ret = new MapStringIntMsg();
        oc.decode(bytes, ret);
        System.out.println(ret);
    }
    
    private static void testIntStringMap(ObjectCoder oc) throws IOException
    {
        MapIntStringMsg msg1 = new MapIntStringMsg();
        msg1.code = "1234567890";
        msg1.map.put(0x0001, "123");
        msg1.map.put(0x0002, "456");
        msg1.map.put(0x0003, "789");
        byte[] bytes = oc.encode(msg1);
        MapIntStringMsg ret = new MapIntStringMsg();
        oc.decode(bytes, ret);
        System.out.println(ret);
    }
    
    private static void testIntBytesMap(ObjectCoder oc) throws IOException
    {
        MapIntBytesMsg msg1 = new MapIntBytesMsg();
        msg1.code = "1234567890";
        msg1.map.put(0x0001, OCBytes.valueOf("str123"));
        msg1.map.put(0x0002, OCBytes.valueOf(456, true));
        msg1.map.put(0x0003, OCBytes.valueOf((byte)64));
        byte[] bytes = oc.encode(msg1);
        MapIntBytesMsg ret = new MapIntBytesMsg();
        oc.decode(bytes, ret);
        System.out.println(ret);
    }
    
    private static void testIntObjectMap(ObjectCoder oc) throws IOException
    {
        MapIntObjectMsg msg1 = new MapIntObjectMsg();
        msg1.code = "1234567890";
        msg1.map.put(0x0001, new MapHuman("human1", 22));
        msg1.map.put(0x0002, new MapHuman("human2", 25));
        msg1.map.put(0x0003, new MapHuman("human3", 26));
        byte[] bytes = oc.encode(msg1);
        MapIntObjectMsg ret = new MapIntObjectMsg();
        oc.decode(bytes, ret);
        System.out.println(ret);
    }
    
    static class MapIntIntMsg
    {
        String code;
        
        OCMap<Integer, Integer> map = new OCMap<Integer, Integer>();

        @Override
        public String toString()
        {
            return "MapIntIntMsg [code=" + code + ", map=" + map + "]";
        }
        
    }
    
    static class MapStringIntMsg
    {
        String code;
        
        OCMap<String, Integer> map = new OCMap<String, Integer>();

        @Override
        public String toString()
        {
            return "MapStringIntMsg [code=" + code + ", map=" + map + "]";
        }
        
    }
    
    static class MapIntStringMsg
    {
        String code;
        
        OCMap<Integer, String> map = new OCMap<Integer, String>();

        @Override
        public String toString()
        {
            return "MapIntStringMsg [code=" + code + ", map=" + map + "]";
        }
        
    }
    
    static class MapIntBytesMsg
    {
        String code;
        
        OCMap<Integer, OCBytes> map = new OCMap<Integer, OCBytes>();

        @Override
        public String toString()
        {
            return "MapIntBytesMsg [code=" + code + ", map=" + map + "]";
        }
        
    }
    
    static class MapIntObjectMsg
    {
        String code;
        
        OCMap<Integer, MapHuman> map = new OCMap<Integer, MapHuman>();

        @Override
        public String toString()
        {
            return "MapIntObjectMsg [code=" + code + ", map=" + map + "]";
        }
        
    }
    
    static class MapHuman
    {
        String name;
        
        int age;

        public MapHuman()
        {
            
        }

        public MapHuman(String name, int age)
        {
            super();
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString()
        {
            return "MapHuman [name=" + name + ", age=" + age + "]";
        }
        
    }
}
