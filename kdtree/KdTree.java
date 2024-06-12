
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.Queue;
import java.util.ArrayList;
import java.util.List;

public class KdTree 
{
    private static class Node
    {
        private Point2D p;
        private Node ln;
        private Node rn;
        private Node parent;
        private int lvl;
        public Node (Point2D p, int lvl, Node parent)
        {
            this.p = p;
            //this.rect = rect;
            this.lvl = lvl;
            this.parent = parent;
        }

    }

    //instance variable of KdTree
    private Node root;
    private int pointsNum;

    //construct an empty set of points
    public KdTree()
    {
        this.root = null;
        this.pointsNum = 0;
    }
    
    //is the set empty?
    public boolean isEmpty()
    {
        if (root == null)
            return (true);
        else
            return (false);
    }

    //number of points in the set
    public int size()
    {
        return (pointsNum);
    }

    //add the point to the set (if it's not already in the set)
    public void insert(Point2D p)
    {
        Node parent;

        if (p == null)
            throw new IllegalArgumentException();
        if (root == null)
        {
            root = new Node(p, 1, null);
            pointsNum++;
            return ;
        }
        parent = getParentNode(root, p);
        //test
        //StdOut.println("Parent node " + parent.p.toString() + "\n" + "Level " + parent.lvl + "\n");
        //
        if (parent == null)
        {
            //test
            //StdOut.println("Parent is null, no inserting\n");
            //
            return ;
        }
        pointsNum++;
        if (parent.lvl % 2 != 0)
        {
            if (p.x() > parent.p.x())
                parent.rn = new Node(p, parent.lvl + 1, parent);
            else
                parent.ln = new Node(p, parent.lvl + 1, parent);
        }
        else
        {
            if (p.y() > parent.p.y())
                parent.rn = new Node(p, parent.lvl + 1, parent);
            else
                parent.ln = new Node(p, parent.lvl + 1, parent);
        }
    }

    private Node getParentNode(Node cur, Point2D p)
    {
        Node parent;
        //test
        //StdOut.println("In getParentNode cur node " + cur.p.toString() + "\n" + "Level " + cur.lvl + "\n");
        //StdOut.println("point to insert " + p.toString());
        //
        //compare x
        if (cur.lvl % 2 != 0)
        {
            if (p.x() > cur.p.x())
            {
                if (cur.p.equals(p))
                    return (null);
                if (cur.rn == null)
                {
                    //test
                    //StdOut.println("return parent node " + cur.p.toString());
                    //
                    return (cur);
                }
                parent = getParentNode(cur.rn, p);
            }
            else
            {
                if (cur.p.equals(p))
                {
                    //test
                    //StdOut.println("Detected identical point " + cur.p.toString());
                    //
                    return (null);
                }
                if (cur.ln == null)
                    return (cur);
                parent = getParentNode(cur.ln, p);
            }
        }
        else
        {
            //compare y
            if (p.y() > cur.p.y())
            {
                if (cur.p.equals(p))
                    return (null);
                if (cur.rn == null)
                {
                    //test
                    //StdOut.println("return parent node " + cur.p.toString());
                    //
                    return (cur);
                }
                parent = getParentNode(cur.rn, p);
            }
            else
            {
                if (cur.p.equals(p))
                    return (null);
                if (cur.ln == null)
                    return (cur);
                parent = getParentNode(cur.ln, p);
            }
        }
        return (parent);
    }

    //does the set contain point p?
    public boolean contains(Point2D p)
    {
        if (p == null)
            throw new IllegalArgumentException();
        if (root == null)
            return (false);
        if (getTargetNode(root, p) != null)
            return (true);
        else
            return (false);
    }

