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
import darks.codec.basetype.BaseType.BaseTypeBox;
import darks.codec.basetype.impl.ByteType;
import darks.codec.basetype.impl.DoubleType;
import darks.codec.basetype.impl.FloatType;
import darks.codec.basetype.impl.IntegerType;
import darks.codec.basetype.impl.LongType;
import darks.codec.basetype.impl.ShortType;
import darks.codec.basetype.impl.StringType;
import darks.codec.basetype.impl.array.ByteArrayType;
import darks.codec.basetype.impl.array.DoubleArrayType;
import darks.codec.basetype.impl.array.FloatArrayType;
import darks.codec.basetype.impl.array.IntegerArrayType;
import darks.codec.basetype.impl.array.LongArrayType;
import darks.codec.basetype.impl.array.ObjectArrayType;
import darks.codec.basetype.impl.array.ShortArrayType;
import darks.codec.basetype.impl.array.StringArrayType;

/**
 * Mapping base type with base type coder.
 * 
 * BaseTypeFactory.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public final class BaseTypeFactory
{

    private static ConcurrentHashMap<Class<?>, BaseType> codecs = new ConcurrentHashMap<Class<?>, BaseType>(60);

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
        
        registerCodec(int[].class, new IntegerArrayType(BaseTypeBox.NONE));
        registerCodec(Integer[].class, new IntegerArrayType(BaseTypeBox.BOX));
        registerCodec(short[].class, new ShortArrayType(BaseTypeBox.NONE));
        registerCodec(Short[].class, new ShortArrayType(BaseTypeBox.BOX));
        registerCodec(byte[].class, new ByteArrayType(BaseTypeBox.NONE));
        registerCodec(Byte[].class, new ByteArrayType(BaseTypeBox.BOX));
        registerCodec(float[].class, new FloatArrayType(BaseTypeBox.NONE));
        registerCodec(Float[].class, new FloatArrayType(BaseTypeBox.BOX));
        registerCodec(long[].class, new LongArrayType(BaseTypeBox.NONE));
        registerCodec(Long[].class, new LongArrayType(BaseTypeBox.BOX));
        registerCodec(double[].class, new DoubleArrayType(BaseTypeBox.NONE));
        registerCodec(Double[].class, new DoubleArrayType(BaseTypeBox.BOX));
        registerCodec(String[].class, new StringArrayType());
        registerCodec(Object[].class, new ObjectArrayType());
    }

    private BaseTypeFactory()
    {

    }

    /**
     * Register base type codec.
     * 
     * @param clazz Base type class.
     * @param codec Base type coder.
     */
    public static void registerCodec(Class<?> clazz, BaseType codec)
    {
        codecs.put(clazz, codec);
    }

    /**
     * Get base type coder by class.
     * 
     * @param clazz Base type class object
     * @return Base type coder.
     */
    public static BaseType getCodec(Class<?> clazz)
    {
        return codecs.get(clazz);
    }

    /**
     * Get base type coder by object and parameters.
     * 
     * @param obj Target object
     * @param param Codec parameter.
     * @return If exists, return base type coder object.
     */
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
        BaseType baseType = codecs.get(clazz);
        if (baseType == null && clazz.isArray())
        {
        	baseType = codecs.get(Object[].class); 
        }
        return baseType;
    }
}
