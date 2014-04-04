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

package darks.codec.type;

import java.io.IOException;

import darks.codec.CodecConfig;
import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.Encoder;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;

public class OCMessage extends OCObject
{
    /**
     * 
     */
    private static final long serialVersionUID = -3217240880451032242L;

    protected OCInteger identifier;

    protected OCInteger endIdentifier;

    protected OCInt32 totalLength = new OCInt32();

    protected OCObject ocObject;

    private Object object;

    public OCMessage()
    {

    }

    public OCMessage(OCObject ocObject)
    {
        this.ocObject = ocObject;
    }

    public OCMessage(Object object)
    {
        this.object = object;
    }

    public OCMessage(int identifier)
    {
        this.identifier = new OCInt16(identifier);
    }

    @Override
    public void writeObject(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws IOException
    {
        CodecConfig cfg = param.getCodecConfig();
        if (cfg.isHasIdentifier())
        {
            //identifier = new OCInteger();
            //cfg.getIdentifier().clone(identifier);
            //identifier.writeObject(encoder, out, param);
            //out.newBufferHeadFirst(cfg.getIdentifier().getLength()).put(
            //        cfg.getIdentifier().getBytes(param.isLittleEndian()));
        }
        if (cfg.isHasTotalLength())
        {
            totalLength.writeObject(encoder, out, param);
        }
        if (ocObject != null)
        {
            if (cfg.isHasTotalLength())
            {
                ocObject.setLenType(totalLength);
            }
            ocObject.writeObject(encoder, out, param);
        }
        else if (object != null)
        {
            int start = out.size();
            encoder.encodeObject(out, object, param);
            int end = out.size();
            if (cfg.isHasTotalLength())
            {
                totalLength.setValue(end - start);
                int pos = totalLength.getBytePos();
                out.setCursor(pos);
                totalLength.writeObject(encoder, out, param);
                out.moveLast();
            }
        }
        if (cfg.isHasEndIdentifier())
        {
            // endIdentifier = new OCInteger();
            // cfg.getEndIdentifier().clone(endIdentifier);
            // endIdentifier.writeObject(encoder, out, param);
            //out.newBufferTailEnd(cfg.getEndIdentifier().getLength()).put(
            //        cfg.getEndIdentifier().getBytes(param.isLittleEndian()));
        }
    }

    @Override
    public void readObject(Decoder decoder, BytesInputStream in,
            CodecParameter param) throws IOException
    {
        CodecConfig cfg = param.getCodecConfig();
        if (cfg.isHasEndIdentifier())
        {
            //in.setCount(in.getCount() - cfg.getEndIdentifier().getLength());
        }
        if (cfg.isHasIdentifier())
        {
            //identifier = new OCInteger();
            //cfg.getIdentifier().clone(identifier);
            //identifier.readObject(decoder, in, param);
        }
        if (cfg.isHasTotalLength())
        {
            totalLength.readObject(decoder, in, param);
        }
        if (ocObject != null)
        {
            if (cfg.isHasTotalLength())
            {
                ocObject.setLenType(totalLength);
            }
            ocObject.readObject(decoder, in, param);
        }
        else if (object != null)
        {
            decoder.decodeObject(in, object, param);
        }
    }

    @Override
    public String toString()
    {
        return "OCMessage [identifier=" + identifier + ", totalLength="
                + totalLength + ", ocObject=" + ocObject + ", object=" + object
                + "]";
    }

}
