//imports
import java.util.Scanner;
import java.util.Random;
import java.io.File;
class Trivia{
    // PROPERTIES --------------------
    File trivia = new File("TriviaQ.csv");
    Scanner triv = new Scanner("trivia");
    String genre = "";
    Boolean rightflag;
    // 0 - trivia, 1 - 2 - 3 - 4 - ect ill update this later

    // CONSTRUCTOR --------------------
public Trivia(String genre){
    this.genre = genre;
}
    // Methoods -----------------------
    
    //Takes the trivia question and all of its properties
    //the MAIN function of trivia.
    //then, asks them for an answer and returns a flag wether their answer is right or not.
    //Using this flag, they can determine to give the reward or the consequence based on which object
    //is calling Triviarun

    //Split this into multiple objects? Getters and setters
public Boolean Triviarun(){
    System.out.println("This program works! - Triviarun");
    //WEIOUIL HEOIHLHASO DJADASDCFEFSDC
    return rightflag;
}
    //Checks the answer


}