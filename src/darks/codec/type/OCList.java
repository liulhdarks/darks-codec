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
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import darks.codec.CodecParameter;
import darks.codec.Decoder;
import darks.codec.Encoder;
import darks.codec.exceptions.OCException;
import darks.codec.iostream.BytesInputStream;
import darks.codec.iostream.BytesOutputStream;

public class OCList<E> extends OCBase implements List<E>
{

    /**
     */
    private static final long serialVersionUID = 6262137524025156151L;

    private List<E> list;

    public OCList()
    {
        list = new LinkedList<E>();
    }

    public OCList(List<E> list)
    {
        this.list = list;
    }

    public OCList(Collection<? extends E> c)
    {
        list = new LinkedList<E>(c);
    }

    public OCList(OCInteger lenType)
    {
        super(lenType);
        list = new LinkedList<E>();
    }

    public OCList(List<E> list, OCInteger lenType)
    {
        super(lenType);
        this.list = list;
    }

    @Override
    public int size()
    {
        return list.size();
    }

    @Override
    public boolean isEmpty()
    {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o)
    {
        return list.contains(o);
    }

    @Override
    public Iterator<E> iterator()
    {
        return list.iterator();
    }

    @Override
    public Object[] toArray()
    {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a)
    {
        return list.toArray(a);
    }

    @Override
    public boolean add(E e)
    {
        return list.add(e);
    }

    @Override
    public boolean remove(Object o)
    {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c)
    {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c)
    {
        return list.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
        return list.retainAll(c);
    }

    @Override
    public void clear()
    {
        list.clear();
    }

    @Override
    public E get(int index)
    {
        return list.get(index);
    }

    @Override
    public E set(int index, E element)
    {
        return list.set(index, element);
    }

    @Override
    public void add(int index, E element)
    {
        list.add(index, element);
    }

    @Override
    public E remove(int index)
    {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o)
    {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o)
    {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator()
    {
        return list.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index)
    {
        return list.listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex)
    {
        return list.subList(fromIndex, toIndex);
    }

    @Override
    public void writeObject(Encoder encoder, BytesOutputStream out,
            CodecParameter param) throws IOException
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

    @SuppressWarnings("unchecked")
    @Override
    public void readObject(Decoder decoder, BytesInputStream in,
            CodecParameter param) throws IOException
    {
        readAutoLength(decoder, in, param);
        Class<E> genericType = (Class<E>) param.getGenericType(0);
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
            Class<E> genericType, CodecParameter param) throws IOException
    {
        E obj = getGenerticValue(decoder, in, genericType, param);
        if (obj != null)
        {
            add(obj);
        }
    }

    @Override
    public String toString()
    {
        return "OCList [list=" + list + "]";
    }

}
