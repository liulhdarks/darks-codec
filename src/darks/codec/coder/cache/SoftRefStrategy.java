package darks.codec.coder.cache;

import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SoftRefStrategy implements CacheStrategy
{

    private Map<Class<?>, SoftReference<Field[]>> fieldsMap = new ConcurrentHashMap<Class<?>, SoftReference<Field[]>>();
    
    @Override
    public Field[] getCacheFields(Class<?> clazz)
    {
        SoftReference<Field[]> ref = fieldsMap.get(clazz);
        if (ref == null)
        {
            return null;
        }
        return ref.get();
    }

    @Override
    public void putCacheFields(Class<?> clazz, Field[] fields)
    {
        SoftReference<Field[]> ref = new SoftReference<Field[]>(fields);
        fieldsMap.put(clazz, ref);
    }

}
