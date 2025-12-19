package tictactoe;

import java.util.List;
import java.util.Random;

/**
 * Easy AI strategy that randomly selects from available empty cells.
 * Provides an easy opponent for beginners.
 */
public class RandomMoveStrategy implements ComputerMoveStrategy {
    
    private Random random;

    /**
     * Creates a new RandomMoveStrategy with default random seed.
     */
    public RandomMoveStrategy() {
        this.random = new Random();
    }

    /**
     * Creates a new RandomMoveStrategy with a specific seed (for testing).
     * @param seed the random seed
     */
    public RandomMoveStrategy(long seed) {
        this.random = new Random(seed);
    }

    @Override
    public Move choose(Board board, char mark) {
        List<Move> emptyCells = board.getEmptyCells();
        
        if (emptyCells.isEmpty()) {
            return null; // No moves available
        }
        
        // Pick a random empty cell
        int index = random.nextInt(emptyCells.size());
        return emptyCells.get(index);
    }

    @Override
    public String getName() {
        return "Easy (Random)";
    }
}
