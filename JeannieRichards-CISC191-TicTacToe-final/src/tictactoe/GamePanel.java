package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Custom painted game board panel with theme support and visual effects.
 * Supports sparkles, gradients, neon glow, and other animated effects.
 */
public class GamePanel extends JPanel {
    
    private Board board;
    private Theme theme;
    private boolean gameOver = false;
    private int[] winningLine = null;
    private CellClickListener clickListener;
    
    // Animation state
    private List<CellAnimation> cellAnimations = new ArrayList<>();
    private List<Particle> particles = new ArrayList<>();
    private Timer animationTimer;
    private Random random = new Random();
    private long animationTime = 0;
    
    // Visual settings
    private static final int CELL_PADDING = 15;
    private static final int LINE_WIDTH = 8;
    
    private int hoveredRow = -1;
    private int hoveredCol = -1;

    /**
     * Creates a new game panel with default dark theme.
     */
    public GamePanel() {
        this.board = new Board();
        this.theme = Theme.darkTheme();
        
        Dimension size = new Dimension(300, 300);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setBackground(theme.getBackgroundColor());
        
        // Set up animation timer (60 FPS)
        animationTimer = new Timer(16, e -> {
            animationTime += 16;
            updateAnimations();
            repaint();
        });
        animationTimer.start();
        
        // Mouse listener for clicks and hover effects
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                hoveredRow = -1;
                hoveredCol = -1;
                repaint();
            }
        });
        
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                updateHover(e.getX(), e.getY());
            }
        });
    }

    /**
     * Interface for handling cell clicks.
     */
    public interface CellClickListener {
        void onCellClicked(int row, int col);
    }

    /**
     * Sets the click listener.
     */
    public void setCellClickListener(CellClickListener listener) {
        this.clickListener = listener;
    }

    /**
     * Sets the theme.
     */
    public void setTheme(Theme theme) {
        this.theme = theme;
        setBackground(theme.getBackgroundColor());
        particles.clear();
        repaint();
    }

    /**
     * Gets the current theme.
     */
    public Theme getTheme() {
        return theme;
    }

    /**
     * Updates the board reference.
     */
    public void setBoard(Board board) {
        this.board = board;
        cellAnimations.clear();
        winningLine = null;
        repaint();
    }

    /**
     * Sets whether the game is over.
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
        if (gameOver) {
            // Create celebration particles
            spawnCelebrationParticles();
        }
    }

    /**
     * Sets the winning line for highlighting.
     */
    public void setWinningLine(int[] cells) {
        this.winningLine = cells;
        repaint();
    }

    /**
     * Triggers an animation for a new mark.
     */
    public void animateMark(int row, int col, char mark) {
        cellAnimations.add(new CellAnimation(row, col, mark));
        
        // Add sparkle particles for sparkle theme
        if (theme.getStyle() == Theme.ThemeStyle.SPARKLE) {
            spawnSparkles(row, col, 15);
        }
    }

    /**
     * Clears all state for a new game.
     */
    public void reset() {
        cellAnimations.clear();
        particles.clear();
        winningLine = null;
        gameOver = false;
        repaint();
    }

    private void handleClick(int x, int y) {
        if (gameOver || clickListener == null) return;
        
        int cellSize = Math.min(getWidth(), getHeight()) / 3;
        int col = x / cellSize;
        int row = y / cellSize;
        
        if (row >= 0 && row < 3 && col >= 0 && col < 3) {
            clickListener.onCellClicked(row, col);
        }
    }

    private void updateHover(int x, int y) {
        int cellSize = Math.min(getWidth(), getHeight()) / 3;
        int newCol = x / cellSize;
        int newRow = y / cellSize;
        
        if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
            if (newRow != hoveredRow || newCol != hoveredCol) {
                hoveredRow = newRow;
                hoveredCol = newCol;
                repaint();
            }
        }
    }

    private void updateAnimations() {
        // Update cell animations
        cellAnimations.removeIf(anim -> anim.isComplete());
        for (CellAnimation anim : cellAnimations) {
            anim.update();
        }
        
        // Update particles
        particles.removeIf(p -> !p.isAlive());
        for (Particle p : particles) {
            p.update();
        }
        
        // Continuously spawn particles for certain themes
        if (theme.getStyle() == Theme.ThemeStyle.SPARKLE && random.nextInt(10) == 0) {
            spawnRandomSparkle();
        }
        if (theme.getStyle() == Theme.ThemeStyle.FIRE && random.nextInt(5) == 0) {
            spawnFireParticle();
        }
    }

    private void spawnSparkles(int row, int col, int count) {
        int cellSize = getWidth() / 3;
        int centerX = col * cellSize + cellSize / 2;
        int centerY = row * cellSize + cellSize / 2;
        
        for (int i = 0; i < count; i++) {
            particles.add(new Particle(
                centerX + random.nextInt(60) - 30,
                centerY + random.nextInt(60) - 30,
                random.nextFloat() * 4 - 2,
                random.nextFloat() * -3 - 1,
                getSparkleColor(),
                random.nextInt(30) + 20
            ));
        }
    }

    private void spawnRandomSparkle() {
        particles.add(new Particle(
            random.nextInt(getWidth()),
            random.nextInt(getHeight()),
            0,
            random.nextFloat() * -1,
            getSparkleColor(),
            random.nextInt(40) + 20
        ));
    }

    private void spawnFireParticle() {
        particles.add(new Particle(
            random.nextInt(getWidth()),
            getHeight(),
            random.nextFloat() * 2 - 1,
            random.nextFloat() * -3 - 2,
            getFireColor(),
            random.nextInt(30) + 20
        ));
    }

    private void spawnCelebrationParticles() {
        for (int i = 0; i < 50; i++) {
            particles.add(new Particle(
                getWidth() / 2,
                getHeight() / 2,
                random.nextFloat() * 8 - 4,
                random.nextFloat() * 8 - 4,
                getRandomBrightColor(),
                random.nextInt(60) + 40
            ));
        }
    }

    private Color getSparkleColor() {
        float hue = random.nextFloat();
        return Color.getHSBColor(hue, 0.3f, 1.0f);
    }

    private Color getFireColor() {
        int r = 255;
        int g = random.nextInt(150) + 50;
        int b = random.nextInt(50);
        return new Color(r, g, b, 200);
    }

    private Color getRandomBrightColor() {
        return Color.getHSBColor(random.nextFloat(), 0.8f, 1.0f);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        // Enable anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        
        int width = getWidth();
        int height = getHeight();
        int cellSize = Math.min(width, height) / 3;
        
        // Draw background effects based on theme style
        drawBackgroundEffects(g2d, width, height);
        
        // Draw hover effect
        if (!gameOver && hoveredRow >= 0 && hoveredCol >= 0 && 
            board.isEmpty(hoveredRow, hoveredCol)) {
            g2d.setColor(theme.getHoverColor());
            g2d.fillRect(hoveredCol * cellSize, hoveredRow * cellSize, cellSize, cellSize);
        }
        
        // Draw winning glow
        if (winningLine != null) {
            g2d.setColor(theme.getWinGlowColor());
            for (int i = 0; i < 6; i += 2) {
                int r = winningLine[i];
                int c = winningLine[i + 1];
                g2d.fillRect(c * cellSize, r * cellSize, cellSize, cellSize);
            }
        }
        
        // Draw grid lines
        drawGrid(g2d, width, height, cellSize);
        
        // Draw marks
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                char cell = board.getCell(row, col);
                if (cell != Board.EMPTY) {
                    float progress = 1.0f;
                    for (CellAnimation anim : cellAnimations) {
                        if (anim.row == row && anim.col == col) {
                            progress = anim.getProgress();
                            break;
                        }
                    }
                    drawMark(g2d, row, col, cell, cellSize, progress);
                }
            }
        }
        
        // Draw winning line
        if (winningLine != null) {
            drawWinningLine(g2d, cellSize);
        }
        
        // Draw particles
        drawParticles(g2d);
        
        g2d.dispose();
    }

    private void drawBackgroundEffects(Graphics2D g2d, int width, int height) {
        switch (theme.getStyle()) {
            case GRADIENT:
                drawRainbowBackground(g2d, width, height);
                break;
            case OCEAN:
                drawOceanWaves(g2d, width, height);
                break;
            case NEON:
                drawNeonGlow(g2d, width, height);
                break;
            default:
                // Solid background already set
                break;
        }
    }

    private void drawRainbowBackground(Graphics2D g2d, int width, int height) {
        float hueOffset = (animationTime % 5000) / 5000f;
        for (int y = 0; y < height; y += 5) {
            float hue = (hueOffset + (float) y / height) % 1.0f;
            g2d.setColor(Color.getHSBColor(hue, 0.3f, 0.2f));
            g2d.fillRect(0, y, width, 5);
        }
    }

    private void drawOceanWaves(Graphics2D g2d, int width, int height) {
        g2d.setColor(theme.getBackgroundColor());
        g2d.fillRect(0, 0, width, height);
        
        // Draw wave lines
        g2d.setColor(new Color(50, 100, 150, 50));
        for (int i = 0; i < 5; i++) {
            int baseY = height / 5 * i;
            Path2D wave = new Path2D.Float();
            wave.moveTo(0, baseY);
            for (int x = 0; x <= width; x += 10) {
                double offset = Math.sin((x + animationTime / 10.0 + i * 50) * 0.05) * 10;
                wave.lineTo(x, baseY + offset);
            }
            g2d.setStroke(new BasicStroke(3));
            g2d.draw(wave);
        }
    }

    private void drawNeonGlow(Graphics2D g2d, int width, int height) {
        // Pulsing glow effect
        float pulse = (float) (Math.sin(animationTime / 500.0) * 0.3 + 0.7);
        int glowSize = 20;
        
        // Corner glows
        Color glowColor = new Color(
            theme.getAccentColor().getRed(),
            theme.getAccentColor().getGreen(),
            theme.getAccentColor().getBlue(),
            (int) (30 * pulse)
        );
        
        g2d.setColor(glowColor);
        g2d.fillOval(-glowSize, -glowSize, glowSize * 3, glowSize * 3);
        g2d.fillOval(width - glowSize * 2, -glowSize, glowSize * 3, glowSize * 3);
        g2d.fillOval(-glowSize, height - glowSize * 2, glowSize * 3, glowSize * 3);
        g2d.fillOval(width - glowSize * 2, height - glowSize * 2, glowSize * 3, glowSize * 3);
    }

    private void drawGrid(Graphics2D g2d, int width, int height, int cellSize) {
        Stroke gridStroke = new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        
        if (theme.getStyle() == Theme.ThemeStyle.NEON) {
            // Draw glow behind grid for neon effect
            g2d.setColor(new Color(
                theme.getGridColor().getRed(),
                theme.getGridColor().getGreen(),
                theme.getGridColor().getBlue(),
                100
            ));
            g2d.setStroke(new BasicStroke(12, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            drawGridLines(g2d, width, height, cellSize);
        }
        
        g2d.setColor(theme.getGridColor());
        g2d.setStroke(gridStroke);
        drawGridLines(g2d, width, height, cellSize);
    }

    private void drawGridLines(Graphics2D g2d, int width, int height, int cellSize) {
        // Vertical lines
        g2d.drawLine(cellSize, CELL_PADDING, cellSize, height - CELL_PADDING);
        g2d.drawLine(cellSize * 2, CELL_PADDING, cellSize * 2, height - CELL_PADDING);
        
        // Horizontal lines
        g2d.drawLine(CELL_PADDING, cellSize, width - CELL_PADDING, cellSize);
        g2d.drawLine(CELL_PADDING, cellSize * 2, width - CELL_PADDING, cellSize * 2);
    }

    private void drawMark(Graphics2D g2d, int row, int col, char mark, int cellSize, float progress) {
        int x = col * cellSize;
        int y = row * cellSize;
        int padding = CELL_PADDING + 20;
        
        Color markColor;
        if (theme.getStyle() == Theme.ThemeStyle.GRADIENT) {
            // Rainbow cycling color
            float hue = ((animationTime / 50f) + row * 0.1f + col * 0.1f) % 1.0f;
            markColor = Color.getHSBColor(hue, 0.8f, 1.0f);
        } else {
            markColor = (mark == Board.X) ? theme.getXColor() : theme.getOColor();
        }
        
        // Draw glow for neon theme
        if (theme.getStyle() == Theme.ThemeStyle.NEON) {
            g2d.setColor(new Color(markColor.getRed(), markColor.getGreen(), markColor.getBlue(), 100));
            g2d.setStroke(new BasicStroke(LINE_WIDTH + 8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            drawMarkShape(g2d, mark, x, y, cellSize, padding, progress);
        }
        
        g2d.setColor(markColor);
        g2d.setStroke(new BasicStroke(LINE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        drawMarkShape(g2d, mark, x, y, cellSize, padding, progress);
    }

    private void drawMarkShape(Graphics2D g2d, char mark, int x, int y, int cellSize, int padding, float progress) {
        if (mark == Board.X) {
            int drawLength = (int) ((cellSize - padding * 2) * progress);
            int centerX = x + cellSize / 2;
            int centerY = y + cellSize / 2;
            int halfLen = drawLength / 2;
            
            // First diagonal
            g2d.drawLine(centerX - halfLen, centerY - halfLen,
                        centerX + halfLen, centerY + halfLen);
            // Second diagonal
            if (progress > 0.5f) {
                float secondProgress = (progress - 0.5f) * 2;
                int halfLen2 = (int) (halfLen * secondProgress);
                g2d.drawLine(centerX + halfLen2, centerY - halfLen2,
                            centerX - halfLen2, centerY + halfLen2);
            }
        } else if (mark == Board.O) {
            int diameter = cellSize - padding * 2;
            int arcExtent = (int) (360 * progress);
            g2d.drawArc(x + padding, y + padding, diameter, diameter, 90, -arcExtent);
        }
    }

    private void drawWinningLine(Graphics2D g2d, int cellSize) {
        if (winningLine == null) return;
        
        Color lineColor = theme.getWinGlowColor();
        if (lineColor.getAlpha() < 255) {
            lineColor = new Color(lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue(), 255);
        }
        
        // Pulsing effect
        float pulse = (float) (Math.sin(animationTime / 200.0) * 0.3 + 0.7);
        int strokeWidth = (int) (6 + 4 * pulse);
        
        g2d.setColor(lineColor);
        g2d.setStroke(new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        int startX = winningLine[1] * cellSize + cellSize / 2;
        int startY = winningLine[0] * cellSize + cellSize / 2;
        int endX = winningLine[5] * cellSize + cellSize / 2;
        int endY = winningLine[4] * cellSize + cellSize / 2;
        
        g2d.drawLine(startX, startY, endX, endY);
    }

    private void drawParticles(Graphics2D g2d) {
        for (Particle p : particles) {
            int alpha = (int) (255 * p.getLifeRatio());
            Color c = new Color(p.color.getRed(), p.color.getGreen(), p.color.getBlue(), alpha);
            g2d.setColor(c);
            int size = (int) (p.getLifeRatio() * 6) + 2;
            g2d.fillOval((int) p.x - size / 2, (int) p.y - size / 2, size, size);
        }
    }

    // ============ INNER CLASSES ============

    /**
     * Animation for cell marks appearing.
     */
    private class CellAnimation {
        int row, col;
        char mark;
        float progress = 0f;
        
        CellAnimation(int row, int col, char mark) {
            this.row = row;
            this.col = col;
            this.mark = mark;
        }
        
        void update() {
            progress += 0.08f;
            if (progress > 1f) progress = 1f;
        }
        
        float getProgress() {
            return progress;
        }
        
        boolean isComplete() {
            return progress >= 1f;
        }
    }

    /**
     * Particle for sparkle and other effects.
     */
    private class Particle {
        float x, y;
        float vx, vy;
        Color color;
        int life;
        int maxLife;
        
        Particle(float x, float y, float vx, float vy, Color color, int life) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            this.color = color;
            this.life = life;
            this.maxLife = life;
        }
        
        void update() {
            x += vx;
            y += vy;
            vy += 0.1f; // Gravity
            life--;
        }
        
        boolean isAlive() {
            return life > 0;
        }
        
        float getLifeRatio() {
            return (float) life / maxLife;
        }
    }
}
