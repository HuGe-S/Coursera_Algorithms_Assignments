import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    private final int[][] tiles;
    public Board(int[][] tiles) {
        if (!(tiles.length * tiles[0].length < 128*128 && tiles.length * tiles[0].length > 2)) {
            throw new IndexOutOfBoundsException("Tiles is too large");
        }
        this.tiles = new int[tiles.length][tiles[0].length];
        for (int i = 0; i < tiles.length; i++) {
            this.tiles[i] = Arrays.copyOf(tiles[i], tiles[i].length);
        }

    }

    // string representation of this board
    public String toString() {
        StringBuilder stb = new StringBuilder();
        assert tiles != null;
        stb.append(tiles.length+"\n");
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                stb.append(tiles[i][j] + " ");
            }
            stb.append("\n");
        }
        return stb.toString();
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        int count = 0;
        for (int[] row: tiles
             ) {
            for (int i : row
            ) {
                if (count == tiles.length * tiles[0].length - 1) {
                    break;
                }
                count++;
                if (i != count) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sumManhattan = 0;
        int count = 0;
        for (int[] row:
             tiles) {
            for (int i:
            row) {
                if (i == 0) {
                    count++;
                    continue;
                }
                i-=1;
                int rowDistance = Math.abs((count / this.dimension()) - (i / this.dimension()));
                int colDistance = Math.abs((count % this.dimension()) - (i % this.dimension()));
                sumManhattan += rowDistance + colDistance;
                count++;
            }

        }
        return sumManhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (!y.getClass().equals(this.getClass())) {
            return false;
        }
        if (this.dimension() != ((Board) y).dimension()) {
            return false;
        }

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] != ((Board) y).tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();
        boolean up, down, left, right;
        up = down = left = right = true;
        int zeroPosition = -1;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] == 0) {
                    zeroPosition = i * tiles.length + j;
                }
            }
        }
        // left
        if (zeroPosition % tiles.length == 0) {
            left = false;
        }
        // right
        if (zeroPosition % tiles.length == tiles.length-1) {
            right = false;
        }
        // up
        if (zeroPosition / tiles.length == 0) {
            up = false;
        }
        // down
        if (zeroPosition / tiles.length == tiles.length-1) {
            down = false;
        }

        if (left) {
            int [][]leftTile = this.copy(tiles);
            int temp;
            temp = leftTile [ zeroPosition / tiles.length][zeroPosition % tiles.length];
            leftTile [ zeroPosition / tiles.length][zeroPosition % tiles.length] = leftTile [ zeroPosition / tiles.length][(zeroPosition % tiles.length) - 1];
            leftTile [ zeroPosition / tiles.length][(zeroPosition % tiles.length) - 1] = temp;
            neighbors.push(new Board(leftTile));
        }
        if (right) {
            int [][]rightTile = this.copy(tiles);
            int temp;
            temp = rightTile [ zeroPosition / tiles.length][zeroPosition % tiles.length];
            rightTile [ zeroPosition / tiles.length][zeroPosition % tiles.length] = rightTile [ zeroPosition / tiles.length][(zeroPosition % tiles.length) + 1];
            rightTile [ zeroPosition / tiles.length][(zeroPosition % tiles.length) + 1] = temp;
            neighbors.push(new Board(rightTile));
        }
        if (up) {
            int [][]upTile = this.copy(tiles);
            int temp;
            temp = upTile [ zeroPosition / tiles.length][zeroPosition % tiles.length];
            upTile [ zeroPosition / tiles.length][zeroPosition % tiles.length] = upTile [ (zeroPosition / tiles.length) -1][(zeroPosition % tiles.length)];
            upTile [ (zeroPosition / tiles.length) - 1][(zeroPosition % tiles.length)] = temp;
            neighbors.push(new Board(upTile));
        }
        if (down) {
            int [][]downTile = this.copy(tiles);
            int temp;
            temp = downTile [ zeroPosition / tiles.length][zeroPosition % tiles.length];
            downTile [ zeroPosition / tiles.length][zeroPosition % tiles.length] = downTile [ (zeroPosition / tiles.length) + 1][(zeroPosition % tiles.length)];
            downTile [ (zeroPosition / tiles.length) + 1][(zeroPosition % tiles.length) ] = temp;
            neighbors.push(new Board(downTile));
        }
        return neighbors;

    }
    private int[][] copy(int [][]origin) {
        int[][] copy = new int[origin.length][origin[0].length];
        for (int i = 0; i < origin.length; i++) {
            for (int j = 0; j < origin[0].length; j++) {
                copy[i][j] = origin [i][j];
            }
        }
        return copy;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twinTiles = this.copy(this.tiles);
        if (tiles.length == 1)  return null;
        if (tiles[0][0] != 0 && tiles[0][1] != 0) {
            int temp = twinTiles[0][0];
            twinTiles[0][0] = twinTiles[0][1];
            twinTiles[0][1] = temp;
        }
        else {
            int temp = twinTiles[1][0];
            twinTiles[1][0] = twinTiles[1][1];
            twinTiles[1][1] = temp;
        }
        Board twin = new Board(twinTiles);
        return twin;

    }

    // unit testing (not graded)
    public static void main(String[] args) {
    int[][] tiles = {{1, 0}, {2, 3}};
    Board b = new Board(tiles);
    StdOut.println(b.toString());
    StdOut.println(b.dimension());
    StdOut.println(b.hamming());
    StdOut.println(b.manhattan());
    StdOut.println(b.neighbors());
    StdOut.println("test completed");

}
}
