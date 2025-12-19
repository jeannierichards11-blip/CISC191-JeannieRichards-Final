package tictactoe;

/**
 * Custom exception thrown when an invalid move is attempted.
 * This includes moves to occupied cells, out-of-bounds moves,
 * or moves made when it's not the player's turn.
 */
public class InvalidMoveException extends Exception {
    
    /**
     * Creates a new InvalidMoveException with the specified message.
     * @param message description of why the move is invalid
     */
    public InvalidMoveException(String message) {
        super(message);
    }
}
