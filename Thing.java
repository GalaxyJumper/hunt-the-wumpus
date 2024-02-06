// name
// project
// date
// period

/**
* DESCRIPTION:
*/

import java.util.UUID;

public class Thing {

  public enum Types {
    THING, PLAYER, WUMPUS, BAT, BEAR, PIT
  }

  //////////////////////////
  // Properties
  //////////////////////////
  private Types type;
  private String name;
  private UUID id;

  //////////////////////////
  // Constructor(s)
  //////////////////////////
  public Thing() {
    this.type = Types.THING;
    this.id = UUID.randomUUID();

  }

  //////////////////////////
  // Methods
  //////////////////////////
  public void setType(Types type) {
    this.type = type;
  }

  public Types getType() {
    return this.type;
  }

  public UUID getID() {
    return this.id;
  }

}