import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int last = 0;
    private Item[] arr = (Item[]) new Object[last];

    private class RandomQueueIterator implements Iterator<Item>
    {
        int current = last;
        public boolean hasNext()
        {
            return current > 0;
        }
        public Item next()
        {
            if (current <= 0)
            {
                throw new NoSuchElementException();
            }
            Item item = arr[--current];
            return item;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue()
    {

    }

    // is the randomized queue empty?
    public boolean isEmpty()
    {
        return last == 0;
    }

    // return the number of items on the randomized queue
    public int size()
    {
        return last;
    }

    // add the item
    public void enqueue(Item item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException();
        }
        if (last == arr.length)
        {
            resize((arr.length + 1) * 2);
        }
        arr[last++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }
        Item randomItem = sample();
        Iterator<Item> it = iterator();
        Item[] newArr = (Item[]) new Object[last - 1];
        int startInd = last - 1;
        while (it.hasNext())
        {
            Item item = it.next();
            if (item != randomItem)
            {
                newArr[--startInd] = item;
            }
        }
        arr = newArr;
        last = arr.length;
        return randomItem;
    }

    // return a random item (but do not remove it)
    public Item sample()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException();
        }
        int randomIndex = StdRandom.uniform(0, last);
        return arr[randomIndex] == null ? sample() : arr[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator()
    {
        return new RandomQueueIterator();
    }
    private void resize(int capacity)
    {
        Item[] newArr = (Item[]) new Object[capacity];
        for (int i = 0; i < arr.length; i++)
        {
            newArr[i] = arr[i];
        }
        arr = newArr;
    }

    // unit testing (required)
    public static void main(String[] args)
    {
    }

}