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

import java.lang.reflect.Field;

import darks.codec.CodecConfig.EndianType;
import darks.codec.coder.FinalEncodeQueue;
import darks.codec.coder.cache.Cache;
import darks.codec.helper.StringHelper;

/**
 * Codec parameter. The parameter will be available in current flow. It's not
 * global parameters.
 * 
 * CodecParameter.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class CodecParameter
{

    /**
     * Codec config
     */
    private CodecConfig codecConfig;

    /**
     * Whether current endian is little-endian.
     */
    private boolean littleEndian;

    /**
     * Whether current configuration set automatic object length.
     */
    private boolean autoLength = false;

    /**
     * Whether ignore object type's automatic length, when autoLength is true.
     */
    private boolean ignoreObjectAutoLength = false;

    /**
     * String object encoding.
     */
    private String encoding;

    /**
     * Current cache
     */
    private Cache cache;

    /**
     * Current encoding object's field.
     */
    private Field currentfield;

    /**
     * Final encoding queue will be called in final encoding handle.
     */
    private FinalEncodeQueue finalQueue;

    /**
     * Construct parameter through global {@linkplain darks.codec.CodecConfig
     * CodecConfig}.
     * 
     * @param codecConfig Global codec configuration.
     * @param cache Current cache object.
     */
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

    public Field getCurrentfield()
    {
        return currentfield;
    }

    public void setCurrentfield(Field currentfield)
    {
        this.currentfield = currentfield;
    }

    public FinalEncodeQueue getFinalQueue()
    {
        return finalQueue;
    }

    public void setFinalQueue(FinalEncodeQueue finalQueue)
    {
        this.finalQueue = finalQueue;
    }

    @Override
    public String toString()
    {
        return StringHelper.buffer("CodecParameter [codecConfig=", codecConfig,
                ", littleEndian=", littleEndian, ", autoLength=", autoLength,
                ", ignoreObjectAutoLength=", ignoreObjectAutoLength,
                ", encoding=", encoding, ", cache=", cache, ", currentfield=",
                currentfield, ", finalQueue=", finalQueue, ']');
    }

}
