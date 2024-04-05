//Kavya Tayal
//02/12/24
//Scores Object

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;

public class HighScore {
    ///Properties
    File highScoreFile;

    ///Constructors
    public HighScore() throws FileNotFoundException{
    
        highScoreFile = new File("Highscore.csv");
        //Player[] players = putPlayersIntoArray(highScoreFile);



        //File HighScore = new File("Highscore.csv");
        //BufferedWriter bw = new BufferedWriter(fw);
        //BufferedReader br = new BufferedReader(fw);
        //Filewriter fw = new FileWriter("Highscore.csv",true);

    }



    ///Method

    //checks if the current player has a high score, updates data table, and displays data table
    public void endOfGame(Player player) throws FileNotFoundException{
        endOfGameCheck(player);
        if(endOfGameCheck(player) != -1) endOfGameUpdate(player);
        displayTable();
    }
    

    //adds all players into array
    /*public static Player[] putPlayersIntoArray(File f) throws FileNotFoundException {
    
    //makes a list of players using the length of the file
    Player[] players = new Player[lengthOfFile(f) - 1];
    
    Scanner s = new Scanner(f);

    //advances this to the row without all the ttles 
    String line = s.nextLine();

    for (int i = 0; s.hasNextLine(); i++) {
      //makes an array for each line
      line = s.nextLine();
      String[] pLine = line.split(",");

      //assigns the players their properties using the array made before
      players[i] = new Player(pLine);
    }


    return players;
  }*/
   
  public static int lengthOfFile(File f) throws FileNotFoundException {
    int length = 0;
    Scanner fileScanner = new Scanner(f);
    for (int i = 0; fileScanner.hasNextLine(); i++) {
      String line = fileScanner.nextLine();
      length++;
    }
    return length;
  }

  
  //checks if player has a high score
    private int endOfGameCheck(Player player) throws FileNotFoundException{
        Scanner s = new Scanner(highScoreFile);
        for(int i = 0; s.hasNextLine(); i++){
            String line = s.nextLine();
            String[] pLine = line.split(",");
            int score = Integer.parseInt(pLine[1]);
            if (score < player.calcScore()) return i;
        }
        s.close();
        return -1;
    }
    
    //updates data table with player information
    public void endOfGameUpdate(Player player){}

    //removes a player line from file
    private Player[] removePlayer(Player[] players, Player player) throws FileNotFoundException{
        Scanner s = new Scanner(highScoreFile);
        //for 
        s.close();

    }

    private void addPlayer(Player player){

    }
    //displays data table
    public void displayTable(){};



}


//constructor makes a file, puts all the players of the file into an array
//checks if that player is higher than any of them. if it is, it returns the lowest score
