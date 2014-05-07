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
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;

/**
 * 
 * ByteArrayType.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class ByteArrayType extends BaseType
{
	
	BaseTypeBox typeBox;

	public ByteArrayType(BaseTypeBox typeBox)
	{
		this.typeBox = typeBox;
	}

    /**
     * {@inheritDoc}
     */
    @Override
    public void encode(Encoder encoder, BytesOutputStream out, Object obj, CodecParameter param)
            throws Exception
    {
        if (obj != null)
        {
        	if (typeBox == BaseTypeBox.BOX)
        	{
                Byte[] vs = (Byte[]) obj;
                out.writeInt(vs.length);
                for (Byte v : vs)
                {
                	if (v == null)
                	{
                		v = 0;
                	}
                	out.write(v);
                }
        	}
        	else
        	{
        		byte[] vs = (byte[]) obj;
                out.writeInt(vs.length);
                for (int v : vs)
                {
                	out.write(v);
                }
        	}
        }
        else
        {
            out.writeInt(0);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object decode(Decoder decoder, BytesInputStream in, Object obj, CodecParameter param)
            throws Exception
    {
        int len = in.readInt();
        if (len <= 0)
        {
        	return null;
        }

    	if (typeBox == BaseTypeBox.BOX)
    	{
    		Byte[] vs = new Byte[len];
            for (int i = 0; i < len; i++)
            {
            	int v = in.read();
            	vs[i] = (byte) v;
            }
            return vs;
    	}
    	else
    	{
    		byte[] vs = new byte[len];
            for (int i = 0; i < len; i++)
            {
            	int v = in.read();
            	vs[i] = (byte) v;
            }
            return vs;
    	}
    }

}
