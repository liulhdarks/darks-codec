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

import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.Encoder;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;

/**
 * 
 * WrapChain.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class WrapChain
{
    Wrapper head;

    Wrapper tail;

    /**
     * Add wrapper to wrap chain
     * 
     * @param wrap {@linkplain darks.codec.wrap.Wrapper Wrapper} object.
     */
    public void add(Wrapper wrap)
    {
        if (head == null)
        {
            head = wrap;
            tail = wrap;
        }
        else
        {
            wrap.next = head;
            head.prev = wrap;
            head = wrap;
        }
    }

    public void beforeEncode(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws IOException
    {
        Wrapper wrap = head;
        while (wrap != null)
        {
            wrap.beforeEncode(encoder, out, param);
            wrap = wrap.next;
        }
    }

    public void afterEncode(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws IOException
    {
        Wrapper wrap = head;
        while (wrap != null)
        {
            wrap.afterEncode(encoder, out, param);
            wrap = wrap.next;
        }
    }

    public void beforeDecode(Decoder decoder, BytesInputStream in,
            CodecParameter param) throws IOException
    {
        Wrapper wrap = tail;
        while (wrap != null)
        {
            wrap.beforeDecode(decoder, in, param);
            wrap = wrap.prev;
        }
    }

    public void afterDecode(Decoder decoder, BytesInputStream in,
            CodecParameter param) throws IOException
    {
        Wrapper wrap = tail;
        while (wrap != null)
        {
            wrap.afterDecode(decoder, in, param);
            wrap = wrap.prev;
        }
    }
}
