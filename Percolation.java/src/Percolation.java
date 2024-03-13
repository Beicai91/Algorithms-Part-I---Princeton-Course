import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] site;
    private int openCount;
    private int length;
    private WeightedQuickUnionUF unionFind;
    private WeightedQuickUnionUF unionFind2;
    private int ele;
    private int virtualTop;
    private int virtualBottom;


    // create n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        if (n <= 0)
        {
            throw new IllegalArgumentException("n should be greater than 0");
        } 
        site = new boolean[n][n];
        openCount = 0;
        length = n;
        unionFind = new WeightedQuickUnionUF(n * n + 1);
        unionFind2 = new WeightedQuickUnionUF(n * n + 2);
        virtualTop = n * n;
        virtualBottom = n * n + 1;
    }

    //check if the site is out of range
    private boolean checkRange(int row, int col)
    {
        if (row < 1 || row > length || col < 1 || col > length)
            return false;
        else
            return true;
    }

    private int index(int row, int col)
    {
        ele = (row - 1) * length + (col - 1);
        return (ele);
    }
    
    //opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        if (checkRange(row, col)) {
            if (!isOpen(row, col))
            {
                site[row - 1][col - 1] = true;
                openCount++;

                if (row == 1){
                    unionFind.union(index(1, col), virtualTop);
                    unionFind2.union(index(1, col), virtualTop);
                }

                if (row == length)
                    unionFind2.union(index(length, col), virtualBottom);

                if (checkRange(row - 1, col) && isOpen(row - 1, col)){
                        unionFind.union(index(row, col), index(row - 1, col));
                        unionFind2.union(index(row, col), index(row - 1, col));
                }
                if (checkRange(row + 1, col) && isOpen(row + 1, col)){
                        unionFind.union(index(row, col), index(row + 1, col));
                        unionFind2.union(index(row, col), index(row + 1, col));
                }
                if (checkRange(row, col - 1) && isOpen(row, col - 1)){
                        unionFind.union(index(row, col), index(row, col - 1));
                        unionFind2.union(index(row, col), index(row, col - 1));
                }
                if (checkRange(row, col + 1) && isOpen(row, col + 1)){
                        unionFind.union(index(row, col), index(row, col + 1));
                        unionFind2.union(index(row, col), index(row, col + 1));
                }
            }
        }
        else
            throw new IllegalArgumentException("invalid site to open");
    }


    //is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        if (checkRange(row, col))
            return site[row - 1][col - 1]; 
        else
            throw new IllegalArgumentException();
    }

    //is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        if (checkRange(row, col))
            return unionFind.find(index(row, col)) == unionFind.find(virtualTop);
        else
            throw new IllegalArgumentException("Invalide site to judge");
        
    }

    //returns the number of open sites
    public int numberOfOpenSites()
    {
        return openCount;
    }

    //does the system percolate?
    public boolean percolates(){
        if (unionFind2.find(virtualTop) == unionFind2.find(virtualBottom))
            return true;
        else
            return false;
    }

    //test client (optional)
    /* 
    public static void main (String [] args){
        int n = 5;
        int row;
        int col;
        int openCount;

        Percolation test = new Percolation(n);
        while(!test.percolates())
        {
            row = StdRandom.uniformInt(1, n+1);
            StdOut.println(row);
            col = StdRandom.uniformInt(1, n+1);
            StdOut.println(col);
            test.open(row, col);
        }
        openCount = test.numberOfOpenSites();
        StdOut.println(openCount);

    }
    */
    
}