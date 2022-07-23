import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class KdTree {
    private final BSTree tree;


    // создаем пустой набор точек
    public KdTree()
    {
        tree = new BSTree();
    }
    private class BSTree implements Iterable<Point2D>
    {
        private Node[] arrayOfAllPoints;
        private final ArrayList<Point2D> listOfEnteredPoints = new ArrayList<>();
        private Node root;
        private int size = 0;
        private int index = 0;
        private Point2D minimalPoint;

        @Override
        public Iterator<Point2D> iterator() {
            return new PointsIterator();
        }
        public void searchPoints(RectHV rect, Node newRoot)
        {
            Node root;
            if (newRoot == null)
            {
                if (this.root == null) throw new IllegalArgumentException();
                else                   root = this.root;
            }
            else root = newRoot;

            if (rect.contains(root.key)) listOfEnteredPoints.add(root.key);

            if (root.isVertical)
            {
                if (root.firstArea.intersects(rect))
                {
                    if (root.left != null) searchPoints(rect, root.left);
                }

                if (root.secondArea.intersects(rect))
                {
                    if (root.right != null) searchPoints(rect, root.right);
                }

            }
            else
            {
                if (root.firstArea.intersects(rect))
                {
                    if (root.left != null) searchPoints(rect, root.left);
                }

                if (root.secondArea.intersects(rect))
                {
                    if (root.right != null) searchPoints(rect, root.right);
                }
            }
        }
        private class PointsIterator implements Iterator<Point2D>
        {
            private int index = 0;
            @Override
            public boolean hasNext()
            {
                return index != listOfEnteredPoints.size();
            }

            @Override
            public Point2D next() {
                if (index >= listOfEnteredPoints.size()) throw new NoSuchElementException();
                return listOfEnteredPoints.get(index++);
            }
        }

        class Node
        {
            private final Point2D key;
            private Node left, right;

            private final RectHV firstArea;
            private final RectHV secondArea;

            private final boolean isVertical;


            public Node(Point2D key, boolean isVertical, RectHV firstArea, RectHV secondArea)
            {
                this.key = key;
                this.isVertical = isVertical;
                this.firstArea = firstArea;
                this.secondArea = secondArea;
            }
        }
        private int compareTo(Point2D obj1, Point2D obj2, boolean isY)
        {
            if (isY)
            {
                double y1 = obj1.y();
                double y2 = obj2.y();

                return Double.compare(y1, y2);
            }
            else
            {
                double x1 = obj1.x();
                double x2 = obj2.x();

                return Double.compare(x1, x2);
            }
        }
        private Point2D searchNearest(Point2D comparablePoint, Node newRoot)
        {
            Node root;
            if (newRoot != null) root = newRoot;
            else
            {
                if (this.root == null) return null;
                root = this.root;
                minimalPoint = root.key;
            }

            if (root.key.distanceSquaredTo(comparablePoint) <
                    minimalPoint.distanceSquaredTo(comparablePoint))
                        minimalPoint = root.key;

            boolean left = root.left != null && minimalPoint.distanceSquaredTo(comparablePoint) >=
                    root.firstArea.distanceSquaredTo(comparablePoint);
            boolean right =root.right != null && minimalPoint.distanceSquaredTo(comparablePoint) >=
                    root.secondArea.distanceSquaredTo(comparablePoint);

            if (root.firstArea.contains(comparablePoint))
            {
                if (left)  searchNearest(comparablePoint, root.left);
                if (right) searchNearest(comparablePoint, root.right);
            }
            else
            {
                if (right)  searchNearest(comparablePoint, root.right);
                if (left)   searchNearest(comparablePoint, root.left);
            }
            return minimalPoint;
        }
        public Point2D searchNearest(Point2D comparablePoint)
        {
            if (isEmpty())  return null;
            else            return searchNearest(comparablePoint, null);
        }

        public int getSize()
        {
            return size;
        }

        public boolean isEmpty()
        {
            return size == 0;
        }

        private Node getNode(Node parentNode, Point2D coordinates, boolean isLeftNode)
        {
            Node finishNode = null;
            if (parentNode.isVertical)
            {
                RectHV parentRect = null;
                if (isLeftNode)   parentRect = parentNode.firstArea;
                else          parentRect = parentNode.secondArea;
                finishNode = new Node(
                        coordinates,
                        false,
                        new RectHV(parentRect.xmin(), parentRect.ymin(), parentRect.xmax(), coordinates.y()),
                        new RectHV(parentRect.xmin(), coordinates.y(), parentRect.xmax(), parentRect.ymax())
                );
            }
            else
            {
                RectHV parentRect = null;
                if (isLeftNode)   parentRect = parentNode.firstArea;
                else          parentRect = parentNode.secondArea;
                finishNode = new Node(
                        coordinates,
                        true,
                        new RectHV(parentRect.xmin(), parentRect.ymin(), coordinates.x(), parentRect.ymax()),
                        new RectHV(coordinates.x(), parentRect.ymin(), parentRect.xmax(), parentRect.ymax())
                );
            }
            return finishNode;
        }
        public void put(Node newRoot, Point2D key)
        {
            Node root;
            if (newRoot != null)    root = newRoot;
            else                    root = this.root;

            if (root == null)
            {
                arrayOfAllPoints = new Node[1];
                this.root = new Node(
                        key,
                        true,
                        new RectHV(0,0, key.x(), 1),
                        new RectHV(key.x(), 0, 1, 1)
                );
                addToArray(this.root);
                size++;
                return ;
            }

            if (root.key.equals(key)) return;
            int cmp = compareTo(key, root.key, !root.isVertical);

            if (cmp < 0)
            {
                if (root.left == null)
                {
                    root.left = getNode(root, key, true);
                    addToArray(root.left);
                    size++;
                }
                else put(root.left, key);
            }
            else
            {
                if (root.right == null)
                {
                    root.right = getNode(root, key, false);
                    addToArray(root.right);
                    size++;
                }
                else put(root.right, key);
            }
        }
        public boolean search(Point2D key)
        {
            if (this.root == null) return false;
            Node root = this.root;
            while(true)
            {
                if (root.key.compareTo(key) == 0)   return true;
                int cmp = compareTo(key, root.key, !root.isVertical);
                if (cmp == -1)
                {
                    if (root.left == null) return false;
                    else                   root = root.left;
                }
                else
                {
                    if (root.right == null) return false;
                    else                    root = root.right;
                }
            }
        }

        private void resize(int capacity)
        {
            Node[] array = new Node[capacity];
            System.arraycopy(arrayOfAllPoints, 0, array, 0, arrayOfAllPoints.length);
            arrayOfAllPoints = array;
        }
        private void addToArray(Node node)
        {
            if (index >= arrayOfAllPoints.length) resize(arrayOfAllPoints.length * 2);
            arrayOfAllPoints[index++] = node;
        }

    }

    public boolean isEmpty()
    {
        return tree.isEmpty();
    }

    public int size()
    {
        return tree.getSize();
    }

    public void insert(Point2D p)
    {
        if (p == null) throw new IllegalArgumentException();
        tree.put(null, p);
    }

    public boolean contains(Point2D p)
    {
        if (p == null) throw new IllegalArgumentException();
        return tree.search(p);
    }

    public void draw()
    {
        BSTree.Node[] arr = tree.arrayOfAllPoints;
        for (BSTree.Node node : arr)
        {
            if (node == null) break;
            if (node.isVertical)
            {
                StdDraw.setPenColor(StdDraw.RED);
                RectHV rectFirstArea = node.firstArea;
                StdDraw.line(rectFirstArea.xmax(), rectFirstArea.ymin(), rectFirstArea.xmax(), rectFirstArea.ymax());
            }
            else
            {
                StdDraw.setPenColor(StdDraw.BLUE);
                RectHV rectFirstArea = node.firstArea;
                StdDraw.line(rectFirstArea.xmin(), rectFirstArea.ymax(), rectFirstArea.xmax(), rectFirstArea.ymax());
            }
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(node.key.x(), node.key.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect)
    {
        if (rect == null) throw new IllegalArgumentException();
        if (isEmpty())  return null;        tree.searchPoints(rect, null);
        return tree;
    }

    // ближайший сосед в множестве к точке p; null, если набор пуст
    public Point2D nearest(Point2D p)
    {
        if (p == null) throw new IllegalArgumentException();
        return tree.searchNearest(p);
    }

    public static void main(String[] args)
    {
        KdTree kdTree = new KdTree();
//        A 0.25 0.375
//        B  1.0 0.125
//        C  0.0 0.875
//        D  0.875 0.0
//        E  0.125 0.625
//        - reference sequence of kd-tree nodes involved in calls to Point2D methods:
//    A B D
//        kdTree.insert(new Point2D(0.25, 0.375));
//        kdTree.insert(new Point2D(1.0, 0.125));
//        kdTree.insert(new Point2D(0.0, 0.875));
//        kdTree.insert(new Point2D(0.875, 0.0));
//        kdTree.insert(new Point2D(0.125, 0.625));
//
//        Point2D comp = new Point2D(0.625, 0.25);
//        System.out.println(kdTree.nearest(comp));
    }
}
