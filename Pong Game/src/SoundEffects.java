import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundEffects {
    private Clip clip;
    // Method to play sound effect
    public void playSound(String soundFile) {
        try {
            // Load the audio file
            File file = new File(soundFile);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);

            // Get a sound clip resource and open the audio stream
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Play the sound
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Method to load and play sound
    public void playSoundLoop(String soundFilePath) {
        try {
            // Load the sound file (ensure it's a .wav file)
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFilePath));

            // Get a sound clip resource
            clip = AudioSystem.getClip();

            // Open the clip and load samples from the audio input stream
            clip.open(audioInputStream);

            // Loop the clip continuously
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            // Start the clip (optional; some systems may require calling this explicitly)
            clip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Method to stop the sound (if needed)
    public void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}