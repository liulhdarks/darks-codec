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
public class OCInteger extends OCBaseType<Integer>
{
    
    public static final int BIT8_LEN = 1;
    
    public static final int BIT16_LEN = 2;
    
    public static final int BIT32_LEN = 4;
    
    public OCInteger()
    {
        
    }
    
    public OCInteger(int val)
    {
        super(val);
    }
    
    public OCInteger(int val, int len)
    {
        super(val, len);
    }
    
    public OCInteger(OCInteger lenType)
    {
        super(lenType);
    }
    
    @Override
    public void writeObject(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws IOException
    {
        byte[] bytes = getBytes(param.isLittleEndian());
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

        int len = getLength();
        switch (len)
        {
        case BIT8_LEN:
            setValue(ByteHelper.convertToInt8(bytes));
            break;
        case BIT16_LEN:
            setValue(ByteHelper.convertToInt16(bytes));
            break;
        case BIT32_LEN:
            setValue(ByteHelper.convertToInt32(bytes));
            break;
        }
    }
    
    public byte[] getBytes(boolean littleEndian)
    {
        byte[] bytes = null;
        int len = getLength();
        switch (len)
        {
        case BIT8_LEN:
            bytes = ByteHelper.convertInt8(getValue(0));
            break;
        case BIT16_LEN:
            bytes = ByteHelper.convertInt16(getValue(0), littleEndian);
            break;
        case BIT32_LEN:
            bytes = ByteHelper.convertInt32(getValue(0), littleEndian);
            break;
        }
        return bytes;
    }
    
}
