import java.awt.HeadlessException;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Main {
    public static void main (String[] args) throws HeadlessException, FileNotFoundException{
        Scanner scan = new Scanner(System.in);

        System.out.println("Is this program running in Github Codepaces? (y/n)");
        boolean workingInCodespaces = scan.next().equals("y");
        if (!workingInCodespaces){ 
            Gui gui = new Gui("HUNT THE WUMPUS", 960, 540); 
        }

        Trivia trivia = new Trivia();
         //Here's a little thing for testing Triva
        System.out.println(trivia.Triviahint());
        System.out.println("Want to be close");
        //bonjour 
        System.out.println("Your code works ~*,`");
       
    }
}
