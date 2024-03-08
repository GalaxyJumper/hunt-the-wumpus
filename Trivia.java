//imports
import java.util.Scanner;
import java.util.Random;
import java.io.File;
class Trivia{
    // PROPERTIES --------------------
    File trivia = new File("TriviaQ.csv");
    Scanner triv = new Scanner("trivia");
    int genre = 0;
    Boolean rightflag;
    // 0 - trivia, 1 - 2 - 3 - 4 - ect ill update this later

    // CONSTRUCTOR --------------------
public Trivia(int genre){
    this.genre = genre;
}
    // Methoods -----------------------
    
    //Takes the trivia question and all of its properties
    //then, asks them for an answer and returns a flag wether their answer is right or not.
    //Using this flag, they can determine to give the reward or the consequence based on which object
    //is calling Triviarun
public Boolean Triviarun(){
    //WEIOUIL HEOIHLHASO DJADASDCFEFSDC
    return rightflag;
}
    //Checks the answer


}