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

package darks.codec.coder.cache;

import java.lang.reflect.Field;

/**
 * Cache strategy.
 * 
 * CacheStrategy.java
 * @see Cache
 * @see SoftRefStrategy
 * @see StrongRefStrategy
 * @version 1.0.0
 * @author Liu lihua
 */
public interface CacheStrategy
{
    /**
     * Get object's fields from cache.
     * 
     * @param clazz Target class.
     * @return Fields arrays.
     */
    public Field[] getCacheFields(Class<?> clazz);

    /**
     * Put object's fields to cache.
     * 
     * @param clazz Target class
     * @param fields Class's mapping fields.
     */
    public void putCacheFields(Class<?> clazz, Field[] fields);
}
