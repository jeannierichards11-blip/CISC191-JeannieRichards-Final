package tictactoe;

/**
 * Strategy interface for AI move selection.
 * Allows different algorithms to be plugged in (Strategy Pattern).
 */
public interface ComputerMoveStrategy {
    
    /**
     * Chooses a move for the given mark on the current board.
     * @param board the current game board
     * @param mark the mark to play (typically O for computer)
     * @return the chosen Move
     */
    Move choose(Board board, char mark);

    /**
     * Returns a display name for this strategy.
     * @return the strategy name (e.g., "Easy", "Smart")
     */
    String getName();
}
