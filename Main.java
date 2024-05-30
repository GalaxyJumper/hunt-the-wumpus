import java.awt.FontFormatException;
import java.awt.HeadlessException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args)
            throws HeadlessException, FileNotFoundException, FontFormatException, IOException {
                System.setProperty("sun.java2d.uiScale", "0.7");
        GameControl freak = new GameControl();
        
        //making sure highscore works! 
        //String[] test = new String[] {"bobby", "3", "2", "3", "7", "cave"};
        //Player player = new Player(test);
        //HighScore highscore = new HighScore(player);
        //highscore.endOfGame();

        // This should always run at the end of main!!!!
        Cave.println("Your code works ~*,`");
    }
}

/*
 * This is a general idea for the whole mechanism of this game:
 * The gui has a bunch of buttons and input thingys
 * When those are pressed, it sends a signal to GameControl to do whatever was
 * defined by the button
 * GameControl does the method that the Gui's button is calling
 * Repeat.
 */