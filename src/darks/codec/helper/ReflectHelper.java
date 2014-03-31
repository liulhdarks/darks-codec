package darks.codec.helper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import darks.codec.logs.Logger;
import darks.codec.type.IOCSerializable;

public final class ReflectHelper
{

    private static final String EXCEPT_PACK = "darks.codec.type.";

    private static final Logger log = Logger.getLogger(ReflectHelper.class);

    private ReflectHelper()
    {

    }

    /**
     * <实例化> <功能详细描述>
     * 
     * @param clazz
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static <E> E newInstance(Class<E> clazz)
    {
        try
        {
            Constructor<E> ctr = clazz.getConstructor();
            return ctr.newInstance();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <对象是否为默认类型> <功能详细描述>
     * 
     * @param clazz 对象类
     * @return true/false
     * @see [类、类#方法、类#成员]
     */
    public static boolean isDefaultType(Class<?> clazz)
    {
        if (clazz == null || Object.class.equals(clazz))
        {
            return false;
        }
        Class<?>[] clazzs = clazz.getInterfaces();
        for (Class<?> cls : clazzs)
        {
            if (IOCSerializable.class.equals(cls))
            {
                return true;
            }
        }
        return isDefaultType(clazz.getSuperclass());
    }

    public static boolean isDefaultType(Object type)
    {
        if (type == null)
        {
            return false;
        }
        if (type instanceof IOCSerializable)
        {
            return true;
        }
        return false;
    }

    /**
     * <一句话功能简述> <功能详细描述>
     * 
     * @param obj
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Field[] getDeclaredFields(Object obj)
    {
        Class<?> clazz = obj.getClass();
        return clazz.getDeclaredFields();
    }

    public static Field getDeepField(Class<?> clazz, String fieldName)
    {
        if (clazz.equals(Object.class))
        {
            return null;
        }
        boolean checkSuper = false;
        Field field = null;
        try
        {
            field = clazz.getDeclaredField(fieldName);
            if (field == null)
            {
                checkSuper = true;
            }
            else
            {
                return field;
            }
        }
        catch (Exception e)
        {
            checkSuper = true;
        }
        if (checkSuper)
        {
            return getDeepField(clazz.getSuperclass(), fieldName);
        }
        else
        {
            return null;
        }
    }

    public static Field[] getValidField(Object obj)
    {
        List<Field> result = new LinkedList<Field>();
        Field seq = getDeepField(obj.getClass(), "fieldSequence");
        if (seq != null)
        {
            try
            {
                seq.setAccessible(true);
                Object val = seq.get(obj);
                if (val != null)
                {
                    String[] params = (String[]) val;
                    for (String param : params)
                    {
                        Field f = getDeepField(obj.getClass(), param);
                        result.add(f);
                    }
                    Field[] fields = new Field[result.size()];
                    return result.toArray(fields);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        getValidField(obj.getClass(), result);
        Field[] fields = new Field[result.size()];
        return result.toArray(fields);
    }

    public static void getValidField(Class<?> clazz, List<Field> result)
    {
        if (clazz == null || Object.class.equals(clazz)
                || clazz.getName().indexOf(EXCEPT_PACK) >= 0)
        {
            return;
        }
        getValidField(clazz.getSuperclass(), result);
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields)
        {
            if (Modifier.isFinal(field.getModifiers()) 
                    && Modifier.isStatic(field.getModifiers()))
            {
                continue;
            }
            if ("fieldSequence".equals(field.getName()))
            {
                continue;
            }
            else
            {
                result.add(field);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(Object obj, Field field)
    {
        boolean acc = field.isAccessible();
        field.setAccessible(true);
        try
        {
            return (T) field.get(obj);
        }
        catch (Exception e)
        {
            log.error("Fail to get field value", e);
        }
        finally
        {
            field.setAccessible(acc);
        }
        return null;
    }
    
    public static void setFieldValue(Object obj, Field field, Object value)
    {
        boolean acc = field.isAccessible();
        field.setAccessible(true);
        try
        {
            field.set(obj, value);
        }
        catch (Exception e)
        {
            log.error("Fail to set field value", e);
        }
        finally
        {
            field.setAccessible(acc);
        }
    }
    
    public static Type[] getGenericTypes(Field field)
    {
        Type type = field.getGenericType();
        if (type == null)
        {
            return null;
        }
        if (type instanceof ParameterizedType)
        {
            ParameterizedType tc = (ParameterizedType)type;
            return tc.getActualTypeArguments();
        }
        return null;
    }
}
