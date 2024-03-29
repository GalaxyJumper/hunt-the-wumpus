// Joshua Lennon
// Nick Lennon
// 2/12/2024
// Game Locations Object

//make an array representing each pentagonal room 
//tracking hazards and wumpus

import java.util.ArrayList;
import java.util.Random;

public class GameLocations {
    ///////////////////////////////////////////////
    // VARIABLES
    ///////////////////////////////////////////////

    private Random random = new Random();

    private int numPits;
    private int numBats;
    private boolean isPit;
    private boolean isBat;
    private ArrayList<GameLocations> Map = new ArrayList<GameLocations>();



    //ArrayList of pentagonal rooms that represent the map
    //Each one's location in the array is its assigned "number" (0-19 for a 20-sided dodecahedron)
    //Two of the locations are randomly chosen to be "pits" 
    //Two of the locations, aside from the two pits, are randomly chosen to be "bats"
    
    //GameControl will have a method to check when the Player moves into a new room if it is a "pit"
    //Or if it contains "Bats" with a boolean set to "true"



    ///////////////////////////////////////////////
    // CONSTRUCTORS
    ///////////////////////////////////////////////

    //determining 

        public GameLocations(){
            if (this.numPits < 2){
                
            }

            if (this.numBats < 2){
                
            }
        }

    ///////////////////////////////////////////////
    // METHODS
    ///////////////////////////////////////////////
}
