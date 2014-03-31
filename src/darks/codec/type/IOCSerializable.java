package darks.codec.type;

import java.io.IOException;
import java.io.Serializable;

import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.Encoder;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;

public interface IOCSerializable extends Serializable
{

    public void writeObject(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws IOException;

    public void readObject(Decoder decoder, BytesInputStream in,
            CodecParameter param) throws IOException;
}
