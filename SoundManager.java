import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SoundManager {
    private ArrayList<File> soundFiles;

    public SoundManager() {
        this.soundFiles = new ArrayList<File>();
        this.soundFiles.add(new File("./gameOver.wav"));
        this.soundFiles.add(new File("./Ambiance.wav"));
        this.soundFiles.add(new File("./correctAnswer.wav"));
        this.soundFiles.add(new File("./disappointment.wav"));
        this.soundFiles.add(new File("./wrongAnswer.wav"));
    }

    public void playSound(int index) {
        try {
            if (index < 0 || index >= soundFiles.size()) {
                System.err.println("Invalid index");
                return;
            }

            File soundFile = soundFiles.get(index);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
            // Allow sound to finish playing before moving to the next one
            Thread.sleep(clip.getMicrosecondLength() / 1000);
            clip.stop(); // Stop the clip after it's done playing
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}