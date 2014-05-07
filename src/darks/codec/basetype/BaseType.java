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

package darks.codec.basetype;

import java.io.IOException;

import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.Encoder;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;

/**
 * Indicate the interface to coding base java type usch as String, int, short
 * and so on.
 * 
 * BaseType.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public abstract class BaseType
{
	
	public enum BaseTypeBox
	{
		NONE, BOX
	}

    /**
     * Encoding base type.
     * @param encoder Encoder object
     * @param out Encoding IO stream.
     * @param obj Target field object.
     * @param param Codec parameter.
     */
	public abstract void encode(Encoder encoder, BytesOutputStream out,
            Object obj, CodecParameter param) throws Exception;

    /**
     * Decoding base type.
     * @param decoder Decoder object
     * @param in Decoding IO stream.
     * @param obj Target field object.
     * @param param Codec parameter.
     */
    public abstract Object decode(Decoder decoder, BytesInputStream in,
            Object obj, CodecParameter param) throws Exception;

    /**
     * Write base type's automatic length.
     * 
     * @param out Encoding IO stream.
     * @param length Length value.
     * @param param Codec parameter.
     * @throws IOException IO exception.
     */
    protected void writeAutoLength(BytesOutputStream out, int length,
            CodecParameter param) throws IOException
    {
        if (param.isAutoLength())
        {
            out.writeInt(length);
        }
    }

    /**
     * Read base type' automatic length.
     * 
     * @param in Decoding IO stream
     * @param param Codec parameter.
     * @return Base type length.
     * @throws IOException IO exception.
     */
    protected int readAutoLength(BytesInputStream in, CodecParameter param)
            throws IOException
    {
        if (param.isAutoLength() && in.available() >= 4)
        {
            return in.readInt();
        }
        return -1;
    }
}
