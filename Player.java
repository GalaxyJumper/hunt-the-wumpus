// name
// project
// date
// period

/**
 * DESCRIPTION:
 */

public class Player extends Thing {
  //////////////////////////
  // Properties
  //////////////////////////

  //////////////////////////
  // Constructor(s)
  //////////////////////////
  public Player() {
    setType(Types.PLAYER);
    System.out.println(this.getType());
  }

  //////////////////////////
  // Methods
  //////////////////////////
  public int getCellNumber(){
    return 4;
  }
  public void setCellNumber(int cellNumber){
    System.out.println(cellNumber);
  }
}