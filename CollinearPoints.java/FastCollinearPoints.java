import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MergeX;

public class FastCollinearPoints {
    private final LineSegment[] segments;

   public FastCollinearPoints(Point[] points){
        int len = points.length;
        Point[] pointsDup = new Point[len];
        double slope_ref;
        int storage_idx;
        int point_counter;
        Point[][] storage = new Point[len * len][2];
        int seg_idx;
        int k;

        //check for invalid point
        for (int i = 0; i < points.length; i++)
        {
            if (points[i] == null)
                throw new IllegalArgumentException("One of the input points is NULL.");
        }
        //check for duplication
        for (int j = 0; j < points.length; j++)
        {
            for (int t = j + 1; t < points.length; t++)
            {
                if (points[j].compareTo(points[t]) == 0)
                    throw new IllegalArgumentException("Two input points are equal.");
            }
        }
        //sort the points firstly
        Arrays.sort(points);

        //test
        //StdOut.println("Sorted points are\n");
        //for (int t = 0; t < len; t++)
            //StdOut.println(points[t]);
        //

        storage_idx = 0;
        for (int i = 0; i < len; i++)
        {
            //duplicate points[] and use pointsDup for sorting
            for (int m = 0; m < len; m++)
                pointsDup[m] = points[m];

            //sort pointsDup according to the slope between the points and the anchor point. Slope between anchor point and its duplication in pointsDup will be negative infinity
            MergeX.sort(pointsDup, points[i].slopeOrder());

            //test
            //StdOut.println("\nanchor point is\n");
            //StdOut.println(points[i]);
            //StdOut.println("\nsorted duplicated points\n");
            //for (int t = 0; t < len; t++)
                //StdOut.println(pointsDup[t]);
            //

            k = 1;
            while (k < len)
            {
                slope_ref = points[i].slopeTo(pointsDup[k]); //points[i] is the anchor point

                //test
                //StdOut.println("\nSlope reference is\n");
                //StdOut.println(slope_ref);
                //

                point_counter = 0;
                while (k < len && points[i].slopeTo(pointsDup[k]) == slope_ref)
                {
                    //test
                    //StdOut.println("\nslope bwteewn anchor point and this point \n");
                    //StdOut.println(points[i].slopeTo(pointsDup[k]));
                    //
                    point_counter++;
                    k++;
                }
                if (point_counter >= 3)
                {
                    storage[storage_idx][0] = points[i];
                    //test
                    //StdOut.println("\nSegment starts\n");
                    //StdOut.println(points[i]);
                    //
                    storage[storage_idx][1] = pointsDup[k - 1];
                    //test
                    //StdOut.println("\nSegement ends\n");
                    //StdOut.println(pointsDup[k - 1]);
                    //
                    storage_idx++;
                }
            }
        }

        //test
        //StdOut.println("\nstorage idx\n");
        //StdOut.println(storage_idx);
        //

        //remove duplicated segments ex. P0234, P3024, P2023 and P4023 and set the segments variable of this class
        
        Point[][] storageReduced = new Point[storage_idx][2];
        int idx = 0;
        int reduced_idx = 0;
        while (idx < storage_idx)
        {
            if (checker(idx, storage) == 0)
            {
                storageReduced[reduced_idx][0] = storage[idx][0];
                storageReduced[reduced_idx][1] = storage[idx][1];
                reduced_idx++;
            }
            idx++;
        }

        //test
        //for (int i = 0; i < reduced_idx; i++)
        //{
            //StdOut.println("\nprinted out points in storage\n");
            //StdOut.println(storageReduced[i][0]);
            //StdOut.println(storageReduced[i][1]);
        //}
        //


        seg_idx = 0;
        k = 0;
        segments = new LineSegment[reduced_idx];
        while (k < reduced_idx)
        {
            segments[seg_idx] = new LineSegment(storageReduced[k][0], storageReduced[k][1]);
            seg_idx++;
            k++;
        }
        
        //test
        //int u = 0;
        //while (u < reduced_idx)
        //{
            //StdOut.println(segments[u]);
            //u++;
        //}
        //

   }

   //helper method for checking duplicated segments used in constructor
    private int  checker(int k, Point[][] storage)
    {
        int j;
    
        j = k - 1;
        double slope1 = storage[k][0].slopeTo(storage[k][1]);
        /*/test
        StdOut.println("\ncurrent segment points\n");
        StdOut.println(storage[k][0]);
        StdOut.println(storage[k][1]);
        */
        while (j >= 0)
        {
            double slope2 = storage[j][0].slopeTo(storage[j][1]);
            /*/test
            StdOut.println("\nprevious segment points\n");
            StdOut.println(storage[j][0]);
            StdOut.println(storage[j][1]);
            StdOut.println("\ncompare two points of current segment and previous segment\n");
            StdOut.println(storage[k][0].compareTo(storage[j][1]) == 0);
            StdOut.println(storage[k][1].compareTo(storage[j][1]) == 0);
            StdOut.println(slope1);
            StdOut.println(slope2);
            */
            if (slope1 == slope2 && (storage[k][0].compareTo(storage[j][1]) == 0 || storage[k][1].compareTo(storage[j][1]) == 0))
                return (1);
            j--;
        }
        return (0);
    }


   //function to get the number of segments
    public           int numberOfSegments()
    {
        int num;
        
        num = 0;
        while (segments[num] != null)
            num++;
        return (num);

    } 

   //getter function
    public LineSegment[] segments()
    {
        return (segments);
    }
    public static void main(String[] args) 
    {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
