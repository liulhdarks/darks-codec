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
import darks.codec.type.OCInt16;
import darks.codec.type.OCInt32;
import darks.codec.type.OCInt8;
import darks.codec.type.OCInteger;

public class IdentifyWrapper extends Wrapper
{
    
    private OCInteger headIdentify;

    private OCInteger tailIdentify;

    public IdentifyWrapper(byte identify)
    {
        this.headIdentify = new OCInt8(identify);
    }

    public IdentifyWrapper(short identify)
    {
        this.headIdentify = new OCInt16(identify);
    }

    public IdentifyWrapper(int identify)
    {
        this.headIdentify = new OCInt32(identify);
    }

    public IdentifyWrapper(OCInteger headIdentify)
    {
        this.headIdentify = headIdentify;
    }
    
    public IdentifyWrapper(OCInteger headIdentify, OCInteger tailIdentify)
    {
        this.headIdentify = headIdentify;
        this.tailIdentify = tailIdentify;
    }

    @Override
    public void afterEncode(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws IOException
    {
        if (headIdentify != null)
        {
            out.newBufferHeadFirst(headIdentify.getLength()).put(
                    headIdentify.getBytes(param.isLittleEndian()));
        }
        if (tailIdentify != null)
        {
            out.newBufferTailEnd(tailIdentify.getLength()).put(tailIdentify.getBytes(param.isLittleEndian()));
        }
    }

    @Override
    public void beforeDecode(Decoder decoder, BytesInputStream in,
            CodecParameter param) throws IOException
    {
        if (headIdentify != null)
        {
            in.offset(headIdentify.getLength(), 0);
        }
        if (tailIdentify != null)
        {
            in.offset(0, tailIdentify.getLength());
        }
        in.moveHead();
    }

}
