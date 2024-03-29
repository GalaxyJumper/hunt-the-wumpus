//Kavya Tayal
//02/12/24
//Scores Object

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;

public class HighScore {
    ///Properties


    ///Constructors
    public HighScore(){
    
        //String path = GetDefaultLogFileName();
        //File highScoreFile = new File(path);

        


        //File HighScore = new File("Highscore.csv");
        //BufferedWriter bw = new BufferedWriter(fw);
        //BufferedReader br = new BufferedReader(fw);
        //Filewriter fw = new FileWriter("Highscore.csv",true);

    }



    ///Methods
    //private static String GetDefaultLogFileName() {
        //return System.getProperty(C:\git\hunt-the-wumpus-1\Highscore.csv);
       // }

    //checks if the current player has a high score, updates data table, and displays data table
    public void endOfGame(Player player){
        endOfGameCheck(player);
        if(endOfGameCheck(player)) endOfGameUpdate(player);
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
