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

package darks.codec;

import java.io.IOException;

import darks.codec.type.OCObject;

/**
 * Codec interface. Developers can implement it to customize codec.<br>
 * 
 * Codec.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public abstract class Codec
{

    protected CodecConfig codecConfig;

    public Codec(CodecConfig codecConfig)
    {
        this.codecConfig = codecConfig;
    }

    /**
     * Be called when first executing.
     */
    public void activated()
    {
    }

    /**
     * Encode {@linkplain darks.codec.type.OCObject OCObject} object.
     * 
     * @param msg Message object.
     * @return If successful encoding, return bytes arrays. Otherwise return
     *         null.
     * @throws IOException IO exception
     */
    public abstract byte[] encode(OCObject msg) throws IOException;

    /**
     * Decode {@linkplain darks.codec.type.OCObject OCObject} object.
     * 
     * @param bytes Bytes array.
     * @param source Target decoding object.
     * @return If successful encoding, return
     *         {@linkplain darks.codec.type.OCObject OCObject} object. Otherwise
     *         return null.
     * @throws IOException
     */
    public abstract OCObject decode(byte[] bytes, OCObject source)
            throws IOException;

    public CodecConfig getCodecConfig()
    {
        return codecConfig;
    }

    public void setCodecConfig(CodecConfig codecConfig)
    {
        this.codecConfig = codecConfig;
    }

}
