
import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class PointSET 
{
    //instance variable
    private final SET<Point2D> points;

    //construct an empty set of points
    public PointSET()
    {
        this.points = new SET<>();
    }

    //is the set empty?
    public boolean isEmpty()
    {
        return (points.isEmpty());
    }

    //number of points in the set
    public int size()
    {
        return (points.size());
    }

    //add the point to the set (if it's not already in the set)
    public void insert(Point2D p)
    {
        if (p == null)
            throw new IllegalArgumentException();
        if (!points.contains(p))
            points.add(p);
    }

    //does the set contain point p?
    public boolean contains(Point2D p)
    {
        if (p == null)
            throw new IllegalArgumentException();
        return (points.contains(p));
    }

    //draw all points to standard draw
    public void draw()
    {
        for (Point2D p: points)
            StdDraw.point(p.x(), p.y());
    }

    //all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect)
    {
        if (rect == null)
            throw new IllegalArgumentException();
        List<Point2D> pIn = new ArrayList<>();
        for (Point2D p : points)
        {
            if (rect.contains(p))
                pIn.add(p);
        }
        return (pIn);
    }

    //a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p)
    {
        if (p == null)
            throw new IllegalArgumentException();
        if (size() == 0)
            return (null);
        Point2D target = points.iterator().next();
        for (Point2D curP : points)
        {
            if (curP.distanceSquaredTo(p) < target.distanceSquaredTo(p))
                target = curP;
        }
        return (target);
    }

    //unit testing of the methods
    public static void main(String[] args)
    {
        Point2D p = new Point2D(0.0, 0.0);
        PointSET points = new PointSET();
        points.insert(new Point2D(0.1, 0.1));
        points.insert(new Point2D(0.2, 0.4));
        points.insert(new Point2D(0.7, 0.9));
        Point2D nearest = points.nearest(p);
        StdOut.println("Nearest point to p is " + nearest.toString());
    }
}
