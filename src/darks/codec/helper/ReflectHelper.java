/**
 * 
 *Copyright 2014 The Darks Codec Project (Liu lihua)
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */

package darks.codec.helper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import darks.codec.CodecParameter;
import darks.codec.annotations.CodecType;
import darks.codec.exceptions.ReflectException;
import darks.codec.logs.Logger;
import darks.codec.type.OCType;

/**
 * 
 * ReflectHelper.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public final class ReflectHelper
{

    private static final Logger log = Logger.getLogger(ReflectHelper.class);
    
    private static Map<Class<?>, Constructor<?>> constructorMap = new ConcurrentHashMap<Class<?>, Constructor<?>>();

    private ReflectHelper()
    {

    }

    public static <E> E newInstance(Class<E> clazz)
    {
        try
        {
            @SuppressWarnings("unchecked")
            Constructor<E> ctr = (Constructor<E>)constructorMap.get(clazz);
            if (ctr == null)
            {
                ctr = clazz.getDeclaredConstructor();
                constructorMap.put(clazz, ctr);
                ctr.setAccessible(true);
            }
            return ctr.newInstance();
        }
        catch (Exception e)
        {
            try
            {
                return clazz.newInstance();
            }
            catch (Exception ex)
            {
                log.error("Fail to instance JAVA bean object.", ex);
            }
        }
        return null;
    }

    public static boolean isDefaultType(Class<?> clazz)
    {
        if (clazz == null || Object.class.equals(clazz))
        {
            return false;
        }
        Class<?>[] clazzs = clazz.getInterfaces();
        for (Class<?> cls : clazzs)
        {
            if (OCType.class.equals(cls))
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
        if (type instanceof OCType)
        {
            return true;
        }
        return false;
    }

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

    /**
     * Get valid object fields.
     * 
     * @param obj Target object.
     * @param codecParam Codec parameter.
     * @return If field 'fieldSequence' exists, return the fields specify by
     *         'fieldSequence'. Otherwise return object fields deeply without
     *         the class flaged by CodecType.
     */
    public static Field[] getValidField(Object obj, CodecParameter codecParam)
    {
        Class<?> clazz = obj == null ? null : obj.getClass();
        if (clazz == null || Object.class.equals(clazz))
        {
            return new Field[0];
        }
        Field seq = getDeepField(clazz, "fieldSequence");
        if (seq != null)
        {
            Field[] fields = getFieldSequence(seq, obj);
            if (fields != null)
            {
                return fields;
            }
        }
        List<Field> result = new LinkedList<Field>();
        getClassField(obj.getClass(), result, codecParam);
        Field[] fields = new Field[result.size()];
        return result.toArray(fields);
    }

    public static void getClassField(Class<?> clazz, List<Field> result,
            CodecParameter codecParam)
    {
        if (clazz == null || Object.class.equals(clazz)
                || clazz.getAnnotation(CodecType.class) != null)
        {
            return;
        }
        getClassField(clazz.getSuperclass(), result, codecParam);
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields)
        {
            if (Modifier.isStatic(field.getModifiers()))
            {
                if (codecParam.getCodecConfig().isIgnoreStaticField())
                {
                    continue;
                }
                if (Modifier.isFinal(field.getModifiers())
                        && codecParam.getCodecConfig().isIgnoreConstField())
                {
                    continue;
                }
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
            ParameterizedType tc = (ParameterizedType) type;
            return tc.getActualTypeArguments();
        }
        return null;
    }

    private static Field[] getFieldSequence(Field seq, Object obj)
    {
        try
        {
            seq.setAccessible(true);
            Object val = seq.get(obj);
            if (val != null)
            {
                List<Field> result = new LinkedList<Field>();
                String[] params = (String[]) val;
                for (String param : params)
                {
                    Field f = getDeepField(obj.getClass(), param);
                    result.add(f);
                }
                Field[] fields = new Field[result.size()];
                return result.toArray(fields);
            }
            return null;
        }
        catch (Exception e)
        {
            throw new ReflectException(
                    "Fail to get fields through sequence. Cause "
                            + e.getMessage(), e);
        }
    }

    public static Class<?> getClass(Object obj, CodecParameter param)
    {
        if (obj != null)
        {
            return obj.getClass();
        }
        if (param.getCurrentfield() != null)
        {
            return param.getCurrentfield().getType();
        }
        return null;
    }
}
