package darks.codec.type;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.Encoder;
import darks.codec.exceptions.OCException;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;

public class OCMap<K, E> extends OCBase implements Map<K, E>
{
    
    /**
     * ×¢ÊÍÄÚÈÝ
     */
    private static final long serialVersionUID = 6262137524025156151L;
    
    Map<K, E> map = null;
    
    Map<K, List<E>> mapList = null;
    
    public OCMap()
    {
        map = new HashMap<K, E>();
        mapList = new HashMap<K, List<E>>();
    }
    
    @Override
    public int size()
    {
        return map.size();
    }
    
    @Override
    public boolean isEmpty()
    {
        return map.isEmpty();
    }
    
    @Override
    public boolean containsKey(Object key)
    {
        return map.containsKey(key);
    }
    
    @Override
    public boolean containsValue(Object value)
    {
        return map.containsValue(value);
    }
    
    @Override
    public E get(Object key)
    {
        return map.get(key);
    }
    
    @Override
    public E put(K key, E value)
    {
        List<E> list = mapList.get(key);
        if (list == null)
        {
            list = new ArrayList<E>();
            mapList.put(key, list);
        }
        list.add(value);
        return map.put(key, value);
    }
    
    @Override
    public E remove(Object key)
    {
        mapList.remove(key);
        return map.remove(key);
    }
    
    @Override
    public void putAll(Map<? extends K, ? extends E> m)
    {
        map.putAll(m);
    }
    
    @Override
    public void clear()
    {
        map.clear();
    }
    
    @Override
    public Set<K> keySet()
    {
        return map.keySet();
    }
    
    @Override
    public Collection<E> values()
    {
        return map.values();
    }
    
    @Override
    public Set<Entry<K, E>> entrySet()
    {
        return map.entrySet();
    }
    
    public List<E> getKeyList(K key)
    {
        List<E> list = mapList.get(key);
        if (list == null)
        {
            list = new ArrayList<E>();
            mapList.put(key, list);
        }
        return list;
    }
    
    public Set<Map.Entry<K, List<E>>> getKeyListEntrySet()
    {
        return mapList.entrySet();
    }
    
    public Collection<List<E>> getKeyListValues()
    {
        return mapList.values();
    }
    
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
    
    @SuppressWarnings("unchecked")
    @Override
    public void readObject(Decoder decoder, BytesInputStream in,
            CodecParameter param) throws IOException
    {
        readAutoLength(decoder, in, param);
        Class<K> keyType = (Class<K>)param.getGenericType(0);
        Class<E> valueType =
            (Class<E>)param.getGenericType(1);
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
        Class<E> valueType, CodecParameter param)
        throws IOException
    {
        K key = getGenerticValue(decoder, in, keyType, param);
        E val = getGenerticValue(decoder, in, valueType, param);
        if (key != null && val != null)
        {
            put(key, val);
        }
    }
    
    @Override
    public String toString()
    {
        return "OCMap [map=" + map + "]";
    }
    
}
