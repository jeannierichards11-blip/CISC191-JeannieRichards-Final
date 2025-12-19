package tictactoe;

import java.awt.Color;

/**
 * Represents a visual theme for the game.
 * Contains colors and style settings.
 */
public class Theme {
    
    private String name;
    private Color backgroundColor;
    private Color gridColor;
    private Color xColor;
    private Color oColor;
    private Color hoverColor;
    private Color winGlowColor;
    private Color textColor;
    private Color panelColor;
    private Color accentColor;
    private ThemeStyle style;

    /**
     * Visual style effects for the theme.
     */
    public enum ThemeStyle {
        CLASSIC,        // Simple solid colors
        NEON,           // Glowing neon effect
        GRADIENT,       // Rainbow gradient
        SPARKLE,        // Sparkle particles
        RETRO,          // Pixelated retro look
        OCEAN,          // Wave animation
        FIRE            // Flame effect
    }

    /**
     * Creates a new theme with the specified settings.
     */
    public Theme(String name, Color backgroundColor, Color gridColor, 
                 Color xColor, Color oColor, Color hoverColor, 
                 Color winGlowColor, Color textColor, Color panelColor,
                 Color accentColor, ThemeStyle style) {
        this.name = name;
        this.backgroundColor = backgroundColor;
        this.gridColor = gridColor;
        this.xColor = xColor;
        this.oColor = oColor;
        this.hoverColor = hoverColor;
        this.winGlowColor = winGlowColor;
        this.textColor = textColor;
        this.panelColor = panelColor;
        this.accentColor = accentColor;
        this.style = style;
    }

    // Getters
    public String getName() { return name; }
    public Color getBackgroundColor() { return backgroundColor; }
    public Color getGridColor() { return gridColor; }
    public Color getXColor() { return xColor; }
    public Color getOColor() { return oColor; }
    public Color getHoverColor() { return hoverColor; }
    public Color getWinGlowColor() { return winGlowColor; }
    public Color getTextColor() { return textColor; }
    public Color getPanelColor() { return panelColor; }
    public Color getAccentColor() { return accentColor; }
    public ThemeStyle getStyle() { return style; }

    // ============ PREDEFINED THEMES ============

    /**
     * Dark theme - the default modern dark look.
     */
    public static Theme darkTheme() {
        return new Theme(
            "Dark",
            new Color(45, 52, 54),      // background
            new Color(99, 110, 114),    // grid
            new Color(116, 185, 255),   // X - blue
            new Color(255, 118, 117),   // O - red
            new Color(75, 85, 90),      // hover
            new Color(0, 255, 136, 100),// win glow
            new Color(220, 220, 220),   // text
            new Color(45, 45, 50),      // panel
            new Color(116, 185, 255),   // accent
            ThemeStyle.CLASSIC
        );
    }

    /**
     * Light theme - clean and bright.
     */
    public static Theme lightTheme() {
        return new Theme(
            "Light",
            new Color(245, 245, 245),   // background
            new Color(180, 180, 180),   // grid
            new Color(41, 128, 185),    // X - blue
            new Color(231, 76, 60),     // O - red
            new Color(220, 220, 220),   // hover
            new Color(46, 204, 113, 100),// win glow
            new Color(50, 50, 50),      // text
            new Color(255, 255, 255),   // panel
            new Color(41, 128, 185),    // accent
            ThemeStyle.CLASSIC
        );
    }

    /**
     * Neon theme - glowing cyberpunk style.
     */
    public static Theme neonTheme() {
        return new Theme(
            "Neon",
            new Color(10, 10, 20),      // background - very dark
            new Color(50, 50, 80),      // grid
            new Color(0, 255, 255),     // X - cyan
            new Color(255, 0, 255),     // O - magenta
            new Color(30, 30, 50),      // hover
            new Color(0, 255, 0, 150),  // win glow - green
            new Color(255, 255, 255),   // text
            new Color(15, 15, 30),      // panel
            new Color(0, 255, 255),     // accent
            ThemeStyle.NEON
        );
    }

    /**
     * Rainbow theme - colorful gradient effects.
     */
    public static Theme rainbowTheme() {
        return new Theme(
            "Rainbow",
            new Color(30, 30, 40),      // background
            new Color(100, 100, 120),   // grid
            new Color(255, 100, 100),   // X - will cycle
            new Color(100, 100, 255),   // O - will cycle
            new Color(50, 50, 60),      // hover
            new Color(255, 255, 255, 100),// win glow
            new Color(255, 255, 255),   // text
            new Color(40, 40, 50),      // panel
            new Color(255, 200, 100),   // accent
            ThemeStyle.GRADIENT
        );
    }

