import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import java.util.Iterator;

public class Solver{
    private SearchNode curNode;
    private boolean isSolvable;

   //find a solution to the initial board (using the A* algorithm)
   //two kinds of board: the board can reach the goal board (solvable); the board cant(unsolvable) but if we swipe any non-zero pair of its tiles to obtain its twin board, its twin board can surely reach the goal board. So to prove if a board is solvable, we just need to see if its twin board can reach the goal board(mathematically this can be proved)
   public Solver(Board initial)
   {
        SearchNode curNodeTwin;

        if (initial == null)
            throw new IllegalArgumentException("The initial board is not valid");
        MinPQ<SearchNode> mainPQ = new MinPQ<>();
        mainPQ.insert(new SearchNode(initial, 0, null));
        this.curNode = mainPQ.delMin();
        MinPQ<SearchNode> twinPQ = new MinPQ<>();
        twinPQ.insert(new SearchNode(initial.twin(), 0, null));
        curNodeTwin = twinPQ.delMin();
        this.isSolvable = this.curNode.board.isGoal();
        while (!this.isSolvable)
        {
            if (curNodeTwin.board.isGoal())
                break;
            //add neighbors of the current searchNode to its PQ
            for (Board neighbor: this.curNode.board.neighbors())
            {
                if (this.curNode.previous == null)
                    mainPQ.insert(new SearchNode(neighbor, this.curNode.moves + 1, this.curNode));
                else
                {
                    if (!neighbor.equals(this.curNode.previous.board))
                        mainPQ.insert(new SearchNode(neighbor, this.curNode.    moves + 1, this.curNode));
                }
            }
            this.curNode = mainPQ.delMin();
            this.isSolvable = this.curNode.board.isGoal();
            //add neighbors of the twin searchNode to its PQ
            for (Board neighbor: curNodeTwin.board.neighbors())
            {
                if (curNodeTwin.previous == null)
                    twinPQ.insert(new SearchNode(neighbor, curNodeTwin.moves + 1, curNodeTwin));
                else
                {
                    if (!neighbor.equals(curNodeTwin.previous.board))
                        twinPQ.insert(new SearchNode(neighbor, curNodeTwin.moves + 1, curNodeTwin));
                }
            }
            curNodeTwin = twinPQ.delMin();
        }
        if (!this.isSolvable)
            curNode = null;
   }
   
   //is the initial board solvable?
   public boolean isSolvable()
   {
        return (this.isSolvable);
   }

   //min number of moves to solve initial board; -1 if unsolvable
   public int moves()
   {
        if (!this.isSolvable)
            return (-1);
        else
            return (this.curNode.moves);
   }

   //sequence of boards in a shortest solution; null if unsolvable
   public Iterable<Board> solution()
   {
        if (!this.isSolvable)
            return (null);
        else
            return (new boardsToSolution());
   }

   private class boardsToSolution implements Iterable<Board>
   {
        private final Stack<Board> boardsPassed = new Stack<>();

        public boardsToSolution()
        {
            SearchNode finalNode = Solver.this.curNode;
            while (finalNode != null)
            {
                (this.boardsPassed).push(finalNode.board);
                finalNode = finalNode.previous;
            }
        }
        public Iterator<Board> iterator()
        {
            return (this.boardsPassed.iterator());
        }

   }

   //create a class for a search node which contains instance variables: board, moves made from the orginal board, distance to the goal board. (Kinda like create a structure in C)
   private class SearchNode implements Comparable<SearchNode>{
        private final Board board;
        private final int moves;
        private final SearchNode previous;
        private final int mDistance;
        private final int priority;

        public SearchNode(Board board, int moves, SearchNode prev)
        {
            this.board = board;
            this.moves = moves;
            this.previous = prev;
            this.mDistance = board.manhattan();
            this.priority = this.moves + this.mDistance;
        }

        @Override
        public int compareTo(SearchNode that)
        {
            return (this.priority - that.priority);
        }

   }

   //test client
   public static void main(String[] args)
   {
        //create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int row = 0; row < n; row++)
        {
            for (int col = 0; col < n; col++)
                tiles[row][col] = in.readInt();
        }
        Board initial = new Board(tiles);
        //test
        StdOut.println("initial board\n");
        StdOut.println(initial.toString());
        //

        //solve the puzzle
        Solver solver = new Solver(initial);

        //print solution
        if (!solver.isSolvable())
            StdOut.println("No solution found\n");
        else
        {
            StdOut.println("Solution found. Minumum number of moves: " + solver.moves());
            for (Board boardPassed: solver.solution())
                StdOut.println(boardPassed.toString());
        }
   }
}
