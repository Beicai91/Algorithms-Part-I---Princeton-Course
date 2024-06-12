import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Board 
{
    private final int[][] board;
    private int blank_x;
    private int blank_y;
    //create a board from an n-by-n array of tiles
    //where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles)
    {
        int n = tiles.length;
        this.board = new int[n][n];
        for (int row = 0; row < n; row++)
        {
            for (int col = 0; col < n; col++)
            {
                this.board[row][col] = tiles[row][col];
                if (tiles[row][col] == 0)
                {
                    this.blank_x = row;
                    this.blank_y = col;
                }
            }
        }
    }

    //string representation of this board
    public String toString()
    {
        int n = board.length;
        StringBuilder s = new StringBuilder();
        s.append(n).append("\n");
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
                s.append(board[i][j]).append(" ");
            s.append("\n");
        }
        return s.toString();
    }

    //board dimension n
    public int dimension()
    {
        return board.length;
    }

    //number of tiles out of place
    public int hamming()
    {
        int err;
        int n;
        int content;

        content = 1;
        err = 0;
        n = board.length;
        for (int row = 0; row < n; row++)
        {
            for (int col = 0; col < n; col++)
            {
                if (board[row][col] != content)
                    err++;
                content++;
            }
        }
        return (err - 1);
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan()
    {
        int sum;
        int n;
        int content;
        int row_o;
        int col_o;

        sum = 0;
        n = board.length;
        content = 1;
        row_o = 0;
        col_o = 0;
        for (int round = 0; round < n * n - 1; round++)
        {
            for (int row = 0; row < n; row++)
            {
                for (int col = 0; col < n; col++)
                {
                    if (board[row][col] == content)
                        sum += (Math.abs(row - row_o) + Math.abs(col - col_o));

                }
            }
            content++;
            if (col_o < 2)
                col_o++;
            else if (col_o == 2)
            {
                col_o = 0;
                row_o++;
            }
        }
        return (sum);
    }

    // is this board the goal board?
    public boolean isGoal()
    {
        int indicator;
        int n;
        int content;

        indicator = 1;
        n = board.length;
        content = 1;
        for (int row = 0; row < n; row++)
        {
            for (int col = 0; col < n; col++)
            {
                if (row == n - 1 && col == n - 1)
                    content = 0;
                if (board[row][col] != content)
                {
                    indicator = 0;
                    break;
                }
                content++;
            }
        }
        if (indicator == 1)
            return (true);
        else
            return (false);
    }

    // does this board equal y?
    public boolean equals(Object y)
    {
        if (this == y)
            return (true);
        if (y == null || this.getClass() != y.getClass())
            return (false);
        Board that = (Board)y;
        if (that.board.length != this.board.length)
            return (false);
        for (int row = 0; row < this.board.length; row++)
        {
            for (int col = 0; col < this.board.length; col++)
            {
                if (this.board[row][col] != that.board[row][col])
                    return (false);
            }
        }
        return (true);
    }

    // all neighboring boards
    public Iterable<Board> neighbors()
    {
        return new neighborIterable();
    }

    private class neighborIterable implements Iterable<Board>
    {
        private List<Board> neighbors = new ArrayList<Board>();
        //constructor of this inner class
        public neighborIterable()
        {
            int n = Board.this.dimension();
            if (blank_y != 0)
                createNeighbor(blank_x, blank_y - 1);
            if (blank_x != 0)
                createNeighbor(blank_x - 1, blank_y);
            if (blank_y != n - 1)
                createNeighbor(blank_x, blank_y + 1);
            if (blank_x != n - 1)
                createNeighbor(blank_x + 1, blank_y);
        }

        public void createNeighbor(int new_x, int new_y)
        {
            swap(board, blank_x, blank_y, new_x, new_y);
            neighbors.add(new Board(board));
            swap(board, blank_x, blank_y, new_x, new_y);
        }


        public Iterator<Board> iterator()
        {
            return neighbors.iterator();
        }
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int count = 0;
        int p1 = 0, q1 = 0, p2 = 0, q2 = 0;
        int n = this.dimension();
        for (int i = 0; i < n && count < 2; i++) {
            for (int j = 0; j < n && count < 2; j++) {
                if (board[i][j] != 0) {
                    if (count == 0) {
                        p1 = i;
                        q1 = j;
                    } else {
                        p2 = i;
                        q2 = j;
                    }
                    count++;
                }
            }
        }
        swap(board, p1, q1, p2, q2);
        Board twin = new Board(board);
        swap(board, p1, q1, p2, q2);
        return twin;
    }
    /* This method switches randomly any pair of tiles
    public Board twin()
    {
        List<int[]> nonZeroPos = new ArrayList<>();
        int len = this.board.length;
        int[] pos1;
        int[] pos2;

        for (int row = 0; row < len; row++)
        {
            for (int col = 0; col < len; col++)
            {
                if (this.board[row][col] != 0)
                    nonZeroPos.add(new int[]{row, col});
            }
        }
        Random rand = new Random();
        pos1 = nonZeroPos.get(rand.nextInt(nonZeroPos.size()));
        pos2 = nonZeroPos.get(rand.nextInt(nonZeroPos.size()));
        while (pos1 == pos2)
            pos2 = nonZeroPos.get(rand.nextInt(nonZeroPos.size()));
        swap(board, pos1[0], pos1[1], pos2[0], pos2[1]);
        Board twin_board = new Board(board);
        swap(board, pos1[0], pos1[1], pos2[0], pos2[1]); 
        return (twin_board);
    }*/

    private void swap(int[][] board, int x, int y, int new_x, int new_y)
    {
        int temp = board[x][y];
        board[x][y] = board[new_x][new_y];
        board[new_x][new_y] = temp;
    }

    // unit testing (not graded)
    public static void main(String[] args)
    {
        int[][] tiles1 = new int[][]{
            {8, 1, 3},
            {4, 0, 2},
            {7, 6, 5}
        };
        Board board1 = new Board(tiles1);
        System.out.println("Board info\n");
        System.out.println("Dimension: " + board1.dimension());
        System.out.println("Hamming distance: " + board1.hamming());
        System.out.println("Manhattan distance: " + board1.manhattan());
        System.out.println("Neighbours:\n");
        for (Board neighbor: board1.neighbors()){
            System.out.println(neighbor.toString());
        }
        System.out.println("Twin:\n");
        System.out.println(board1.twin());
        int[][] tiles2 = new int[][]{
            {8, 1, 3},
            {4, 0, 2},
            {7, 6, 5}
        };
        Board board2 = new Board(tiles2);
        System.out.println("Are board1 and board2 equal?\n");
        if (board1.equals(board2))
            System.out.println("Yes\n");
        else
            System.out.println("No\n");
        int[][] tiles3 = new int[][]{
            {2, 4, 8},
            {1, 0, 6}
        };
        Board board3 = new Board(tiles3);
        System.out.println("Are board1 and board3 equal?");
        if (board1.equals(board3))
            System.out.println("Yes\n");
        else
            System.out.println("No\n");
        
    }
    
}
