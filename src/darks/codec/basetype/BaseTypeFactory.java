package darks.codec.basetype;

import java.util.concurrent.ConcurrentHashMap;

import darks.codec.basetype.impl.ByteType;
import darks.codec.basetype.impl.DoubleType;
import darks.codec.basetype.impl.FloatType;
import darks.codec.basetype.impl.IntegerType;
import darks.codec.basetype.impl.LongType;
import darks.codec.basetype.impl.ShortType;
import darks.codec.basetype.impl.StringType;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  lWX148392
 * @version  [版本号, 2013-7-29]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public final class BaseTypeFactory
{
    
    private static ConcurrentHashMap<Class<?>, BaseType> codecs =
        new ConcurrentHashMap<Class<?>, BaseType>();
    
    static
    {
        registerCodec(Integer.class, new IntegerType());
        registerCodec(int.class, new IntegerType());
        registerCodec(Short.class, new ShortType());
        registerCodec(int.class, new ShortType());
        registerCodec(Byte.class, new ByteType());
        registerCodec(byte.class, new ByteType());
        registerCodec(Float.class, new FloatType());
        registerCodec(float.class, new FloatType());
        registerCodec(Double.class, new DoubleType());
        registerCodec(double.class, new DoubleType());
        registerCodec(Long.class, new LongType());
        registerCodec(long.class, new LongType());
        registerCodec(String.class, new StringType());
    }
    
    private BaseTypeFactory()
    {
        
    }
    
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param clazz
     * @param codec
     * @see [类、类#方法、类#成员]
     */
    public static void registerCodec(Class<?> clazz, BaseType codec)
    {
        codecs.put(clazz, codec);
    }
    
    public static BaseType getCodec(Class<?> clazz)
    {
        return codecs.get(clazz);
    }
}
