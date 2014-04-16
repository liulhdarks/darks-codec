package darks.codec.examples.fieldseq;

import darks.codec.type.OCObject;
import darks.codec.type.OCString;

public class FieldCodecMsg extends OCObject
{
    public FieldCodecMsg()
    {
        setFieldSequence(new String[]{"id", "version", "command"});
    }
    OCString command = new OCString();
    int id;
    byte version;
}
