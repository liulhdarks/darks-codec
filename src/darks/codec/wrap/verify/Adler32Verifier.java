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

import java.util.zip.Adler32;

import darks.codec.helper.ByteHelper;
import darks.codec.logs.Logger;

public class Adler32Verifier extends Verifier
{

    private static Logger log = Logger.getLogger(Adler32Verifier.class);

    public Adler32Verifier()
    {
    }

    @Override
    public byte[] getVerifyCode(Object code, boolean littleEndian)
    {
        Adler32 adler = (Adler32) code;
        if (adler != null)
        {
            return ByteHelper
                    .convertInt32((int) adler.getValue(), littleEndian);
        }
        return null;
    }

    @Override
    public Object update(Object initData, byte[] data, int offset, int length)
    {
        Adler32 adler = (Adler32) initData;
        if (adler == null)
        {
            adler = new Adler32();
        }
        adler.update(data, offset, length);
        if (log.isDebugEnabled())
        {
            log.debug("Adler32 update " + ByteHelper.toHexString(data, offset, length));
        }
        return adler;
    }

    @Override
    public int verifyLength()
    {
        return 4;
    }

}
