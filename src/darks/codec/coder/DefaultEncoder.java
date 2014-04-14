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
import darks.codec.Encoder;
import darks.codec.basetype.BaseType;
import darks.codec.basetype.BaseTypeFactory;
import darks.codec.exceptions.OCException;
import darks.codec.helper.ReflectHelper;
import darks.codec.helper.StringHelper;
import darks.codec.iostream.BytesOutputStream;
import darks.codec.logs.Logger;
import darks.codec.type.OCType;
import darks.codec.type.OCObject;

/**
 * Default encoder in {@linkplain darks.codec.coder.DefaultCodec DefaultCodec}.
 * 
 * DefaultEncoder.java
 * 
 * @see Encoder
 * @version 1.0.0
 * @author Liu lihua
 */
public class DefaultEncoder extends Encoder
{

    private static Logger log = Logger.getLogger(DefaultEncoder.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void encodeObject(BytesOutputStream out, Object obj,
            CodecParameter param) throws IOException
    {
        BaseType baseType = BaseTypeFactory.getCodec(obj, param);
        if (baseType != null)
        {
            if (log.isDebugEnabled())
            {
                log.debug(StringHelper.buffer("Encode base type:[",
                        ReflectHelper.getClass(obj, param), "] ", obj));
            }
            baseType.encode(out, obj, param);
        }
        else if (ReflectHelper.isDefaultType(obj))
        {
            encodeDefault(out, (OCType) obj, param);
        }
        else
        {
            encodeOther(out, obj, param);
        }
    }

    /**
     * Encoding default type which inherit {@inheritDoc darks.codec.type.OCType
     * OCType}.
     * 
     * @param out Encoding IO stream.
     * @param type Default type object.
     * @param param Codec parameter.
     */
    private void encodeDefault(BytesOutputStream out, OCType type,
            CodecParameter param)
    {
        try
        {
            if (log.isDebugEnabled())
            {
                log.debug(StringHelper.buffer("Encode default object:[",
                        ReflectHelper.getClass(type, param), "] ", type));
            }
            if (type == null && param.getCurrentfield() != null)
            {
                type = (OCType) ReflectHelper.newInstance(param
                        .getCurrentfield().getType());
            }
            if (type != null)
            {
                type.writeObject(this, out, param);
            }
        }
        catch (IOException e)
        {
            throw new OCException("Fail to encode default object.", e);
        }
    }

    /**
     * Encoding java object.
     * 
     * @param out Encoding IO stream.
     * @param object Java object.
     * @param param Codec parameter
     * @throws IOException IO exception
     */
    private void encodeOther(BytesOutputStream out, Object object,
            CodecParameter param) throws IOException
    {
        if (log.isDebugEnabled())
        {
            log.debug(StringHelper.buffer("Encode other object:[",
                    ReflectHelper.getClass(object, param), "] ", object));
        }
        new OCObject(object).writeObject(this, out, param);
    }

}
