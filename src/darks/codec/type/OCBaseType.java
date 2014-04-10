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

@CodecType
public abstract class OCBaseType<T> extends OCBase
{
    
    private T value;
    
    private int length = -1;
    
    public OCBaseType()
    {
        super();
    }
    
    public OCBaseType(T value)
    {
        super();
        this.value = value;
    }
    
    public OCBaseType(T value, int length)
    {
        super();
        this.value = value;
        this.length = length;
    }
    
    public OCBaseType(OCInteger lenType)
    {
        super(lenType);
    }
    
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
    
    @Override
    public String toString()
    {
        return "OCType [value=" + value + ", length=" + length + "]";
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OCBaseType<?> other = (OCBaseType<?>)obj;
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
