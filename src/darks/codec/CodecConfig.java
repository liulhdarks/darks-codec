package darks.codec;

import darks.codec.type.OCInteger;


public class CodecConfig
{

    public enum EndianType
    {
        LITTLE, BIG
    }
    
    protected OCInteger identifier;
    
    private boolean hasIdentifier = true;

    private boolean hasTotalLength = true;
    
    private boolean hasHeader = true;
    
    private EndianType endianType = EndianType.LITTLE;
    
    private boolean autoLength = false;
    
    private boolean ignoreObjectAutoLength = false;
    
    public CodecConfig()
    {
        
    }

    public OCInteger getIdentifier()
    {
        return identifier;
    }

    public void setIdentifier(OCInteger identifier)
    {
        this.identifier = identifier;
        setHasIdentifier(true);
    }

    public boolean isHasIdentifier()
    {
        return hasIdentifier && (identifier != null);
    }

    public void setHasIdentifier(boolean hasIdentifier)
    {
        this.hasIdentifier = hasIdentifier;
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

    public boolean isHasHeader()
    {
        return hasHeader;
    }

    public void setHasHeader(boolean hasHeader)
    {
        this.hasHeader = hasHeader;
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

}
