package darks.codec;

import java.io.IOException;

import darks.codec.iostream.BytesOutputStream;

public abstract class Encoder
{

    public abstract void encodeObject(BytesOutputStream out, Object obj, CodecParameter param)
            throws IOException;

}
