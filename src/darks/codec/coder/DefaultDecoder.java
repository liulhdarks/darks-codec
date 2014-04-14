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

package darks.codec.coder;

import java.io.IOException;

import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.basetype.BaseType;
import darks.codec.basetype.BaseTypeFactory;
import darks.codec.exceptions.OCException;
import darks.codec.helper.ReflectHelper;
import darks.codec.helper.StringHelper;
import darks.codec.iostream.BytesInputStream;
import darks.codec.logs.Logger;
import darks.codec.type.OCType;
import darks.codec.type.OCObject;

/**
 * Default decoder in {@linkplain darks.codec.coder.DefaultCodec DefaultCodec}.
 * 
 * DefaultDecoder.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class DefaultDecoder extends Decoder
{

    private static Logger log = Logger.getLogger(DefaultDecoder.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Object decodeObject(BytesInputStream in, Object obj,
            CodecParameter param) throws IOException
    {
        BaseType baseType = BaseTypeFactory.getCodec(obj, param);
        if (baseType != null)
        {
            if (log.isDebugEnabled())
            {
                log.debug(StringHelper.buffer("Decode base type:[",
                        ReflectHelper.getClass(obj, param), "] ", obj));
            }
            return baseType.decode(in, obj, param);
        }
        else if (ReflectHelper.isDefaultType(obj))
        {
            decodeDefault(in, (OCType) obj, param);
        }
        else
        {
            decodeOther(in, obj, param);
        }
        return null;
    }
    
    /**
     * Decoding default type which inherit {@inheritDoc darks.codec.type.OCType
     * OCType}.
     * 
     * @param in Decoding IO stream.
     * @param type Default type object.
     * @param param Codec parameter.
     */
    private void decodeDefault(BytesInputStream in, OCType type,
            CodecParameter param) throws IOException
    {
        try
        {
            if (log.isDebugEnabled())
            {
                log.debug(StringHelper.buffer("Decode default:[",
                        ReflectHelper.getClass(type, param), "] ", type));
            }
            if (type == null && param.getCurrentfield() != null)
            {
                type = (OCType) ReflectHelper.newInstance(param
                        .getCurrentfield().getType());
            }
            if (type != null)
            {
                type.readObject(this, in, param);
            }
        }
        catch (IOException e)
        {
            throw new OCException("Fail to decode default object.", e);
        }
    }

    /**
     * Decoding java object.
     * 
     * @param out Decoding IO stream.
     * @param object Java object.
     * @param param Codec parameter
     * @throws IOException IO exception
     */
    private void decodeOther(BytesInputStream in, Object object,
            CodecParameter param) throws IOException
    {
        if (log.isDebugEnabled())
        {
            log.debug(StringHelper.buffer("Decode default:[",
                    ReflectHelper.getClass(object, param), "] ", object));
        }
        new OCObject(object).readObject(this, in, param);
    }
}
