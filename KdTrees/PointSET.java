import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeSet;

public class PointSET {

    private final TreeSet<Point2D> setOfPoints;
    private Point2D[] pointsArray = new Point2D[1];
    private int index = 0;

    public PointSET()
    {
        setOfPoints = new TreeSet<Point2D>();
    }
    private void resize(int capacity)
    {
        Point2D[] cpy = new Point2D[capacity];
        for (int t = 0; t < pointsArray.length; t++)
        {
            cpy[t] = pointsArray[t];
        }
        pointsArray = cpy;
    }

    private void add(Point2D point)
    {
        if (index == pointsArray.length) resize((pointsArray.length + 1) * 2);
        pointsArray[index++] = point;
    }

    private class DataPoints implements Iterable<Point2D>
    {
        @Override
        public Iterator<Point2D> iterator() {
            return new PointsIterator();
        }
        private class PointsIterator implements Iterator<Point2D>
        {
            int index = 0;
            @Override
            public boolean hasNext() {
                if (index >= pointsArray.length) return false;
                return pointsArray[index] != null;
            }
            @Override
            public Point2D next()
            {
                if (index >= pointsArray.length) throw new NoSuchElementException();
                return pointsArray[index++];
            }
        }

    }

    public boolean isEmpty()
    {
        return setOfPoints.isEmpty();
    }

    public int size()
    {
        return setOfPoints.size();
    }

    public void insert(Point2D p)
    {
        if (p == null) throw new IllegalArgumentException();
        setOfPoints.add(p);
    }
    public boolean contains(Point2D p)
    {
        if (p == null) throw new IllegalArgumentException();
        return setOfPoints.contains(p);
    }

    public void draw()
    {
        for (Point2D point : setOfPoints) {
            StdDraw.point(point.x(), point.y());
        }
    }

    // все точки внутри прямоугольника (или на границе)
    public Iterable<Point2D> range(RectHV rect)
    {
        if (rect == null) throw new IllegalArgumentException();
        for (Point2D point : setOfPoints)
        {
            if (rect.contains(point)) add(point);
        }

        return new DataPoints();
    }

    // ближайший сосед в множестве к точке p; null, если набор пуст
    public Point2D nearest(Point2D p)
    {
        if (p == null) throw new IllegalArgumentException();
        if (setOfPoints.isEmpty()) return null;
        double minX = 0, minY = 0;
        int counter = 0;
        for (Point2D point : setOfPoints)
        {
            StdDraw.line(p.x(), p.y(), point.x(), point.y());
            if (counter++ == 0)
            {
                minX = point.x();
                minY = point.y();
            }

            else
            {
                if (new Point2D(point.x(), point.y()).distanceSquaredTo(p)
                        < new Point2D(minX, minY).distanceSquaredTo(p))
                {
                    minX = point.x();
                    minY = point.y();
                }
            }
        }
        return new Point2D(minX, minY);
    }

    public static void main(String[] args)
    {
    }
}