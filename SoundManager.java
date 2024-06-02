import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SoundManager {
    private ArrayList<File> soundFiles;
    private Clip currentClip;

    public SoundManager() {
        this.soundFiles = new ArrayList<File>();
        this.soundFiles.add(new File("./gameOver.wav"));
        this.soundFiles.add(new File("./disappointment.wav"));
        this.soundFiles.add(new File("./wrongAnswer.wav"));
        this.soundFiles.add(new File("./correctAnswer.wav"));
        this.soundFiles.add(new File("./caveNoise.wav"));
    }

    public void playSound(int index) {
        File soundFile = soundFiles.get(index);
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
    

    public void stopCurrentSound() {
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
        }
    }
}
