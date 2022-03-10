/* *****************************************************************************
 *  Name: Anuj Upadhyay
 *  Date: 09 March 2022
 *  Description: A mutable data type
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;

public class KdTree {
    private Node root;
    private int count;

    private static class Node {
        private Point2D pt;
        private RectHV rt;
        private Node a;
        private Node b;

        public Node(Point2D pt, RectHV rt, Node a, Node b) {
            this.pt = pt;
            this.rt = rt;
            this.a = a;
            this.b = b;
        }
    }

    public KdTree() {
        root = null;
        count = 0;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        root = insert(root, 0, 0, 1, 1, true, p);
    }
    private Node insert(Node h, double xLo, double yLo, double xHi, double yHi, boolean isSplit, Point2D pt) {
        if (h == null) {
            count++;
            RectHV r = new RectHV(xLo, yLo, xHi, yHi);
            return new Node(pt, r, null, null);
        }
        if (h.pt.equals(pt)) return h;

        double cmpKey1, cmpKey2;

        if (isSplit) {
            cmpKey2 = pt.x();
            cmpKey1 = h.pt.x();
        } else {
            cmpKey2 = pt.y();
            cmpKey1 = h.pt.y();
        }
        if (cmpKey1 > cmpKey2) {
            if (isSplit) h.a = insert(h.a, xLo, yLo, h.pt.x(), yHi, false, pt);
            else h.a = insert(h.a, xLo, yLo, xHi, h.pt.y(), false, pt);
        } else if (cmpKey1 < cmpKey2 || cmpKey1 == cmpKey2) {
            if (isSplit) h.b = insert(h.b, h.pt.x(), yLo, xHi, yHi, false, pt);
            else h.b = insert(h.b, xLo, h.pt.y(), xHi, yHi, false, pt);
        }

        return h;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return contains(root, true, p) != null;
    }
    private Point2D contains(Node h, boolean isSplit, Point2D p) {
        if (h == null) return null;
        if (p.equals(h.pt)) return h.pt;

        double cmpKey1, cmpKey2;

        if (isSplit) {
            cmpKey2 = p.x();
            cmpKey1 = h.pt.x();
        } else {
            cmpKey2 = p.y();
            cmpKey1 = h.pt.y();
        }
        if (cmpKey1 > cmpKey2) return contains(h.a, false, p);
        else if (cmpKey1 <= cmpKey2) return contains(h.b, false, p);

        return null;
    }

    public void draw() {
        draw(root, true);
    }
    private void draw(Node h, boolean isSplit) {
        if (h == null) return;

        draw(h.a, false);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(h.pt.x(), h.pt.y());
        StdDraw.setPenRadius();

        if (!isSplit) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(h.pt.x(), h.rt.ymin(), h.pt.x(), h.rt.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(h.rt.xmin(), h.pt.y(), h.rt.xmax(), h.pt.y());
        }

        draw(h.a, false);
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        Node n = nearest(root, root, p);

        if (n == null) return null;

        return n.pt;
    }
    private Node nearest(Node h1, Node h2, Point2D p) {
        if (isEmpty()) return null;

        double distance1 = p.distanceTo(h2.pt);
        if (h1 == null || distance1 < h1.rt.distanceTo(p)) return h2;

        double distance2 = p.distanceTo(h1.pt);
        if (distance2 < distance1) h2 = h1;
        if (h1.a != null && h1.b != null) {
            if (h1.a.rt.distanceSquaredTo(p) < h1.b.rt.distanceSquaredTo(p)) {
                h2 = nearest(h1.a, h2, p);
                h2 = nearest(h1.b, h2, p);
            } else {
                h2 = nearest(h1.b, h2, p);
                h2 = nearest(h1.a, h2, p);
            }
        } else {
            h2 = nearest(h1.a, h2, p);
            h2 = nearest(h1.b, h2, p);
        }

        return h2;
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        return range(root, rect);
    }
    private Iterable<Point2D> range(Node h, RectHV rect) {
        SET<Point2D> s = new SET<Point2D>();

        if (h == null || !rect.intersects(h.rt)) return s;
        if (rect.contains(h.pt)) s.add(h.pt);

        Iterable<Point2D> lt = range(h.a, rect);
        Iterable<Point2D> rt = range(h.b, rect);
        lt.forEach(s::add);
        rt.forEach(s::add);

        return s;
    }

    public static void main(String[] args) {
        KdTree kdtree = new KdTree();
        while (!StdIn.isEmpty()) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }

        kdtree.draw();
    }
}
