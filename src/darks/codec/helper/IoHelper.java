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

package darks.codec.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import darks.codec.logs.Logger;

/**
 * 
 * IoUtils.java
 * @version 1.0.0
 * @author Liu lihua
 */
public final class IoHelper
{

    private static final Logger log = Logger.getLogger(IoHelper.class);

	private IoHelper()
	{
	}

	public static void closeIO(InputStream ins)
	{
		try
		{
			if (ins != null)
			{
				ins.close();
			}
		}
		catch (Exception e)
		{
			log.error("Closing IO error. Cause " + e.getMessage(), e);
		}
	}

	public static void closeIO(OutputStream os)
	{
		try
		{
			if (os != null)
			{
				os.close();
			}
		}
		catch (Exception e)
		{
			log.error("Closing IO error. Cause " + e.getMessage(), e);
		}
	}

	public static void closeIO(Reader reader)
	{
		try
		{
			if (reader != null)
			{
				reader.close();
			}
		}
		catch (Exception e)
		{
			log.error("Closing IO error. Cause " + e.getMessage(), e);
		}
	}

	public static void closeIO(Writer writer)
	{
		try
		{
			if (writer != null)
			{
				writer.close();
			}
		}
		catch (Exception e)
		{
			log.error("Closing IO error. Cause " + e.getMessage(), e);
		}
	}
	
	public static byte[] getObjectBytes(Object obj) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try
		{
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.flush();
			return baos.toByteArray();
		}
		finally
		{
			IoHelper.closeIO(oos);
		}
	}
}
