import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<>();
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }
        Point[] sortedpoints = points.clone();
        Arrays.sort(sortedpoints);
        for (int i = 0; i < sortedpoints.length-1; i++)
        {
            if (sortedpoints[i].equals(sortedpoints[i + 1])) {
                throw new IllegalArgumentException();
            }
        }

        ArrayList<LineSegment> abadonsegments = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            Point[] pointslope = sortedpoints.clone();
            Arrays.sort(pointslope, pointslope[i].slopeOrder());
            double slope = Double.NEGATIVE_INFINITY;
            for (int j = 1; j < pointslope.length-2; j++) {
                if (sortedpoints[i].slopeTo(pointslope[j]) == sortedpoints[i].slopeTo(pointslope[j+2])
                        && sortedpoints[i].compareTo(pointslope[j]) < 0) {
                        if (sortedpoints[i].slopeTo(pointslope[j]) == slope) {
                            segments.remove(segments.size()-1);
                            segments.add(new LineSegment(sortedpoints[i], pointslope[j+2]));

                            int head = j-1;
                            int tail = j+2;
                            while (sortedpoints[i].slopeTo(pointslope[head]) == sortedpoints[i].slopeTo(pointslope[tail])) {
                                abadonsegments.add(new LineSegment(pointslope[head], pointslope[tail]));
                                head--;

                            }
                        } else {
                            LineSegment tempsegment = new LineSegment(sortedpoints[i], pointslope[j + 2]);
                            int redflag = 0;
                            for (LineSegment s: abadonsegments
                                 ) {
                                if (s.toString().equals(tempsegment.toString())) {
                                    redflag = 1;
                                    break;
                                }
                            }
                            if (redflag == 0) {
                                slope = sortedpoints[i].slopeTo(pointslope[j]);
                                segments.add(tempsegment);
                            }

                        }
                }
            }
        }
    } // finds all line segments containing 4 or more points
    public int numberOfSegments() {
        return segments.size();
    }        // the number of line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }                // the line segments
    public static void main(String[] args) {
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
