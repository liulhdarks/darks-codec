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

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.Encoder;
import darks.codec.annotations.CodecType;
import darks.codec.exceptions.DecodingException;
import darks.codec.exceptions.OCException;
import darks.codec.helper.ReflectHelper;
import darks.codec.helper.StringHelper;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;

/**
 * Store Key-value pair values.
 * 
 * OCMap.java
 * 
 * @version 1.0.0
 * @author Liu lihua
 * @param <K>
 * @param <E>
 */
@CodecType
public class OCMap<K, E> extends OCBase implements Map<K, E>
{

    Map<K, E> map = null;

    public OCMap()
    {
        map = new HashMap<K, E>();
    }

    public OCMap(Map<K, E> map)
    {
        this.map = map;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size()
    {
        return map.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty()
    {
        return map.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsKey(Object key)
    {
        return map.containsKey(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsValue(Object value)
    {
        return map.containsValue(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E get(Object key)
    {
        return map.get(key);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public E put(K key, E value)
    {
        return map.put(key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E remove(Object key)
    {
        return map.remove(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putAll(Map<? extends K, ? extends E> m)
    {
        map.putAll(m);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear()
    {
        map.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<K> keySet()
    {
        return map.keySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<E> values()
    {
        return map.values();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Entry<K, E>> entrySet()
    {
        return map.entrySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeObject(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws IOException
    {
        if (map == null)
        {
            throw new OCException("OCMap collection is null");
        }
        writeAutoLength(encoder, out, param);
        out.moveLast();
        for (Entry<K, E> entry : map.entrySet())
        {
            encoder.encodeObject(out, entry.getKey(), param);
            encoder.encodeObject(out, entry.getValue(), param);
        }
        writeDynamicLength(size(), encoder, out, param);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void readObject(Decoder decoder, BytesInputStream in,
            CodecParameter param) throws IOException
    {
        Type[] types = ReflectHelper.getGenericTypes(param.getCurrentfield());
        if (types == null)
        {
            throw new DecodingException("Map generic type is null");
        }
        readAutoLength(decoder, in, param);
        Class<K> keyType = (Class<K>) types[0];
        Class<E> valueType = (Class<E>) types[1];
        if (isDynamicLength())
        {
            int len = getLenType().getValue();
            for (int i = 0; i < len && in.available() > 0; i++)
            {
                parse(decoder, in, keyType, valueType, param);
            }
        }
        else
        {
            while (in.available() > 0)
            {
                parse(decoder, in, keyType, valueType, param);
            }
        }
    }

    private void parse(Decoder decoder, BytesInputStream in, Class<K> keyType,
            Class<E> valueType, CodecParameter param) throws IOException
    {
        K key = getGenerticValue(decoder, in, keyType, param);
        E val = getGenerticValue(decoder, in, valueType, param);
        if (key != null && val != null)
        {
            put(key, val);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return StringHelper.buffer("OCMap [map=", map, ']');
    }

}
