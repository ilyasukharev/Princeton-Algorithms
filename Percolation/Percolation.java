import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static final int VIRTUAL_TOP = 0;
    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private final int virtualBottom;
    private final int size;
    private int openSitesCounter;
    private final boolean[][] openArr;

    public Percolation(int n)
    {
        if (n <= 0)
        {
            throw new IllegalArgumentException();
        }
        this.size = n;
        this.weightedQuickUnionUF = new WeightedQuickUnionUF(size * size + 2);
        this.virtualBottom = size * size + 1;
        this.openArr = new boolean[size][size];
    }
    private int getIndex(int row, int col)
    {
        return (row - 1) * size + col;
    }
    private void isCorrectlyIndexes(int row, int col)
    {
        if (row <= 0 || col <= 0)
        {
            throw new IllegalArgumentException();
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        isCorrectlyIndexes(row, col);
        if (!isOpen(row, col))
        {
            openArr[row - 1][col - 1] = true;
            openSitesCounter++;
        }
        if (row == 1)
        {
            weightedQuickUnionUF.union(getIndex(row, col), VIRTUAL_TOP);
        }
        if (row == size)
        {
            weightedQuickUnionUF.union(getIndex(row, col), virtualBottom);
        }
        if (row > 1)
        {
            if (isOpen(row - 1, col))
            {
                weightedQuickUnionUF.union(getIndex(row, col), getIndex(row - 1, col));
            }
        }
        if (row < size)
        {
            if (isOpen(row + 1, col))
            {
                weightedQuickUnionUF.union(getIndex(row, col), getIndex(row + 1, col));
            }
        }
        if (col > 1)
        {
            if (isOpen(row, col - 1))
            {
                weightedQuickUnionUF.union(getIndex(row, col), getIndex(row, col - 1));
            }
        }
        if (col < size)
        {
            if (isOpen(row, col + 1))
            {
                weightedQuickUnionUF.union(getIndex(row, col), getIndex(row, col + 1));
            }
        }
    }
//
    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        isCorrectlyIndexes(row, col);
        return openArr[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        isCorrectlyIndexes(row, col);
        return weightedQuickUnionUF.find(getIndex(row, col)) == weightedQuickUnionUF.find(VIRTUAL_TOP);
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
        return openSitesCounter;
    }
    // does the system percolate?
    public boolean percolates()
    {
        return weightedQuickUnionUF.find(VIRTUAL_TOP) == weightedQuickUnionUF.find(virtualBottom);
    }

    // test client (optional)
//    public static void main(String[] args)
//    {
//
//    }
}
