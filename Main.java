import java.awt.FontFormatException;
import java.awt.HeadlessException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)
            throws HeadlessException, FileNotFoundException, FontFormatException, IOException {
                System.setProperty("sun.java2d.uiScale", "0.7");
        GameControl freak = new GameControl();
        
        Trivia trivia = new Trivia();
        // Here's a little thing for testing Triva

        // bonjour
        Cave.println("Your code works ~*,`");


        //making sure highscore works! (should add a null player to the highscore csv)
        Player player = new Player();
        HighScore highscore = new HighScore(player);
        highscore.endOfGame();


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