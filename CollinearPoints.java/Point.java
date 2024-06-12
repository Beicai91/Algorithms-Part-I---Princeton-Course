import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        double  slope;
        
        if (this.y == that.y && this.x != that.x)
            slope = +0.0;
        else if (this.y == that.y && this.x == that.x)
            slope = Double.NEGATIVE_INFINITY;
        else if (this.y != that.y && this.x == that.x)
            slope = Double.POSITIVE_INFINITY;
        else
            slope = (double)(that.y - this.y) / (that.x - this.x);
        return (slope);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        int res;

        res = 1000;
        if (this.x == that.x && this.y == that.y)
            res = 0;
        if (this.y < that.y || (this.y == that.y && this.x < that.x))
            res = -1;
        if (this.y > that.y || (this.y == that.y && this.x > that.x))
            res = 1;
        return (res);
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            public int compare(Point p1, Point p2)
            {
                double slope1 = slopeTo(p1);
                double slope2 = slopeTo(p2);
                if (slope1 > slope2)
                    return (1);
                else if (slope1 < slope2)
                    return (-1);
                else
                    return (0);
            }
        };
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* 
        List<Point> points = new ArrayList<>();
        points.add(new Point(15, 20));
        points.add(new Point(10, 20));
        points.add(new Point(25, 30));
        Point p_origin = new Point(0, 0);
        int origin_p0;
        double slopeP1_2;
        double s_order;
        Comparator<Point> slopeComparator;

        origin_p0 = p_origin.compareTo(points.get(0));
        slopeP1_2 = points.get(1).slopeTo(points.get(2));
        slopeComparator = p_origin.slopeOrder();
        s_order = slopeComparator.compare(points.get(0), points.get(1));
        */
    }
}
