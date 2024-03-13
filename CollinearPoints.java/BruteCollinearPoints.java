import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final LineSegment[] segments;

    public   BruteCollinearPoints(Point[] points){
        int segmentsNum = 0;
        int len = points.length;
        Point[][] storage = new Point[len * len][2];

        //check for invalid point
        for (int i = 0; i < points.length; i++)
        {
            if (points[i] == null)
                throw new IllegalArgumentException("One of the input points is NULL.");
        }
        //check for duplication
        for (int j = 0; j < points.length; j++)
        {
            for (int k = j + 1; k < points.length; k++)
            {
                if (points[j].compareTo(points[k]) == 0)
                    throw new IllegalArgumentException("Two input points are equal.");
            }
        }

        //sort the points firstly so that the segment will be represented by the smallest and biggest point
        Arrays.sort(points);

        //check for collinear points
        for (int p = 0; p < points.length; p++)
        {
            for (int q = p + 1; q < points.length; q++)
            {
                for (int r = q + 1; r < points.length; r++)
                {
                    for (int s = r + 1; s < points.length; s++)
                    {
                        double s_pq = points[p].slopeTo(points[q]);
                        double s_pr = points[p].slopeTo(points[r]);
                        double s_ps = points[p].slopeTo(points[s]);

                        if (Double.compare(s_pq, s_pr) == 0 && Double.compare(s_pq, s_ps) == 0 && Double.compare(s_pr, s_ps) == 0)
                        {
                            storage[segmentsNum][0] = points[p];
                            storage[segmentsNum][1] = points[s];
                            segmentsNum++;
                        }
                    }
                }
            }
        }
        //test
        //for (int i = 0; i < segmentsNum; i++)
        //{
            //StdOut.println("\nprinted out points in storage\n");
            //StdOut.println(storage[i][0]);
            //StdOut.println(storage[i][1]);
        //}
        //

        //set the value of the class variable which is an array of objects of class LineSegment
        segments = new LineSegment[segmentsNum];
        for (int i = 0; i < segmentsNum; i++)
            segments[i] = new LineSegment(storage[i][0], storage[i][1]);

    }

    //other methods of this class
    public   int numberOfSegements()
    {
        return (segments.length);
    }
   
    public   LineSegment[] segments()
    {
        return segments;
    }
    public static void main(String[] args) {

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
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
}
}
