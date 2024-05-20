//Kavya Tayal
//Nick Lennon
//02/12/24
//Scores Object

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
 
  /// Properties//////
  File highScoreFile;
  ArrayList<player> players;

  /// Constructors////
  public HighScore() throws FileNotFoundException {

    highScoreFile = new File("Highscore.csv");
    players = putPlayersIntoArray();

  }

  /// Methods///

  // checks if the current player has a high score, updates data table
  public void endOfGame(Player player) throws FileNotFoundException {
    endOfGameUpdate(player);
    updateFile();
  }

  // adds all players into array
  public static Player[] putPlayersIntoArray() throws FileNotFoundException {
   
   //makes a list of players using the length of the file
   ArrayList<Player> players = new Player[lengthOfFile() - 1];
   
   Scanner s = new Scanner(highScoreFile);
   
   //advances this to the row without all the ttles
   String line = s.nextLine();
    
    for (int i = 0; s.hasNextLine(); i++) {
      //makes an array for each line
      line = s.nextLine();
      String[] pLine = line.split(",");
   
      //assigns the players their properties using the array made before
      players.add( new Player(pLine));
   }
   
   
   return players;
 }
  

  public static int lengthOfFile() throws FileNotFoundException {
    int length = 0;
    Scanner fileScanner = new Scanner(highScoreFile);
    for (int i = 0; fileScanner.hasNextLine(); i++) {
      String line = fileScanner.nextLine();
      length++;
    }
    return length;
  }

  // checks if player has a high score and updates array accordingy
  private void endOfGameUpdate(Player player) throws FileNotFoundException {
    for( int i = players.length-1; i >= 0; i-- ){
      if (players.get(i).calcScore() <= player.calcScore) {
        players.set(i, player);
      }
    }
  }

  //updates the file with the current list of players
  public void updateFile(){
    FileWriter fw = new FileWriter(highScoreFile);
    for (Player p: players){
      fw.write(p.toString() + " \n");
    }

  }

}
