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

package darks.codec.basetype.impl.array;

import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.Encoder;
import darks.codec.basetype.BaseType;
import darks.codec.helper.ByteHelper;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;

/**
 * 
 * StringArrayType.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class StringArrayType extends BaseType
{

    /**
     * {@inheritDoc}
     */
    @Override
    public void encode(Encoder encoder, BytesOutputStream out, Object obj, CodecParameter param)
            throws Exception
    {
    	if (obj == null)
    	{
    		out.writeInt(0);
    	}
    	else
    	{
            String[] array = (String[]) obj;
    		out.writeInt(array.length);
            for (String s : array)
            {
                byte[] bytes = ByteHelper.convertString(s, param.getEncoding());
                out.writeInt(bytes.length);
                out.write(bytes);
            }
    	}
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object decode(Decoder decoder, BytesInputStream in, Object obj, CodecParameter param)
            throws Exception
    {
    	int arrayLen = in.readInt();
    	if (arrayLen <= 0)
    	{
    		return null;
    	}
    	String[] array = new String[arrayLen];
    	for (int i = 0; i < arrayLen; i++)
    	{
            int strLen = in.readInt();
            byte[] bytes = ByteHelper.readBytes(in, strLen, false);
            if (bytes == null)
            {
                return null;
            }
            array[i] = ByteHelper.convertToString(bytes, param.getEncoding());
    	}
    	return array;
    }

}
