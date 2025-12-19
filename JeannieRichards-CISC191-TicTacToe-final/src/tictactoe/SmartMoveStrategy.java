package tictactoe;

import java.util.List;

/**
 * Smarter AI strategy that follows basic Tic-Tac-Toe tactics:
 * 1. Win if possible
 * 2. Block opponent's winning move
 * 3. Take center if available
 * 4. Take a corner if available
 * 5. Take any available cell
 */
public class SmartMoveStrategy implements ComputerMoveStrategy {

    @Override
    public Move choose(Board board, char mark) {
        char opponentMark = (mark == Board.X) ? Board.O : Board.X;
        
        // 1. Check if we can win
        Move winningMove = findWinningMove(board, mark);
        if (winningMove != null) {
            return winningMove;
        }
        
        // 2. Block opponent's winning move
        Move blockingMove = findWinningMove(board, opponentMark);
        if (blockingMove != null) {
            return blockingMove;
        }
        
        // 3. Take center if available
        if (board.isEmpty(1, 1)) {
            return new Move(1, 1);
        }
        
        // 4. Take a corner if available
        int[][] corners = {{0, 0}, {0, 2}, {2, 0}, {2, 2}};
        for (int[] corner : corners) {
            if (board.isEmpty(corner[0], corner[1])) {
                return new Move(corner[0], corner[1]);
            }
        }
        
        // 5. Take any available edge
        int[][] edges = {{0, 1}, {1, 0}, {1, 2}, {2, 1}};
        for (int[] edge : edges) {
            if (board.isEmpty(edge[0], edge[1])) {
                return new Move(edge[0], edge[1]);
            }
        }
        
        // 6. Fallback: take first available cell
        List<Move> emptyCells = board.getEmptyCells();
        if (!emptyCells.isEmpty()) {
            return emptyCells.get(0);
        }
        
        return null; // No moves available
    }

    /**
     * Finds a winning move for the specified mark, if one exists.
     * @param board the current board
     * @param mark the mark to check for winning moves
     * @return a Move that would win, or null if none exists
     */
    private Move findWinningMove(Board board, char mark) {
        List<Move> emptyCells = board.getEmptyCells();
        
        for (Move move : emptyCells) {
            // Create a copy and test the move
            Board testBoard = board.copy();
            try {
                testBoard.placeMark(move.getRow(), move.getCol(), mark);
                if (testBoard.checkWin(mark)) {
                    return move;
                }
            } catch (InvalidMoveException e) {
                // Should not happen since we're checking empty cells
            }
        }
        
        return null;
    }

    @Override
    public String getName() {
        return "Smart";
    }
}
