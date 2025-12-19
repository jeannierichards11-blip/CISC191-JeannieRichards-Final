package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Enhanced main GUI frame for the Tic-Tac-Toe game.
 * Features animated graphics, sound effects, and multiple difficulty levels.
 */
public class TicTacToeFrame extends JFrame {
    
    private TicTacToeGame game;
    private GamePanel gamePanel;
    private JLabel statusLabel;
    private JLabel scoreLabel;
    private ScoreManager scoreManager;
    private SoundManager soundManager;
    private String playerName;
    private Theme currentTheme;
    
    // UI panels that need theme updates
    private JPanel headerPanel;
    private JPanel bottomPanel;
    
    // Score tracking for current session
    private int sessionWins = 0;
    private int sessionLosses = 0;
    private int sessionTies = 0;

    // UI Colors (dark theme)
    private static final Color BG_COLOR = new Color(30, 30, 35);
    private static final Color PANEL_COLOR = new Color(45, 45, 50);
    private static final Color TEXT_COLOR = new Color(220, 220, 220);
    private static final Color ACCENT_COLOR = new Color(116, 185, 255);
    private static final Color WIN_COLOR = new Color(0, 255, 136);
    private static final Color LOSE_COLOR = new Color(255, 118, 117);

    /**
     * Creates and displays the game window.
     */
    public TicTacToeFrame() {
        this.game = new TicTacToeGame();
        this.scoreManager = new ScoreManager();
        this.soundManager = new SoundManager();
        this.playerName = "Player";
        this.currentTheme = Theme.darkTheme();
        
        initializeUI();
        setVisible(true);
    }

    /**
     * Sets up the user interface components.
     */
    private void initializeUI() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(BG_COLOR);

        // Create menu bar
        createMenuBar();

        // Header panel
        headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Game panel (center)
        gamePanel = new GamePanel();
        gamePanel.setBoard(game.getBoard());
        gamePanel.setCellClickListener(this::handleCellClick);
        
        JPanel gamePanelWrapper = new JPanel();
        gamePanelWrapper.setLayout(new BoxLayout(gamePanelWrapper, BoxLayout.Y_AXIS));
        gamePanelWrapper.setBackground(BG_COLOR);
        gamePanelWrapper.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        gamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gamePanelWrapper.add(Box.createVerticalGlue());
        gamePanelWrapper.add(gamePanel);
        gamePanelWrapper.add(Box.createVerticalGlue());
        add(gamePanelWrapper, BorderLayout.CENTER);

