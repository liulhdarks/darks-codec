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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.Encoder;
import darks.codec.helper.ByteHelper;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;
import darks.codec.logs.Logger;

public class OCBytes extends OCType<byte[]>
{
    
    /**
     */
    private static final long serialVersionUID = -3809172632938626001L;
    
    private static Logger log = Logger.getLogger(OCBytes.class);
    
    public OCBytes()
    {
        
    }
    
    public OCBytes(OCInteger lenType)
    {
        super(lenType);
    }
    
    public OCBytes(byte[] bytes)
    {
        super(bytes);
    }
    
    public OCBytes(byte[] bytes, OCInteger lenType)
    {
        super(bytes, lenType);
    }
    
    public OCBytes(byte[] bytes, int len)
    {
        super(bytes, len);
    }
    
    public static OCBytes int8(int v)
    {
        return new OCBytes(ByteHelper.convertInt8(v));
    }
    
    public static OCBytes int16(int v, boolean littleEndian)
    {
        return new OCBytes(ByteHelper.convertInt16(v, littleEndian));
    }
    
    public static OCBytes int32(int v, boolean littleEndian)
    {
        return new OCBytes(ByteHelper.convertInt32(v, littleEndian));
    }
    
    public static OCBytes string(String s)
    {
        return new OCBytes(s.getBytes());
    }
    
    public static OCBytes string(String s, String encoding)
    {
        try
        {
            return new OCBytes(s.getBytes(encoding));
        }
        catch (UnsupportedEncodingException e)
        {
            log.error(e.getMessage(), e);
            return null;
        }
    }
    
    public String getString()
    {
        return new String(getValue());
    }
    
    public String getString(String encoding)
    {
        try
        {
            return new String(getValue(), encoding);
        }
        catch (UnsupportedEncodingException e)
        {
            log.error(e.getMessage(), e);
            return null;
        }
    }
    
    public int getInt8()
    {
        return ByteHelper.convertToInt8(getValue());
    }
    
    public int getInt16(boolean littleEndian)
    {
        return ByteHelper.convertToInt16(getValue(), littleEndian);
    }
    
    public int getInt32(boolean littleEndian)
    {
        return ByteHelper.convertToInt32(getValue(), littleEndian);
    }
    
    @Override
    public void writeObject(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws IOException
    {
        if (getValue() == null)
        {
            return;
        }
        writeAutoLength(encoder, out, param);
        byte[] bytes = getValue(new byte[0]);
        super.writeBytes(encoder, out, bytes, param);
    }
    
    @Override
    public void readObject(Decoder decoder, BytesInputStream in,
            CodecParameter param) throws IOException
    {
        readAutoLength(decoder, in, param);
        if (isDynamicLength())
        {
            int length = getLenType().getValue();
            setLength(length);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[length];
            if (in.available() > 0)
            {
                int len = in.read(buf);
                if (len == length)
                {
                    baos.write(buf, 0, len);
                }
                else
                {
                    throw new IOException(
                        "Read data length is not matched. Require:" + length
                            + " but:" + len);
                }
            }
            setValue(baos.toByteArray());
        }
        else
        {
            int len = in.available();
            setLength(len);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            while (in.available() > 0)
            {
                len = in.read(buf);
                if (len > 0)
                {
                    baos.write(buf, 0, len);
                }
            }
            setValue(baos.toByteArray());
        }
    }
    
    @Override
    public String toString()
    {
        return "OCBytes [getValue()=" + ByteHelper.toHexString(getValue())
            + ", getLength()=" + getLength() + "]";
    }
    
}
