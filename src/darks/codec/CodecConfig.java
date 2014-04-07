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

import darks.codec.wrap.WrapChain;

/**
 * 
 * CodecConfig.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class CodecConfig
{

    public enum EndianType
    {
        LITTLE, BIG
    }

    public enum CacheType
    {
        NONE, GLOBAL, LOCAL
    }

    private boolean hasTotalLength = true;

    private EndianType endianType = EndianType.LITTLE;

    private boolean autoLength = false;

    private boolean ignoreObjectAutoLength = false;

    private boolean ignoreStaticField = false;

    private boolean ignoreConstField = true;

    private CacheType cacheType = CacheType.LOCAL;

    private String encoding;

    private WrapChain wrapChain = new WrapChain();

    public CodecConfig()
    {

    }

    public boolean isHasTotalLength()
    {
        return hasTotalLength;
    }

    public void setHasTotalLength(boolean hasTotalLength)
    {
        this.hasTotalLength = hasTotalLength;
    }

    public EndianType getEndianType()
    {
        return endianType;
    }

    public void setEndianType(EndianType endianType)
    {
        this.endianType = endianType;
    }

    public boolean isAutoLength()
    {
        return autoLength;
    }

    public void setAutoLength(boolean autoLength)
    {
        this.autoLength = autoLength;
    }

    public boolean isIgnoreObjectAutoLength()
    {
        return ignoreObjectAutoLength;
    }

    public void setIgnoreObjectAutoLength(boolean ignoreObjectAutoLength)
    {
        this.ignoreObjectAutoLength = ignoreObjectAutoLength;
    }

    public String getEncoding()
    {
        return encoding;
    }

    public void setEncoding(String encoding)
    {
        this.encoding = encoding;
    }

    public boolean isIgnoreStaticField()
    {
        return ignoreStaticField;
    }

    public void setIgnoreStaticField(boolean ignoreStaticField)
    {
        this.ignoreStaticField = ignoreStaticField;
    }

    public boolean isIgnoreConstField()
    {
        return ignoreConstField;
    }

    public void setIgnoreConstField(boolean ignoreConstField)
    {
        this.ignoreConstField = ignoreConstField;
    }

    public CacheType getCacheType()
    {
        return cacheType;
    }

    public void setCacheType(CacheType cacheType)
    {
        this.cacheType = cacheType;
    }

    public WrapChain getWrapChain()
    {
        return wrapChain;
    }

    @Override
    public String toString()
    {
        return "CodecConfig [hasTotalLength=" + hasTotalLength
                + ", endianType=" + endianType + ", autoLength=" + autoLength
                + ", ignoreObjectAutoLength=" + ignoreObjectAutoLength
                + ", ignoreStaticField=" + ignoreStaticField
                + ", ignoreConstField=" + ignoreConstField + ", encoding="
                + encoding + "]";
    }

}
