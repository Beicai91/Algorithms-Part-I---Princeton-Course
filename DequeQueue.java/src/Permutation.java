import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int j; 

        RandomizedQueue<String> rq = new RandomizedQueue<>();

        while(!StdIn.isEmpty())
            rq.enqueue(StdIn.readString());
        
        
        Iterator<String> itr = rq.iterator();
        j = Integer.parseInt(args[0]);
        if (j < 0 || j > rq.size())
            throw new IllegalArgumentException();

        while (itr.hasNext() && j > 0)
        {
            StdOut.println(itr.next());
            j--;
        }
    }
}