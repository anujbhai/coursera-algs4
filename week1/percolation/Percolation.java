/* *****************************************************************************
 *  Name:              Anuj Upadhyay
 *  Coursera User ID:  123456
 *  Last modified:     13/01/2022
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int grid;
    private boolean[][] open;
    private int top = 0;
    private int bottom;
    private int openTiles;
    private WeightedQuickUnionUF fullGrid;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        grid = n;
        bottom = grid * grid + 1;
        fullGrid = new WeightedQuickUnionUF(grid * grid + 2);
        open = new boolean[grid][grid];
        openTiles = 0;
    }

    private void checkException(int row, int col) {
        if (row <= 0 || row > grid || col <= 0 || col > grid) {
            throw new IllegalArgumentException();
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkException(row, col);
        open[row - 1][col - 1] = true;
        ++openTiles;

        if (row == 1) {
            fullGrid.union(getIndex(row, col), top);
        }

        if (row == grid) {
            fullGrid.union(getIndex(row, col), bottom);
        }

        if (row > 1 && isOpen(row - 1, col)) {
            fullGrid.union(getIndex(row, col), getIndex(row - 1, col));
        }

        if (row < grid && isOpen(row + 1, col)) {
            fullGrid.union(getIndex(row, col), getIndex(row + 1, col));
        }

        if (col > 1 && isOpen(row, col - 1)) {
            fullGrid.union(getIndex(row, col), getIndex(row, col - 1));
        }

        if (col < grid && isOpen(row, col + 1)) {
            fullGrid.union(getIndex(row, col), getIndex(row, col + 1));
        }
    }

    private int getIndex(int row, int col) {
        return grid * (row - 1) + col;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkException(row, col);
        return open[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if ((row > 0 && row <= grid) && (col > 0 && col <= grid)) {
            return fullGrid.find(top) == fullGrid.find(getIndex(row, col));
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openTiles;
    }

    // does the system percolate?
    public boolean percolates() {
        return fullGrid.find(top) == fullGrid.find(bottom);
    }
}
