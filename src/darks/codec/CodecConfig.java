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
 * Codec configuration object.
 * 
 * CodecConfig.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class CodecConfig
{

    /**
     * Endian type
     * 
     * @version 1.0.0
     * @author Liu lihua
     */
    public enum EndianType
    {
        LITTLE, BIG
    }

    /**
     * Cache type
     * 
     * @version 1.0.0
     * @author Liu lihua
     */
    public enum CacheType
    {
        /**
         * Don't use cache object.
         */
        NONE,
        /**
         * Global cache object is available in all coders.
         */
        GLOBAL,
        /**
         * Local cache object is only available in current coder.
         */
        LOCAL
    }

    /**
     * Total length type
     * 
     * @version 1.0.0
     * @author Liu lihua
     */
    public enum TotalLengthType
    {
        /**
         * Automatic object total length. It calculate object length without
         * head or tail wrapper's length only when autoLength is true.
         */
        AUTO,
        /**
         * Body total length will calculate object length and tail wrapper's
         * length without head wrapper's length.
         */
        BODY,
        /**
         * Head and body total length will calculate head wrapper, tail wrapper
         * and object length.
         */
        HEAD_BODY
    }

    /**
     * Total length type. Default AUTO
     */
    private TotalLengthType totalLengthType = TotalLengthType.AUTO;

    /**
     * Endian type. Default little-endian.
     */
    private EndianType endianType = EndianType.LITTLE;

    /**
     * Whether add type length automatically. Default false.
     */
    private boolean autoLength = false;

    /**
     * Whether ignore object type's automatic length, when autoLength is true.
     * Default false.
     */
    private boolean ignoreObjectAutoLength = false;

    /**
     * Whether ignore object's static fields when get object's fields. Default
     * false.
     */
    private boolean ignoreStaticField = false;

    /**
     * Whether ignore object's constant fields when get object's fields. Default
     * true.
     */
    private boolean ignoreConstField = true;

    /**
     * Cache type. Default LOCAL.
     */
    private CacheType cacheType = CacheType.LOCAL;

    /**
     * String object encoding. Default null.
     */
    private String encoding;

    /**
     * Wrapper chain. It will be called before and after encoding or decoding.
     */
    private WrapChain wrapChain = new WrapChain();

    public CodecConfig()
    {

    }

    /**
     * Check current setting whether total length exists.
     * 
     * @return If exists, return true. Otherwise return false.
     */
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
