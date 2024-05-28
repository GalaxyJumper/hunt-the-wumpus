//Kavya Tayal
//Nick Lennon
//02/12/24
//Scores Object

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
  public HighScore(Player player) throws FileNotFoundException {

    highScoreFile = new File("Highscore.csv");
    players = putPlayersIntoArray();
    this.player = player;

  }

  /// Methods///

  // checks if the current player has a high score, updates data table
  public String[][] endOfGame() throws IOException{
    endOfGameUpdate();
    updateFile();
    return twoDArray();
  }

  // adds all players into array
  public ArrayList<Player> putPlayersIntoArray() throws FileNotFoundException {
   
   //makes a list of players using the length of the file
   ArrayList<Player> players = new ArrayList<Player>(lengthOfFile()-1);
   
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
