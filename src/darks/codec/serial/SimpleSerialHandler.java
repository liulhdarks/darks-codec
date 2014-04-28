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

package darks.codec.serial;

import darks.codec.ObjectCoder;

/**
 * Serial object to bytes by simple structure. It can reduce bytes length and
 * improve coding efficient.
 * 
 * SimpleSerialHandler.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 */
public class SimpleSerialHandler implements SerialHandler
{

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] encode(ObjectCoder coder, Object obj) throws Exception
    {
        if (obj == null)
        {
            return null;
        }
        SimpleSerialStruct struct = new SimpleSerialStruct();
        struct.objClass.setValue(obj.getClass());
        struct.bytesObject.setObject(obj);
        return coder.encode(struct);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object decode(ObjectCoder coder, byte[] bytes) throws Exception
    {
        if (bytes == null)
        {
            return null;
        }
        SimpleSerialStruct struct = new SimpleSerialStruct();
        coder.decode(bytes, struct);
        return struct.bytesObject.getObject();
    }

}
