/* *****************************************************************************
 *  Name: Anuj Upadhyay
 *  Date: 25 Feb, 2022
 *  Description: A data type that models an n-by-n board with sliding tiles.
 **************************************************************************** */

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private int[][] t;
    private int n;
    private int manhattan;

    public Board(int[][] tiles) {
        n = tiles.length;
        t = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                t[i][j] = tiles[i][j];
            }
        }

        manhattan = findManhattan();
    }

    private boolean isEqual(int i, int j) {
        if (i < n && i >= 0 && j < n && j >= 0) return true;
        return false;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", t[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        int a = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (t[i][j] == 0) continue;

                int tY = (t[i][j] - 1) % n;
                int tX = (t[i][j] - 1) / n;

                if (!(i == tX && j == tY)) a++;
            }
        }

        return a;
    }

    private int findManhattan() {
        int a = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (t[i][j] == 0) continue;

                int tY = (t[i][j] - 1) % n;
                int tX = (t[i][j] - 1) / n;

                if (!(i == tX && j == tY)) {
                    a += Math.abs(tX - i);
                    a += Math.abs(tY - j);
                }
            }
        }

        return a;
    }

    public int manhattan() {
        return manhattan;
    }

    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (t[i][j] == 0) {
                    if (i != n - 1 || j != n - 1) return false;
                    continue;
                }

                if (!((t[i][j] - 1) == i * n + j)) return false;
            }
        }

        return true;
    }

    public boolean equals(Object y) {
        Board board = (Board) y;

        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        if (n != board.n) return false;

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (t[i][j] != board.t[i][j]) return false;

        return true;
    }

    public Iterable<Board> neighbors() {
        Stack<Board> pq = new Stack<>();
        int tX = -1, tY = -1;
        int[] a = {1, -1};

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (t[i][j] == 0) {
                    tX = i;
                    tY = j;
                }
            }
        }

        for (int i : a) {
            if (isEqual(tY + i, tX)) {
                Board b = new Board(t);
                b.exch(b.t, tY, tX, tY + i, tX);
                pq.push(b);
            } else if (isEqual(tY, tX + i)) {
                Board b = new Board(t);
                b.exch(b.t, tY, tX, tY, tX + i);
                pq.push(b);
            }
        }

        return pq;
    }

    public Board twin() {
        int[][] tNew = new int[n][n];
        int x0, y0, xN, yN;

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tNew[i][j] = t[i][j];

        if (tNew[0][0] != 0 && tNew[0][1] != 0) {
            x0 = 0;
            y0 = 0;
            xN = 0;
            yN = 1;
        } else {
            x0 = 1;
            y0 = 0;
            xN = 1;
            yN = 1;
        }

        int u = tNew[x0][y0];
        tNew[x0][y0] = tNew[xN][yN];
        tNew[xN][yN] = u;

        return new Board(tNew);
    }

    private void exch(int[][] x, int i, int j, int k, int m) {
        int swap = x[j][i];
        x[j][i] = x[m][k];
        x[m][k] = swap;
        manhattan = findManhattan();
    }

    public static void main(String[] args) {
        int [][] tiles = new int[][]{
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        };
        Board board = new Board(tiles);
        StdOut.println("Our board:");
        StdOut.println(board);
    }
}
