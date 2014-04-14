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

import darks.codec.CodecConfig;
import darks.codec.CodecConfig.TotalLengthType;
import darks.codec.CodecParameter;
import darks.codec.Encoder;
import darks.codec.iostream.BytesOutputStream;
import darks.codec.logs.Logger;

/**
 * Calculate bytes total length in final encoding handle.
 * 
 * TotalLengthWrapper.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class TotalLengthWrapper extends Wrapper
{
    private static Logger log = Logger.getLogger(TotalLengthWrapper.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void finalEncode(Encoder encoder, BytesOutputStream out,
            CodecParameter param, Object extern) throws IOException
    {
        CodecConfig cfg = param.getCodecConfig();
        int totalSize = 0;
        if (out.getHead() != null
                && cfg.getTotalLengthType() == TotalLengthType.HEAD_BODY)
        {
            for (ByteBuffer buf : out.getHead())
            {
                totalSize += buf.position();
            }
        }
        if (out.getTail() != null)
        {
            for (ByteBuffer buf : out.getTail())
            {
                totalSize += buf.position();
            }
        }
        totalSize += out.size();
        if (cfg.getTotalLengthType() == TotalLengthType.BODY)
        {
            totalSize -= 4;
        }
        if (log.isDebugEnabled())
        {
            log.debug("Final encode total length:" + totalSize);
        }
        out.setCursor(0);
        out.writeInt(totalSize);
        out.moveLast();
    }

}
