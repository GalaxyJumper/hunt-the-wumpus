import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SoundManager {
    private Clip activeClip;
    private ArrayList<File> soundFiles;
    private ArrayList<Clip> soundClips;

    public SoundManager() {
        soundFiles = new ArrayList<File>();
        soundClips = new ArrayList<Clip>();
        soundFiles.add(new File("./gameOver.wav"));
        soundFiles.add(new File("./disappointment.wav"));
        soundFiles.add(new File("./caveNoise.wav"));
        soundFiles.add(new File("./wrongAnswer.wav"));
        soundFiles.add(new File("./correctAnswer.wav"));
        this.soundClips = convertFilesToClips(soundFiles);
    }


    public void play(int index) {
        Clip activeClip = this.soundClips.get(index);
        if (!activeClip.isRunning()) {
            activeClip.start();
        }
    }

    public void stop() {
        if (activeClip.isRunning()) {
            activeClip.stop();
        }
    }

    
    private ArrayList<Clip> convertFilesToClips(ArrayList<File> files) {
        ArrayList<Clip> arr = new ArrayList<Clip>();
        for (File file : soundFiles) {
            try {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(file));
                soundClips.add(clip);
            } catch (Exception e) {
                // Handle any exceptions (e.g., unsupported audio format, file not found)
                e.printStackTrace();
            }
        }
        return arr;
    }
}
