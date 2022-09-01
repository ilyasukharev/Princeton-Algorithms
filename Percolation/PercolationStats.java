import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] endArray;
    private final int trials;
    public PercolationStats(int n, int trials)
    {
        if (n <= 0 || trials <= 0)
        {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        endArray = new double[trials];
        int trial = 1;
        while (trial <= trials)
        {
            Percolation percolation = new Percolation(n);
            int openSites = 0;
            while (!percolation.percolates())
            {
                int a = StdRandom.uniform(1, n + 1);
                int b = StdRandom.uniform(1, n + 1);
                if (!percolation.isOpen(a, b))
                {
                    percolation.open(a, b);
                    openSites++;
                }
            }
            double result = (double) openSites / (n * n);
            endArray[trial - 1] = result;
            trial++;
        }
    }
    public double mean()
    {
        return StdStats.mean(endArray);
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return StdStats.stddev(endArray);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
        double result = CONFIDENCE_95 * stddev() / Math.sqrt(trials);
        result = mean() - result;
        return result;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        double result = CONFIDENCE_95 * stddev() / Math.sqrt(trials);
        result = mean() + result;
        return result;
    }

    // test client (see below)
    public static void main(String[] args)
    {
        int size = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(size, trials);

        System.out.println("mean                     = " + percolationStats.mean());
        System.out.println("stddev                   = " + percolationStats.stddev());
        System.out.println("95% confidence interval  = [" + percolationStats.confidenceLo() +
                ", " + percolationStats.confidenceHi() + "]");
    }
}