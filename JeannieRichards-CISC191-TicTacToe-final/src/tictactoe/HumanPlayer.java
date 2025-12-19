package tictactoe;

/**
 * Human player implementation.
 * Extends AbstractPlayer. Move selection happens through the GUI,
 * so chooseMove() returns null (moves come from button clicks).
 */
public class HumanPlayer extends AbstractPlayer {

    /**
     * Creates a human player with the specified mark.
     * @param mark the player's mark (typically X)
     */
    public HumanPlayer(char mark) {
        super(mark, "You");
    }

    /**
     * Creates a human player with a custom name.
     * @param mark the player's mark
     * @param name the player's display name
     */
    public HumanPlayer(char mark, String name) {
        super(mark, name);
    }

    @Override
    public Move chooseMove(Board board) {
        // Human moves come from GUI clicks, not computed here
        return null;
    }

    @Override
    public boolean isHuman() {
        return true;
    }
}
