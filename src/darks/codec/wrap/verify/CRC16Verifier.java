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

package darks.codec.wrap.verify;

import darks.codec.helper.ByteHelper;
import darks.codec.wrap.VerifyWrapper;

/**
 * CRC16 verify bytes arrays.
 * 
 * CRC16Verifier.java
 * 
 * @see VerifyWrapper
 * @version 1.0.0
 * @author Liu lihua
 */
public class CRC16Verifier extends Verifier
{

    private short[] crcTable = new short[256];

    private int crcPloy = 0x1021;

    public CRC16Verifier()
    {
        computeCrcTable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] getVerifyCode(Object code, boolean littleEndian)
    {
        int crc = (Integer) code;
        crc = crc & 0xFFFF;
        return ByteHelper.convertInt16(crc, littleEndian);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object update(Object initData, byte[] data, int offset, int length)
    {
        int crc = (Integer) (initData == null ? 0 : initData);
        for (int i = 0; i < length; i++)
        {
            crc = ((crc & 0xFF) << 8)
                    ^ crcTable[(((crc & 0xFF00) >> 8) ^ data[i + offset]) & 0xFF];
        }
        return crc;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int verifyLength()
    {
        return 2;
    }

    private short getCrcOfByte(int aByte)
    {
        int value = aByte << 8;
        for (int count = 7; count >= 0; count--)
        {
            if ((value & 0x8000) != 0)
            {
                value = (value << 1) ^ crcPloy;
            }
            else
            {
                value = value << 1;
            }
        }
        value = value & 0xFFFF;
        return (short) value;
    }

    private void computeCrcTable()
    {
        for (int i = 0; i < 256; i++)
        {
            crcTable[i] = getCrcOfByte(i);
        }
    }
}
