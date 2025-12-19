package tictactoe;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Manages game sound effects.
 * Generates simple tones programmatically (no external files needed).
 */
public class SoundManager {
    
    private static final float SAMPLE_RATE = 44100f;
    private boolean soundEnabled = true;

    /**
     * Plays a click sound when placing a mark.
     */
    public void playClick() {
        if (!soundEnabled) return;
        playTone(800, 50, 0.3);
    }

    /**
     * Plays a sound when X is placed.
     */
    public void playX() {
        if (!soundEnabled) return;
        playTone(600, 80, 0.4);
    }

    /**
     * Plays a sound when O is placed.
     */
    public void playO() {
        if (!soundEnabled) return;
        playTone(400, 80, 0.4);
    }

    /**
     * Plays a winning fanfare.
     */
    public void playWin() {
        if (!soundEnabled) return;
        new Thread(() -> {
            playTone(523, 150, 0.5); // C
            sleep(100);
            playTone(659, 150, 0.5); // E
            sleep(100);
            playTone(784, 150, 0.5); // G
            sleep(100);
            playTone(1047, 300, 0.6); // High C
        }).start();
    }

    /**
     * Plays a losing sound.
     */
    public void playLose() {
        if (!soundEnabled) return;
        new Thread(() -> {
            playTone(400, 200, 0.4);
            sleep(50);
            playTone(350, 200, 0.4);
            sleep(50);
            playTone(300, 400, 0.5);
        }).start();
    }

    /**
     * Plays a draw/tie sound.
     */
    public void playDraw() {
        if (!soundEnabled) return;
        new Thread(() -> {
            playTone(440, 150, 0.4);
            sleep(100);
            playTone(440, 150, 0.4);
        }).start();
    }

    /**
     * Plays a new game sound.
     */
    public void playNewGame() {
        if (!soundEnabled) return;
        new Thread(() -> {
            playTone(523, 100, 0.3);
            sleep(50);
            playTone(659, 100, 0.3);
        }).start();
    }

    /**
     * Plays an error/invalid move sound.
     */
    public void playError() {
        if (!soundEnabled) return;
        playTone(200, 150, 0.5);
    }

    /**
     * Generates and plays a simple sine wave tone.
     * @param frequency the frequency in Hz
     * @param durationMs the duration in milliseconds
     * @param volume the volume (0.0 to 1.0)
     */
    private void playTone(int frequency, int durationMs, double volume) {
        try {
            byte[] buffer = generateTone(frequency, durationMs, volume);
            
            AudioFormat format = new AudioFormat(SAMPLE_RATE, 8, 1, true, false);
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            
            if (!AudioSystem.isLineSupported(info)) {
                return;
            }

            Clip clip = (Clip) AudioSystem.getLine(info);
            clip.open(format, buffer, 0, buffer.length);
            clip.start();
            
            // Clean up after playing
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });
            
        } catch (LineUnavailableException e) {
            // Silently fail if audio not available
        }
    }

    /**
     * Generates a sine wave tone with fade in/out to prevent clicking.
     */
    private byte[] generateTone(int frequency, int durationMs, double volume) {
        int samples = (int) (SAMPLE_RATE * durationMs / 1000);
        byte[] buffer = new byte[samples];
        
        int fadeLength = Math.min(samples / 10, 500); // Fade in/out length
        
        for (int i = 0; i < samples; i++) {
            double angle = 2.0 * Math.PI * frequency * i / SAMPLE_RATE;
            double value = Math.sin(angle) * volume;
            
            // Apply fade in
            if (i < fadeLength) {
                value *= (double) i / fadeLength;
            }
            // Apply fade out
            if (i > samples - fadeLength) {
                value *= (double) (samples - i) / fadeLength;
            }
            
            buffer[i] = (byte) (value * 127);
        }
        
        return buffer;
    }

    /**
     * Enables or disables sound effects.
     * @param enabled true to enable sounds
     */
    public void setSoundEnabled(boolean enabled) {
        this.soundEnabled = enabled;
    }

    /**
     * Returns whether sound is currently enabled.
     * @return true if sounds are enabled
     */
    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    /**
     * Helper method for delays between notes.
     */
    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
