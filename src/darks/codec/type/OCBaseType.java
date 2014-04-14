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

package darks.codec.type;

import darks.codec.annotations.CodecType;
import darks.codec.helper.StringHelper;

/**
 * Base type abstract class.
 * 
 * OCBaseType.java
 * 
 * @see CodecType
 * @version 1.0.0
 * @author Liu lihua
 * @param <T>
 */
@CodecType
public abstract class OCBaseType<T> extends OCBase
{

    private T value;

    private int length = -1;

    public OCBaseType()
    {
    }

    /**
     * Initialize value
     * 
     * @param value Default value
     */
    public OCBaseType(T value)
    {
        this.value = value;
    }

    public OCBaseType(T value, int length)
    {
        this.value = value;
        this.length = length;
    }

    /**
     * Construct base type by length type object.
     * 
     * @param lenType Length type
     */
    public OCBaseType(OCInteger lenType)
    {
        super(lenType);
    }

    /**
     * Construct base type by length type object.
     * 
     * @param value Value 
     * @param lenType Length type
     */
    public OCBaseType(T value, OCInteger lenType)
    {
        super(lenType);
        this.value = value;
    }

    public T getValue()
    {
        return value;
    }

    public T getValue(T defaultValue)
    {
        if (value == null)
        {
            return defaultValue;
        }
        return value;
    }

    public void setValue(T value)
    {
        this.value = value;
    }

    public int getLength()
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

    public OCBaseType<T> clone(OCBaseType<T> target)
    {
        if (target == null)
        {
            return null;
        }
        target.length = length;
        target.value = value;
        return target;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return StringHelper.buffer("OCBaseType [value=", value, ", length=",
                length, ']');
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OCBaseType<?> other = (OCBaseType<?>) obj;
        if (value == null)
        {
            if (other.value != null)
                return false;
        }
        else if (!value.equals(other.value))
            return false;
        return true;
    }

}
