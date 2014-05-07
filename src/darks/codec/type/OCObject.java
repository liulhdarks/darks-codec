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

import java.lang.reflect.Field;

import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.Encoder;
import darks.codec.annotations.CodecType;
import darks.codec.coder.cache.Cache;
import darks.codec.exceptions.DecodingException;
import darks.codec.helper.ReflectHelper;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;

/**
 * Indicate that coding java object
 * 
 * OCObject.java
 * @version 1.0.1
 * @author Liu lihua
 */
@CodecType
public class OCObject extends OCBase
{

    private String[] fieldSequence;
    
    private Object object;
    
    private OCClass objClass;

    public OCObject()
    {
        object = this;
    }
    
    /**
     * Coding java object.
     * @param object Java object
     */
    public OCObject(Object object)
    {
        this.object = object;
    }

    public OCObject(OCInteger lenType)
    {
        super(lenType);
        object = this;
    }

    public OCObject(Object object, OCInteger lenType)
    {
        super(lenType);
        this.object = object;
    }
    
    public OCObject(OCClass objClass)
    {
        this.objClass = objClass;
    }
    
    public OCObject(OCClass objClass, OCInteger lenType)
    {
        super(lenType);
        this.objClass = objClass;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeObject(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws Exception
    {
        if (!param.isIgnoreObjectAutoLength())
        {
            writeAutoLength(encoder, out, param);
        }
        int start = out.size();
        Field[] fields = getFields(object, param);
        for (Field field : fields)
        {
            param.setCurrentfield(field);
            Object val = ReflectHelper.getFieldValue(object, field);
            encoder.encodeObject(out, val, param);
        }
        int end = out.size();
        writeDynamicLength(end - start, encoder, out, param);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void readObject(Decoder decoder, BytesInputStream in,
            CodecParameter param) throws Exception
    {
        checkObject();
        if (!param.isIgnoreObjectAutoLength())
        {
            readAutoLength(decoder, in, param);
        }
        if (getLenType() != null && getLenType().getValue() <= 0)
        {
            return;
        }
        Field[] fields = getFields(object, param);
        for (Field field : fields)
        {
            param.setCurrentfield(field);
            Object val = ReflectHelper.getFieldValue(object, field);
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

    private void checkObject()
    {
        if (objClass != null && object == null)
        {
            Class<?> clazz = objClass.getValue();
            if (clazz != null)
            {
                object = ReflectHelper.newInstance(clazz);
            }
            if (object == null)
            {
                throw new DecodingException("Fail to decode null object.");
            }
        }
    }
    
    public String[] getFieldSequence()
    {
        return fieldSequence;
    }

    public void setFieldSequence(String[] fieldSequence)
    {
        this.fieldSequence = fieldSequence;
    }

    public Object getObject()
    {
        return object;
    }

    public void setObject(Object object)
    {
        this.object = object;
    }

    
}
