package darks.codec;

import java.io.IOException;

import darks.codec.type.OCObject;

public abstract class OCCodec
{

    
    protected CodecConfig codecConfig;
    
    public OCCodec(CodecConfig codecConfig)
    {
        this.codecConfig = codecConfig;
    }
    
    public abstract byte[] encode(OCObject msg)
        throws IOException;
    
    public abstract OCObject decode(byte[] bytes, OCObject source)
        throws IOException;

    public CodecConfig getCodecConfig()
    {
        return codecConfig;
    }

    public void setCodecConfig(CodecConfig codecConfig)
    {
        this.codecConfig = codecConfig;
    }
    
    
}
