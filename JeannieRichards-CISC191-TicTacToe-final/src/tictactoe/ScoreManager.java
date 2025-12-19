package tictactoe;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages game score persistence to a CSV file.
 * Handles file creation, appending results, and loading history.
 */
public class ScoreManager {
    
    private Path filePath;

    /**
     * Creates a ScoreManager with the default scores.csv file.
     */
    public ScoreManager() {
        this(Paths.get("scores.csv"));
    }

    /**
     * Creates a ScoreManager with a specific file path.
     * @param filePath the path to the scores file
     */
    public ScoreManager(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Ensures the score file exists, creating it if necessary.
     * @throws IOException if file creation fails
     */
    private void ensureFileExists() throws IOException {
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
            // Write CSV header
            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                writer.write("Player,Opponent,Result,Moves,Timestamp");
                writer.newLine();
            }
        }
    }

    /**
     * Appends a new score entry to the file.
     * @param entry the ScoreEntry to save
     * @throws IOException if writing fails
     */
    public void append(ScoreEntry entry) throws IOException {
        ensureFileExists();
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, 
                StandardOpenOption.APPEND)) {
            writer.write(entry.toCsv());
            writer.newLine();
        }
    }

    /**
     * Loads all score entries from the file.
     * @return list of all saved ScoreEntry objects
     * @throws IOException if reading fails
     */
    public List<ScoreEntry> loadAll() throws IOException {
        List<ScoreEntry> entries = new ArrayList<>();
        
        if (!Files.exists(filePath)) {
            return entries; // No scores yet
        }
        
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            boolean firstLine = true;
            
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip header
                    continue;
                }
                
                ScoreEntry entry = ScoreEntry.fromCsv(line);
                if (entry != null) {
                    entries.add(entry);
                }
            }
        }
        
        return entries;
    }

    /**
     * Gets statistics summary for display.
     * @return formatted statistics string
     */
    public String getStatsSummary() {
        try {
            List<ScoreEntry> entries = loadAll();
            
            if (entries.isEmpty()) {
                return "No games played yet.";
            }
            
            int wins = 0, losses = 0, ties = 0;
            for (ScoreEntry entry : entries) {
                switch (entry.getResult()) {
                    case "WIN": wins++; break;
                    case "LOSS": losses++; break;
                    case "TIE": ties++; break;
                }
            }
            
            int total = entries.size();
            double winRate = (total > 0) ? (wins * 100.0 / total) : 0;
            
            return String.format(
                "Games Played: %d\n" +
                "Wins: %d\n" +
                "Losses: %d\n" +
                "Ties: %d\n" +
                "Win Rate: %.1f%%",
                total, wins, losses, ties, winRate
            );
            
        } catch (IOException e) {
            return "Error loading scores: " + e.getMessage();
        }
    }

    /**
     * Clears all saved scores.
     * @throws IOException if file deletion fails
     */
    public void clearAll() throws IOException {
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }
    }
}
