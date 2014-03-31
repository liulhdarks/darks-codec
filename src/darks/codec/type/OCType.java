package darks.codec.type;

public abstract class OCType<T> extends OCBase
{
    
    /**
     * ×¢ÊÍÄÚÈÝ
     */
    private static final long serialVersionUID = 1278146293079114843L;
    
    private T value;
    
    private int length;
    
    public OCType()
    {
        super();
    }
    
    public OCType(T value)
    {
        super();
        this.value = value;
    }
    
    public OCType(T value, int length)
    {
        super();
        this.value = value;
        this.length = length;
    }
    
    public OCType(OCInteger lenType)
    {
        super(lenType);
    }
    
    public OCType(T value, OCInteger lenType)
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
    
    public OCType<T> clone(OCType<T> target)
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
        OCType<?> other = (OCType<?>)obj;
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
