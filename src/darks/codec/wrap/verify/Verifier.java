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

package darks.codec.wrap.verify;

/**
 * Verify bytes arrays before decoding.
 * 
 * Verifier.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public abstract class Verifier
{

    /**
     * Update verify calculate bytes.
     * 
     * @param lastData Last verify data.
     * @param data Bytes arrays.
     * @return Current verify data.
     */
    public Object update(Object lastData, byte[] data)
    {
        return update(lastData, data, 0, data.length);
    }

    /**
     * Update verify calculate bytes.
     * 
     * @param lastData Last verify data.
     * @param data Bytes arrays.
     * @param offset Bytes offset.
     * @param length Bytes length.
     * @return Current verify data.
     */
    public abstract Object update(Object lastData, byte[] data, int offset,
            int length);

    /**
     * Get verify code through verify data and little-endian.
     * 
     * @param code Verify data.
     * @param littleEndian Whether configure little-endian.
     * @return Code bytes arrays
     */
    public abstract byte[] getVerifyCode(Object code, boolean littleEndian);

    /**
     * Verify code byte length.
     * 
     * @return Byte length
     */
    public abstract int verifyLength();
}
