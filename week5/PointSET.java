/* *****************************************************************************
 *  Name: Anuj Upadhyay
 *  Date: 09 March 2022
 *  Description: A mutable data type that represents a set of points in the unit square.
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private SET<Point2D> points;

    public PointSET() {
        points = new SET<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        points.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return points.contains(p);
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);

        points.forEach((Point2D::draw));
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        Stack<Point2D> r = new Stack<Point2D>();
        for (Point2D p : points) {
            if (rect.contains(p)) r.push(p);
        }

        return r;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        double r = Double.POSITIVE_INFINITY;
        Point2D closest = null;

        for (Point2D p2 : points) {
            double distance = p.distanceTo(p2);

            if (distance < r) {
                r = distance;
                closest = p2;
            }
        }

        return closest;
    }

    public static void main(String[] args) {

    }
}
