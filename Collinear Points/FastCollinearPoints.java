import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private LineSegment[] lineSegments;
    public FastCollinearPoints(Point[] points)
    {
        checkPoints(points);
        Point[] temp1 = Arrays.copyOf(points, points.length);
        Point[] temp2 = Arrays.copyOf(points, points.length);
        ArrayList<LineSegment> segmentsList = new ArrayList<LineSegment>();
        Arrays.sort(temp2);
        for (int i = 0; i < temp2.length; ++i)
        {
            Point origin = temp2[i];
            Arrays.sort(temp1);
            Arrays.sort(temp1, origin.slopeOrder());
            int count = 1;
            Point lineBeginning = null;
            for (int j = 0; j < temp1.length - 1; ++j)
            {
                if (temp1[j].slopeTo(origin) == temp1[j + 1].slopeTo(origin))
                {
                    count++;
                    if (count == 2)
                    {
                        lineBeginning = temp1[j];
                        count++;
                    }
                    else if (count >= 4 && j + 1 == temp1.length - 1)
                    {
                        if (lineBeginning.compareTo(origin) > 0)
                        {
                            segmentsList.add(new LineSegment(origin, temp1[j + 1]));
                        }
                        count = 1;
                    }
                }
                else if (count >= 4)
                {
                    if (lineBeginning.compareTo(origin) > 0)
                    {
                        segmentsList.add(new LineSegment(origin, temp1[j]));
                    }
                    count = 1;
                }
                else
                {
                    count = 1;
                }


            }

        }
        lineSegments = segmentsList.toArray(new LineSegment[segmentsList.size()]);
    }
    public int numberOfSegments()
    {
        return lineSegments.length;
    }
    public LineSegment[] segments()
    {
        return Arrays.copyOf(lineSegments, numberOfSegments());
    }
    private void checkPoints(Point[] array)
    {
        if (array == null)
        {
            throw new IllegalArgumentException();
        }
        if (array == null) throw new IllegalArgumentException();
        Point[] arr = array.clone();
        for (int t = 0; t < array.length; t++)
        {
            if (array[t] == null)
            {
                throw new IllegalArgumentException();
            }
            if (t != array.length - 1)
            {
                for (int i = 0; i < arr.length; i++)
                {
                    if (i != t)
                    {
                        if (array[t].compareTo(arr[i]) == 0)
                        {
                            throw new IllegalArgumentException();
                        }
                    }
                }
            }
        }
    }
}
