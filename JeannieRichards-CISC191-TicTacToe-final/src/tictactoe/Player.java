package tictactoe;

/**
 * Interface defining the contract for all players (human or AI).
 * Allows polymorphic handling of different player types.
 */
public interface Player {
    
    /**
     * Returns the mark this player uses (X or O).
     * @return the player's mark character
     */
    char getMark();

    /**
     * Returns the player's display name.
     * @return the player's name
     */
    String getName();

    /**
     * Chooses the next move based on the current board state.
     * For human players, this may return null (move comes from UI).
     * For AI players, this computes and returns the chosen move.
     * 
     * @param board the current game board
     * @return the chosen Move, or null if not applicable
     */
    Move chooseMove(Board board);
}
