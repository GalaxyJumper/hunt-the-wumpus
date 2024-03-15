import java.awt.HeadlessException;
import java.util.Scanner;
public class Main {
    public static void main (String[] args) throws HeadlessException{
        Scanner scan = new Scanner(System.in);
        System.out.println("Is this program running in Replit? (y/n)");
        boolean workingInReplit = scan.next().equals("y");
        if (workingInReplit){ 
            Gui gui = new Gui("HUNT THE WUMPUS", 960, 540); 
        }
        Trivia trivia = new Trivia();
        trivia.Triviahint();
        //bonjour 
        System.out.println("Your code works ~*,`");
        //Here's a little thing for testing Triva

    }
}
