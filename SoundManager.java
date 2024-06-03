import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.util.ArrayList;

public class SoundManager {
    private Clip clip;
    public SoundManager(String fileName) {
        // specify the sound to play
        // (assuming the sound can be played by the audio system)
        // from a wave File
        try {
            sfx.add("./gameOver.wav");
            sfx.add("./disappointment.wav");
            sfx.add("./wrongAnswer.wav");
            sfx.add("./correctAnswer.wav");
            sfx.add("./caveNoise.wav");
            File file = new File(fileName);
            if (file.exists()) {
                AudioInputStream sound = AudioSystem.getAudioInputStream(file);
             // load the sound into memory (a Clip)
                clip = AudioSystem.getClip();
                clip.open(sound);
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

    // play, stop, loop the sound clip
    }
    public void play(int index){
        try {
            File f = sfx.get(index);
        
            clip.setFramePosition(0);  // Must always rewind!
            clip.start();
        } catch (Exception FileNotFoundException) {
            // TODO: handle exception
        }
        
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
            clip.stop();
        }
    }