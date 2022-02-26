import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public final class Solver {
    private Stack<Board> s;
    private boolean solvable;

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        MinPQ<SearchNode> pq = new MinPQ<>(new SearchNodeComparator());
        s = new Stack<>();

        SearchNode a = new SearchNode(initial, 0, null);
        SearchNode aTwin = new SearchNode(initial.twin(), 0, null);
        pq.insert(a);
        pq.insert(aTwin);

        while (!pq.isEmpty()) {
            SearchNode sn = pq.delMin();

            if (sn.sBoard.isGoal()) {
                while (sn.sNode != null) {
                    s.push(sn.sBoard);
                    sn = sn.sNode;
                }

                if (sn.sBoard.equals(initial)) {
                    s.push(sn.sBoard);
                    solvable = true;
                }

                return;
            }

            for (Board b : sn.sBoard.neighbors()) {
                SearchNode member = new SearchNode(b, sn.sMoves + 1, sn);

                if (sn.sNode != null && b.equals(sn.sNode.sBoard)) continue;
                pq.insert(member);
            }
        }
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        if (s.size() > 0 && isSolvable()) return s.size() - 1;
        return -1;
    }


    public Iterable<Board> solution() {
        if (!isSolvable()) return null;

        return s;
    }

    private static class SearchNodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode thisObj, SearchNode thatObj) {
            if (thisObj.sDistance == thatObj.sDistance) {
                return Integer.compare(thisObj.sBoard.hamming(), thatObj.sBoard.hamming());
            } else if (thisObj.sDistance > thatObj.sDistance) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    private class SearchNode {
        Board sBoard;
        int sMoves;
        SearchNode sNode;
        int sDistance;

        public SearchNode(Board sBoard, int sMoves, SearchNode sNode) {
            this.sBoard = sBoard;
            this.sMoves = sMoves;
            this.sNode = sNode;
            this.sDistance = sBoard.manhattan() + sMoves;
        }
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            assert solver.solution() != null;
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
