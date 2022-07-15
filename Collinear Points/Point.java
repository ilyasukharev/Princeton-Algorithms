import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point>
{
    private final int x, y;

    private static class Comparator2Points implements Comparator<Point>
    {
        final int x, y;
        public Comparator2Points(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
        private double slopeTo(Point that)
        {
            if (this.x == that.x && this.y == that.y)
            {
                return Double.NEGATIVE_INFINITY;
            }
            else if (that.y != this.y && ((that.x - this.x) == 0))
            {
                return Double.POSITIVE_INFINITY;
            }
            double result = that.y - this.y;
            double tmp = that.x - this.x;
            result = result / tmp;
            return result;
        }

        public int compare(Point point1, Point point2)
        {
            double res1 = slopeTo(point1);
            double res2 = slopeTo(point2);
            if (res1 < res2)
            {
                return -1;
            }
            else if (res1 > res2)
            {
                return 1;
            }
            else return 0;
        }
    }

    // constructs the point (x, y)
    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    // draws this point
    public void draw()
    {
        StdDraw.point(this.x, this.y);
    }
    // draws the line segment from this point to that point
    public void drawTo(Point that)
    {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    // string representation
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }

    // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point that)
    {
        if (this.y < that.y || this.y == that.y && this.x < that.x)
        {
            return -1;
        }
        else if (this.y > that.y || this.y == that.y && this.x > that.x)
        {
            return 1;
        }
        else return 0;
    }
    // the slope between this point and that point
    public double slopeTo(Point that)
    {
        if (this.x == that.x && this.y == that.y)
        {
            return Double.NEGATIVE_INFINITY;
        }
        else if (that.y != this.y && ((that.x - this.x) == 0))
        {
            return Double.POSITIVE_INFINITY;
        }
        else if (that.y == this.y)
        {
            return 0.0;
        }
        return (double) (that.y - this.y) / (that.x - this.x);
    }
    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder()
    {
        return new Comparator2Points(x, y);
    }

}
