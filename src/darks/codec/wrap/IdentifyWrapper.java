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

public class IdentifyWrapper extends Wrapper
{

    @Override
    public void afterEncode(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws IOException
    {
        CodecConfig cfg = param.getCodecConfig();
        if (cfg.isHasIdentifier())
        {
            out.newBufferHeadFirst(cfg.getIdentifier().getLength()).put(
                    cfg.getIdentifier().getBytes(param.isLittleEndian()));
        }
        if (cfg.isHasEndIdentifier())
        {
            out.newBufferTailEnd(cfg.getEndIdentifier().getLength()).put(
                    cfg.getEndIdentifier().getBytes(param.isLittleEndian()));
        }
    }

    @Override
    public void beforeDecode(Decoder decoder, BytesInputStream in,
            CodecParameter param) throws IOException
    {
        CodecConfig cfg = param.getCodecConfig();
        if (cfg.isHasIdentifier())
        {
            in.offset(cfg.getIdentifier().getLength(), 0);
        }
        if (cfg.isHasEndIdentifier())
        {
            in.offset(0, cfg.getEndIdentifier().getLength());
        }
        in.moveHead();
    }

}
