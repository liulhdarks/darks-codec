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

import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.Encoder;
import darks.codec.annotations.CodecType;
import darks.codec.exceptions.DecodingException;
import darks.codec.helper.ByteHelper;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;

/**
 * Wrap {@linkplain java.lang.Class Class}.
 * 
 * OCClass.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
@CodecType
public class OCClass extends OCBaseType<Class<?>>
{

    public OCClass()
    {
        super();
    }

    public OCClass(Class<?> clazz, OCInteger lenType)
    {
        super(clazz);
    }

    public OCClass(OCInteger lenType)
    {
        super(lenType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeObject(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws Exception
    {
        writeAutoLength(encoder, out, param);
        byte[] bytes = ByteHelper
                .convertString(getValue().getName(), param.getEncoding());
        super.writeBytes(encoder, out, bytes, param);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void readObject(Decoder decoder, BytesInputStream in,
            CodecParameter param) throws Exception
    {
        readAutoLength(decoder, in, param);
        byte[] bytes = null;
        if (!isDynamicLength())
        {
            int len = in.available();
            setLength(len);
            bytes = ByteHelper.readBytes(in, len, false);
        }
        else
        {
            int len = getLenType().getValue();
            setLength(len);
            bytes = ByteHelper.readBytes(in, len, false);
        }
        String classFullName = ByteHelper.convertToString(bytes, param.getEncoding());
        try
        {
            Class<?> clazz = Class.forName(classFullName);
            setValue(clazz);
        }
        catch (Exception e)
        {
            throw new DecodingException("Fail to decode OCClass object for " + classFullName, e);
        }
    }
}
