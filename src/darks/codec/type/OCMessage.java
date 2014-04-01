package darks.codec.type;

import java.io.IOException;

import darks.codec.CodecConfig;
import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.Encoder;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;

public class OCMessage extends OCObject
{
    /**
     * 
     */
    private static final long serialVersionUID = -3217240880451032242L;


    protected OCInteger identifier;

    protected OCInt32 totalLength = new OCInt32();

    protected OCObject ocObject;

    private Object object;

    public OCMessage()
    {

    }

    public OCMessage(OCObject ocObject)
    {
        this.ocObject = ocObject;
    }

    public OCMessage(Object object)
    {
        this.object = object;
    }

    public OCMessage(int identifier)
    {
        this.identifier = new OCInt16(identifier);
    }
    
    @Override
    public void writeObject(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws IOException
    {
        CodecConfig cfg = param.getCodecConfig();
        if (cfg.isHasIdentifier())
        {
            identifier = new OCInteger();
            cfg.getIdentifier().clone(identifier);
            identifier.writeObject(encoder, out, param);
        }
        if (cfg.isHasTotalLength())
        {
            totalLength.writeObject(encoder, out, param);
        }
        if (ocObject != null)
        {
            if (cfg.isHasTotalLength())
            {
                ocObject.setLenType(totalLength);
            }
            ocObject.writeObject(encoder, out, param);
        }
        else if (object != null)
        {
            int start = out.size();
            encoder.encodeObject(out, object, param);
            int end = out.size();
            if (cfg.isHasTotalLength())
            {
                totalLength.setValue(end - start);
                int pos = totalLength.getBytePos();
                out.setCursor(pos);
                totalLength.writeObject(encoder, out, param);
                out.moveLast();
            }
        }
    }

    @Override
    public void readObject(Decoder decoder, BytesInputStream in,
            CodecParameter param) throws IOException
    {
        CodecConfig cfg = param.getCodecConfig();
        if (cfg.isHasIdentifier())
        {
            identifier = new OCInteger();
            cfg.getIdentifier().clone(identifier);
            identifier.readObject(decoder, in, param);
        }
        if (cfg.isHasTotalLength())
        {
            totalLength.readObject(decoder, in, param);
        }
        if (ocObject != null)
        {
            if (cfg.isHasTotalLength())
            {
                ocObject.setLenType(totalLength);
            }
            ocObject.readObject(decoder, in, param);
        }
        else if (object != null)
        {
            decoder.decodeObject(in, object, param);
        }
    }

    @Override
    public String toString()
    {
        return "OCMessage [identifier=" + identifier + ", totalLength="
                + totalLength + ", ocObject=" + ocObject + ", object=" + object
                + "]";
    }
    
    
}