    private Node getTargetNode(Node cur, Point2D p)
    {
        Node target;

        if (cur == null)
            return (null);
        if (cur.p.equals(p))
            return (cur);
        if (cur.lvl % 2 != 0)
        {
            if (p.x() <= cur.p.x())
                target = getTargetNode(cur.ln, p);
            else
                target = getTargetNode(cur.rn, p);
        }
        else
        {
            if (p.y() <= cur.p.y())
                target = getTargetNode(cur.ln, p);
            else
                target = getTargetNode(cur.rn, p);
        }
        return (target);
    }

    //draw all points to standard draw
    public void draw()
    {
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        drawTree(root, rect);
    }

    private void drawTree(Node cur, RectHV rect)
    {
        if (cur == null)
            return ;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(cur.p.x(), cur.p.y());
        StdDraw.setPenRadius();
        if (cur.lvl % 2 != 0)
        {
            //draw vertical lines
            StdDraw.setPenColor(StdDraw.RED);
            if (cur.lvl == 1)
                StdDraw.line(cur.p.x(), rect.ymax(), cur.p.x(), rect.ymin());
            else if (cur.p.y() < cur.parent.p.y())
                StdDraw.line(cur.p.x(), cur.parent.p.y(), cur.p.x(), rect.ymin());
            else
                StdDraw.line(cur.p.x(), rect.ymax(), cur.p.x(), cur.parent.p.y());
        }
        else
        {
            //draw horizontal lines
            StdDraw.setPenColor(StdDraw.BLUE);
            if (cur.p.x() < cur.parent.p.x())
                StdDraw.line(rect.xmin(), cur.p.y(), cur.parent.p.x(), cur.p.y());
            else
                StdDraw.line(cur.parent.p.x(), cur.p.y(), rect.xmax(), cur.p.y());
        }
        drawTree(cur.ln, rect);
        drawTree(cur.rn, rect);
    }

    //all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect)
    {
        //RectHV zone = new RectHV(0.0, 0.0, 1.0, 1.0);
        if (rect == null)
            throw new IllegalArgumentException();
        List<Point2D> pIn = new ArrayList<>();
        populateList(root, rect, pIn);
        return (pIn);
    }

    private void populateList(Node cur, RectHV range, List<Point2D> pIn)
    {
        if (cur == null)
            return ;
        if (range.contains(cur.p))
            pIn.add(cur.p);
        if (cur.lvl % 2 != 0)
        {
            if (cur.p.x() < range.xmin())
                populateList(cur.rn, range, pIn);
            else
                populateList(cur.ln, range, pIn);
        }
        else
        {
            if (cur.p.y() < range.ymin())
                populateList(cur.rn, range, pIn);
            else
                populateList(cur.ln, range, pIn);
        }
    }

    //a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p)
    {
        if (p == null)
            throw new IllegalArgumentException();
        if (root == null)
            return (null);
        Point2D target = findNearest(root, p, root.p);
        return (target);
    }

    private Point2D findNearest(Node cur, Point2D queryP, Point2D closest)
    {
        double dClosest = queryP.distanceTo(closest);
        double dCur = queryP.distanceTo(cur.p);
        if (dCur < dClosest)
            closest = cur.p;
        //test
        //StdOut.println(" At this point Closest " + closest.toString() + "\n");
        //
        if ((cur.lvl % 2 != 0 && queryP.x() < cur.p.x()) || (cur.lvl % 2 == 0 && queryP.y() < cur.p.y()))
        {
            //right/up
            if (cur.ln != null)
            {
                closest = findNearest(cur.ln, queryP, closest);
                dClosest = queryP.distanceTo(closest);
            }
            if (cur.rn != null && cur.lvl % 2 != 0 && dClosest > (cur.p.x() - queryP.x()))
                closest = findNearest(cur.rn, queryP, closest);
            else if (cur.rn != null && cur.lvl % 2 == 0 && dClosest > (cur.p.y() - queryP.y()))
                closest = findNearest(cur.rn, queryP, closest);
        }
        else if (cur.lvl % 2 != 0 && queryP.x() >= cur.p.x() || (cur.lvl % 2 == 0 && queryP.y() >= cur.p.y()))
        {
            //left/down
            if (cur.rn != null)
            {
                closest = findNearest(cur.rn, queryP, closest);
                dClosest = queryP.distanceTo(closest);
                //test
                //StdOut.println("Closest " + closest.toString() + "\n");
                //StdOut.println("dClosest " + dClosest + "\n");
                //StdOut.println("cur node " + cur.p.toString());
                //
            }
            if (cur.ln != null && cur.lvl % 2 != 0 && dClosest > (queryP.x() - cur.p.x()))
                closest = findNearest(cur.ln, queryP, closest);
            else if (cur.ln != null && cur.lvl % 2 == 0 && dClosest > (queryP.y() - cur.p.y()))
            {
                //test
                //StdOut.println("need to search lower side of" + cur.p.toString() + "\n");
                //
                closest = findNearest(cur.ln, queryP, closest);
            }
        }
        return (closest);
    }

