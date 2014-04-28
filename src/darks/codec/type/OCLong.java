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
import darks.codec.exceptions.EncodingException;
import darks.codec.helper.ByteHelper;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;

/**
 * Kust like long type.
 * 
 * OCLong.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
@CodecType
public class OCLong extends OCBaseType<Long>
{

    public OCLong()
    {
        setLength(8);
    }

    public OCLong(long val)
    {
        super(val, 8);
    }

    public OCLong(OCInteger lenType)
    {
        super(lenType);
        setLength(8);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeObject(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws Exception
    {
        long v = getValue(0l);
        byte[] bytes = ByteHelper.convertLong(v, param.isLittleEndian());
        if (bytes == null)
        {
            throw new EncodingException("Fail to encode " + getClass());
        }
        super.writeBytes(encoder, out, bytes, param);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void readObject(Decoder decoder, BytesInputStream in,
            CodecParameter param) throws Exception
    {
        byte[] bytes = ByteHelper.readBytes(in, getLength(),
                param.isLittleEndian());
        long v = ByteHelper.convertToLong(bytes);
        setValue(v);
    }

}
