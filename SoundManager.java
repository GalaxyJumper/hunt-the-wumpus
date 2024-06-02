import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SoundManager {
    private ArrayList<File> soundFiles;
    private Clip currentClip;

    public SoundManager() {
        soundFiles = new ArrayList<File>();
        soundFiles.add(new File("./gameOver.wav"));
        soundFiles.add(new File("./disappointment.wav"));
        soundFiles.add(new File("./caveNoise.wav"));
        soundFiles.add(new File("./wrongAnswer.wav"));
        soundFiles.add(new File("./correctAnswer.wav"));
    }

    

    public void play(int index) {
        if (index >= 0 && index < soundFiles.size()) {
            try {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFiles.get(index));
                currentClip = AudioSystem.getClip();
                currentClip.open(audioIn);
                currentClip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
        }
    }

    public void loop(int index) {
        if (index >= 0 && index < soundFiles.size()) {
            try {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFiles.get(index));
                currentClip = AudioSystem.getClip();
                currentClip.open(audioIn);
                currentClip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }
}