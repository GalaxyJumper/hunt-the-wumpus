import java.awt.FontFormatException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws FontFormatException, IOException {

        System.setProperty("sun.java2d.uiScale", "0.7");

        new GameControl();

        // making sure highscore works!
        // String[] test = new String[] {"bobby", "3", "2", "3", "7", "cave"};
        // Player player = new Player(test);
        // HighScore highscore = new HighScore(player);
        // highscore.endOfGame();

        // This should always run at the end of main!!!!
        Cave.println("Your code works ~*,`");
    }
}