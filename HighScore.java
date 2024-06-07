// Highscore Object
// Kavya Tayal
// Nick Lennon
// Josh Lennon
// 06/07/2024
// Mr. Reiber AP CSA Periods 5 & 6

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class HighScore {
 
  /// Properties//////
  File highScoreFile;
  ArrayList<Player> players;
  Player player;

  /// Constructors////
  public HighScore(Player player, String caveName) throws FileNotFoundException {

    highScoreFile = new File("Highscore.csv");
    players = putPlayersIntoArray();
    this.player = player;
    player.setCave(caveName);
  }

  /// Methods///

  // checks if the current player has a high score, updates data table
  public String[][] endOfGame() {
    try {
      endOfGameUpdate();
      updateFile();
      return twoDArray();
    }
    catch (Exception e){
      e.printStackTrace();
    }
    return null;
  }

  // adds all players into array
  public ArrayList<Player> putPlayersIntoArray() throws FileNotFoundException {
   
   //makes a list of players
   ArrayList<Player> players = new ArrayList<Player>();
   
   Scanner s = new Scanner(highScoreFile);
   
   //advances this to the row without all the ttles
    String line = s.nextLine();
    
    while (s.hasNextLine()) {
      //makes an array for each line

      line = s.nextLine();
      String[] pLine = line.split(",");
   
      //assigns the players their properties using the array made before
      players.add( new Player(pLine));

   }
   
   s.close();
   return players;
 }
  

  public int lengthOfFile() throws FileNotFoundException {
    int length = 0;
    Scanner fileScanner = new Scanner(highScoreFile);
    while (fileScanner.hasNextLine()) {
      fileScanner.nextLine();
      length++;
    }
    fileScanner.close();
    return length;
  }

  // checks if player has a high score and updates array accordingy
  private void endOfGameUpdate() throws FileNotFoundException {

    //if there are less than 10 players in the csv, then the new player is automatically added to the csv
    if(players.size() < 10) players.add(player);
    else{
      for( int i = players.size() -1; i >= 0; i-- ){
        if (players.get(i).getScore() <= player.getScore()) {
          players.set(i, player);
          break;
        }
      }
    }
  }


  //updates the file with the current list of players
  public void updateFile() throws IOException {

    FileWriter fw = new FileWriter(highScoreFile);
    fw.write("name,score,turns,coins,arrows,cave" + " \n");
    for (Player p: players){
      fw.write(p.toString() + " \n");
    }
    fw.close();

  }

  //complies everything in the csv into a string array
  public String[][] twoDArray() throws FileNotFoundException{
    String[][] result = new String[lengthOfFile()-1][6];
    Scanner s = new Scanner(highScoreFile);
    int i = 0;
   
   //advances this to the row without all the ttles
    String line = s.nextLine();
    
    while (s.hasNextLine()) {
      
      //makes an array for each line
      line = s.nextLine();
      String[] pLine = line.split(",");
      result[i] = pLine;
      i++;
    }

    s.close();
    return result;

  }

}
