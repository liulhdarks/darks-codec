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
import java.nio.ByteBuffer;

import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.Encoder;
import darks.codec.exceptions.VerifyException;
import darks.codec.extern.Verifier;
import darks.codec.helper.ByteHelper;
import darks.codec.helper.StringHelper;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;
import darks.codec.logs.Logger;

public class VerifyWrapper extends Wrapper
{

    static Logger log = Logger.getLogger(VerifyWrapper.class);

    Verifier verifier;

    public VerifyWrapper(Verifier verifier)
    {
        this.verifier = verifier;
    }

    @Override
    public void afterEncode(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws IOException
    {
        computeTotalLength(out, verifier.verifyLength(), param.getCodecConfig());
        Object code = null;
        if (out.getHead() != null)
        {
            for (ByteBuffer buf : out.getHead())
            {
                code = verifier.update(code, buf.array());
            }
        }
        code = verifier.update(code, out.getDirectBytes(), 0, out.size());
        if (out.getTail() != null)
        {
            for (ByteBuffer buf : out.getTail())
            {
                code = verifier.update(code, buf.array());
            }
        }
        byte[] codes = verifier.getVerifyCode(code, param.isLittleEndian());
        out.newBufferTailEnd(codes.length).put(codes);
    }

    @Override
    public void beforeDecode(Decoder decoder, BytesInputStream in,
            CodecParameter param) throws IOException
    {
        int codeLen = verifier.verifyLength();
        byte[] srcCode = new byte[codeLen];
        in.setCursor(in.getCount() - codeLen);
        in.read(srcCode);
        in.moveHead();
        byte[] bytes = in.getDirectBytes();
        in.offset(0, codeLen);
        Object code = verifier
                .update(null, bytes, in.position(), in.getCount());
        byte[] codes = verifier.getVerifyCode(code, param.isLittleEndian());
        if (log.isDebugEnabled())
        {
            log.debug(StringHelper.buffer("Verify source:",
                    ByteHelper.toHexString(srcCode), " target:",
                    ByteHelper.toHexString(codes), " by verifier ",
                    verifier.getClass()));
        }
        for (int i = 0; i < codeLen; i++)
        {
            if (srcCode[i] != codes[i])
            {
                throw new VerifyException("Verify wrapper failed.source:"
                        + ByteHelper.toHexString(srcCode) + " current:"
                        + ByteHelper.toHexString(codes));
            }
        }
    }

}
