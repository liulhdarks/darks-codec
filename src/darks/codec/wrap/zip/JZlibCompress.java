package darks.codec.wrap.zip;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.jcraft.jzlib.GZIPInputStream;
import com.jcraft.jzlib.GZIPOutputStream;

import darks.codec.helper.IoHelper;

public class JZlibCompress extends ZipCompress
{

    @Override
    public void compress(InputStream input, OutputStream out)
            throws IOException
    {
        GZIPOutputStream gzipOut = null;
        try
        {
            gzipOut = new GZIPOutputStream(out);
            byte[] buf = new byte[1024];
            int len;
            while ((len = input.read(buf)) > 0)
            {
                gzipOut.write(buf, 0, len);
            }
            gzipOut.flush();
        }
        finally
        {
            IoHelper.closeIO(gzipOut);
        }
    }

    @Override
    public void uncompress(InputStream input, OutputStream out)
            throws IOException
    {
        GZIPInputStream gzipIn = null;
        try
        {
            gzipIn = new GZIPInputStream(input);
            byte[] buf = new byte[1024];
            int len;
            while ((len = gzipIn.read(buf)) > 0)
            {
                out.write(buf, 0, len);
            }
            out.flush();
        }
        finally
        {
            IoHelper.closeIO(gzipIn);
        }

    }

}
