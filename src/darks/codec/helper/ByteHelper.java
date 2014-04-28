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

package darks.codec.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import darks.codec.exceptions.OCException;

/**
 * ByteHelper.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public final class ByteHelper
{

    private static final char[] HEX_DIGIT = new char[] { '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    private ByteHelper()
    {

    }

    /**
     * Convert 32-bits integer value to bytes arrays.
     * 
     * @param v 32-bits integer value
     * @param isLE Whether endian is LITTLE-ENDIAN.
     * @return Bytes arrays.
     */
    public static byte[] convertInt32(int v, boolean isLE)
    {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) ((v >>> 24) & 0xFF);
        bytes[1] = (byte) ((v >>> 16) & 0xFF);
        bytes[2] = (byte) ((v >>> 8) & 0xFF);
        bytes[3] = (byte) ((v >>> 0) & 0xFF);
        if (isLE)
        {
            bytes = reverseBytes(bytes);
        }
        return bytes;
    }

    /**
     * Convert long value to bytes arrays.
     * 
     * @param v long value
     * @param isLE Whether endian is LITTLE-ENDIAN.
     * @return Bytes arrays.
     */
    public static byte[] convertLong(long v, boolean isLE)
    {
        byte[] bytes = new byte[8];
        if (isLE)
        {
            bytes[0] = (byte) (v >>> 0);
            bytes[1] = (byte) (v >>> 8);
            bytes[2] = (byte) (v >>> 16);
            bytes[3] = (byte) (v >>> 24);
            bytes[4] = (byte) (v >>> 32);
            bytes[5] = (byte) (v >>> 40);
            bytes[6] = (byte) (v >>> 48);
            bytes[7] = (byte) (v >>> 56);
        }
        else
        {
            bytes[0] = (byte) (v >>> 56);
            bytes[1] = (byte) (v >>> 48);
            bytes[2] = (byte) (v >>> 40);
            bytes[3] = (byte) (v >>> 32);
            bytes[4] = (byte) (v >>> 24);
            bytes[5] = (byte) (v >>> 16);
            bytes[6] = (byte) (v >>> 8);
            bytes[7] = (byte) (v >>> 0);
        }
        return bytes;
    }

    /**
     * Convert bytes to 32-bits integer by BIG-ENDIAN
     * 
     * @param bytes Bytes array
     * @return 32-bits integer
     */
    public static int convertToInt32(byte[] bytes)
    {
        if (bytes.length >= 4)
        {
            return (((bytes[0] & 0xff) << 24) + ((bytes[1] & 0xff) << 16)
                    + ((bytes[2] & 0xff) << 8) + ((bytes[3] & 0xff) << 0));
        }
        return 0;
    }

    /**
     * Convert bytes to long
     * 
     * @param bytes Bytes array
     * @return long value
     */
    public static long convertToLong(byte[] longBuffer)
    {
        if (longBuffer.length >= 8)
        {
            return (((long) longBuffer[0] << 56)
                    + ((long) (longBuffer[1] & 255) << 48)
                    + ((long) (longBuffer[2] & 255) << 40)
                    + ((long) (longBuffer[3] & 255) << 32)
                    + ((long) (longBuffer[4] & 255) << 24)
                    + ((longBuffer[5] & 255) << 16)
                    + ((longBuffer[6] & 255) << 8) + ((longBuffer[7] & 255) << 0));
        }
        return 0;
    }

    /**
     * Convert bytes to 32-bits integer
     * 
     * @param bytes Bytes array
     * @param isLE Whether bytes are LITTLE-ENDIAN
     * @return 32-bits integer
     */
    public static int convertToInt32(byte[] bytes, boolean isLE)
    {
        if (bytes.length >= 4)
        {
            if (isLE)
            {
                return (((bytes[3] & 0xff) << 24) + ((bytes[2] & 0xff) << 16)
                        + ((bytes[1] & 0xff) << 8) + ((bytes[0] & 0xff) << 0));
            }
            else
            {
                return (((bytes[0] & 0xff) << 24) + ((bytes[1] & 0xff) << 16)
                        + ((bytes[2] & 0xff) << 8) + ((bytes[3] & 0xff) << 0));
            }
        }
        return 0;
    }

    /**
     * Convert 16-bits integer to bytes array
     * 
     * @param 16-bits integer
     * @param isLE Whether bytes are LITTLE-ENDIAN
     * @return bytes Bytes array
     */
    public static byte[] convertInt16(int v, boolean isLE)
    {
        return convertInt16((short) v, isLE);
    }

    /**
     * Convert 16-bits integer value to bytes arrays.
     * 
     * @param v 16-bits integer value
     * @param isLE Whether endian is LITTLE-ENDIAN.
     * @return Bytes arrays.
     */
    public static byte[] convertInt16(short v, boolean isLE)
    {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) ((v >>> 8) & 0xFF);
        bytes[1] = (byte) ((v >>> 0) & 0xFF);
        if (isLE)
        {
            bytes = reverseBytes(bytes);
        }
        return bytes;
    }

    /**
     * Convert bytes to 16-bits integer by BIG-ENDIAN
     * 
     * @param bytes Bytes array
     * @return 16-bits integer
     */
    public static int convertToInt16(byte[] bytes)
    {
        if (bytes.length >= 2)
        {
            return ((bytes[0] & 0xff) << 8) + ((bytes[1] & 0xff) << 0);
        }
        return 0;
    }

    /**
     * Convert bytes to 16-bits integer
     * 
     * @param bytes Bytes array
     * @param isLE Whether bytes are LITTLE-ENDIAN
     * @return 16-bits integer
     */
    public static int convertToInt16(byte[] bytes, boolean isLE)
    {
        if (bytes.length >= 2)
        {
            if (isLE)
            {
                return ((bytes[1] & 0xff) << 8) + ((bytes[0] & 0xff) << 0);
            }
            else
            {
                return ((bytes[0] & 0xff) << 8) + ((bytes[1] & 0xff) << 0);
            }
        }
        return 0;
    }

    /**
     * Convert 8-bits integer value to bytes arrays.
     * 
     * @param v 8-bits integer value
     * @return Bytes arrays.
     */
    public static byte[] convertInt8(int v)
    {
        byte[] bytes = new byte[1];
        bytes[0] = (byte) v;
        return bytes;
    }

    public static int convertToInt8(byte[] bytes)
    {
        if (bytes.length > 0)
        {
            return (bytes[0] & 0xff);
        }
        return 0;
    }

    public static byte[] convertString(String s, String encoding)
    {
        if (s == null)
        {
            return new byte[0];
        }
        try
        {
            if (encoding == null || "".equals(encoding))
            {
                return s.getBytes();
            }
            return s.getBytes(encoding);
        }
        catch (UnsupportedEncodingException e)
        {
            throw new OCException("Not support encoding " + encoding, e);
        }
    }

    public static String convertToString(byte[] bytes, String encoding)
    {
        try
        {
            if (encoding == null || "".equals(encoding))
            {
                return new String(bytes);
            }
            return new String(bytes, encoding);
        }
        catch (UnsupportedEncodingException e)
        {
            throw new OCException("Not support encoding " + encoding, e);
        }
    }

    public static byte[] reverseBytes(byte[] bytes)
    {
        int size = bytes.length;
        int len = size / 2;
        int max = size - 1;
        for (int i = 0; i < len; i++)
        {
            bytes[i] ^= bytes[max - i];
            bytes[max - i] ^= bytes[i];
            bytes[i] ^= bytes[max - i];
        }
        return bytes;
    }

    /**
     * Read bytes from input stream.
     * 
     * @param in Input stream.
     * @param len Bytes length.
     * @param isLE Whether it's little-endian
     * @return Bytes arrays.
     * @throws IOException
     */
    public static byte[] readBytes(InputStream in, int len, boolean isLE)
            throws IOException
    {
        if (len == 0)
        {
            return new byte[0];
        }
        byte[] bytes = new byte[len];
        int rlen = in.read(bytes);
        if (rlen != len)
        {
            throw new IOException("readBytes length is not match." + len
                    + " which is " + rlen);
        }
        if (isLE)
        {
            bytes = reverseBytes(bytes);
        }
        return bytes;
    }
    
    public static byte[] objectToBytes(Serializable s) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        try
        {
            oos.writeObject(s);
            oos.flush();
            return baos.toByteArray();
        }
        finally
        {
            oos.close();
        }
        
    }
    
    public static Object bytesToObject(byte[] bytes) throws IOException, ClassNotFoundException
    {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        try
        {
            return ois.readObject();
        }
        finally
        {
            ois.close();
        }
    }

    public static String toHexString(byte[] coded)
    {
        if (coded == null)
        {
            return "";
        }
        return toHexString(coded, 0, coded.length);
    }

    public static String toHexString(byte[] coded, int offset, int length)
    {
        if (coded == null)
        {
            return "";
        }
        StringBuilder result = new StringBuilder(length * 3);
        for (int i = 0; i < length; i++)
        {
            int c = coded[i + offset];
            if (c < 0)
            {
                c += 256;
            }
            int hex1 = c & 0xF;
            int hex2 = c >> 4;
            result.append(HEX_DIGIT[hex2]);
            result.append(HEX_DIGIT[hex1]);
            result.append(' ');
        }
        return result.toString();
    }
}
