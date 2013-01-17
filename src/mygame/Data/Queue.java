package mygame.Data;

import java.lang.reflect.Array;

public class Queue<T> {
    
    public int length, size,head,tail;
    public T[] buffer;  
    public boolean isEmpty, isFull;
    
    public Queue(Class<T> c, int _size)
    {
        length=0; tail=0; head=size-1;
        size=_size;
        buffer = (T[]) Array.newInstance(c,size);
    }
   
    public boolean IsEmpty()
    {
        return length == 0;
    }
 
    public boolean IsFull()
    {
        return length == size;
    }
    
    
    public T Dequeue()
    {
        if (!isEmpty)
        {
        T dequeued = (T)buffer[tail];
        buffer[tail]=null;
        tail = NextPosition(tail);
        length--;
        return dequeued;
        }
        return null;
    }
    
    public void Enqueue(T toAdd)
    {
        head = NextPosition(head);
        buffer[head] = toAdd;
        if (isFull)
        {
              head = NextPosition(head);
        }
        length++;
    }    

private int NextPosition(int position)
{
    return (position + 1) % size;
}
    
    public T Top()
    {
           return (T)buffer[head];
    }
    
    public void delete(int in)
    {  
        for(int i=0;i<size;i++)
        {
            if(i==in)
            {
                Dequeue();
            }
            else
            {
                Enqueue(Dequeue());
            }
        }
    }
        
    public int FindPlayer(int _in)
    {
        for(int i=0;i<size;i++)
        {
            if(buffer[i]!=null)
            {
                if(_in==((PlayerData)buffer[i]).getID())
                {
                   return i;
                }
            }
        }
        return -1;
    }
    
    public int FindPlayer(String _in)
    {
        for(int i=0;i<size;i++)
        {
            if(buffer[i]!=null)
            {
                if(_in.equals(((PlayerData)buffer[i]).getName()))
                {
                   return i;
                }
            }
        }
        return -1;
    }
    
    public int FindItem(int _in)
    {
        for(int i=0;i<size;i++)
        {
            if(buffer[i]!=null)
            {
                if(_in==((Item)buffer[i]).getID())
                {
                   return i;
                }
            }
        }
        return -1;
    }
    
    
}
