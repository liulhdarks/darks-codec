package darks.codec.type;

import java.io.IOException;

import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.Encoder;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;

public class OCChoice<T> extends OCBase
{
    /**
     * ×¢ÊÍÄÚÈÝ
     */
    private static final long serialVersionUID = -1128977532526843170L;
    
    private OCBase selected;
    
    public OCChoice()
    {
        
    }
    
    @Override
    public void writeObject(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws IOException
    {
        if (selected != null)
        {
            selected.writeObject(encoder, out, param);
        }
    }

    
    
    @Override
    public void readObject(Decoder decoder, BytesInputStream in,
            CodecParameter param) throws IOException
    {
        
    }

    public OCBase getSelected()
    {
        return selected;
    }
    
    public void setSelected(OCBase selected)
    {
        this.selected = selected;
    }
    
}
