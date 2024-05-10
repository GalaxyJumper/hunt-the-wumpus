import java.awt.HeadlessException;
import java.io.FileNotFoundException;

public class Main {
    public static void main (String[] args) throws HeadlessException, FileNotFoundException{

        GameControl freak = new GameControl();

        Trivia trivia = new Trivia();
         //Here's a little thing for testing Triva
        System.out.println("-" + trivia.Triviahint());
        
        //bonjour 
        Cave.println("Your code works ~*,`");
       
    }
}
