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
       
        //simulate wumpus. 3/5
        boolean right = trivia.triviaRun(5, 3, null);
        
    }
}
