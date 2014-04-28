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

import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.Encoder;
import darks.codec.annotations.CodecType;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;

/**
 * Codec type object.
 * 
 * OCType.java
 * 
 * @see CodecType
 * @version 1.0.0
 * @author Liu lihua
 */
@CodecType
public interface OCType
{

    /**
     * Write object to encoding IO stream.
     * 
     * @param encoder Encoding object.
     * @param out Encoding IO stream.
     * @param param Codec parameter object.
     * @throws Exception exception.
     */
    public void writeObject(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws Exception;

    /**
     * Read object from decoding IO stream.
     * 
     * @param decoder Decoding object,
     * @param in Decoding IO stream.
     * @param param Codec parameter object.
     * @throws Exception exception.
     */
    public void readObject(Decoder decoder, BytesInputStream in,
            CodecParameter param) throws Exception;
}
