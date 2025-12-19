package tictactoe;

import java.util.List;

/**
 * Unbeatable AI strategy using the Minimax algorithm with recursion.
 * Demonstrates recursion (LO9) through recursive game tree exploration.
 * 
 * Minimax works by:
 * 1. Recursively exploring all possible future game states
 * 2. Assuming the opponent plays optimally
 * 3. Choosing the move that maximizes our minimum guaranteed outcome
 */
public class MinimaxStrategy implements ComputerMoveStrategy {

    private char aiMark;
    private char humanMark;
    private int nodesExplored; // For demonstration purposes

    @Override
    public Move choose(Board board, char mark) {
        this.aiMark = mark;
        this.humanMark = (mark == Board.X) ? Board.O : Board.X;
        this.nodesExplored = 0;

        List<Move> availableMoves = board.getEmptyCells();
        
        if (availableMoves.isEmpty()) {
            return null;
        }

        Move bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        // Try each available move and find the best one
        for (Move move : availableMoves) {
            Board testBoard = board.copy();
            try {
                testBoard.placeMark(move.getRow(), move.getCol(), aiMark);
            } catch (InvalidMoveException e) {
                continue;
            }

            // Recursively calculate the score for this move
            int score = minimax(testBoard, 0, false);

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }

        System.out.println("Minimax explored " + nodesExplored + " nodes");
        return bestMove;
    }

    /**
     * Recursive Minimax algorithm.
     * 
     * @param board the current board state
     * @param depth the current depth in the game tree
     * @param isMaximizing true if it's the AI's turn (maximizing player)
     * @return the score of the board position
     */
    private int minimax(Board board, int depth, boolean isMaximizing) {
        nodesExplored++;

        // Base cases: check for terminal states
        if (board.checkWin(aiMark)) {
            return 10 - depth; // Win sooner is better
        }
        if (board.checkWin(humanMark)) {
            return depth - 10; // Lose later is better
        }
        if (board.isFull()) {
            return 0; // Draw
        }

        List<Move> availableMoves = board.getEmptyCells();

        if (isMaximizing) {
            // AI's turn - maximize the score
            int bestScore = Integer.MIN_VALUE;
            
            for (Move move : availableMoves) {
                Board testBoard = board.copy();
                try {
                    testBoard.placeMark(move.getRow(), move.getCol(), aiMark);
                } catch (InvalidMoveException e) {
                    continue;
                }
                
                // Recursive call
                int score = minimax(testBoard, depth + 1, false);
                bestScore = Math.max(score, bestScore);
            }
            
            return bestScore;
        } else {
            // Human's turn - minimize the score (opponent plays optimally)
            int bestScore = Integer.MAX_VALUE;
            
            for (Move move : availableMoves) {
                Board testBoard = board.copy();
                try {
                    testBoard.placeMark(move.getRow(), move.getCol(), humanMark);
                } catch (InvalidMoveException e) {
                    continue;
                }
                
                // Recursive call
                int score = minimax(testBoard, depth + 1, true);
                bestScore = Math.min(score, bestScore);
            }
            
            return bestScore;
        }
    }

    @Override
    public String getName() {
        return "Impossible (Minimax)";
    }
}
