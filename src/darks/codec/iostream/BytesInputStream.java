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

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import darks.codec.CodecConfig;
import darks.codec.CodecConfig.EndianType;
import darks.codec.helper.ByteHelper;
import darks.codec.helper.StringHelper;

/**
 * 
 * BytesInputStream.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class BytesInputStream extends InputStream
{

    protected boolean isLittleEndian;

    private byte longBuffer[] = new byte[8];

    private byte[] buffer;

    private int pos;

    private int count;

    private int offsetStart;

    private int offsetEnd;

    public BytesInputStream(byte[] buf, CodecConfig codecConfig)
    {
        this.buffer = buf;
        count = buf.length;
        isLittleEndian = codecConfig.getEndianType() == EndianType.LITTLE;
    }

    /**
     * Reads the next byte of data from this input stream. The value byte is
     * returned as an <code>int</code> in the range <code>0</code> to
     * <code>255</code>. If no byte is available because the end of the stream
     * has been reached, the value <code>-1</code> is returned.
     * <p>
     * This <code>read</code> method cannot block.
     * 
     * @return the next byte of data, or <code>-1</code> if the end of the
     *         stream has been reached.
     */
    public int read()
    {
        return (pos - offsetStart < count) ? (buffer[pos++] & 0xff) : -1;
    }

    public int read(byte b[], int off, int len)
    {
        if (b == null)
        {
            throw new NullPointerException();
        }
        else if (off < 0 || len < 0 || len > b.length - off)
        {
            throw new IndexOutOfBoundsException();
        }
        if (pos - offsetStart >= count)
        {
            return -1;
        }
        if (pos + len - offsetStart > count)
        {
            len = count - pos + offsetStart;
        }
        if (len <= 0)
        {
            return 0;
        }
        System.arraycopy(buffer, pos, b, off, len);
        pos += len;
        return len;
    }

    /**
     * Returns the number of remaining bytes that can be read (or skipped over)
     * from this input stream.
     * <p>
     * The value returned is
     * <code>count&nbsp;-&nbsp;pos&nbsp;+&nbsp;offsetStart</code>, which is the
     * number of bytes remaining to be read from the input buffer.
     * 
     * @return the number of remaining bytes that can be read (or skipped over)
     *         from this input stream without blocking.
     */
    public int available()
    {
        return count - pos + offsetStart;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public void setCursor(int pos)
    {
        this.pos = pos + offsetStart;
    }

    public byte[] getDirectBytes()
    {
        return buffer;
    }

    public void readFully(byte[] b) throws IOException
    {
        readFully(b, 0, b.length);
    }

    public void readFully(byte[] b, int off, int len) throws IOException
    {
        if (len < 0)
        {
            throw new IndexOutOfBoundsException();
        }
        int n = 0;
        while (n < len)
        {
            int count = read(b, off + n, len - n);
            if (count < 0)
                throw new EOFException();
            n += count;
        }
    }

    public int skipBytes(int n) throws IOException
    {
        int total = 0;
        int cur = 0;

        while ((total < n) && ((cur = (int) skip(n - total)) > 0))
        {
            total += cur;
        }

        return total;
    }

    public boolean readBoolean() throws IOException
    {
        int ch = read();
        if (ch < 0)
        {
            throw new EOFException();
        }
        return (ch != 0);
    }

    public byte readByte() throws IOException
    {
        int ch = read();
        if (ch < 0)
        {
            throw new EOFException();
        }
        return (byte) (ch);
    }

    public int readUnsignedByte() throws IOException
    {
        int ch = read();
        if (ch < 0)
        {
            throw new EOFException();
        }
        return ch;
    }

    public short readShort() throws IOException
    {
        int ch1 = read();
        int ch2 = read();
        if ((ch1 | ch2) < 0)
        {
            throw new EOFException();
        }
        if (isLittleEndian)
        {
            return (short) ((ch2 << 8) + (ch1 << 0));
        }
        else
        {
            return (short) ((ch1 << 8) + (ch2 << 0));
        }
    }

    public int readUnsignedShort() throws IOException
    {
        int ch1 = read();
        int ch2 = read();
        if ((ch1 | ch2) < 0)
        {
            throw new EOFException();
        }
        if (isLittleEndian)
        {
            return (ch2 << 8) + (ch1 << 0);
        }
        else
        {
            return (ch1 << 8) + (ch2 << 0);
        }
    }

    public char readChar() throws IOException
    {
        int ch1 = read();
        int ch2 = read();
        if ((ch1 | ch2) < 0)
        {
            throw new EOFException();
        }
        if (isLittleEndian)
        {
            return (char) ((ch2 << 8) + (ch1 << 0));
        }
        else
        {
            return (char) ((ch1 << 8) + (ch2 << 0));
        }
    }

    public int readInt() throws IOException
    {
        int ch1 = read();
        int ch2 = read();
        int ch3 = read();
        int ch4 = read();
        if ((ch1 | ch2 | ch3 | ch4) < 0)
        {
            throw new EOFException();
        }
        if (isLittleEndian)
        {
            return ((ch4 << 24) + (ch3 << 16) + (ch2 << 8) + (ch1 << 0));
        }
        else
        {
            return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
        }
    }

    public long readLong() throws IOException
    {
        readFully(longBuffer, 0, 8);
        if (isLittleEndian)
        {
            return (((long) longBuffer[7] << 56)
                    + ((long) (longBuffer[6] & 255) << 48)
                    + ((long) (longBuffer[5] & 255) << 40)
                    + ((long) (longBuffer[4] & 255) << 32)
                    + ((long) (longBuffer[3] & 255) << 24)
                    + ((longBuffer[2] & 255) << 16)
                    + ((longBuffer[1] & 255) << 8) + ((longBuffer[0] & 255) << 0));
        }
        else
        {
            return (((long) longBuffer[0] << 56)
                    + ((long) (longBuffer[1] & 255) << 48)
                    + ((long) (longBuffer[2] & 255) << 40)
                    + ((long) (longBuffer[3] & 255) << 32)
                    + ((long) (longBuffer[4] & 255) << 24)
                    + ((longBuffer[5] & 255) << 16)
                    + ((longBuffer[6] & 255) << 8) + ((longBuffer[7] & 255) << 0));
        }
    }

    public float readFloat() throws IOException
    {
        return Float.intBitsToFloat(readInt());
    }

    public double readDouble() throws IOException
    {
        return Double.longBitsToDouble(readLong());
    }

    public void reset(byte[] bytes)
    {
        this.buffer = bytes;
        count = bytes.length;
        pos = 0;
        offsetStart = 0;
        offsetEnd = 0;
    }

    public int getOffsetStart()
    {
        return offsetStart;
    }

    public void setOffsetStart(int offsetStart)
    {
        this.offsetStart = offsetStart;
    }

    public int getOffsetEnd()
    {
        return offsetEnd;
    }

    public void setOffsetEnd(int offsetEnd)
    {
        this.offsetEnd = offsetEnd;
    }

    public void offset(int offstart, int offend)
    {
        offsetStart += offstart;
        offsetEnd += offend;
        count -= offend;
        count -= offstart;
    }

    public void moveHead()
    {
        pos = offsetStart;
    }

    public int position()
    {
        return pos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return StringHelper.buffer("BytesInputStream [pos=", pos, ", count=",
                count, ']', ByteHelper.toHexString(buffer, offsetStart, count));
    }

}
