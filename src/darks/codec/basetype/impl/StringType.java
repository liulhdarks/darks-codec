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

package darks.codec.basetype.impl;

import java.io.IOException;

import darks.codec.CodecParameter;
import darks.codec.basetype.BaseType;
import darks.codec.helper.ByteHelper;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;
import darks.codec.logs.Logger;

public class StringType extends BaseType
{
    
    private static Logger log = Logger.getLogger(StringType.class);
    
    @Override
    public void encode(BytesOutputStream out, Object obj, CodecParameter param)
    {
        String s = (String)obj;
        try
        {
            byte[] bytes = ByteHelper.convertString(s, param.getEncoding());
            writeAutoLength(out, bytes.length, param);
            out.write(bytes);
        }
        catch (IOException e)
        {
            log.error(e.getMessage(), e);
        }
    }
    
    @Override
    public Object decode(BytesInputStream in, Object obj, CodecParameter param)
    {
        try
        {
            int len = in.available();
            if (len <= 0)
            {
                return null;
            }
            len = readAutoLength(in, param);
            int ioLen = in.available();
            len = (len < 0 || ioLen < len) ? ioLen : len;
            byte[] bytes = ByteHelper.readBytes(in, len, false);
            return ByteHelper.convertToString(bytes, param.getEncoding());
        }
        catch (IOException e)
        {
            log.error(e.getMessage(), e);
            return null;
        }
    }
    
}
