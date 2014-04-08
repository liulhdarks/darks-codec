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

public class CRC16Verifier extends Verifier
{

    private short[] crcTable = new short[256];

    private int gPloy = 0x1021; // 生成多项式

    public CRC16Verifier()
    {
        computeCrcTable();
    }

    private short getCrcOfByte(int aByte)
    {
        int value = aByte << 8;
        for (int count = 7; count >= 0; count--)
        {
            if ((value & 0x8000) != 0)
            { // 高第16位为1，可以按位异或
                value = (value << 1) ^ gPloy;
            }
            else
            {
                value = value << 1; // 首位为0，左移
            }
        }
        value = value & 0xFFFF; // 取低16位的值
        return (short) value;
    }

    /*
     * 生成0 - 255对应的CRC16校验码
     */
    private void computeCrcTable()
    {
        for (int i = 0; i < 256; i++)
        {
            crcTable[i] = getCrcOfByte(i);
        }
    }

    @Override
    public byte[] getVerifyCode(Object code, boolean littleEndian)
    {
        int crc = (Integer) code;
        crc = crc & 0xFFFF;
        return ByteHelper.convertInt16(crc, littleEndian);
    }

    @Override
    public Object update(Object initData, byte[] data, int offset, int length)
    {
        int crc = (Integer)(initData == null ? 0 : initData);
        for (int i = 0; i < length; i++)
        {
            crc = ((crc & 0xFF) << 8)
                    ^ crcTable[(((crc & 0xFF00) >> 8) ^ data[i + offset]) & 0xFF];
        }
        return crc;
    }

    @Override
    public int verifyLength()
    {
        return 2;
    }

    
}
