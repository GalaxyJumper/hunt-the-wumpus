//Lincon
//shivansh

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
    String question;
    ArrayList<String[]> answer = new ArrayList<String[]>();
    String pQuestion;
    String key;
    int amount; // amount requested
    int right; // amound needed to be right
    ArrayList<String[]> triviaData = new ArrayList<String[]>();
    // 0 - lore, 1 - misc 2 - actions 3 - chem 4 - ect ill update this later
    ArrayList<String[]> hintData = new ArrayList<String[]>();
    // 0 - id, 1 - hint 2 - type

    // CONSTRUCTOR --------------------
    public Trivia() throws FileNotFoundException {
        in = new Scanner(System.in); // Scans Usr imput

        triviaFile = new File("TriviaQuestions.csv"); // The file we refer to
        hintFile = new File("TriviaHints.csv"); // the file we refer to

        triviaQuestionScanner = new Scanner(triviaFile); // the scanner that reads the file ("TriviaQ.csv")
        triviaHintScanner = new Scanner(hintFile); // The scanner that reads the file ("TriviaQ.csv")

        triviaData = compile(triviaQuestionScanner);
        hintData = compile(triviaHintScanner);
    }
    // Methoods -----------------------

    public ArrayList<String[]> compile(Scanner scn) { // compiles files into respective arraylists. Used for convinient
                                                      // pulling from an array.
        ArrayList<String[]> fileParts = new ArrayList<String[]>();
        scn.nextLine();
        while (scn.hasNextLine()) {
            String line = scn.nextLine();
            System.out.println(line);
            String[] data = line.split(",");
            fileParts.add(data);
        }
        return fileParts;
    }

    public String randomTrivia(ArrayList<String[]> data, int index) { // Grabs a random hint
        // int filelengthT = fileParts.length;
        int randc = (int) (Math.random() * (data.size()));
        hint = data.get(randc)[index];

        // fancy algorhithm to determine which hint to give them go here.
        // occurs when you want to get a hint
        System.out.print("Works - Trivhint");
        return hint;

    }

    // Split this into multiple objects? Getters and setters
    public boolean triviaRun(int amount, int right, String genre) { // Runs the questioning process

        int filelength = triviaData.size() - 1;
        int qCount = 5; // replace with int amount later, 5 is a testing variable.
        int correct = 0;
        // gui.openTriviaMenu();
        while (qCount > 0) {
            newQnAnK();

            qCount -= 1;
            String currentQ = question;
            ArrayList<String> currentA = new ArrayList<String>();
            for (int i = 0; i < 1; i++) {
                for (int k = 0; k < (answer.get(i)).length; k++) {
                    currentA.add(answer.get(i)[k]);

                }
            }
            String currentK = key;

            // terminal work
            System.out.println("-");
            // gui.nextQuestion(new String{q, g, a1-4}, new int[] {numQuestions,
            // numRequired}, boolean correct);

        }
        System.out.println("This program works! - Triviarun");
        // gui.closeTriviaMenu(boolean win);
        return true;
    }

    // Smaller functions for assisting in being able to grab things such as
    // questions
    public String[] getQandA() {
        String[] QandA = new String[5];
        QandA[0] = question;
        for (int i = 1; i < answer.size(); i++) {
            QandA[i] = answer.get(i)[0];
        }
        return QandA;
    }

    public void newQnAnK() {
        int rand = (int) (Math.random() * triviaData.size());
        this.question = triviaData.get(rand)[1];
        this.answer.clear();
        this.answer.add((triviaData.get(rand)[2]).split("~"));
        this.key = triviaData.get(rand)[3];
        System.out.println(question);
        System.out.println(answer.get(0)[1]);
        System.out.println(key);
    }
    public boolean isCorrect(String pAnswer){
        if(pAnswer.equals(this.key)){
                return true;
           }  
        return false; 
    }

}