package tictactoe;

/**
 * AI player implementation that delegates move selection to a strategy.
 * Extends AbstractPlayer and allows the AI difficulty to be changed at runtime.
 */
public class AIPlayer extends AbstractPlayer {
    
    private ComputerMoveStrategy strategy;

    /**
     * Creates an AI player with the specified mark and strategy.
     * @param mark the mark to use (typically O)
     * @param strategy the move selection strategy
     */
    public AIPlayer(char mark, ComputerMoveStrategy strategy) {
        super(mark, "Computer (" + strategy.getName() + ")");
        this.strategy = strategy;
    }

    @Override
    public Move chooseMove(Board board) {
        return strategy.choose(board, mark);
    }

    @Override
    public boolean isHuman() {
        return false;
    }

    /**
     * Changes the AI strategy at runtime.
     * @param newStrategy the new strategy to use
     */
    public void setStrategy(ComputerMoveStrategy newStrategy) {
        this.strategy = newStrategy;
        this.name = "Computer (" + newStrategy.getName() + ")";
    }

    /**
     * Returns the current strategy.
     * @return the current ComputerMoveStrategy
     */
    public ComputerMoveStrategy getStrategy() {
        return strategy;
    }
}
