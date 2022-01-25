/* *****************************************************************************
 *  Name:              Anuj Upadhyay
 *  Coursera User ID:  123456
 *  Last modified:     13/01/2022
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static double fraction = 1.96;
    private int trialsCount;
    private double[] threshold;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        trialsCount = trials;
        threshold = new double[trialsCount];

        for (int i = 0; i < trialsCount; i++) {
            Percolation perc = new Percolation(n);

            int openTiles = 0;

            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);

                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                    openTiles++;
                }
            }

            double fractionAmt = (double) openTiles / (n * n);

            threshold[i] = fractionAmt;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((fraction * stddev()) / Math.sqrt(trialsCount));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((fraction * stddev()) / Math.sqrt(trialsCount));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stat = new PercolationStats(n, trials);

        System.out.println("mean                    = " + stat.mean());
        System.out.println("stddev                  = " + stat.stddev());
        System.out.println(
                "95% confidence interval = " + stat.confidenceLo() + ", " + stat.confidenceHi());
    }
}
