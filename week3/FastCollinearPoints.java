/* *****************************************************************************
 *  Name: Anuj Upadhyay
 *  Date: 13 Feb, 2022
 *  Description: A program that examines 4 points at a time and checks whether
 * they all lie on the same line segment, returning all such line segments.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private int n;
    private ArrayList<LineSegment> ls;

    public FastCollinearPoints(Point[] points) {
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

        n = points.length;
        ls = new ArrayList<LineSegment>();

        Point ptNew;
        Point[] segmentPoints = new Point[n - 1];
        for (int p = 0; p < n; p++) {
            ptNew = pt[p];
            for (int j = 0; j < n; j++) {
                if (j < p) segmentPoints[j] = pt[j];
                if (j > p) segmentPoints[j - 1] = pt[j];
            }
            Arrays.sort(segmentPoints, ptNew.slopeOrder());
            int index = 0;
            int c = 2;
            for (int i = 1; i < n - 1; i++) {
                double obj1 = segmentPoints[i - 1].slopeTo(ptNew);
                double obj2 = segmentPoints[i].slopeTo(ptNew);
                if (obj1 == obj2) {
                    c++;
                    if ((i == n - 2) && (c > 3)) {
                        if (ptNew.compareTo(segmentPoints[index]) < 0) {
                            Point indexNew = segmentPoints[i];
                            LineSegment lsNew = new LineSegment(ptNew, indexNew);
                            ls.add(lsNew);
                        }
                    }
                }
                else {
                    if ((c > 3) && (ptNew.compareTo(segmentPoints[index]) < 0)) {
                        Point indexNew = segmentPoints[i - 1];
                        LineSegment lsNew = new LineSegment(ptNew, indexNew);
                        ls.add(lsNew);
                    }
                    index = i;
                    c = 2;
                }
            }
        }
    }

    public int numberOfSegments() {
        n = ls.size();
        return n;
    }

    public LineSegment[] segments() {
        LineSegment[] segs = new LineSegment[ls.size()];

        for (int i = 0; i < ls.size(); i++)
            segs[i] = ls.get(i);

        return segs;
    }

    public static void main(String[] args) {
        // read the n points from a file
        int n = StdIn.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
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
