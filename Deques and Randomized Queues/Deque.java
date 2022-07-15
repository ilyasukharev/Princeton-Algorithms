import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item>
{
    private Node first, last;

    private class Node
    {
        Node next;
        Node previous;
        Item item;
    }
    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;

        public boolean hasNext()
        {
            return current != null;
        }
        public Item next()
        {
            if (current == null)
            {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
        public void remove()
        {
            throw new UnsupportedOperationException();
        }

    }
    // construct an empty deque
    public Deque()
    {

    }

    private void isArgumentEmpty(Item item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException();
        }
    }

    // is the deque empty?
    public boolean isEmpty()
    {
        return first == null && last == null;
    }

    // return the number of items on the deque
    public int size()
    {
        Iterator<Item> it = iterator();
        int counter = 0;
        while (it.hasNext())
        {
            it.next();
            counter++;
        }
        return counter;
    }

    // add the item to the front
    public void addFirst(Item item)
    {
        isArgumentEmpty(item);
        boolean state = isEmpty();
        Node old = first;
        first = new Node();
        first.item = item;
        first.next = null;
        if (!isEmpty() && !state)
        {
            if (old != null)
            {
                old.previous = first;
            }
            first.next = old;
        }
        else
        {
            last = first;
        }
    }

    // add the item to the back
    public void addLast(Item item)
    {
        isArgumentEmpty(item);
        boolean state = isEmpty();
        Node old = last;
        last = new Node();
        last.item = item;
        if (!isEmpty() && !state)
        {
            old.next = last;
            last.previous = old;
        }
        else first = last;
    }

    // remove and return the item from the front
    public Item removeFirst()
    {
        if (!isEmpty())
        {
            Item returnItem = first.item;
            first = first.next;
            if (first == null)
            {
                last = null;
            }
            return returnItem;
        }
        else throw new NoSuchElementException();
    }


    // remove and return the item from the back
    public Item removeLast()
    {
        if (!isEmpty())
        {
            Item returnItem = last.item;
            Node old = last.previous;
            if (old == null)
            {
                first = last = null;
            }
            else
            {
                last = old;
                old.next = null;
            }
            return returnItem;
        }
        else throw new NoSuchElementException();
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator()
    {
        return new ListIterator();
    }

    // unit testing (required)
    public static void main(String[] args)
    {
    }
}
