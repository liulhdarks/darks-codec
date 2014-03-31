package darks.codec;

import java.io.IOException;

import darks.codec.iostream.BytesInputStream;

public abstract class Decoder
{
    
    public abstract Object decodeObject(BytesInputStream in, Object obj, CodecParameter param)
        throws IOException;

}
