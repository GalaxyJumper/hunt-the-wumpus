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
import java.util.Scanner;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;

public class HighScore {
    ///Properties


    ///Constructors
    public HighScore(){
    
        File highScoreFile = new File("Highscore.csv");


        //File HighScore = new File("Highscore.csv");
        //BufferedWriter bw = new BufferedWriter(fw);
        //BufferedReader br = new BufferedReader(fw);
        //Filewriter fw = new FileWriter("Highscore.csv",true);

    }



    ///Method

    //checks if the current player has a high score, updates data table, and displays data table
    public void endOfGame(Player player){
        endOfGameCheck(player);
        if(endOfGameCheck(player)) endOfGameUpdate(player);
        displayTable();
    }
    
    //checks if player has a high score
    public boolean endOfGameCheck(Player player){
        //Scanner user = new Scanner(this.highScoreFile);

        return false;
    }
    
    //updates data table with player information
    public void endOfGameUpdate(Player player){}

    //displays data table
    public void displayTable(){};



}
