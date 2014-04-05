package darks.codec.wrap.zip;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

import darks.codec.helper.IoHelper;

public class CommonsCompress extends ZipCompress
{
    
    private CompressorStreamFactory factory = new CompressorStreamFactory();
    
    private String type;
    
    public CommonsCompress()
    {
        this.type = CompressorStreamFactory.GZIP;
    }
    
    public CommonsCompress(String type)
    {
        this.type = type;
    }
    
    
    @Override
    public void compress(InputStream input, OutputStream out)
            throws IOException
    {
        CompressorOutputStream cos = null;
        try
        {
            cos = factory.createCompressorOutputStream(type, out);
            byte[] buf = new byte[1024];
            int len;
            while ((len = input.read(buf)) > 0)
            {
                cos.write(buf, 0, len);
            }
            cos.flush();
        }
        catch (CompressorException e)
        {
            throw new IOException("Fail to compress data by commons compress. Cause " + e.getMessage(), e);
        }
        finally
        {
            IoHelper.closeIO(cos);
        }
    }

    @Override
    public void uncompress(InputStream input, OutputStream out)
            throws IOException
    {
        CompressorInputStream cin = null;
        try
        {
            cin = factory.createCompressorInputStream(type, input);
            byte[] buf = new byte[1024];
            int len;
            while ((len = cin.read(buf)) > 0)
            {
                out.write(buf, 0, len);
            }
            out.flush();
        }
        catch (CompressorException e)
        {
            throw new IOException("Fail to decompress data by commons compress. Cause " + e.getMessage(), e);
        }
        finally
        {
            IoHelper.closeIO(cin);
        }

    }

}
