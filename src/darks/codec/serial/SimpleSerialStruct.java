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

import darks.codec.type.OCClass;
import darks.codec.type.OCInt32;
import darks.codec.type.OCObject;

/**
 * Simple serial struct
 * SimpleStruct.java
 * @version 1.0.0
 * @author Liu lihua
 */
public class SimpleSerialStruct extends OCObject
{

    OCInt32 classLength = new OCInt32();
    
    OCClass objClass = new OCClass(classLength);

    OCInt32 bytesLength = new OCInt32();
    
    OCObject bytesObject = new OCObject(objClass, bytesLength);

    public SimpleSerialStruct()
    {
        setFieldSequence(new String[]{"classLength", "objClass", "bytesLength", "bytesObject"});
    }
}
