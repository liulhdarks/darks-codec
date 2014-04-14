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

package darks.codec.wrap.cipher;

import darks.codec.wrap.CipherWrapper;

/**
 * AES cipher.
 * 
 * AESCipher.java
 * 
 * @see CipherWrapper
 * @version 1.0.0
 * @author Liu lihua
 */
public class AESCipher extends AbstractCipher
{

    private static final int DEFAULT_KEY_SIZE = 128;

    private static final String ALGORITHM = "AES";

    /**
     * Construct AES cipher. Default key size 128.
     * 
     * @param key AES key.
     */
    public AESCipher(String key)
    {
        this(key.getBytes());
    }

    /**
     * Construct AES cipher
     * 
     * @param key AES key.
     * @param keySize AES key size.
     */
    public AESCipher(String key, int keySize)
    {
        this(key.getBytes(), keySize);
    }

    /**
     * Construct AES cipher. Default key size 128.
     * 
     * @param key AES key bytes.
     */
    public AESCipher(byte[] key)
    {
        this(key, DEFAULT_KEY_SIZE);
    }

    /**
     * Construct AES cipher
     * 
     * @param key AES key bytes.
     * @param keySize AES key size.
     */
    public AESCipher(byte[] key, int keySize)
    {
        super(ALGORITHM, key, keySize);
    }
}
