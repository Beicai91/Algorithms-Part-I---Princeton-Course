import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

    private Percolation test;
    private int testNum;
    private double sampleMean;
    private double sampleStandDevi;
    private double confiLo;
    private double confiHi;
    private double[] pArray;
    private int row;
    private int col;
    private int len;



   //perform independant trials on an n-by-n grid
   public PercolationStats(int n, int trials)
   {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        len = n;
        testNum = trials;
        sampleMean = 0;
        sampleStandDevi = 0;
        confiLo = 0;
        confiHi = 0;
        pArray = new double[testNum];
        for (int i = 0; i < testNum; i++)
        {
            test = new Percolation(n);
            pArray[i] = getArrayele();
        }

   }

   private double getArrayele()
   {
        double eleValue;
        while(!test.percolates())
        {
            row = StdRandom.uniformInt(1, len + 1);
            //StdOut.println("row" + row);
            col = StdRandom.uniformInt(1, len + 1);
            //StdOut.println("col" + col);
            test.open(row, col);
    }
        //StdOut.println(test.numberOfOpenSites());
        eleValue = (double)test.numberOfOpenSites() / (len * len);
        return eleValue;
        
   }
   
   //sample mean of percolation threshold
   public double mean()
   {
        sampleMean = StdStats.mean(pArray);
        return sampleMean;
   }

   //sample standard deviation of percolation threshold
   public double stddev()
   {
        sampleStandDevi = StdStats.stddev(pArray);
        return sampleStandDevi;
   }

   //low endpoint of 95% condifence interval
   public double confidenceLo()
   {
        confiLo = mean() - (1.96 * stddev()) / Math.sqrt(testNum);
        return confiLo;
   }

   //high endpoint of 95% condifence interval
   public double confidenceHi()
   {
        confiHi = mean() + (1.96 * stddev()) / Math.sqrt(testNum);
        return confiHi;
   }

   //test client
   public static void main(String [] args)
   {
        double getMean;
        double getDev;
        double getLo;
        double getHi;

        PercolationStats getStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        getMean = getStats.mean();
        getDev = getStats.stddev();
        getLo = getStats.confidenceLo();
        getHi = getStats.confidenceHi();

        StdOut.println("mean                    = " + getMean);
        StdOut.println("stddev                  = " + getDev);
        StdOut.println("95% confidence interval = [" + getLo + ", " + getHi + "]");
   }
}
