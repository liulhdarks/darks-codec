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

package darks.codec.basetype;

import java.util.concurrent.ConcurrentHashMap;

import darks.codec.CodecParameter;
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
    
    public static BaseType getCodec(Object obj, CodecParameter param)
    {
        Class<?> clazz = null;
        if (obj != null)
        {
            clazz = obj.getClass();
        }
        else if (param.getCurrentfield() != null)
        {
            clazz = param.getCurrentfield().getType();
        }
        else
        {
            return null;
        }
        return codecs.get(clazz);
    }
}
