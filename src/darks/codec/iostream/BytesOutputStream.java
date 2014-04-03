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

package darks.codec.iostream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import darks.codec.CodecConfig;
import darks.codec.CodecConfig.EndianType;

public class BytesOutputStream extends OutputStream
{
    private static final int DEFAULT_OFFSET = 0;

    private static final int FIRST_POS = 0;

    private static final int LAST_POS_INVALID = -1;

    protected boolean isLittleEndian;

    private byte longBuffer[] = new byte[8];

    private byte[] buffer = null;

    private int count = 0;

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
        buffer = new byte[size];
        count = 0;
        lastCount = LAST_POS_INVALID;
        offset = DEFAULT_OFFSET;
        isLittleEndian = codecConfig.getEndianType() == EndianType.LITTLE;
    }

    /**
     * Writes the specified byte to this byte array output stream.
     * 
     * @param b the byte to be written.
     */
    public void write(int b)
    {
        int newcount = count + 1;
        if (newcount > buffer.length)
        {
            buffer = Arrays.copyOf(buffer,
                    Math.max(buffer.length << 1, newcount));
        }
        buffer[count] = (byte) b;
        count = newcount;
    }

    /**
     * Writes <code>len</code> bytes from the specified byte array starting at
     * offset <code>off</code> to this byte array output stream.
     * 
     * @param b the data.
     * @param off the start offset in the data.
     * @param len the number of bytes to write.
     */
    public void write(byte b[], int off, int len)
    {
        if ((off < 0) || (off > b.length) || (len < 0)
                || ((off + len) > b.length) || ((off + len) < 0))
        {
            throw new IndexOutOfBoundsException();
        }
        else if (len == 0)
        {
            return;
        }
        int newcount = count + len;
        if (newcount > buffer.length)
        {
            buffer = Arrays.copyOf(buffer,
                    Math.max(buffer.length << 1, newcount));
        }
        System.arraycopy(b, off, buffer, count, len);
        count = newcount;
    }

    public byte[] toByteArray()
    {
        return Arrays.copyOf(buffer, count);
    }

    public int size()
    {
        return count;
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

    public byte[] getDirectBytes()
    {
        return buffer;
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
        if (pos + bytes.length > buffer.length)
        {
            throw new ArrayIndexOutOfBoundsException(buffer.length);
        }
        System.arraycopy(bytes, 0, buffer, pos, bytes.length);
    }

    public void writeBoolean(boolean v) throws IOException
    {
        write(v ? 1 : 0);
    }

    public void writeByte(int v) throws IOException
    {
        write(v);
    }

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

    public void writeFloat(float v) throws IOException
    {
        writeInt(Float.floatToIntBits(v));
    }

    public void writeDouble(double v) throws IOException
    {
        writeLong(Double.doubleToLongBits(v));
    }

    public void writeBytes(String s) throws IOException
    {
        int len = s.length();
        for (int i = 0; i < len; i++)
        {
            write((byte) s.charAt(i));
        }
    }

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
