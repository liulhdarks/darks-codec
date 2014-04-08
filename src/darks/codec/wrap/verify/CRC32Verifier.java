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

import java.util.zip.CRC32;

import darks.codec.helper.ByteHelper;
import darks.codec.logs.Logger;

public class CRC32Verifier extends Verifier
{

    private static Logger log = Logger.getLogger(CRC32Verifier.class);

    public CRC32Verifier()
    {
    }

    @Override
    public byte[] getVerifyCode(Object code, boolean littleEndian)
    {
        CRC32 crc32 = (CRC32) code;
        if (crc32 != null)
        {
            return ByteHelper
                    .convertInt32((int) crc32.getValue(), littleEndian);
        }
        return null;
    }

    @Override
    public Object update(Object initData, byte[] data, int offset, int length)
    {
        CRC32 crc32 = (CRC32) initData;
        if (crc32 == null)
        {
            crc32 = new CRC32();
        }
        crc32.update(data, offset, length);
        if (log.isDebugEnabled())
        {
            log.debug("CRC32 update "
                    + ByteHelper.toHexString(data, offset, length));
        }
        return crc32;
    }

    @Override
    public int verifyLength()
    {
        return 4;
    }

}
