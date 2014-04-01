package darks.codec.coder.cache;

import java.lang.reflect.Field;

public interface CacheStrategy
{
    public Field[] getCacheFields(Class<?> clazz);
    
    public void putCacheFields(Class<?> clazz, Field[] fields);
}
