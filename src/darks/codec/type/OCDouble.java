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

import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.Encoder;
import darks.codec.annotations.CodecType;
import darks.codec.exceptions.EncodingException;
import darks.codec.helper.ByteHelper;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;

@CodecType
public class OCDouble extends OCBaseType<Double>
{
    
    public OCDouble()
    {
        setLength(8);
    }
    
    public OCDouble(double val)
    {
        super(val, 8);
    }
    
    public OCDouble(OCInteger lenType)
    {
        super(lenType);
        setLength(8);
    }
    
    @Override
    public void writeObject(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws IOException
    {
        double v = getValue(0.);
        byte[] bytes = ByteHelper.convertLong(Double.doubleToLongBits(v), param.isLittleEndian());
        if (bytes == null)
        {
            throw new EncodingException("Fail to encode " + getClass());
        }
        super.writeBytes(encoder, out, bytes, param);
    }
    
    @Override
    public void readObject(Decoder decoder, BytesInputStream in,
            CodecParameter param) throws IOException
    {
        byte[] bytes = ByteHelper.readBytes(in, getLength(), param.isLittleEndian());
        long v = ByteHelper.convertToLong(bytes);
        setValue(Double.longBitsToDouble(v));
    }
    
}
