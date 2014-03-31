package darks.codec;

import java.lang.reflect.Type;

import darks.codec.CodecConfig.EndianType;

public class CodecParameter
{

    private Type[] genericType;
    
    private CodecConfig codecConfig;
    
    private boolean littleEndian;
    
    private boolean autoLength = false;
    
    private boolean ignoreObjectAutoLength = false;
    
    public CodecParameter(CodecConfig codecConfig)
    {
        this.codecConfig = codecConfig;
        littleEndian = (codecConfig.getEndianType() == EndianType.LITTLE);
        autoLength = codecConfig.isAutoLength();
        ignoreObjectAutoLength = codecConfig.isIgnoreObjectAutoLength();
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

}
