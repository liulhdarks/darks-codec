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

import java.lang.reflect.Type;

import darks.codec.CodecConfig.EndianType;
import darks.codec.coder.cache.Cache;

public class CodecParameter
{

    private Type[] genericType;
    
    private CodecConfig codecConfig;
    
    private boolean littleEndian;
    
    private boolean autoLength = false;
    
    private boolean ignoreObjectAutoLength = false;
    
    private String encoding;
    
    private Cache cache;
    
    public CodecParameter(CodecConfig codecConfig, Cache cache)
    {
        this.codecConfig = codecConfig;
        this.cache = cache;
        littleEndian = (codecConfig.getEndianType() == EndianType.LITTLE);
        autoLength = codecConfig.isAutoLength();
        ignoreObjectAutoLength = codecConfig.isIgnoreObjectAutoLength();
        encoding = codecConfig.getEncoding();
    }
    
    public boolean isLittleEndian()
    {
        return littleEndian;
    }

    public Type getGenericType(int index)
    {
        if (genericType == null || index < 0 || index >= genericType.length)
        {
            return null;
        }
        return genericType[index];
    }

    public Type[] getGenericType()
    {
        return genericType;
    }

    public void setGenericType(Type[] genericType)
    {
        this.genericType = genericType;
    }

    public CodecConfig getCodecConfig()
    {
        return codecConfig;
    }

    public boolean isAutoLength()
    {
        return autoLength;
    }

    public boolean isIgnoreObjectAutoLength()
    {
        return ignoreObjectAutoLength;
    }

    public String getEncoding()
    {
        return encoding;
    }

    public Cache getCache()
    {
        return cache;
    }

}
