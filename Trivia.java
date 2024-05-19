//Lincon
//Nick

//imports
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;

class Trivia {
    // PROPERTIES --------------------

    File triviaFile; // Trivia stores all of our trivia questions in a CSV format
    File hintFile; // Hint gives you a mix of useless info and valuable locations

    Scanner in; // Scans User imput
    Scanner triviaHintScanner; // The scanner that reads the file ("TriviaT.csv")
    Scanner triviaQuestionScanner; // the scanner that reads the file ("TriviaQ.csv")

    // Values of the questions
    String genre = ""; // Gets the genre
    String hint; // gets the hint
    int amount; // amount requested
    int right; // amound needed to be right
    ArrayList<String[]> triviaData = new ArrayList<String[]>();
    // 0 - lore, 1 - misc 2 - actions 3 - chem 4 - ect ill update this later
    ArrayList<String[]> hintData = new ArrayList<String[]>();
    // 0 - id, 1 - hint 2 - type

    // CONSTRUCTOR --------------------
    public Trivia() throws FileNotFoundException {
        in = new Scanner(System.in); // Scans Usr imput
        triviaQuestionScanner = new Scanner(triviaFile); // the scanner that reads the file ("TriviaQ.csv")
        triviaHintScanner = new Scanner(hintFile); // The scanner that reads the file ("TriviaQ.csv")

        triviaFile = new File("TriviaQuestions.csv"); // The file we refer to
        hintFile = new File("TriviaHints.csv"); // the file we refer to

        triviaData = compile(triviaQuestionScanner);
        hintData = compile(triviaHintScanner);
    }
    // Methoods -----------------------

    // Takes the trivia question and all of its properties
    // the MAIN function of trivia.
    // then, asks them for an answer and returns a flag wether their answer is right
    // or not.
    // Using this flag, they can determine to give the reward or the consequence
    // based on which object
    // is calling Triviarun

    public ArrayList<String[]> compile(Scanner scn) {
        ArrayList<String[]> fileParts = new ArrayList<String[]>();

        while (scn.hasNextLine()) {
            String line = scn.nextLine();
            System.out.println(line);
            String[] data = line.split(",");
            fileParts.add(data);
        }
        return fileParts;
    }

    public String randomTrivia(ArrayList<String[]> data, int index) {
        // int filelengthT = fileParts.length;
        int randc = (int) (Math.random() * (data.size()));
        hint = data.get(randc)[index];

        // fancy algorhithm to determine which hint to give them go here.
        // occurs when you want to get a hint
        System.out.print("Works - Trivhint");
        return hint;

    }

    // Split this into multiple objects? Getters and setters
    public boolean triviaRun(int amount, int right, String genre) {

        String[] currentRow;
        int filelength = triviaCompile.size() - 1;
        int qCount = amount;
        while (qCount > 0) {
            int randq = (int) (Math.random() * filelength);
            currentRow = triviaCompile.get(randq);
            qCount = -1;
            String currentQ = currentRow[1];
            String[] currenntA = currentRow[2].split("|");
            String currentK = currentRow[3];
            System.out.println("works - triviarun");
        }
        System.out.println("This program works! - Triviarun");

        // WEIOUIL HEOIHLHASO DJADASDCFEFSDC
        // 1 -
        return true;
    }

    // Returns a hint
    // Hints - Tells the player useless or useful stuff.
    /*
     * 2darray[][]{
     * {id, text, genre}, {id, text, genre}
     * 
     * }
     */

}