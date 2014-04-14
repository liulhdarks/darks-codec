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

package darks.codec.type;

import darks.codec.annotations.CodecType;

/**
 * 16-bits integer just like short type.
 * 
 * OCInt16.java
 * 
 * @see OCInteger
 * @version 1.0.0
 * @author Liu lihua
 */
@CodecType
public class OCInt16 extends OCInteger
{

    public OCInt16()
    {
        setLength(2);
    }

    /**
     * Construct 16-bits integer by initialize value.
     * 
     * @param val 16-bits integer Value
     */
    public OCInt16(int val)
    {
        super(val, 2);
    }

    /**
     * Construct 16-bits integer by length type object.
     * 
     * @param lenType Length type
     */
    public OCInt16(OCInteger lenType)
    {
        super(lenType);
        setLength(2);
        lenType.setValue(2);
    }

}
