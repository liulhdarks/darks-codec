package darks.codec.iostream;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.IOException;

import darks.codec.CodecConfig;
import darks.codec.CodecConfig.EndianType;
import darks.codec.exceptions.OCException;

public class BytesOutputStream extends ByteArrayOutputStream implements
        DataOutput
{
    private static final int DEFAULT_OFFSET = 0;

    private static final int FIRST_POS = 0;

    private static final int LAST_POS_INVALID = -1;
    
    protected boolean isLittleEndian;

    private byte longBuffer[] = new byte[8];

    private byte[] byteArray = null;

    private int lastCount;

    private int offset;

    public BytesOutputStream(CodecConfig codecConfig)
    {
        lastCount = LAST_POS_INVALID;
        offset = DEFAULT_OFFSET;
        isLittleEndian = codecConfig.getEndianType() == EndianType.LITTLE;
    }

    public BytesOutputStream(int size, CodecConfig codecConfig)
    {
        super(size);
        lastCount = LAST_POS_INVALID;
        offset = DEFAULT_OFFSET;
        isLittleEndian = codecConfig.getEndianType() == EndianType.LITTLE;
    }

    public void setCursor(int pos)
    {
        setCursor(pos, DEFAULT_OFFSET);
    }

    public void setCursor(int pos, int offset)
    {
        lastCount = count;
        count = pos;
        this.offset = offset;
    }

    public void moveFirst()
    {
        setCursor(FIRST_POS);
    }

    public int moveLast()
    {
        if (lastCount == LAST_POS_INVALID)
        {
            return size();
        }
        setCursor(lastCount);
        lastCount = LAST_POS_INVALID;
        offset = DEFAULT_OFFSET;
        return size();
    }

    public void replace(int pos, byte[] bytes)
    {
        if (pos + bytes.length > buf.length)
        {
            throw new ArrayIndexOutOfBoundsException(buf.length);
        }
        System.arraycopy(bytes, 0, buf, pos, bytes.length);
    }

    @Override
    public void writeBoolean(boolean v) throws IOException
    {
        write(v ? 1 : 0);
    }

    @Override
    public void writeByte(int v) throws IOException
    {
        write(v);
    }

    @Override
    public void writeShort(int v) throws IOException
    {
        if (isLittleEndian)
        {
            write((v >>> 0) & 0xFF);
            write((v >>> 8) & 0xFF);
        }
        else
        {
            write((v >>> 8) & 0xFF);
            write((v >>> 0) & 0xFF);
        }
    }

    @Override
    public void writeChar(int v) throws IOException
    {
        if (isLittleEndian)
        {
            write((v >>> 0) & 0xFF);
            write((v >>> 8) & 0xFF);
        }
        else
        {
            write((v >>> 8) & 0xFF);
            write((v >>> 0) & 0xFF);
        }
    }

    @Override
    public void writeInt(int v) throws IOException
    {
        if (isLittleEndian)
        {
            write((v >>> 0) & 0xFF);
            write((v >>> 8) & 0xFF);
            write((v >>> 16) & 0xFF);
            write((v >>> 24) & 0xFF);
        }
        else
        {
            write((v >>> 24) & 0xFF);
            write((v >>> 16) & 0xFF);
            write((v >>> 8) & 0xFF);
            write((v >>> 0) & 0xFF);
        }
    }

    @Override
    public void writeLong(long v) throws IOException
    {
        if (isLittleEndian)
        {
            longBuffer[0] = (byte) (v >>> 0);
            longBuffer[1] = (byte) (v >>> 8);
            longBuffer[2] = (byte) (v >>> 16);
            longBuffer[3] = (byte) (v >>> 24);
            longBuffer[4] = (byte) (v >>> 32);
            longBuffer[5] = (byte) (v >>> 40);
            longBuffer[6] = (byte) (v >>> 48);
            longBuffer[7] = (byte) (v >>> 56);
        }
        else
        {
            longBuffer[0] = (byte) (v >>> 56);
            longBuffer[1] = (byte) (v >>> 48);
            longBuffer[2] = (byte) (v >>> 40);
            longBuffer[3] = (byte) (v >>> 32);
            longBuffer[4] = (byte) (v >>> 24);
            longBuffer[5] = (byte) (v >>> 16);
            longBuffer[6] = (byte) (v >>> 8);
            longBuffer[7] = (byte) (v >>> 0);
        }
        write(longBuffer, 0, 8);
    }

    @Override
    public void writeFloat(float v) throws IOException
    {
        writeInt(Float.floatToIntBits(v));
    }

    @Override
    public void writeDouble(double v) throws IOException
    {
        writeLong(Double.doubleToLongBits(v));
    }

    @Override
    public void writeBytes(String s) throws IOException
    {
        int len = s.length();
        for (int i = 0; i < len; i++)
        {
            write((byte) s.charAt(i));
        }
    }

    @Override
    public void writeChars(String s) throws IOException
    {
        int len = s.length();
        for (int i = 0; i < len; i++)
        {
            int v = s.charAt(i);
            write((v >>> 8) & 0xFF);
            write((v >>> 0) & 0xFF);
        }
    }

    @Override
    public void writeUTF(String str) throws IOException
    {
        int strlen = str.length();
        int utflen = 0;
        int c, count = 0;

        for (int i = 0; i < strlen; i++)
        {
            c = str.charAt(i);
            if ((c >= 0x0001) && (c <= 0x007F))
            {
                utflen++;
            }
            else if (c > 0x07FF)
            {
                utflen += 3;
            }
            else
            {
                utflen += 2;
            }
        }

        if (utflen > 65535)
        {
            throw new OCException("encoded string too long: " + utflen
                    + " bytes");
        }

        if (byteArray == null || (byteArray.length < (utflen + 2)))
        {
            byteArray = new byte[(utflen * 2) + 2];
        }

        byteArray[count++] = (byte) ((utflen >>> 8) & 0xFF);
        byteArray[count++] = (byte) ((utflen >>> 0) & 0xFF);

        int i = 0;
        for (i = 0; i < strlen; i++)
        {
            c = str.charAt(i);
            if (!((c >= 0x0001) && (c <= 0x007F)))
                break;
            byteArray[count++] = (byte) c;
        }

        for (; i < strlen; i++)
        {
            c = str.charAt(i);
            if ((c >= 0x0001) && (c <= 0x007F))
            {
                byteArray[count++] = (byte) c;

            }
            else if (c > 0x07FF)
            {
                byteArray[count++] = (byte) (0xE0 | ((c >> 12) & 0x0F));
                byteArray[count++] = (byte) (0x80 | ((c >> 6) & 0x3F));
                byteArray[count++] = (byte) (0x80 | ((c >> 0) & 0x3F));
            }
            else
            {
                byteArray[count++] = (byte) (0xC0 | ((c >> 6) & 0x1F));
                byteArray[count++] = (byte) (0x80 | ((c >> 0) & 0x3F));
            }
        }
        write(byteArray, 0, utflen + 2);
    }

    public int getLastCount()
    {
        return lastCount;
    }

    public void setLastCount(int lastCount)
    {
        this.lastCount = lastCount;
    }

    public int getOffset()
    {
        return offset;
    }

    public void setOffset(int offset)
    {
        this.offset = offset;
    }

}
