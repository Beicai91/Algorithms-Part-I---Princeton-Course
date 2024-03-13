import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> 
{
    private Node    first;
    private Node    last;
    private Node    replacer;
    private int     itemNum;

    private class Node
    {
        Item    item;
        Node    next;
    }
    // construct an empty deque
    public Deque()
    {
        this.first = null;
        this.last = null;
        this.replacer = null;
        this.itemNum = 0;
    }

    // is the deque empty?
    public boolean isEmpty()
    {
        if (size() == 0)
            return true;
        else
            return false;
    }

    // return the number of items on the deque
    public int size()
    {
        return itemNum;
    }

    // add the item to the front
    public void addFirst(Item item)
    {
        if (item == null)
            throw new IllegalArgumentException();
        if (isEmpty())
        {
            first = new Node();
            first.item = item;
            first.next = null;
            last = first;
        }
        else
        {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
        }
        itemNum++;
    }

    // add the item to the back
    public void addLast(Item item)
    {
        if (item == null)
            throw new IllegalArgumentException();
        if (isEmpty())
        {
            last = new Node();
            last.item = item;
            last.next = null;
            first = last;
        }
        else
        {
            Node oldlast = last;
            last = new Node();
            last.item = item;
            oldlast.next = last;
        }
        itemNum++;
    }

    // remove and return the item from the front
    public Item removeFirst()
    {
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = first.item;
        if (size() == 1)
        {
            first = null;
            last = null;
        }
        else
        {
            first = first.next;
        }
        itemNum--;
        return item;
    }

    // remove Item removeLast()
    public Item removeLast()
    {
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = last.item;
        if (size() == 1)
        {
            first = null;
            last = null;
        }
        else
        {
            replacer = first;
            for (int i = 1; i < size() - 1; i++)
            {
                replacer = replacer.next;
            }
            last = replacer;
            last.next = null;
        }
        itemNum--;
        return item;
        
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator()
    {
        return new listIterator();
    }

    private class listIterator implements Iterator<Item>
    {
        private Node    current;

        public listIterator()
        {
            this.current = first;
        }

        public boolean hasNext()
        {
            return current != null;
        }

        public Item next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();   
        }
    }

    //unit testing (required)
    public static void main(String[] args)
    {
        Deque<String> deque = new Deque<>();
        int itemNum;
        itemNum = deque.size();
        StdOut.println("The initial list has " + itemNum + " element.");

        //first list
        deque.addFirst(args[0]);
        deque.addFirst(args[1]);
        deque.addLast(args[2]);
        deque.addLast(args[3]);

        itemNum = deque.size();
        StdOut.println("The list has " + itemNum + " elements now. They are ");
        Iterator<String> iterator = deque.iterator();
        while (iterator.hasNext())
        {
            String element = iterator.next();
            StdOut.println(element);
        }
        
        // second list
        StdOut.println("remove " + deque.removeFirst());
        StdOut.println("remove " + deque.removeLast());

        itemNum = deque.size();
        StdOut.println("The list has " + itemNum + " elements now. They are ");
        iterator = deque.iterator();
        while (iterator.hasNext())
        {
            String element = iterator.next();
            StdOut.println(element);
        }

        // third list
        deque.addLast(args[4]);
        deque.addLast(args[5]);
        StdOut.println("remove " + deque.removeFirst());
        StdOut.println("remove " + deque.removeLast());

        itemNum = deque.size();
        StdOut.println("The list has " + itemNum + " elements now. They are ");
        iterator = deque.iterator();
        while (iterator.hasNext())
        {
            String element = iterator.next();
            StdOut.println(element);
        }

        //fourth list
        StdOut.println("remove " + deque.removeFirst());;
        deque.addLast(args[6]);
        StdOut.println("remove " + deque.removeLast());
        boolean result = deque.isEmpty();

        itemNum = deque.size();
        StdOut.println("The list is now empty?" + result);
        StdOut.println("The list has " + itemNum + " elements now. They are");
        iterator = deque.iterator();
        while (iterator.hasNext())
        {
            String element = iterator.next();
            StdOut.println(element);
        }

        iterator.remove();
    }
}
