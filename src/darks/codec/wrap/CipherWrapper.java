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

package darks.codec.wrap;

import java.io.IOException;

import darks.codec.CodecConfig;
import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.Encoder;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;
import darks.codec.logs.Logger;
import darks.codec.wrap.cipher.AESCipher;
import darks.codec.wrap.cipher.OCCipher;

public class CipherWrapper extends Wrapper
{

    private static Logger log = Logger.getLogger(CipherWrapper.class);

    private OCCipher cipher;
    
    public static CipherWrapper AES(String key)
    {
        return new CipherWrapper(new AESCipher(key));
    }
    
    public static CipherWrapper AES(String key, int keysize)
    {
        return new CipherWrapper(new AESCipher(key, keysize));
    }

    public CipherWrapper(OCCipher cipher)
    {
        this.cipher = cipher;
    }

    @Override
    public void afterEncode(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws IOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Before cipher encrypt bytes:" + out);
        }
        int outSize = out.size();
        CodecConfig cfg = param.getCodecConfig();
        byte[] data = cipher.encrypt(out.getDirectBytes(), out.getOffset(),
                outSize, param);
        out.reset();
        int start = 0;
        boolean hasTotalLen = cfg.isHasTotalLength();
        if (hasTotalLen)
        {
            start = TOTAL_LEN_BITS;
            out.setCursor(start);
        }
        out.write(data);
        if (hasTotalLen)
        {
            int count = out.size() - start;
            out.setCursor(0);
            out.writeInt(count);
            out.moveLast();
            if (log.isDebugEnabled())
            {
                log.debug("Cipher wrapper encrypt final count:" + count);
            }
        }
        if (log.isDebugEnabled())
        {
            log.debug("After cipher encrypt bytes:" + out);
        }
    }

    @Override
    public void beforeDecode(Decoder decoder, BytesInputStream in,
            CodecParameter param) throws IOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Before cipher decrypt bytes:" + in);
        }
        CodecConfig cfg = param.getCodecConfig();
        in.moveHead();
        if (cfg.isHasTotalLength())
        {
            int count = in.readInt();
            if (log.isDebugEnabled())
            {
                log.debug("Cipher wrapper decrypt count:" + count);
            }
        }
        byte[] data = cipher.decrypt(in.getDirectBytes(), in.position(), in.available(), param);
        in.reset(data);
        if (log.isDebugEnabled())
        {
            log.debug("After cipher wrapper decrypt bytes:" + in);
        }
    }
}
