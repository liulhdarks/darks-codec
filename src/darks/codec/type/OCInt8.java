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
 * 8-bits integer just like short type.
 * 
 * OCInt8.java
 * 
 * @see OCInteger
 * @version 1.0.0
 * @author Liu lihua
 */
@CodecType
public class OCInt8 extends OCInteger
{
    
    public OCInt8()
    {
        super(0, 1);
    }

    /**
     * Construct 8-bits integer by initialize value.
     * 
     * @param val 8-bits integer Value
     */
    public OCInt8(int val)
    {
        super(val, 1);
    }

    /**
     * Construct 8-bits integer by length type object.
     * 
     * @param lenType Length type
     */
    public OCInt8(OCInteger lenType)
    {
        super(lenType);
        setLength(1);
        lenType.setValue(1);
    }
    
}
