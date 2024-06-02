import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SoundManager {
    private ArrayList<File> soundFiles;
    private Clip currentClip;

    public SoundPlayer() {
        this.soundFiles = new ArrayList<File>();
        this.soundFiles.add("./gameOver.wav")
    }

    public void playSounds() {
        for (File soundFile : soundFiles) {
            try {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                currentClip = clip; // Save the current clip for stopping
                clip.start();
                // Allow sound to finish playing before moving to the next one
                Thread.sleep(clip.getMicrosecondLength() / 1000);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopCurrentSound() {
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
        }
    }

    public static void main(String[] args) {
        ArrayList<File> soundFiles = new ArrayList<>();
        soundFiles.add(new File("path/to/sound1.wav"));
        soundFiles.add(new File("path/to/sound2.wav"));
        // Add more sound files as needed

        SoundPlayer soundPlayer = new SoundPlayer(soundFiles);
        soundPlayer.playSounds();

        // If you want to stop the currently playing sound
        // soundPlayer.stopCurrentSound();
    }
}