        // Bottom panel
        bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);

        // Set window properties
        setSize(420, 580);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Play startup sound
        soundManager.playNewGame();
    }

    /**
     * Creates the header panel with title and score.
     */
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(PANEL_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Title
        JLabel titleLabel = new JLabel("TIC TAC TOE");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        panel.add(Box.createVerticalStrut(5));

        // Subtitle
        JLabel subtitleLabel = new JLabel("You (X) vs Computer (O)");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(150, 150, 150));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(subtitleLabel);

        panel.add(Box.createVerticalStrut(10));

        // Session score
        scoreLabel = new JLabel("Wins: 0 | Losses: 0 | Ties: 0");
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        scoreLabel.setForeground(ACCENT_COLOR);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(scoreLabel);

        return panel;
    }

    /**
     * Creates the bottom panel with status and buttons.
     */
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(PANEL_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 20, 20));

        // Status label
        statusLabel = new JLabel("Your move");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        statusLabel.setForeground(TEXT_COLOR);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(statusLabel);

        panel.add(Box.createVerticalStrut(15));

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(PANEL_COLOR);

        JButton newGameButton = createStyledButton("New Game");
        newGameButton.addActionListener(e -> startNewGame());
        buttonPanel.add(newGameButton);

        JButton difficultyButton = createStyledButton("Difficulty");
        difficultyButton.addActionListener(e -> showDifficultyDialog());
        buttonPanel.add(difficultyButton);

        JButton themeButton = createStyledButton("Theme");
        themeButton.addActionListener(e -> showThemeDialog());
        buttonPanel.add(themeButton);

        panel.add(buttonPanel);

        return panel;
    }

    /**
     * Creates a styled button.
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(TEXT_COLOR);
        button.setBackground(new Color(60, 60, 70));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 90), 1),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(80, 80, 95));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(60, 60, 70));
            }
        });
        
        return button;
    }

    /**
     * Creates the menu bar with game options.
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(PANEL_COLOR);
        menuBar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        // Game menu
        JMenu gameMenu = new JMenu("Game");
        gameMenu.setForeground(TEXT_COLOR);
        
        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, 
            Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        newGameItem.addActionListener(e -> startNewGame());
        gameMenu.add(newGameItem);
        
        gameMenu.addSeparator();
        
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        gameMenu.add(exitItem);
        
        menuBar.add(gameMenu);

        // Difficulty menu
        JMenu difficultyMenu = new JMenu("Difficulty");
        difficultyMenu.setForeground(TEXT_COLOR);
        ButtonGroup difficultyGroup = new ButtonGroup();
        
        JRadioButtonMenuItem easyItem = new JRadioButtonMenuItem("Easy (Random)", true);
        easyItem.addActionListener(e -> setDifficulty(new RandomMoveStrategy()));
        difficultyGroup.add(easyItem);
        difficultyMenu.add(easyItem);
        
        JRadioButtonMenuItem smartItem = new JRadioButtonMenuItem("Medium (Smart)");
        smartItem.addActionListener(e -> setDifficulty(new SmartMoveStrategy()));
        difficultyGroup.add(smartItem);
        difficultyMenu.add(smartItem);
        
        JRadioButtonMenuItem minimaxItem = new JRadioButtonMenuItem("Impossible (Minimax)");
        minimaxItem.addActionListener(e -> setDifficulty(new MinimaxStrategy()));
        difficultyGroup.add(minimaxItem);
        difficultyMenu.add(minimaxItem);
        
        menuBar.add(difficultyMenu);

        // Sound menu
        JMenu soundMenu = new JMenu("Sound");
        soundMenu.setForeground(TEXT_COLOR);
        
        JCheckBoxMenuItem soundToggle = new JCheckBoxMenuItem("Sound Effects", true);
        soundToggle.addActionListener(e -> soundManager.setSoundEnabled(soundToggle.isSelected()));
        soundMenu.add(soundToggle);
        
        menuBar.add(soundMenu);

        // Theme menu
        JMenu themeMenu = new JMenu("Theme");
        themeMenu.setForeground(TEXT_COLOR);
        ButtonGroup themeGroup = new ButtonGroup();
        
        for (Theme t : Theme.getAllThemes()) {
            JRadioButtonMenuItem themeItem = new JRadioButtonMenuItem(t.getName());
            if (t.getName().equals("Dark")) {
                themeItem.setSelected(true);
            }
            themeItem.addActionListener(e -> applyTheme(t));
            themeGroup.add(themeItem);
            themeMenu.add(themeItem);
        }
        
        menuBar.add(themeMenu);

        // Scores menu
        JMenu scoresMenu = new JMenu("Scores");
        scoresMenu.setForeground(TEXT_COLOR);
        
        JMenuItem viewScoresItem = new JMenuItem("View Statistics");
        viewScoresItem.addActionListener(e -> showScores());
        scoresMenu.add(viewScoresItem);
        
        JMenuItem clearScoresItem = new JMenuItem("Clear Statistics");
        clearScoresItem.addActionListener(e -> clearScores());
        scoresMenu.add(clearScoresItem);
        
        menuBar.add(scoresMenu);

        // Help menu
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setForeground(TEXT_COLOR);
        
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAbout());
        helpMenu.add(aboutItem);
        
        JMenuItem howToPlayItem = new JMenuItem("How to Play");
        howToPlayItem.addActionListener(e -> showHowToPlay());
        helpMenu.add(howToPlayItem);
        
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    /**
     * Handles a click on a cell.
     */
    private void handleCellClick(int row, int col) {
        if (!game.isHumanTurn()) {
            return;
        }

        try {
            game.applyHumanMove(row, col);
            gamePanel.animateMark(row, col, Board.X);
            soundManager.playX();
            updateDisplay();

            if (!game.isGameOver()) {
                // Delay for AI move
                statusLabel.setText("Computer thinking...");
                Timer timer = new Timer(400, e -> makeAIMove());
                timer.setRepeats(false);
                timer.start();
            } else {
                handleGameEnd();
            }

        } catch (InvalidMoveException e) {
            soundManager.playError();
        }
    }

    /**
     * Makes the AI move.
     */
    private void makeAIMove() {
        try {
            Move aiMove = game.applyAIMove();
            if (aiMove != null) {
                gamePanel.animateMark(aiMove.getRow(), aiMove.getCol(), Board.O);
                soundManager.playO();
            }
            updateDisplay();

            if (game.isGameOver()) {
                handleGameEnd();
            }
        } catch (InvalidMoveException e) {
            soundManager.playError();
        }
    }

    /**
     * Updates the display after a move.
     */
    private void updateDisplay() {
        gamePanel.setBoard(game.getBoard());
        statusLabel.setText(game.getStatusMessage());
        
        if (game.isGameOver()) {
            gamePanel.setGameOver(true);
        }
    }

    /**
     * Handles the end of a game.
     */
    private void handleGameEnd() {
        String result = game.getGameResult();
        
        // Show winning line
        int[] winLine = game.getBoard().getWinningLine();
        if (winLine != null) {
            gamePanel.setWinningLine(winLine);
        }
        
        // Update session score and play sound
        switch (result) {
            case "WIN":
                sessionWins++;
                statusLabel.setForeground(WIN_COLOR);
                soundManager.playWin();
                break;
            case "LOSS":
                sessionLosses++;
                statusLabel.setForeground(LOSE_COLOR);
                soundManager.playLose();
                break;
            case "TIE":
                sessionTies++;
                statusLabel.setForeground(TEXT_COLOR);
                soundManager.playDraw();
                break;
        }
        
        updateScoreLabel();
        saveScore();
    }

    /**
     * Updates the session score label.
     */
    private void updateScoreLabel() {
        scoreLabel.setText(String.format("Wins: %d | Losses: %d | Ties: %d",
            sessionWins, sessionLosses, sessionTies));
    }

    /**
     * Saves the game result.
     */
    private void saveScore() {
        String result = game.getGameResult();
        if (result != null) {
            ScoreEntry entry = new ScoreEntry(
                playerName,
                game.getAIPlayer().getName(),
                result,
                game.getBoard().getMoveCount()
            );
            try {
                scoreManager.append(entry);
            } catch (IOException e) {
                System.err.println("Could not save score: " + e.getMessage());
            }
        }
    }

    /**
     * Starts a new game.
     */
    private void startNewGame() {
        game.reset();
        gamePanel.reset();
        gamePanel.setBoard(game.getBoard());
        statusLabel.setText("Your move");
        statusLabel.setForeground(TEXT_COLOR);
        soundManager.playNewGame();
    }

    /**
     * Shows the difficulty selection dialog.
     */
    private void showDifficultyDialog() {
        String[] options = {"Easy (Random)", "Medium (Smart)", "Impossible (Minimax)"};
        int choice = JOptionPane.showOptionDialog(this,
            "Select AI Difficulty:\n\n" +
            "• Easy: Random moves\n" +
            "• Medium: Basic strategy (blocks and wins)\n" +
            "• Impossible: Perfect play (unbeatable)",
            "Select Difficulty",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null, options, options[0]);
        
        if (choice >= 0) {
            ComputerMoveStrategy strategy;
            switch (choice) {
                case 1: strategy = new SmartMoveStrategy(); break;
                case 2: strategy = new MinimaxStrategy(); break;
                default: strategy = new RandomMoveStrategy(); break;
            }
            setDifficulty(strategy);
        }
    }

    /**
     * Shows the theme selection dialog.
     */
    private void showThemeDialog() {
        Theme[] themes = Theme.getAllThemes();
        String[] themeNames = new String[themes.length];
        for (int i = 0; i < themes.length; i++) {
            themeNames[i] = themes[i].getName();
        }
        
        String selected = (String) JOptionPane.showInputDialog(this,
            "Choose a theme:\n\n" +
            "✨ Sparkle - Magical particle effects\n" +
            "🌈 Rainbow - Color cycling gradients\n" +
            "💫 Neon - Glowing cyberpunk style\n" +
            "🌊 Ocean - Calm wave animations\n" +
            "🔥 Fire - Hot flame particles\n" +
            "🎮 Retro - Classic arcade look",
            "Select Theme",
            JOptionPane.PLAIN_MESSAGE,
            null,
            themeNames,
            currentTheme.getName());
        
        if (selected != null) {
            for (Theme t : themes) {
                if (t.getName().equals(selected)) {
                    applyTheme(t);
                    break;
                }
            }
        }
    }

    /**
     * Changes the AI difficulty level.
     */
    private void setDifficulty(ComputerMoveStrategy strategy) {
        AIPlayer newAI = new AIPlayer(Board.O, strategy);
        game.setAIPlayer(newAI);
        startNewGame();
        JOptionPane.showMessageDialog(this,
            "Difficulty set to: " + strategy.getName(),
            "Difficulty Changed",
            JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Applies a theme to the entire UI.
     */
    private void applyTheme(Theme theme) {
        this.currentTheme = theme;
        
        // Update game panel
        gamePanel.setTheme(theme);
        
        // Update frame background
        getContentPane().setBackground(theme.getBackgroundColor());
        
        // Update header panel
        headerPanel.setBackground(theme.getPanelColor());
        for (Component c : headerPanel.getComponents()) {
            if (c instanceof JLabel) {
                ((JLabel) c).setForeground(theme.getTextColor());
            }
        }
        
        // Update score label
        scoreLabel.setForeground(theme.getAccentColor());
        
        // Update bottom panel
        bottomPanel.setBackground(theme.getPanelColor());
        statusLabel.setForeground(theme.getTextColor());
        
        // Update buttons in bottom panel
        updateButtonTheme(bottomPanel, theme);
        
        // Update game panel wrapper
        Container gamePanelWrapper = gamePanel.getParent();
        if (gamePanelWrapper != null) {
            gamePanelWrapper.setBackground(theme.getBackgroundColor());
        }
        
        // Force repaint
        repaint();
        
        soundManager.playClick();
    }

    /**
     * Recursively updates button colors for a theme.
     */
    private void updateButtonTheme(Container container, Theme theme) {
        for (Component c : container.getComponents()) {
            if (c instanceof JButton) {
                JButton btn = (JButton) c;
                btn.setForeground(theme.getTextColor());
                btn.setBackground(new Color(
                    Math.min(255, theme.getPanelColor().getRed() + 20),
                    Math.min(255, theme.getPanelColor().getGreen() + 20),
                    Math.min(255, theme.getPanelColor().getBlue() + 20)
                ));
            } else if (c instanceof Container) {
                ((Container) c).setBackground(theme.getPanelColor());
                updateButtonTheme((Container) c, theme);
            }
        }
    }

    /**
     * Shows the scores dialog.
     */
    private void showScores() {
        String stats = scoreManager.getStatsSummary();
        JOptionPane.showMessageDialog(this,
            stats,
            "Game Statistics (All Time)",
            JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Clears all saved scores.
     */
    private void clearScores() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to clear all saved statistics?",
            "Clear Statistics",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                scoreManager.clearAll();
                sessionWins = 0;
                sessionLosses = 0;
                sessionTies = 0;
                updateScoreLabel();
                JOptionPane.showMessageDialog(this,
                    "Statistics cleared!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                    "Could not clear statistics: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Shows the About dialog.
     */
    private void showAbout() {
        JOptionPane.showMessageDialog(this,
            "Tic Tac Toe\n" +
            "Version 2.0\n\n" +
            "A classic game of X's and O's\n" +
            "with animated graphics and sound.\n\n" +
            "Features:\n" +
            "• Three AI difficulty levels\n" +
            "• 10 custom themes with effects\n" +
            "• Sparkles, neon glow, & more!\n" +
            "• Animated X and O marks\n" +
            "• Sound effects\n" +
            "• Score tracking\n\n" +
            "Built with Java Swing",
            "About Tic Tac Toe",
            JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows the How to Play dialog.
     */
    private void showHowToPlay() {
        JOptionPane.showMessageDialog(this,
            "How to Play Tic Tac Toe\n\n" +
            "1. You are X, the computer is O\n" +
            "2. Click any empty square to place your X\n" +
            "3. Try to get three X's in a row\n" +
            "   (horizontal, vertical, or diagonal)\n" +
            "4. Block the computer from getting three O's\n\n" +
            "Tips:\n" +
            "• The center square is strategically valuable\n" +
            "• Corners are also good positions\n" +
            "• On 'Impossible' mode, the best you can do is tie!",
            "How to Play",
            JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Main entry point for the application.
     */
    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            // Dark theme for dialogs
            UIManager.put("OptionPane.background", new Color(45, 45, 50));
            UIManager.put("Panel.background", new Color(45, 45, 50));
            UIManager.put("OptionPane.messageForeground", new Color(220, 220, 220));
        } catch (Exception e) {
            // Use default
        }

        // Run on EDT
        SwingUtilities.invokeLater(() -> new TicTacToeFrame());
    }
}
