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
import darks.codec.wrap.Wrapper;

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

    public enum TotalLengthType
    {
        AUTO, BODY, HEAD_BODY
    }
    
    private TotalLengthType totalLengthType = TotalLengthType.AUTO;

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
        if (totalLengthType == TotalLengthType.AUTO)
        {
            if (autoLength && !ignoreObjectAutoLength)
            {
                return true;
            }
            return false;
        }
        else
        {
            return true;
        }
    }
    
    public TotalLengthType getTotalLengthType()
    {
        return totalLengthType;
    }

    public void setTotalLengthType(TotalLengthType totalLengthType)
    {
        this.totalLengthType = totalLengthType;
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

    public void addWrap(Wrapper wrap)
    {
        wrapChain.add(wrap);
    }

    @Override
    public String toString()
    {
        return "CodecConfig [totalLengthType=" + totalLengthType
                + ", endianType=" + endianType + ", autoLength=" + autoLength
                + ", ignoreObjectAutoLength=" + ignoreObjectAutoLength
                + ", ignoreStaticField=" + ignoreStaticField
                + ", ignoreConstField=" + ignoreConstField + ", encoding="
                + encoding + "]";
    }

}
