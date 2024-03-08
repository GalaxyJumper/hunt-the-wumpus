//Kavya Tayal
//02/12/24
//Scores Object

    import java.io.File;
    import java.io.*;
    public class HighScore {
    ///Properties


    ///Constructors
    public HighScore(){
        //File HighScore = new File("Highscore.csv");
        //BufferedWriter bw = new BufferedWriter(fw);
         //BufferedReader br = new BufferedReader(fw);
        //Filewriter fw = new FileWriter("Highscore.csv",true);

    }



    ///Methods

    //checks if the current player has a high score, updates data table, and displays data table
    public void endOfGame(Player player){
        endOfGameCheck(player);
        endOfGameUpdate(player);
        displayTable();
    }
    
    //checks if player has a high score
    public boolean endOfGameCheck(Player player){
        return false;
    }
    
    //updates data table with player information
    public void endOfGameUpdate(Player player){}

    //displays data table
    public void displayTable(){};



}