    /**
     * Sparkle theme - with particle effects.
     */
    public static Theme sparkleTheme() {
        return new Theme(
            "Sparkle ✨",
            new Color(25, 25, 50),      // background - deep purple
            new Color(100, 80, 150),    // grid
            new Color(255, 215, 0),     // X - gold
            new Color(192, 192, 255),   // O - silver/lavender
            new Color(40, 40, 70),      // hover
            new Color(255, 255, 200, 150),// win glow - golden
            new Color(255, 255, 255),   // text
            new Color(35, 30, 60),      // panel
            new Color(255, 215, 0),     // accent - gold
            ThemeStyle.SPARKLE
        );
    }

    /**
     * Retro theme - old school arcade style.
     */
    public static Theme retroTheme() {
        return new Theme(
            "Retro",
            new Color(0, 0, 0),         // background - black
            new Color(0, 150, 0),       // grid - green terminal
            new Color(0, 255, 0),       // X - bright green
            new Color(255, 165, 0),     // O - orange
            new Color(0, 50, 0),        // hover
            new Color(255, 255, 0, 100),// win glow - yellow
            new Color(0, 255, 0),       // text - terminal green
            new Color(0, 20, 0),        // panel
            new Color(0, 255, 0),       // accent
            ThemeStyle.RETRO
        );
    }

    /**
     * Ocean theme - calming blue waves.
     */
    public static Theme oceanTheme() {
        return new Theme(
            "Ocean 🌊",
            new Color(10, 30, 60),      // background - deep blue
            new Color(50, 100, 150),    // grid
            new Color(0, 200, 255),     // X - light blue
            new Color(255, 200, 150),   // O - coral
            new Color(20, 50, 80),      // hover
            new Color(100, 255, 255, 100),// win glow
            new Color(200, 230, 255),   // text
            new Color(15, 40, 70),      // panel
            new Color(0, 200, 255),     // accent
            ThemeStyle.OCEAN
        );
    }

    /**
     * Fire theme - hot flames.
     */
    public static Theme fireTheme() {
        return new Theme(
            "Fire 🔥",
            new Color(30, 10, 10),      // background - dark red
            new Color(100, 50, 30),     // grid
            new Color(255, 200, 0),     // X - yellow/gold
            new Color(255, 50, 0),      // O - red/orange
            new Color(50, 20, 20),      // hover
            new Color(255, 255, 0, 150),// win glow - yellow
            new Color(255, 220, 180),   // text
            new Color(40, 15, 15),      // panel
            new Color(255, 150, 0),     // accent
            ThemeStyle.FIRE
        );
    }

    /**
     * Pastel theme - soft and cute.
     */
    public static Theme pastelTheme() {
        return new Theme(
            "Pastel",
            new Color(255, 240, 245),   // background - lavender blush
            new Color(200, 180, 200),   // grid
            new Color(150, 200, 255),   // X - baby blue
            new Color(255, 180, 200),   // O - pink
            new Color(240, 220, 240),   // hover
            new Color(180, 255, 180, 100),// win glow - mint
            new Color(100, 80, 120),    // text
            new Color(255, 245, 250),   // panel
            new Color(200, 150, 200),   // accent
            ThemeStyle.CLASSIC
        );
    }

    /**
     * Matrix theme - digital rain.
     */
    public static Theme matrixTheme() {
        return new Theme(
            "Matrix",
            new Color(0, 10, 0),        // background
            new Color(0, 60, 0),        // grid
            new Color(0, 255, 65),      // X - matrix green
            new Color(150, 255, 150),   // O - lighter green
            new Color(0, 30, 0),        // hover
            new Color(0, 255, 0, 150),  // win glow
            new Color(0, 255, 65),      // text
            new Color(0, 15, 0),        // panel
            new Color(0, 255, 65),      // accent
            ThemeStyle.SPARKLE         // Will look like digital rain
        );
    }

    /**
     * Returns all available themes.
     */
    public static Theme[] getAllThemes() {
        return new Theme[] {
            darkTheme(),
            lightTheme(),
            neonTheme(),
            rainbowTheme(),
            sparkleTheme(),
            retroTheme(),
            oceanTheme(),
            fireTheme(),
            pastelTheme(),
            matrixTheme()
        };
    }
}
