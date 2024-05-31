//Toki
//Nick

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.*;

//All audio clips need to be .wav!!!!

public class SoundManager {
    private File f;
    private ArrayList<File> sounds;
    /*
     * I assume the general purpose for this class is to play sounds
     * when something happens. Most likely going to be used by GameControl.
     * I guess this class will just have a bunch of sounds in a list
     * and have a method that'll iterate over the list
     * to find the correct sfx for whatever the GControl needed.
     */
    public SoundManager() throws FileNotFoundException{
        this.sounds = new ArrayList<File>();

        this.sounds.add(new File("C:\Users\1113179\Hunt-the-wumpus\hunt-the-wumpus\gameOver.wav"));
        this.sounds.add(new File("C:\Users\1113179\Hunt-the-wumpus\hunt-the-wumpus\dissapointment.wav"));
        this.sounds.
        
    }
    
    public static void playSound(){
        clip.start();
    }
    public static void stopSound() {
            clip.flush();
            clip.close();
    }
}

