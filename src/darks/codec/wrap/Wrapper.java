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

public abstract class Wrapper
{
    protected static final int TOTAL_LEN_BITS = 4;
    
    Wrapper next;

    Wrapper prev;

    public void beforeEncode(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws IOException
    {
    }

    public void afterEncode(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws IOException
    {
    }

    public void finalEncode(Encoder encoder, BytesOutputStream out,
            CodecParameter param, Object extern) throws IOException
    {
    }

    public void beforeDecode(Decoder decoder, BytesInputStream in,
            CodecParameter param) throws IOException
    {
    }

    public void afterDecode(Decoder decoder, BytesInputStream in,
            CodecParameter param) throws IOException
    {
    }

    public void computeTotalLength(BytesOutputStream out, int offset,
            CodecConfig codecConfig) throws IOException
    {
        if (offset == 0)
        {
            return;
        }
        if (codecConfig.isHasTotalLength())
        {
            out.incInt(0, offset);
        }
    }
}
