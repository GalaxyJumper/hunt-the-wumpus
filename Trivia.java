
//imports
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;

class Trivia {
    // PROPERTIES --------------------
    Scanner in; // Scans Usr imput
    File trivia; // The file we refer to
    Scanner triv; // the scanner that reads the file ("TriviaQ.csv")
    File Hint; // the file we refer to
    Scanner h; // The scanner that reads the file ("TriviaT.csv")
    Scanner hLength; // The scanner that reads the file ("TriviaT.csv"), for length
    // Values of the questions
    String genre = ""; // Gets the genre
    boolean rightflag; // returns true = right and false = wrong lmao
    String hint; // gets the hint
    int amount; // amount requested
    int right; // amound needed to be right
    ArrayList<String> trivCompile = new ArrayList<String>();
    // 0 - lore, 1 - misc 2 - actions 3 - chem 4 - ect ill update this later

    // CONSTRUCTOR --------------------
    public Trivia() throws FileNotFoundException {
        in = new Scanner(System.in); // Scans Usr imput
        trivia = new File("TriviaQ.csv"); // The file we refer to
        triv = new Scanner("trivia"); // the scanner that reads the file ("TriviaQ.csv")
        Hint = new File("TriviaT.csv"); // the file we refer to
        h = new Scanner(Hint); // The scanner that reads the file ("TriviaQ.csv")
        hLength = new Scanner(Hint);
        compileTrivia();
    }
    // Methoods -----------------------

    // Takes the trivia question and all of its properties
    // the MAIN function of trivia.
    // then, asks them for an answer and returns a flag wether their answer is right
    // or not.
    // Using this flag, they can determine to give the reward or the consequence
    // based on which object
    // is calling Triviarun

    // Split this into multiple objects? Getters and setters
    public boolean triviaRun(int amount, int right, String genre) {

        String in;
        int filelengthT;
        System.out.println("This program works! - Triviarun");

        // WEIOUIL HEOIHLHASO DJADASDCFEFSDC
        // 1 -
        return rightflag;
    }

    public void compileTrivia() {
        while (triv.hasNextLine()) {
            System.out.println(triv.nextLine());
        }
    }

    // Returns a hint
    // Hints - Tells the player useless or useful stuff.
    /*
     * 2darray[][]{
     * {id, text, genre}, {id, text, genre}
     * 
     * }
     */
    public String Triviahint() {
        System.out.print("Works1 - Trivhint");
        String line;

        int filelengthT = 0;
        int k = 0;
        while (hLength.hasNextLine()) {
            filelengthT++;
            System.out.println("trhis happened");
            hLength.nextLine();
        }
        String[][] fileParts = new String[filelengthT][3];
        String[] hintList = new String[filelengthT];
        String temp = " ";

        if (h.hasNextLine()) {
        }

        while (h.hasNextLine()) {
            // * */

            // temp = fileParts[0][k];
            for (int b = 0; b < filelengthT; b++) {
                line = h.nextLine();
                System.out.println(line);
                for (int i = 0; i < filelengthT; i++) {
                    fileParts[b] = line.split(",");
                    System.out.println(fileParts[b]);
                }
                for (int h = 0; h < filelengthT; h++) {
                    hintList[h] = fileParts[h][1];
                }
            }

            System.out.println("hi");
        }
        // int filelengthT = fileParts.length;
        int randc = (int) (Math.random() * (filelengthT - 0) + 0);
        hint = hintList[randc];

        // fancy algorhithm to determine which hint to give them go here.
        // occurs when you want to get a hint
        System.out.print("Works - Trivhint");
        return hint;
    }

}