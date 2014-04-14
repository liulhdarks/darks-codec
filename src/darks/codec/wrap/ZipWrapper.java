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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import darks.codec.CodecConfig;
import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.Encoder;
import darks.codec.helper.IoHelper;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;
import darks.codec.logs.Logger;
import darks.codec.wrap.zip.CommonsCompress;
import darks.codec.wrap.zip.JDKGZipCompress;
import darks.codec.wrap.zip.JZlibCompress;
import darks.codec.wrap.zip.ZipCompress;

/**
 * ZipWrapper can ZIP bytes after encoding and UNZIP before decoding. It Support
 * JDK GZIP, JZLIB, COMMON-COMPRESS to ZIP data.
 * <p>
 * Example:
 * 
 * <pre>
 *  ObjectCoder coder = new ObjectCoder();
 *      ...
 *  coder.getCodecConfig().addWrap(ZipWrapper.JZLIB());
 *      ...
 * </pre>
 * 
 * Or
 * 
 * <pre>
 *  ObjectCoder coder = new ObjectCoder();
 *      ...
 *  coder.getCodecConfig().addWrap(ZipWrapper.COMMON_COMPRESS());
 *      ...
 * </pre>
 * 
 * Or
 * 
 * <pre>
 *  ObjectCoder coder = new ObjectCoder();
 *      ...
 *  coder.getCodecConfig().addWrap(new ZipWrapper(new CustomZipWrapper()));
 *      ...
 * </pre>
 * 
 * ZipWrapper.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class ZipWrapper extends Wrapper
{

    private static Logger log = Logger.getLogger(ZipWrapper.class);

    private ZipCompress compress;

    /**
     * Create JDK GZIP wrapper.
     * 
     * @return ZipWrapper object
     */
    public static ZipWrapper JDK_GZIP()
    {
        return new ZipWrapper(new JDKGZipCompress());
    }

    /**
     * Create JZLIB wrapper.
     * 
     * @return ZipWrapper object
     */
    public static ZipWrapper JZLIB()
    {
        return new ZipWrapper(new JZlibCompress());
    }

    /**
     * Create common compress wrapper.
     * 
     * @return ZipWrapper object
     */
    public static ZipWrapper COMMON_COMPRESS()
    {
        return new ZipWrapper(new CommonsCompress());
    }

    /**
     * Create common compress wrapper.
     * 
     * @return ZipWrapper object
     */
    public static ZipWrapper COMMON_COMPRESS(String type)
    {
        return new ZipWrapper(new CommonsCompress(type));
    }

    public ZipWrapper(ZipCompress compress)
    {
        this.compress = compress;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterEncode(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws IOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Before Zip bytes:" + out);
        }
        int outSize = out.size();
        CodecConfig cfg = param.getCodecConfig();
        ByteArrayInputStream bais = null;
        BufferedInputStream bis = null;
        try
        {
            bais = new ByteArrayInputStream(out.getDirectBytes(),
                    out.getOffset(), outSize);
            bis = new BufferedInputStream(bais);
            out.reset();
            int start = 0;
            if (cfg.isHasTotalLength())
            {
                start = TOTAL_LEN_BITS;
                out.setCursor(start);
            }
            compress.compress(bis, out);
            if (cfg.isHasTotalLength())
            {
                int count = out.size() - start;
                out.setCursor(0);
                out.writeInt(count);
                out.moveLast();
                if (log.isDebugEnabled())
                {
                    log.debug("Zip wrapper zip final count:" + count + " rate:"
                            + ((float) count / (float) outSize));
                }
            }
            if (log.isDebugEnabled())
            {
                log.debug("After Zip bytes:" + out);
            }
        }
        finally
        {
            IoHelper.closeIO(bis);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void beforeDecode(Decoder decoder, BytesInputStream in,
            CodecParameter param) throws IOException
    {
        if (log.isDebugEnabled())
        {
            log.debug("Before Unzip bytes:" + in);
        }
        CodecConfig cfg = param.getCodecConfig();
        in.moveHead();
        if (cfg.isHasTotalLength())
        {
            int count = in.readInt();
            if (log.isDebugEnabled())
            {
                log.debug("Zip wrapper unzip count:" + count);
            }
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(baos);
        compress.uncompress(in, bos);
        in.reset(baos.toByteArray());
        if (log.isDebugEnabled())
        {
            log.debug("After Unzip bytes:" + in);
        }
    }

}
