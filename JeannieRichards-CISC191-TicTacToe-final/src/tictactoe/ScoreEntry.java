package tictactoe;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Data object representing a single game result.
 * Can be serialized to/from CSV format for persistence.
 */
public class ScoreEntry {
    
    private String playerName;
    private String opponentName;
    private String result; // "WIN", "LOSS", or "TIE"
    private int moveCount;
    private LocalDateTime timestamp;

    private static final DateTimeFormatter DATE_FORMAT = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Creates a new ScoreEntry with the current timestamp.
     * @param playerName the human player's name
     * @param opponentName the opponent's name (e.g., "Computer (Easy)")
     * @param result the game result ("WIN", "LOSS", or "TIE")
     * @param moveCount the total number of moves in the game
     */
    public ScoreEntry(String playerName, String opponentName, String result, int moveCount) {
        this.playerName = playerName;
        this.opponentName = opponentName;
        this.result = result;
        this.moveCount = moveCount;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Creates a ScoreEntry from CSV data (for loading saved scores).
     * @param playerName the player name
     * @param opponentName the opponent name
     * @param result the result string
     * @param moveCount the move count
     * @param timestamp the saved timestamp
     */
    public ScoreEntry(String playerName, String opponentName, String result, 
                      int moveCount, LocalDateTime timestamp) {
        this.playerName = playerName;
        this.opponentName = opponentName;
        this.result = result;
        this.moveCount = moveCount;
        this.timestamp = timestamp;
    }

    // Getters
    public String getPlayerName() { return playerName; }
    public String getOpponentName() { return opponentName; }
    public String getResult() { return result; }
    public int getMoveCount() { return moveCount; }
    public LocalDateTime getTimestamp() { return timestamp; }

    /**
     * Serializes this entry to a CSV line.
     * @return CSV-formatted string
     */
    public String toCsv() {
        return String.join(",",
            escapeCsv(playerName),
            escapeCsv(opponentName),
            result,
            String.valueOf(moveCount),
            timestamp.format(DATE_FORMAT)
        );
    }

    /**
     * Creates a ScoreEntry from a CSV line.
     * @param csvLine the CSV line to parse
     * @return a new ScoreEntry, or null if parsing fails
     */
    public static ScoreEntry fromCsv(String csvLine) {
        try {
            String[] parts = csvLine.split(",");
            if (parts.length < 5) return null;
            
            String playerName = unescapeCsv(parts[0]);
            String opponentName = unescapeCsv(parts[1]);
            String result = parts[2];
            int moveCount = Integer.parseInt(parts[3]);
            LocalDateTime timestamp = LocalDateTime.parse(parts[4], DATE_FORMAT);
            
            return new ScoreEntry(playerName, opponentName, result, moveCount, timestamp);
        } catch (Exception e) {
            return null; // Parsing failed
        }
    }

    /**
     * Escapes special characters for CSV format.
     */
    private static String escapeCsv(String value) {
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    /**
     * Unescapes CSV format back to original string.
     */
    private static String unescapeCsv(String value) {
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1).replace("\"\"", "\"");
        }
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s vs %s: %s (%d moves) - %s",
            playerName, opponentName, result, moveCount,
            timestamp.format(DATE_FORMAT));
    }
}
