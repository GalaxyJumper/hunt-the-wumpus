//imports
import java.util.Scanner;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
class Trivia{
    // PROPERTIES --------------------
    Scanner in; //Scans Usr imput
    File trivia; //The file we refer to
    Scanner triv; //the scanner that reads the file ("TriviaQ.csv")
    File Hint; //the file we refer to
    Scanner h; //The scanner that reads the file ("TriviaT.csv")
    //Values of the questions
    String genre =  ""; //Gets the genre
    boolean rightflag; //returns true = right and false = wrong lmao
    String hint; //gets the hint
    int amount; //amount requested
    int right; //amound needed to be right
    // 0 - lore, 1 - misc 2 - actions 3 - chem  4 - ect ill update this later

    // CONSTRUCTOR --------------------
public Trivia() throws FileNotFoundException{
    in     =   new Scanner(System.in); //Scans Usr imput
    trivia =   new File("TriviaQ.csv"); //The file we refer to
    triv   =   new Scanner("trivia"); //the scanner that reads the file ("TriviaQ.csv")
    Hint   =   new File("TriviaT.csv"); //the file we refer to
    h      =   new Scanner(Hint); //The scanner that reads the file ("TriviaQ.csv")

}
    // Methoods -----------------------
    
    //Takes the trivia question and all of its properties
    //the MAIN function of trivia.
    //then, asks them for an answer and returns a flag wether their answer is right or not.
    //Using this flag, they can determine to give the reward or the consequence based on which object
    //is calling Triviarun

    //Split this into multiple objects? Getters and setters
public boolean triviaRun(int amount, int right, String genre){
    String in;
    int filelengthT;
    System.out.println("This program works! - Triviarun");
    
    //WEIOUIL HEOIHLHASO DJADASDCFEFSDC
    //1 - 
    return rightflag;
}
    //Returns a hint
    //Hints - Tells the player useless or useful stuff.
public String Triviahint(){
System.out.println("This program works! - TriviaHint");
    String line;
    String[] fileParts;
    int filelengthT = 0;
    String temp = " ";
    
    if (h.hasNextLine()) {

        h.nextLine();
     }

     while(h.hasNextLine()){
        
         line = h.nextLine();
         fileParts = line.split(",");
         filelengthT = fileParts.length + 2;
         temp = fileParts[1];
         for(int i = 0; i < fileParts.length; i++){
            System.out.println(fileParts[i]);
         }
     }
    //int filelengthT = fileParts.length;
    

int rand = (int)(Math.random() * (filelengthT - 0)+0);
     hint = fileParts[rand];

    //fancy algorhithm to determine which hint to give them go here. 
    //occurs when you want to get a hint
    return hint;
}    


}