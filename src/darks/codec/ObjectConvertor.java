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

import darks.codec.coder.DefaultOCCodec;
import darks.codec.type.OCObject;

/**
 * ObjectConvertor.java
 * @version 1.0.0
 * @author Liu lihua
 */
public class ObjectConvertor
{
    
    private OCCodec codec;
    
    private CodecConfig codecConfig;
    
    
    public ObjectConvertor()
    {
        initialize();
        codec = new DefaultOCCodec(codecConfig);
    }
    
    public ObjectConvertor(OCCodec codec)
    {
        initialize();
        this.codec = codec;
        if (codec.getCodecConfig() == null)
        {
            codec.setCodecConfig(codecConfig);
        }
    }
    
    private void initialize()
    {
        codecConfig = new CodecConfig();
    }
    
    public byte[] encode(OCObject msg)
        throws IOException
    {
        if (codec == null)
        {
            return null;
        }
        return codec.encode(msg);
    }
    
    public OCObject decode(byte[] bytes, OCObject source)
        throws IOException
    {
        if (codec == null)
        {
            return null;
        }
        return codec.decode(bytes, source);
    }
    
    public OCCodec getCodec()
    {
        return codec;
    }
    
    public void setCodec(OCCodec codec)
    {
        this.codec = codec;
    }

    public CodecConfig getCodecConfig()
    {
        return codecConfig;
    }
    
}
