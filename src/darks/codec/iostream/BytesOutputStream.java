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
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.LinkedList;

import darks.codec.CodecConfig;
import darks.codec.CodecConfig.EndianType;
import darks.codec.helper.ByteHelper;

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

    private LinkedList<ByteBuffer> head;

    private LinkedList<ByteBuffer> tail;

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
        // return Arrays.copyOf(buffer, count);
        int size = totalSize();
        ByteBuffer buf = ByteBuffer.allocate(size);
        if (head != null)
        {
            for (ByteBuffer bytes : head)
            {
                buf.put(bytes.array());
            }
        }
        buf.put(buffer, 0, count);
        if (tail != null)
        {
            for (ByteBuffer bytes : tail)
            {
                buf.put(bytes.array());
            }
        }
        return buf.array();
    }

    public int totalSize()
    {
        int sum = 0;
        if (head != null)
        {
            for (ByteBuffer buf : head)
            {
                sum += buf.position();
            }
        }
        if (tail != null)
        {
            for (ByteBuffer buf : tail)
            {
                sum += buf.position();
            }
        }
        sum += count;
        return sum;
    }

    public ByteBuffer newBufferHeadFirst(int size)
    {
        if (head == null)
        {
            head = new LinkedList<ByteBuffer>();
        }
        ByteBuffer buf = ByteBuffer.allocate(size);
        head.addFirst(buf);
        return buf;
    }

    public ByteBuffer newBufferHeadEnd(int size)
    {
        if (head == null)
        {
            head = new LinkedList<ByteBuffer>();
        }
        ByteBuffer buf = ByteBuffer.allocate(size);
        head.addLast(buf);
        return buf;
    }

    public ByteBuffer newBufferTailFirst(int size)
    {
        if (tail == null)
        {
            tail = new LinkedList<ByteBuffer>();
        }
        ByteBuffer buf = ByteBuffer.allocate(size);
        tail.addFirst(buf);
        return buf;
    }

    public ByteBuffer newBufferTailEnd(int size)
    {
        if (tail == null)
        {
            tail = new LinkedList<ByteBuffer>();
        }
        ByteBuffer buf = ByteBuffer.allocate(size);
        tail.addLast(buf);
        return buf;
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
    
    public void incInt(int pos, int inc) throws IOException
    {
        if (pos + 4 > buffer.length)
        {
            throw new ArrayIndexOutOfBoundsException(buffer.length);
        }
        int src = 0;
        if (isLittleEndian)
        {
            src = ((buffer[pos + 3] << 24) + (buffer[pos + 2] << 16) + (buffer[pos + 1] << 8) + (buffer[pos] << 0));
        }
        else
        {
            src = ((buffer[pos] << 24) + (buffer[pos + 1] << 16) + (buffer[pos + 2] << 8) + (buffer[pos + 3] << 0));
        }
        src += inc;
        setCursor(pos);
        writeInt(src);
        moveLast();
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
    
    public void reset()
    {
        this.count = 0;
        this.offset = 0;
        buffer = new byte[buffer.length];
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
    
    public LinkedList<ByteBuffer> getHead()
    {
        return head;
    }

    public LinkedList<ByteBuffer> getTail()
    {
        return tail;
    }

    @Override
    public String toString()
    {
        StringBuilder ret = new StringBuilder(64);
        if (head != null)
        {
            for (ByteBuffer bytes : head)
            {
                ret.append(ByteHelper.toHexString(bytes.array()));
                ret.append("  ");
            }
        }
        ret.append(ByteHelper.toHexString(buffer, 0, count));
        if (tail != null)
        {
            for (ByteBuffer bytes : tail)
            {
                ret.append("  ");
                ret.append(ByteHelper.toHexString(bytes.array()));
            }
        }
        return ret.toString();
    }

}
