import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SoundManager {

    private ArrayList<File> soundFiles;
    private Sound sound;

    public SoundManager() {
         // Need to convert the wav files into somthing more generic like a .pcm file.
        //TODO: Find a file format that plays nicely with the java. As of current, errors appear.
        this.soundFiles = new ArrayList<File>();
        this.sound = new Sound();
        this.soundFiles.add(new File("sound/gameOver.wav"));
        this.soundFiles.add(new File("sound/Ambiance.wav"));
        this.soundFiles.add(new File("sound/correctAnswer.wav"));
        this.soundFiles.add(new File("sound/disappointment.wav"));
        this.soundFiles.add(new File("sound/wrongAnswer.wav"));
    }

    
    public void playSound(int index){
        sound.playSound(this.soundFiles.get(index));
        
    }

}