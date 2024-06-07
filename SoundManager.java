// Sound Manager Object
// Toki Young
// Nick Lennon
// 06/07/2024
// Mr. Reiber AP CSA Periods 5 & 6

import javax.sound.sampled.*;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

public class SoundManager {

    private ArrayList<String> filePaths = new ArrayList<String>();
    private Clip clip;

    // Adds all of our filepaths in the constructor of SoundManager
    public SoundManager() {

        this.filePaths.add("./sound/gameOver.wav");
        this.filePaths.add("./sound/ambiance.wav");
        this.filePaths.add("./sound/correctAnswer.wav");
        this.filePaths.add("./sound/disappointment.wav");
        this.filePaths.add("./sound/wrongAnswer.wav");

    }

    // Plays a sound gathered by taking an index for a specific filepath
    // Turning the sound into a clip and playing it
    public void playSound(int index) {
        if (index < 0 || index >= filePaths.size()) {
            System.out.println("Invalid index.");
            return;
        }

        // quits trying to play if the index is invalid
        stop();

        try {
            File soundFile = new File(filePaths.get(index));
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    // Stops a CURRENTLY RUNNING audio clip
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    // Restarts a clip
    public void rewind() {
        if (clip != null) {
            clip.setMicrosecondPosition(0);
        }
    }
}
