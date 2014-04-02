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

package darks.codec.basetype;

import java.io.IOException;

import darks.codec.CodecParameter;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;

public abstract class BaseType
{
    public abstract void encode(BytesOutputStream out, Object obj, CodecParameter param);
    
    public abstract Object decode(BytesInputStream in, Object obj, CodecParameter param);
    
    protected void writeAutoLength(BytesOutputStream out, int length, CodecParameter param) throws IOException
    {
        if (param.isAutoLength())
        {
            out.writeInt(length);
        }
    }
    
    protected int readAutoLength(BytesInputStream in, CodecParameter param) throws IOException
    {
        if (param.isAutoLength() && in.available() >= 4)
        {
            return in.readInt();
        }
        return -1;
    }
}
