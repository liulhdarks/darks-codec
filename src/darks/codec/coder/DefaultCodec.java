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

package darks.codec.coder;

import java.io.IOException;

import darks.codec.CodecConfig;
import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.Encoder;
import darks.codec.Codec;
import darks.codec.coder.cache.Cache;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;
import darks.codec.logs.Logger;
import darks.codec.type.OCMessage;
import darks.codec.type.OCObject;
import darks.codec.wrap.WrapChain;

public class DefaultCodec extends Codec
{

    private static final Logger log = Logger.getLogger(DefaultCodec.class);

    private static final int INIT_BYTES_SIZE = 128;

    private Encoder encoder;

    private Decoder decoder;
    
    private Cache cache;
    
    private WrapChain wrapChain;
    
    public DefaultCodec(CodecConfig codecConfig)
    {
        super(codecConfig);
        encoder = new DefaultEncoder();
        decoder = new DefaultDecoder();
        wrapChain = codecConfig.getWrapChain();
    }

    @Override
    public void activated()
    {
        cache = Cache.getCache(codecConfig);
    }

    @Override
    public byte[] encode(OCObject msg) throws IOException
    {
        if (codecConfig.isHasHeader())
        {
            msg = new OCMessage(msg);
        }
        CodecParameter param = new CodecParameter(codecConfig, cache);
        BytesOutputStream out = new BytesOutputStream(INIT_BYTES_SIZE, codecConfig);
        wrapChain.beforeEncode(encoder, out, param);
        encoder.encodeObject(out, msg, param);
        wrapChain.afterEncode(encoder, out, param);
        byte[] bytes = out.toByteArray();
        if (log.isDebugEnabled())
        {
            log.debug(out.toString());
        }
        return bytes;
    }

    @Override
    public OCObject decode(byte[] bytes, OCObject msg) throws IOException
    {
        if (codecConfig.isHasHeader())
        {
            msg = new OCMessage(msg);
        }
        CodecParameter param = new CodecParameter(codecConfig, cache);
        BytesInputStream in = new BytesInputStream(bytes, codecConfig);
        wrapChain.beforeDecode(decoder, in, param);
        decoder.decodeObject(in, msg, param);
        wrapChain.afterDecode(decoder, in, param);
        return msg;
    }
}