    //unit testing
    public static void main(String[] args)
    {   
        
        Point2D p = new Point2D(0.219, 0.504);
        KdTree points = new KdTree();
        points.insert(new Point2D(0.25, 0.25));
        StdOut.println("After insertion, size " + points.size());
        points.insert(new Point2D(1.0, 1.0));
        StdOut.println("After insertion, size " + points.size());
        points.insert(new Point2D(0.0, 0.75));
        StdOut.println("After insertion, size " + points.size());
        points.insert(new Point2D(0.75, 0.0));
        StdOut.println("After insertion, size " + points.size());
        points.insert(new Point2D(0.0, 0.0));
        StdOut.println("After insertion, size " + points.size());
        points.insert(new Point2D(1.0, 0.0));
        StdOut.println("After insertion, size " + points.size());
        points.insert(new Point2D(0.5, 0.75));
        StdOut.println("After insertion, size " + points.size());
        points.insert(new Point2D(0.75, 1.0));
        StdOut.println("After insertion, size " + points.size());
        points.insert(new Point2D(0.0, 1.0));
        StdOut.println("After insertion, size " + points.size());
        points.insert(new Point2D(0.0, 0.5));
        StdOut.println("After insertion, size " + points.size());
        Point2D nearest = points.nearest(p);
        StdOut.println("Nearest point to p is " + nearest.toString());
        StdOut.println("Does the set contain (0.219, 0.504)? " + points.contains(p));
        StdOut.println("Does the set contain (0.75, 1.0)? " + points.contains(new Point2D(0.75, 1.0)));
        RectHV range = new RectHV(0.0, 0.0, 0.5, 0.5);
        Iterable<Point2D> pIn = points.range(range);
        StdOut.println("Points inside of the range\n");
        for (Point2D pInside: pIn) 
            StdOut.println(pInside.toString());
        /* 
        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        Stopwatch watch = new Stopwatch();
        while (!in.isEmpty())
        {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            if (kdtree.isEmpty())
                throw new UnsupportedOperationException();
        }
        StdOut.println("Elapsed time: " + watch.elapsedTime() + "\n");
        StdOut.println("size: " + kdtree.size() + "\n");
        //Is the query point in the set?
        Point2D queryP = new Point2D(0.684711, 0.818767);
        StdOut.println("Does the set contain " + queryP.toString() + "?\n");
        StdOut.println("Answer: " + kdtree.contains(queryP) + "\n");
        //what's the nearest point in the set to query point
        Point2D queryP2 = new Point2D(0.864, 0.565);
        Point2D nearest = kdtree.nearest(queryP2);
        StdOut.println("The nearest point to queryP2 " + queryP2.toString() + "is " + nearest.toString() + "\n");
        StdOut.println("Distance between them is " + nearest.distanceSquaredTo(queryP2));*/
        

        //draw
        /*
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0.0, 1.0);
        StdDraw.setYscale(0.0, 1.0);
        points.draw();
        StdDraw.show();*/
    }
}
