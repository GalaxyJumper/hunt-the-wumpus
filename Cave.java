// name
// project
// date
// period

/**
 * DESCRIPTION:
 */

public class Cave {
  //////////////////////////
  // Properties
  //////////////////////////
  String[] gameLocations = new int[30];
  
  //////////////////////////
  // Constructor(s)
  //////////////////////////
  public Cave(Player player, Wumpus wumpus){ 

  }

  //////////////////////////
  // Methods
  //////////////////////////
  public static void up(Player player){
    if (player.getCellNumber() > 6)
      player.setCellNumber(player.getCellNumber() - 6);
    else
      player.setCellNumber(player.getCellNumber() + 24);
  }

  public static void upRight(Player player){
  }
}