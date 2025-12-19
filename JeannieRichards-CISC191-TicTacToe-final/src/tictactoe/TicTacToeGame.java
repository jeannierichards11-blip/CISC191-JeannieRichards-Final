package tictactoe;

/**
 * Controller class that orchestrates the Tic-Tac-Toe game.
 * Manages turns, validates moves, coordinates between players and board.
 */
public class TicTacToeGame {
    
    private Board board;
    private Player aiPlayer;
    private char currentPlayer; // 'X' for human, 'O' for AI
    private boolean gameOver;
    private String statusMessage;

    // Human is always X, AI is always O
    public static final char HUMAN_MARK = Board.X;
    public static final char AI_MARK = Board.O;

    /**
     * Creates a new game with the specified AI player.
     * @param aiPlayer the AI opponent
     */
    public TicTacToeGame(Player aiPlayer) {
        this.board = new Board();
        this.aiPlayer = aiPlayer;
        reset();
    }

    /**
     * Creates a new game with default Easy AI.
     */
    public TicTacToeGame() {
        this(new AIPlayer(AI_MARK, new RandomMoveStrategy()));
    }

    /**
     * Resets the game to initial state.
     */
    public void reset() {
        board.reset();
        currentPlayer = HUMAN_MARK; // Human (X) always goes first
        gameOver = false;
        statusMessage = "Your move (X)";
    }

    /**
     * Returns the current board.
     * @return the Board object
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Returns whose turn it is.
     * @return 'X' or 'O'
     */
    public char getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Checks if it's the human player's turn.
     * @return true if human's turn
     */
    public boolean isHumanTurn() {
        return currentPlayer == HUMAN_MARK && !gameOver;
    }

    /**
     * Checks if the game is over.
     * @return true if game has ended
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Returns the current status message.
     * @return status string for display
     */
    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * Applies a human move at the specified position.
     * @param row the row index
     * @param col the column index
     * @return true if move was successful
     * @throws InvalidMoveException if move is invalid or not human's turn
     */
    public boolean applyHumanMove(int row, int col) throws InvalidMoveException {
        if (gameOver) {
            throw new InvalidMoveException("Game is already over.");
        }
        if (currentPlayer != HUMAN_MARK) {
            throw new InvalidMoveException("Not your turn!");
        }

        board.placeMark(row, col, HUMAN_MARK);
        
        // Check for win or draw
        if (board.checkWin(HUMAN_MARK)) {
            gameOver = true;
            statusMessage = "You win!";
            return true;
        }
        if (board.isFull()) {
            gameOver = true;
            statusMessage = "It's a tie!";
            return true;
        }

        // Switch to AI's turn
        currentPlayer = AI_MARK;
        statusMessage = "Computer's turn...";
        return true;
    }

    /**
     * Requests and applies the AI's move.
     * @return the Move made by the AI, or null if game is over
     * @throws InvalidMoveException if AI makes an invalid move (shouldn't happen)
     */
    public Move applyAIMove() throws InvalidMoveException {
        if (gameOver) {
            return null;
        }
        if (currentPlayer != AI_MARK) {
            throw new InvalidMoveException("Not AI's turn!");
        }

        Move aiMove = aiPlayer.chooseMove(board);
        if (aiMove == null) {
            // No valid moves (shouldn't happen if game logic is correct)
            gameOver = true;
            statusMessage = "No moves available!";
            return null;
        }

        board.placeMark(aiMove.getRow(), aiMove.getCol(), AI_MARK);

        // Check for win or draw
        if (board.checkWin(AI_MARK)) {
            gameOver = true;
            statusMessage = "Computer wins!";
            return aiMove;
        }
        if (board.isFull()) {
            gameOver = true;
            statusMessage = "It's a tie!";
            return aiMove;
        }

        // Switch back to human's turn
        currentPlayer = HUMAN_MARK;
        statusMessage = "Your move (X)";
        return aiMove;
    }

    /**
     * Gets the result of the game for scoring.
     * @return "WIN" if human won, "LOSS" if AI won, "TIE" for draw, null if game not over
     */
    public String getGameResult() {
        if (!gameOver) return null;
        
        char winner = board.getWinner();
        if (winner == HUMAN_MARK) return "WIN";
        if (winner == AI_MARK) return "LOSS";
        return "TIE";
    }

    /**
     * Sets a new AI player (for changing difficulty).
     * @param newAI the new AI player
     */
    public void setAIPlayer(Player newAI) {
        this.aiPlayer = newAI;
    }

    /**
     * Gets the current AI player.
     * @return the AI Player object
     */
    public Player getAIPlayer() {
        return aiPlayer;
    }
}
