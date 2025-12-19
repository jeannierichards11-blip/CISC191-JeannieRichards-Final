package tictactoe;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Tic-Tac-Toe game board.
 * Maintains a 3x3 grid and provides methods for placing marks,
 * checking win conditions, and determining game state.
 */
public class Board {
    public static final int SIZE = 3;
    public static final char EMPTY = ' ';
    public static final char X = 'X';
    public static final char O = 'O';

    private char[][] grid;

    /**
     * Creates a new empty board.
     */
    public Board() {
        grid = new char[SIZE][SIZE];
        reset();
    }

    /**
     * Private constructor for creating a copy.
     */
    private Board(char[][] gridToCopy) {
        grid = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = gridToCopy[i][j];
            }
        }
    }

    /**
     * Resets the board to empty state.
     */
    public void reset() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = EMPTY;
            }
        }
    }

    /**
     * Returns the character at the specified position.
     * @param row the row index (0-2)
     * @param col the column index (0-2)
     * @return the mark at that position (X, O, or EMPTY)
     */
    public char getCell(int row, int col) {
        return grid[row][col];
    }

    /**
     * Returns the entire grid (for display purposes).
     * @return a copy of the 2D grid array
     */
    public char[][] getGrid() {
        char[][] copy = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                copy[i][j] = grid[i][j];
            }
        }
        return copy;
    }

    /**
     * Checks if a position is within bounds.
     * @param row the row index
     * @param col the column index
     * @return true if valid position
     */
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }

    /**
     * Checks if a cell is empty.
     * @param row the row index
     * @param col the column index
     * @return true if the cell is empty
     */
    public boolean isEmpty(int row, int col) {
        return grid[row][col] == EMPTY;
    }

    /**
     * Places a mark on the board.
     * @param row the row index
     * @param col the column index
     * @param mark the mark to place (X or O)
     * @return true if placement was successful
     * @throws InvalidMoveException if the move is invalid
     */
    public boolean placeMark(int row, int col, char mark) throws InvalidMoveException {
        if (!isValidPosition(row, col)) {
            throw new InvalidMoveException("Position (" + row + ", " + col + ") is out of bounds.");
        }
        if (!isEmpty(row, col)) {
            throw new InvalidMoveException("Cell (" + row + ", " + col + ") is already occupied.");
        }
        if (mark != X && mark != O) {
            throw new InvalidMoveException("Invalid mark: " + mark + ". Must be X or O.");
        }
        grid[row][col] = mark;
        return true;
    }

    /**
     * Returns a list of all empty cells as Move objects.
     * @return list of available moves
     */
    public List<Move> getEmptyCells() {
        List<Move> emptyCells = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j] == EMPTY) {
                    emptyCells.add(new Move(i, j));
                }
            }
        }
        return emptyCells;
    }

    /**
     * Checks if the specified mark has won.
     * @param mark the mark to check (X or O)
     * @return true if this mark has won
     */
    public boolean checkWin(char mark) {
        // Check rows
        for (int i = 0; i < SIZE; i++) {
            if (grid[i][0] == mark && grid[i][1] == mark && grid[i][2] == mark) {
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < SIZE; j++) {
            if (grid[0][j] == mark && grid[1][j] == mark && grid[2][j] == mark) {
                return true;
            }
        }

        // Check diagonals
        if (grid[0][0] == mark && grid[1][1] == mark && grid[2][2] == mark) {
            return true;
        }
        if (grid[0][2] == mark && grid[1][1] == mark && grid[2][0] == mark) {
            return true;
        }

        return false;
    }

    /**
     * Checks if the board is full (no empty cells).
     * @return true if no moves remain
     */
    public boolean isFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the game is a draw (board full, no winner).
     * @return true if the game is a tie
     */
    public boolean isDraw() {
        return isFull() && !checkWin(X) && !checkWin(O);
    }

    /**
     * Checks if the game is over (win or draw).
     * @return true if game has ended
     */
    public boolean isGameOver() {
        return checkWin(X) || checkWin(O) || isFull();
    }

    /**
     * Returns the winner mark, or EMPTY if no winner yet.
     * @return X, O, or EMPTY
     */
    public char getWinner() {
        if (checkWin(X)) return X;
        if (checkWin(O)) return O;
        return EMPTY;
    }

    /**
     * Returns the winning line cells if there's a winner.
     * @return array of 6 ints [r1,c1,r2,c2,r3,c3] or null if no winner
     */
    public int[] getWinningLine() {
        char winner = getWinner();
        if (winner == EMPTY) return null;

        // Check rows
        for (int i = 0; i < SIZE; i++) {
            if (grid[i][0] == winner && grid[i][1] == winner && grid[i][2] == winner) {
                return new int[]{i, 0, i, 1, i, 2};
            }
        }

        // Check columns
        for (int j = 0; j < SIZE; j++) {
            if (grid[0][j] == winner && grid[1][j] == winner && grid[2][j] == winner) {
                return new int[]{0, j, 1, j, 2, j};
            }
        }

        // Check diagonals
        if (grid[0][0] == winner && grid[1][1] == winner && grid[2][2] == winner) {
            return new int[]{0, 0, 1, 1, 2, 2};
        }
        if (grid[0][2] == winner && grid[1][1] == winner && grid[2][0] == winner) {
            return new int[]{0, 2, 1, 1, 2, 0};
        }

        return null;
    }

    /**
     * Creates a deep copy of this board for AI look-ahead.
     * @return a new Board with the same state
     */
    public Board copy() {
        return new Board(this.grid);
    }

    /**
     * Counts the number of moves made so far.
     * @return number of X and O marks on the board
     */
    public int getMoveCount() {
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j] != EMPTY) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            sb.append(" ");
            for (int j = 0; j < SIZE; j++) {
                sb.append(grid[i][j] == EMPTY ? "-" : grid[i][j]);
                if (j < SIZE - 1) sb.append(" | ");
            }
            sb.append("\n");
            if (i < SIZE - 1) sb.append("-----------\n");
        }
        return sb.toString();
    }
}
