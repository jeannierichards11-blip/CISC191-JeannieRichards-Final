package tictactoe;

/**
 * Immutable representation of a board position (row, column).
 * Used for both potential and actual moves in the game.
 */
public class Move {
    private final int row;
    private final int col;

    /**
     * Creates a new Move at the specified position.
     * @param row the row index (0-2)
     * @param col the column index (0-2)
     */
    public Move(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * @return the row index of this move
     */
    public int getRow() {
        return row;
    }

    /**
     * @return the column index of this move
     */
    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return "Move(" + row + ", " + col + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Move move = (Move) obj;
        return row == move.row && col == move.col;
    }

    @Override
    public int hashCode() {
        return 31 * row + col;
    }
}
