package darks.codec.coder.cache;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StrongRefStrategy implements CacheStrategy
{

    private Map<Class<?>, Field[]> fieldsMap = new ConcurrentHashMap<Class<?>, Field[]>();
    
    @Override
    public Field[] getCacheFields(Class<?> clazz)
    {
        return fieldsMap.get(clazz);
    }

    @Override
    public void putCacheFields(Class<?> clazz, Field[] fields)
    {
        fieldsMap.put(clazz, fields);
    }

}
