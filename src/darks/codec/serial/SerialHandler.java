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

package darks.codec.serial;

import darks.codec.ObjectCoder;

/**
 * Handle specify serial object.
 * 
 * SerialHandler.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public interface SerialHandler
{
    
    /**
     * Serial object to bytes.
     * @param coder {@linkplain darks.codec.ObjectCoder ObjectCoder}
     * @param obj object
     * @return Bytes arrays
     * @throws Exception
     */
    public byte[] encode(ObjectCoder coder, Object obj) throws Exception;

    /**
     * Convert serial bytes to object
     * @param coder {@linkplain darks.codec.ObjectCoder ObjectCoder}
     * @param bytes Bytes array
     * @return object
     * @throws Exception
     */
    public Object decode(ObjectCoder coder, byte[] bytes) throws Exception;
}
