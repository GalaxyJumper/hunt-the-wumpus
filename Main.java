import java.awt.FontFormatException;
import java.awt.HeadlessException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main (String[] args) throws HeadlessException, FileNotFoundException, FontFormatException, IOException{

        GameControl freak = new GameControl();

        Trivia trivia = new Trivia();
         //Here's a little thing for testing Triva
        System.out.println("-" + trivia.Triviahint());
        
        //bonjour 
        Cave.println("Your code works ~*,`");
       
    }
}


/*
 * This is a general idea for the whole mechanism of this game:
 * The gui has a bunch of buttons and input thingys
 * When those are pressed, it sends a signal to GameControl to do whatever was defined by the button
 * GameControl does the method that the Gui's button is calling
 * Repeat.
 */