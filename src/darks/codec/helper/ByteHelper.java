package darks.codec.helper;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import darks.codec.exceptions.OCException;

/**
 * 
 * <�ֽڸ�����>
 * <������ϸ����>
 * 
 * @author  lWX148392
 * @version  [�汾��, 2013-4-10]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
 */
public final class ByteHelper
{
    
    private ByteHelper()
    {
        
    }
    
    /**
     * <ת������Ϊ32λ�ֽ�����>
     * <������ϸ����>
     * @param v ����
     * @param isLE �Ƿ�ת
     * @return �ֽ�����
     * @see [�ࡢ��#��������#��Ա]
     */
    public static byte[] convertInt32(int v, boolean isLE)
    {
        byte[] bytes = new byte[4];
        bytes[0] = (byte)((v >>> 24) & 0xFF);
        bytes[1] = (byte)((v >>> 16) & 0xFF);
        bytes[2] = (byte)((v >>> 8) & 0xFF);
        bytes[3] = (byte)((v >>> 0) & 0xFF);
        if (isLE)
        {
            bytes = reverseBytes(bytes);
        }
        return bytes;
    }
    
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
     * <ת������Ϊ16λ�ֽ�����>
     * <������ϸ����>
     * @param v ����
     * @param isLE �Ƿ�ת
     * @return �ֽ�����
     * @see [�ࡢ��#��������#��Ա]
     */
    public static byte[] convertInt16(int v, boolean isLE)
    {
        return convertInt16((short)v, isLE);
    }
    
    /**
     * <ת������Ϊ16λ�ֽ�����>
     * <������ϸ����>
     * @param v ����
     * @param isLE �Ƿ�ת
     * @return �ֽ�����
     * @see [�ࡢ��#��������#��Ա]
     */
    public static byte[] convertInt16(short v, boolean isLE)
    {
        byte[] bytes = new byte[2];
        bytes[0] = (byte)((v >>> 8) & 0xFF);
        bytes[1] = (byte)((v >>> 0) & 0xFF);
        if (isLE)
        {
            bytes = reverseBytes(bytes);
        }
        return bytes;
    }
    
    public static int convertToInt16(byte[] bytes)
    {
        if (bytes.length >= 2)
        {
            return ((bytes[0] & 0xff) << 8) + ((bytes[1] & 0xff) << 0);
        }
        return 0;
    }
    
    /**
     * <ת������Ϊ8λ�ֽ�����>
     * <������ϸ����>
     * @param v ����
     * @return �ֽ�����
     * @see [�ࡢ��#��������#��Ա]
     */
    public static byte[] convertInt8(int v)
    {
        byte[] bytes = new byte[1];
        bytes[0] = (byte)v;
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
    
    /**
     * <ת���ַ���Ϊ�ֽ�����>
     * <������ϸ����>
     * @param s �ַ���
     * @return �ֽ�����
     * @see [�ࡢ��#��������#��Ա]
     */
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
    
    /**
     * <��ת�ֽ�>
     * <������ϸ����>
     * @param bytes �ֽ�����
     * @return ����ֽ���
     * @see [�ࡢ��#��������#��Ա]
     */
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
     * <һ�仰���ܼ���>
     * <������ϸ����>
     * @param in
     * @param len
     * @return
     * @throws IOException
     * @see [�ࡢ��#��������#��Ա]
     */
    public static byte[] readBytes(InputStream in, int len, boolean isLE)
        throws IOException
    {
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
    
    public static String toHexString(byte[] coded)
    {
        if (coded == null)
        {
            return "";
        }
        StringBuilder result = new StringBuilder(64);
        String hexDigits = "0123456789ABCDEF";
        for (int i = 0; i < coded.length; i++)
        {
            int c = coded[i];
            if (c < 0)
            {
                c += 256;
            }
            int hex1 = c & 0xF;
            int hex2 = c >> 4;
            result.append(hexDigits.substring(hex2, hex2 + 1));
            result.append(hexDigits.substring(hex1, hex1 + 1) + " ");
        }
        return result.toString();
    }
}