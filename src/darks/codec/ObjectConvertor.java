package darks.codec;

import java.io.IOException;

import darks.codec.coder.DefaultOCCodec;
import darks.codec.type.OCObject;

public class ObjectConvertor
{
    
    private OCCodec codec;
    
    private CodecConfig codecConfig;
    
    
    public ObjectConvertor()
    {
        initialize();
        codec = new DefaultOCCodec(codecConfig);
    }
    
    public ObjectConvertor(OCCodec codec)
    {
        initialize();
        this.codec = codec;
        if (codec.getCodecConfig() == null)
        {
            codec.setCodecConfig(codecConfig);
        }
    }
    
    private void initialize()
    {
        codecConfig = new CodecConfig();
    }
    
    public byte[] encode(OCObject msg)
        throws IOException
    {
        if (codec == null)
        {
            return null;
        }
        return codec.encode(msg);
    }
    
    public OCObject decode(byte[] bytes, OCObject source)
        throws IOException
    {
        if (codec == null)
        {
            return null;
        }
        return codec.decode(bytes, source);
    }
    
    public OCCodec getCodec()
    {
        return codec;
    }
    
    public void setCodec(OCCodec codec)
    {
        this.codec = codec;
    }

    public CodecConfig getCodecConfig()
    {
        return codecConfig;
    }
    
}
