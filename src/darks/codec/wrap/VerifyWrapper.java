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
import java.util.LinkedList;
import java.util.List;

import darks.codec.CodecConfig;
import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.Encoder;
import darks.codec.CodecConfig.TotalLengthType;
import darks.codec.exceptions.VerifyException;
import darks.codec.helper.ByteHelper;
import darks.codec.helper.StringHelper;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;
import darks.codec.logs.Logger;
import darks.codec.wrap.verify.CRC16Verifier;
import darks.codec.wrap.verify.CRC32Verifier;
import darks.codec.wrap.verify.Verifier;
import darks.codec.wrap.verify.VerifyExtern;

public class VerifyWrapper extends Wrapper
{

    static Logger log = Logger.getLogger(VerifyWrapper.class);

    Verifier verifier;

    public VerifyWrapper(Verifier verifier)
    {
        this.verifier = verifier;
    }

    public static VerifyWrapper CRC16()
    {
        return new VerifyWrapper(new CRC16Verifier());
    }

    public static VerifyWrapper CRC32()
    {
        return new VerifyWrapper(new CRC32Verifier());
    }

    @Override
    public void afterEncode(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws IOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Pre-encode verify " + verifier + " length:" + verifier.verifyLength());
        }
        List<ByteBuffer> headList = null;
        List<ByteBuffer> tailList = null;
        CodecConfig cfg = param.getCodecConfig();
        if (cfg.getTotalLengthType() == TotalLengthType.AUTO)
        {
            computeTotalLength(out, verifier.verifyLength(), cfg);
        }
        if (out.getHead() != null)
        {
            headList = new LinkedList<ByteBuffer>();
            for (ByteBuffer buf : out.getHead())
            {
                headList.add(buf);
            }
        }
        if (out.getTail() != null)
        {
            tailList = new LinkedList<ByteBuffer>();
            for (ByteBuffer buf : out.getTail())
            {
                tailList.add(buf);
            }
        }
        int len = verifier.verifyLength();
        ByteBuffer verifyBuf = (ByteBuffer)out.newBufferTailEnd(len).position(len);
        param.getFinalQueue().addWrap(this, new VerifyExtern(headList, tailList, verifyBuf));
    }

    @Override
    public void finalEncode(Encoder encoder, BytesOutputStream out,
            CodecParameter param, Object extern) throws IOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Final encode verify " + verifier);
        }
        VerifyExtern vExtern = (VerifyExtern)extern;
        Object code = null;
        if (vExtern.headList != null)
        {
            for (ByteBuffer buf : vExtern.headList)
            {
                code = verifier.update(code, buf.array());
            }
        }
        code = verifier.update(code, out.getDirectBytes(), 0, out.size());
        if (vExtern.tailList != null)
        {
            for (ByteBuffer buf : vExtern.tailList)
            {
                code = verifier.update(code, buf.array());
            }
        }
        byte[] codes = verifier.getVerifyCode(code, param.isLittleEndian());
        if (codes == null)
        {
            throw new VerifyException("Fail to get verify code by " + verifier);
        }
        vExtern.verifyBuf.flip();
        vExtern.verifyBuf.put(codes);
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
        if (codes == null)
        {
            throw new VerifyException("Fail to get verify code by " + verifier);
        }
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
