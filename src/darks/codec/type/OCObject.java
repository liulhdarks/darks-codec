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

package darks.codec.type;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.Encoder;
import darks.codec.coder.cache.Cache;
import darks.codec.exceptions.OCException;
import darks.codec.helper.ReflectHelper;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;

public class OCObject extends OCBase
{

    /**
     */
    private static final long serialVersionUID = -8825839864474490788L;

    private String[] fieldSequence;
    
    private Object object;

    public OCObject()
    {
        object = this;
    }
    
    public OCObject(Object externObject)
    {
        this.object = externObject;
    }

    public OCObject(OCInteger lenType)
    {
        super(lenType);
        object = this;
    }

    public OCObject(Object externObject, OCInteger lenType)
    {
        super(lenType);
        this.object = externObject;
    }

    @Override
    public void writeObject(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws IOException
    {
        if (!param.isIgnoreObjectAutoLength())
        {
            writeAutoLength(encoder, out, param);
        }
        int start = out.size();
        Field[] fields = getFields(object, param);
        for (Field field : fields)
        {
            Object val = ReflectHelper.getFieldValue(object, field);
            encoder.encodeObject(out, val, param);
        }
        int end = out.size();
        writeDynamicLength(end - start, encoder, out, param);
    }

    @Override
    public void readObject(Decoder decoder, BytesInputStream in,
            CodecParameter param) throws IOException
    {
        if (!param.isIgnoreObjectAutoLength())
        {
            readAutoLength(decoder, in, param);
        }
        Field[] fields = getFields(object, param);
        for (Field field : fields)
        {
            Object val = ReflectHelper.getFieldValue(object, field);
            if (val instanceof OCList<?> || val instanceof OCMap<?, ?>)
            {
                Type[] types = ReflectHelper.getGenericTypes(field);
                if (types == null)
                {
                    throw new OCException("Generic type is null");
                }
                param.setGenericType(types);
            }
            val = decoder.decodeObject(in, val, param);
            if (val != null)
            {
                ReflectHelper.setFieldValue(object, field, val);
            }
        }
    }
    
    private Field[] getFields(Object obj, CodecParameter codecParam)
    {
        Field[] result = null;
        Cache cache = codecParam.getCache();
        if (cache != null)
        {
            result = cache.getCacheFields(obj.getClass());
            if (result == null)
            {
                result = ReflectHelper.getValidField(object, codecParam);
                cache.putCacheFields(obj.getClass(), result);
            }
        }
        else
        {
            result = ReflectHelper.getValidField(object, codecParam);
        }
        return result;
    }

    public String[] getFieldSequence()
    {
        return fieldSequence;
    }

    public void setFieldSequence(String[] fieldSequence)
    {
        this.fieldSequence = fieldSequence;
    }

}
