package darks.codec.examples.serial;



public class SerialMainBean
{
    int id;
    int version;
    SerialSubBean subSerial;
    @Override
    public String toString()
    {
        return "SerialMainBean [id=" + id + ", version=" + version
                + ", subSerial=" + subSerial + "]";
    }
    
}

class SerialSubBean
{
    String equip;
    int code;
    String content;
    
    @Override
    public String toString()
    {
        return "SerialSubBean [equip=" + equip + ", code=" + code
                + ", content=" + content + "]";
    }
}