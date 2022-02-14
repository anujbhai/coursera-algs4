/* *****************************************************************************
 *  Name: Anuj Upadhyay
 *  Date: 13 Feb, 2022
 *  Description: A program that examines 4 points at a time and checks whether
 * they all lie on the same line segment, returning all such line segments.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private int n;
    private LineSegment[] ls;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        for (Point p : points)
            if (p == null) throw new IllegalArgumentException();

        Point[] pt = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            pt[i] = points[i];
        }

        Arrays.sort(pt);
        for (int i = 0; i < points.length - 1; i++) {
            if (pt[i].compareTo(pt[i + 1]) == 0) throw new IllegalArgumentException();
        }

        n = 0;
        ls = new LineSegment[points.length];
        LineSegment[] lsNew = new LineSegment[points.length * 4];

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int m = k + 1; m < points.length; m++) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[m];

                        if (Double.compare(p.slopeTo(q), p.slopeTo(r)) == 0 && Double.compare(p.slopeTo(q), p.slopeTo(s)) == 0) {
                            Point[] segmentPoints = {p, q, r, s};
                            Arrays.sort(segmentPoints);
                            Point p1 = segmentPoints[0];
                            Point p2 = segmentPoints[3];
                            lsNew[n++] = new LineSegment(p1, p2);
                        }
                    }
                }
            }
        }

        ls = new LineSegment[n];

        for (int i = 0; i < n; i++)
            ls[i] = lsNew[i];
    }

    public int numberOfSegments() {
        n = ls.length;
        return n;
    }

    public LineSegment[] segments() {
        LineSegment[] segs = new LineSegment[ls.length];

        for (int i = 0; i < ls.length; i++)
            segs[i] = ls[i];

        return segs;
    }

    public static void main(String[] args) {
        int in = StdIn.readInt();
        Point[] points = new Point[in];

        for (int i = 0; i < in; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            points[i] = new Point(x, y);
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        for (Point p : points)
            p.draw();

        StdDraw.show();

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }

        StdDraw.show();
    }
}
