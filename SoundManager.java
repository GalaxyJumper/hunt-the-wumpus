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
    private Clip clip;

    /*
     * I assume the general purpose for this class is to play sounds
     * when something happens. Most likely going to be used by GameControl.
     * I guess this class will just have a bunch of sounds in a list
     * and have a method that'll iterate over the list
     * to find the correct sfx for whatever the GControl needed.
     * Memoirize the indecies of the sfx.
     */
    public SoundManager() throws FileNotFoundException{
        this.sounds = new ArrayList<File>();
        

        this.sounds.add(new File("./gameOver.wav"));
        this.sounds.add(new File("./dissapointment.wav"));
        this.sounds.add(new File("./correctAnswer"));
        this.sounds.add(new File("./caveNoise.wav"));
        this.sounds.add(new File("./wrongAnswer.wav"));

        try {
            
        } catch (Exception e) {
            // TODO: handle exception
        }
        
    }


    
    public static void playSound(File f){
        try {
            File file = f;
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open();
            clip.start();
        } catch (Exception FileNotFoundException) {
            System.out.println("Falaed");
        }

        
    }
    public static void stopSound() {
            clip.flush();
            clip.close();
    }

    public File getSfx(int index){
        return this.sounds.get(index);
    }

}

