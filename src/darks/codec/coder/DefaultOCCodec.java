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
import darks.codec.OCCodec;
import darks.codec.coder.cache.Cache;
import darks.codec.helper.ByteHelper;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;
import darks.codec.logs.Logger;
import darks.codec.type.OCMessage;
import darks.codec.type.OCObject;

public class DefaultOCCodec extends OCCodec
{

    private static final Logger log = Logger.getLogger(DefaultOCCodec.class);

    private static final int INIT_BYTES_SIZE = 128;

    private Encoder encoder;

    private Decoder decoder;
    
    private Cache cache;
    
    public DefaultOCCodec(CodecConfig codecConfig)
    {
        super(codecConfig);
        encoder = new DefaultEncoder();
        decoder = new DefaultDecoder();
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
        beforeEncode(out, param);
        encoder.encodeObject(out, msg, param);
        afterEncode(out, param);
        byte[] bytes = out.toByteArray();
        if (log.isDebugEnabled())
        {
            log.debug(ByteHelper.toHexString(bytes));
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
        beforeDecode(in, param);
        decoder.decodeObject(in, msg, param);
        afterDecode(in, param);
        return msg;
    }
}
