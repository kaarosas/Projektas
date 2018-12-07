package projektas;

import java.util.*;
import java.io.PrintStream;


public class RingBufferFixedArray<E extends Comparable<E>> extends AbstractList<E>
        implements RandomAccess, RingBufferClassic<E>{
    
    private static PrintStream ro = System.out;
    
    private final int n;
    private final List<E> buf;
    private int head = 0;
    private int tail = 0;
    private int size = 0;
    
    public RingBufferFixedArray(int capacity)
    {
        n = capacity + 1;
        buf = new ArrayList<>(Collections.nCopies(n, (E) null));
    }

    RingBufferFixedArray()
    {
        this(64);
    }
    
    protected void println()
    {
        ro.println("RingBuffer contents:");
        if (tail <= 0)
        {
            ro.println("Ring has no elements");
        }
        else
        {
            for (int i = 0; i <= tail - 1; i++)
            {
                ro.println(buf.get(i));
            }
            ro.println("--------------------");
        }
    }
    
    @Override
    public boolean isEmpty()
    {
        return head == tail;
    }
    
    public boolean isFull()
    {
        if (head == 0 || tail == n)
        {
            return tail == capacity() - 1;
        }
        return head - tail == 1;
    }
    
    @Override
    public void clear()
    {
        for (int count = 0; count != n - 1; count++)
        {
            this.buf.set(count, null);
        }
        this.tail = 0;
    }
    
    private int wrapIndex(int i)
    {
        int m = i % n;
        if (m < 0)
        {
            m += n;
        }
        return m;
    }

    private void shift(int startIndex, int endIndex)
    {
        assert (endIndex > startIndex);
        for (int i = endIndex - 1; i >= startIndex; i--)
        {
            set(i + 1, get(i));
        }
    }
 
    
    @Override
    public int capacity()
    {
        return n - 1;
    }
    
    @Override
    public int size()
    {
        return tail - head + (tail < head ? n : 0);
    }

    @Override
    public E get(int index)
    {
        if (index < 0 || index >= size())
        {
            throw new IndexOutOfBoundsException();
        }
        return buf.get(wrapIndex(head + index));
    }
    
    @Override
    public E set(int i, E e)
    {
        if (i < 0 || i >= n-1)
        {
            throw new IndexOutOfBoundsException();
        }
        return buf.set(wrapIndex(head - size + i), e);
    }
    
    @Override
    public void add(int i, E e)
    {
        int s = size();
        if (s == n - 1)
        {
            throw new IllegalStateException("RingBuffer is filled. "
                    + "Please remove elements from the front before"
                    + " adding more elements to the back.");
        }
        if (i < 0 || i > s)
        {
            throw new IndexOutOfBoundsException();
        }
        tail = wrapIndex(tail + 1);
        if (i < s)
        {
            shift(i, s);
        }
        set(i, e);
    }
    
    
    @Override
    public E remove(int i)
    {
        int s = size();
        if (i < 0 || i >= s)
        {
            throw new IndexOutOfBoundsException();
        }
        E e = get(i);
        if (i > 0)
        {
            shift(0, i);
        }
        head = wrapIndex(head + 1);
        return e;
    }


    
    public void sort()
    {
        List<E> all = asList(Collections.emptyList());
        Collections.<E>sort(all);
        head = tail = 0;
        addAll(all);
    }
    
    public List<E> asList(List<E> a)
    {
        List<E> list = new ArrayList<>(size());
        for (int i = head; i < tail; i++)
        {
            list.add(buf.get(i));
        }
        return list;
    }
    
    public void addAll(List<? extends E> toAdd)
    {
        if (toAdd.size() > capacity() - size())
        {
            throw new RuntimeException("Not enough space to add all elements.");
        }
        else
        {
            for (int i = 0; i < toAdd.size(); i++)
            {
                buf.set(tail, toAdd.get(i));
                tail++;
            }
        }
    }
    
    public E pop()
    {
        if (isEmpty())
        {
            throw new RuntimeException("RingBuffer underflow");
        }
        E item = buf.get(head);
        buf.set(head, null);
        size--;
        head = (head + 1) % n;
        return item;
    }
    
    public void push(E item)
    {
        if (tail == n - 1)
        {
            throw new RuntimeException("RingBuffer overflow");
        }
        head = (head + 1) % n;
        buf.set(head, item);
        size++;
    }
    
    public E getOldest()
    {
        int i = wrapIndex(head + 1);
        
        for (;;i = wrapIndex(++i))
        {
            if (buf.get(i) != null) break;
            if (i == head)
            {
                throw new IllegalStateException("Cannot remove element.");
            }           
        }
        return buf.get(i);
    }
    
    public E getNewest()
    {
        int i = wrapIndex(head);
        if (buf.get(i) == null)
        {
            throw new IndexOutOfBoundsException("Ring Buffer is empty.");
        }
        return buf.get(i);
    }
    
    public E removeOldest() 
    {
        int i = wrapIndex(head++);
        
        for (;;i = wrapIndex(++i))
        {
            if (buf.get(i) != null) break; 
            if (i == head)
            {
                throw new IllegalStateException("Cannot remove element. List is empty.");
            }         
        }
        return buf.set(i, null);
    }
    
    public void insert(E e)
    {
        if (e == null)
        {
            throw new IllegalStateException("Item is null.");
        }
        if (tail != n)
        {
            buf.set(tail, e);
            tail++;
        }
        else    
        {
            E temp = getOldest();
            if (buf.contains(temp));
            {
                removeOldest();
                if (buf.contains(null))
                {
                    int ind = buf.indexOf(null);
                    buf.set(ind, e);
                }
            }
        }
    }
}
    
   
