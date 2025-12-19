package tictactoe;

/**
 * Abstract base class for all players.
 * Demonstrates use of abstract classes (LO4).
 * Provides common functionality while requiring subclasses to implement move selection.
 */
public abstract class AbstractPlayer implements Player {
    
    protected char mark;
    protected String name;
    protected int movesMade;

    /**
     * Creates a player with the specified mark and name.
     * @param mark the player's mark (X or O)
     * @param name the player's display name
     */
    public AbstractPlayer(char mark, String name) {
        this.mark = mark;
        this.name = name;
        this.movesMade = 0;
    }

    @Override
    public char getMark() {
        return mark;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the number of moves this player has made.
     * @return move count
     */
    public int getMovesMade() {
        return movesMade;
    }

    /**
     * Increments the move counter.
     */
    public void incrementMoves() {
        movesMade++;
    }

    /**
     * Resets the move counter for a new game.
     */
    public void resetMoves() {
        movesMade = 0;
    }

    /**
     * Abstract method that subclasses must implement to choose a move.
     * @param board the current game board
     * @return the chosen Move
     */
    @Override
    public abstract Move chooseMove(Board board);

    /**
     * Returns whether this player is human-controlled.
     * @return true if human, false if AI
     */
    public abstract boolean isHuman();

    @Override
    public String toString() {
        return name + " (" + mark + ")";
    }
}
