package darks.codec.wrap.zip;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class ZipCompress
{
    
    public abstract void compress(InputStream input, OutputStream out) throws IOException;
    
    public abstract void uncompress(InputStream input, OutputStream out) throws IOException;
    
}
