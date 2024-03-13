import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> 
{
    private Item [] s;
    private int len;
    private int n;
    private int randomIndex;
    private int sampleIndex;

    // construct an empty randomized queue
    public RandomizedQueue()
    {
        s = (Item[]) new Object[1];
        len = 1;
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty()
    {
        if (n > 0)
            return false;
        else
            return true;
    }

    // return the number of items on the randomized queue
    public int size()
    {
        return n;
    }

    private void resize(int capacity)
    {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++)
            copy[i] = s[i];
        s = copy;
    }
    // add the item
    public void enqueue(Item item)
    {
        if (item == null)
            throw new IllegalArgumentException();
        if (n == len)
        {
            len = len * 2;
            resize(len);
        }
        s[n] = item;
        n++;
    }

    // remove and return a random item
    public Item dequeue()
    {
        if(isEmpty())
            throw new NoSuchElementException();
        randomIndex = StdRandom.uniformInt(n);
        Item    item = s[randomIndex];
        while (randomIndex != n - 1)
        {
            s[randomIndex] = s[randomIndex + 1];
            randomIndex = randomIndex + 1;
        }
        s[randomIndex] = null;
        n--;
        if (n > 0 && n == len / 4)
        {
            len = len / 2;
            resize(len);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample()
    {
        if(isEmpty())
            throw new NoSuchElementException();
        sampleIndex = StdRandom.uniformInt(n);
        Item item = s[sampleIndex];
        return item;
    }

    private class arrayIterator implements Iterator<Item>
    {
        private int index;
        private Item[] copy;

        public arrayIterator()
        {
            index = 0;
            copy = (Item[]) new Object[n]; 
            for (int i = 0; i < n; i++)
            {
                copy[i] = s[i];
            }
            StdRandom.shuffle(copy);
        }

        public boolean hasNext()
        {
            if (index < n)
                return true;
            else
                return false;
        }

        public Item next()
        {
            

            if (!hasNext())
                throw new NoSuchElementException();
            Item item = copy[index];
            index++;
            //StdOut.println(index);
            return item;
        }
    }
    // return an independant iterator over items in random order
    public Iterator<Item> iterator()
    {
        return new arrayIterator();
    }

    // unit testing (required)
    public static void main(String [] args)
    {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        int itemNum;
        String randomItem;

        itemNum = rq.size();
        StdOut.println("The initial array has" + itemNum + " items.");

        // first
        rq.enqueue(args[0]);
        rq.enqueue(args[1]);
        rq.enqueue(args[2]);
        rq.enqueue(args[3]);

        itemNum = rq.size();
        StdOut.println("The array has " + itemNum + "items. They are ");
        Iterator<String> itr = rq.iterator();
        while (itr.hasNext())
        {
            StdOut.println(itr.next());
        }
        randomItem = rq.sample();
        StdOut.println("A random item in the array is " + randomItem);

        //second
        StdOut.println("remove randomly " + rq.dequeue());
        StdOut.println("remove randomly " + rq.dequeue());

        itemNum = rq.size();
        StdOut.println("The array has " + itemNum + "items. They are ");
        itr = rq.iterator();
        while (itr.hasNext())
        {
            StdOut.println(itr.next());
        }
        
        // third
        rq.enqueue(args[4]);
        StdOut.println("remove randomly " + rq.dequeue());
        rq.enqueue(args[5]);
        rq.enqueue(args[6]);
        StdOut.println("remove randomly " + rq.dequeue());
        itemNum = rq.size();
        StdOut.println("The array has " + itemNum + "items. They are ");
        itr = rq.iterator();
        while (itr.hasNext())
        {
            StdOut.println(itr.next());
        }
    }
    
}
