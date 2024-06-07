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
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  // adds all players into array
  public ArrayList<Player> putPlayersIntoArray() throws FileNotFoundException {
    // makes a list of players
    ArrayList<Player> players = new ArrayList<Player>();

    if (!highScoreFile.exists() || highScoreFile.length() == 0) {
      return players;
    }

    Scanner s = new Scanner(highScoreFile);
    
    // advances this to the row without all the titles
    String line = s.nextLine();

    while (s.hasNextLine()) {
      // makes an array for each line
      line = s.nextLine();
      String[] pLine = line.split(",");

      // assigns the players their properties using the array made before
      players.add(new Player(pLine));
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
    return length - 1; // Subtract header line
  }

  // checks if player has a high score and updates array accordingly
  private void endOfGameUpdate() throws FileNotFoundException {
    // Add the new player to the list
    players.add(player);

    // Sort the players list in descending order based on the score
    players.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));

    // If there are more than 10 players, remove the extra players
    if (players.size() > 10) {
        while (players.size() > 10) {
            players.remove(players.size() - 1);
        }
    }
}
  // updates the file with the current list of players up to 10th.
  public void updateFile() throws IOException {
    FileWriter fw = new FileWriter(highScoreFile);
    fw.write("name,score,turns,coins,arrows,cave" + " \n");
    for (int p = 0; p < Math.min(10, players.size()); p++) {
      fw.write(players.get(p).toString() + " \n");
    }
    fw.close();
  }

  // complies everything in the csv into a string array
  public String[][] twoDArray() throws FileNotFoundException {
    String[][] result = new String[lengthOfFile()][6];
    Scanner s = new Scanner(highScoreFile);
    int i = 0;

    // advances this to the row without all the titles
    String line = s.nextLine();

    while (s.hasNextLine()) {
      // makes an array for each line
      line = s.nextLine();
      String[] pLine = line.split(",");
      result[i] = pLine;
      i++;
    }

    s.close();
    return result;
  }

  public int getPlayerIndex(){
    return (players.contains(player))? players.indexOf(player) : -1;
  }
}