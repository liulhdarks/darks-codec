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

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

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
 * Indicate the list collect object.
 * 
 * OCList.java
 * 
 * @see OCBase
 * @version 1.0.0
 * @author Liu lihua
 * @param <E>
 */
@CodecType
public class OCList<E> extends OCBase implements List<E>
{

    private List<E> list;

    public OCList()
    {
        list = new LinkedList<E>();
    }

    /**
     * Construct list by java list.
     * 
     * @param list Java list
     */
    public OCList(List<E> list)
    {
        this.list = list;
    }


    /**
     * Construct list by java collection.
     * 
     * @param c Java collection
     */
    public OCList(Collection<? extends E> c)
    {
        list = new LinkedList<E>(c);
    }

    /**
     * Construct list object by length type object.
     * 
     * @param lenType Length type
     */
    public OCList(OCInteger lenType)
    {
        super(lenType);
        list = new LinkedList<E>();
    }


    /**
     * Construct list object by list object and length type object.
     * 
     * @param list Java list
     * @param lenType Length type
     */
    public OCList(List<E> list, OCInteger lenType)
    {
        super(lenType);
        this.list = list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size()
    {
        return list.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty()
    {
        return list.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Object o)
    {
        return list.contains(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<E> iterator()
    {
        return list.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object[] toArray()
    {
        return list.toArray();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T[] toArray(T[] a)
    {
        return list.toArray(a);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(E e)
    {
        return list.add(e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object o)
    {
        return list.remove(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAll(Collection<?> c)
    {
        return list.containsAll(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(Collection<? extends E> c)
    {
        return list.addAll(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c)
    {
        return list.addAll(index, c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAll(Collection<?> c)
    {
        return list.removeAll(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean retainAll(Collection<?> c)
    {
        return list.retainAll(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear()
    {
        list.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E get(int index)
    {
        return list.get(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E set(int index, E element)
    {
        return list.set(index, element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(int index, E element)
    {
        list.add(index, element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E remove(int index)
    {
        return list.remove(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int indexOf(Object o)
    {
        return list.indexOf(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int lastIndexOf(Object o)
    {
        return list.lastIndexOf(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListIterator<E> listIterator()
    {
        return list.listIterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListIterator<E> listIterator(int index)
    {
        return list.listIterator(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex)
    {
        return list.subList(fromIndex, toIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeObject(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws Exception
    {
        if (list == null)
        {
            throw new OCException("OCLinkedList collection is null");
        }
        writeAutoLength(encoder, out, param);
        out.moveLast();
        for (E e : list)
        {
            encoder.encodeObject(out, e, param);
        }
        writeDynamicLength(size(), encoder, out, param);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void readObject(Decoder decoder, BytesInputStream in,
            CodecParameter param) throws Exception
    {
        Type[] types = ReflectHelper.getGenericTypes(param.getCurrentfield());
        if (types == null)
        {
            throw new DecodingException("List generic type is null");
        }
        readAutoLength(decoder, in, param);
        Class<E> genericType = (Class<E>) types[0];
        if (isDynamicLength())
        {
            int len = getLenType().getValue();
            for (int i = 0; i < len && in.available() > 0; i++)
            {
                parse(decoder, in, genericType, param);
            }
        }
        else
        {
            while (in.available() > 0)
            {
                parse(decoder, in, genericType, param);
            }
        }
    }

    private void parse(Decoder decoder, BytesInputStream in,
            Class<E> genericType, CodecParameter param) throws Exception
    {
        E obj = getGenerticValue(decoder, in, genericType, param);
        if (obj != null)
        {
            add(obj);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return StringHelper.buffer("OCList [list=", list, ']');
    }

}
