import java.util.Arrays;

public class BruteCollinearPoints
{
    private LineSegment[] lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        lineSegments = new LineSegment[10];
        int arrayIndex = 0;
        checkPoints(points);

        Point[] newArr = points.clone();
        Arrays.sort(newArr);
        Point[] temp = new Point[4];
        for (int i = 0; i < newArr.length - 3; i++) {
            temp[0] = newArr[i];
            for (int j = i + 1; j < newArr.length - 2; j++) {
                temp[1] = newArr[j];
                for (int p = j + 1; p < newArr.length - 1; p++) {
                    temp[2] = newArr[p];
                    for (int k = p + 1; k < newArr.length; k++) {
                        temp[3] = newArr[k];
                        if (collinear(temp)) {
                            LineSegment lineSegment = new LineSegment(temp[0], temp[3]);
                            if (arrayIndex == lineSegments.length)
                            {
                                resizeArray((lineSegments.length + 1) * 2);
                            }
                            lineSegments[arrayIndex++] = lineSegment;
                        }
                    }
                }
            }
        }

    }
    private boolean collinear(Point[] arr)
    {
        double fir = arr[0].slopeTo(arr[1]);
        double sec = arr[1].slopeTo(arr[2]);
        double third = arr[2].slopeTo(arr[3]);
        if (Double.compare(fir, sec) == 0 && Double.compare(sec, third) == 0)
        {
            return true;
        }
        else return false;
    }

    // the number of line segments
    public int numberOfSegments()
    {
        int counter = 0;
        for (LineSegment t : lineSegments)
        {
            if (t == null)
            {
                break;
            }
            counter++;
        }
        return counter;
    }
    private boolean checkSuchSegment(LineSegment lineSegment)
    {
        for (LineSegment t : lineSegments)
        {
            if (t == null)
            {
                break;
            }
            if (t != lineSegment)
            {
                return true;
            }
        }
        return false;
    }
    // the line segments
    public LineSegment[] segments()
    {
        LineSegment[] newArr = new LineSegment[numberOfSegments()];
        int index = 0;
        for (LineSegment t : lineSegments)
        {
            if (t == null)
            {
                break;
            }
            newArr[index] = t;
            index++;
        }
        return newArr;
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
    private void resizeArray(int capacity)
    {
        final LineSegment[] copy = new LineSegment[capacity];
        for (int t = 0; t < this.lineSegments.length; t++)
        {
            copy[t] = this.lineSegments[t];
        }
        this.lineSegments = copy;
    }

}
