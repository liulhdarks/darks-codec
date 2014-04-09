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

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import darks.codec.CodecParameter;
import darks.codec.exceptions.CipherException;

public class AbstractCipher extends OCCipher
{

    private byte[] aesKey;

    private SecretKeySpec keySpec;

    private int keySize = 0;

    private String algorithm;

    public AbstractCipher(String algorithm, String key)
    {
        this(algorithm, key.getBytes());
    }

    public AbstractCipher(String algorithm, String key, int keySize)
    {
        this(algorithm, key.getBytes(), keySize);
    }

    public AbstractCipher(String algorithm, byte[] key)
    {
        this.algorithm = algorithm;
        aesKey = key;
        initSecretKey();
    }

    public AbstractCipher(String algorithm, byte[] key, int keySize)
    {
        this.algorithm = algorithm;
        this.keySize = keySize;
        aesKey = key;
        initSecretKey();
    }

    private void initSecretKey()
    {
        try
        {
            KeyGenerator gen = KeyGenerator.getInstance(algorithm);
            if (keySize > 0)
            {
                gen.init(keySize, new SecureRandom(aesKey));
            }
            else
            {
                gen.init(new SecureRandom(aesKey));
            }
            SecretKey secretKey = gen.generateKey();
            keySpec = new SecretKeySpec(secretKey.getEncoded(), algorithm);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new CipherException(
                    "Invalid cipher algorithm, which is " + algorithm, e);
        }
    }

    @Override
    public byte[] encrypt(byte[] data, int offset, int length,
            CodecParameter param)
    {
        if (keySpec == null)
        {
            throw new CipherException("Fail to encrypt " + algorithm
                    + ". Cause fail to initialize secret key.");
        }
        try
        {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            return cipher.doFinal(data, offset, length);
        }
        catch (Exception e)
        {
            throw new CipherException("Fail to encrypt " + algorithm
                    + ". Cause " + e.getMessage(), e);
        }
    }

    @Override
    public byte[] decrypt(byte[] data, int offset, int length,
            CodecParameter param)
    {
        if (keySpec == null)
        {
            throw new CipherException("Fail to decrypt " + algorithm
                    + ". Cause fail to initialize secret key.");
        }
        try
        {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            return cipher.doFinal(data, offset, length);
        }
        catch (Exception e)
        {
            throw new CipherException("Fail to decrypt " + algorithm
                    + ". Cause " + e.getMessage(), e);
        }
    }

}
