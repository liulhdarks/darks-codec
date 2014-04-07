/**
 * 
 *Copyright 2014 The Darks Codec Project (Liu lihua)
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */

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
