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

public abstract class OCCodec
{

    
    protected CodecConfig codecConfig;
    
    public OCCodec(CodecConfig codecConfig)
    {
        this.codecConfig = codecConfig;
    }
    
    public abstract byte[] encode(OCObject msg)
        throws IOException;
    
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
