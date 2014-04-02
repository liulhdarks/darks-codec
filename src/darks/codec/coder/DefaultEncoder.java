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
import darks.codec.type.IOCSerializable;
import darks.codec.type.OCObject;

public class DefaultEncoder extends Encoder
{
    
    private static Logger log = Logger.getLogger(DefaultEncoder.class);
    
    @Override
    public void encodeObject(BytesOutputStream out, Object obj,
            CodecParameter param) throws IOException
    {
        BaseType baseType = BaseTypeFactory.getCodec(obj.getClass());
        if (baseType != null)
        {
            if (log.isDebugEnabled())
            {
                log.debug(StringHelper.buffer("Encode base type:[", obj.getClass(), "] ", obj));
            }
            baseType.encode(out, obj, param);
        }
        else if (ReflectHelper.isDefaultType(obj))
        {
            encodeDefault(out, (IOCSerializable) obj, param);
        }
        else
        {
            encodeOther(out, obj, param);
        }
    }

    private void encodeDefault(BytesOutputStream out, IOCSerializable ocs,
            CodecParameter param)
    {
        try
        {
            if (log.isDebugEnabled())
            {
                log.debug(StringHelper.buffer("Encode default object:[", ocs.getClass(), "] ", ocs));
            }
            ocs.writeObject(this, out, param);
        }
        catch (IOException e)
        {
            throw new OCException("Fail to encode default object.", e);
        }
    }

    private void encodeOther(BytesOutputStream out, Object object,
            CodecParameter param) throws IOException
    {
        if (log.isDebugEnabled())
        {
            log.debug(StringHelper.buffer("Encode other object:[", object.getClass(), "] ", object));
        }
        new OCObject(object).writeObject(this, out, param);
    }

}
