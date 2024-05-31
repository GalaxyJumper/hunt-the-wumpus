import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.util.ArrayList;

/*
 * ./gameOver.wav
 * ./disappointment.wav
 * ./wrongAnswer.wav
 * ./correctAnswer.wav
 * ./caveNoise.wav
 */
public class SoundManager {
    private Clip clip;
    private ArrayList<File> sfx;
    public SoundManager() {
        // specify the sound to play
        // (assuming the sound can be played by the audio system)
        // from a wave File
        this.sfx = new ArrayList<File>();
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
            else {
                throw new RuntimeException("Sound: file not found: " + fileName);
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Malformed URL: " + e);
        }
        catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Unsupported Audio File: " + e);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Input/Output Error: " + e);
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
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