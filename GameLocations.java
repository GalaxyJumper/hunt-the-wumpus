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

    private final String[] TYPES = {"Player", "Wumpus", "Bat", "Pit"};

    private Random random = new Random();
    private int[][][] locsTable;

    


    
    


    //ArrayList of hexagonal rooms that represent the map
    //Each one's location in the array is its assigned "number" (0-19 for a 20-sided dodecahedron)
    //Two of the locations are randomly chosen to be "pits" 
    //Two of the locations, aside from the two pits, are randomly chosen to be "bats"
    
    //GameControl will have a method to check when the Player moves into a new room if it is a "pit"
    //Or if it contains "Bats" with a boolean set to "true"



    ///////////////////////////////////////////////
    // CONSTRUCTORS
    ///////////////////////////////////////////////

    

        public GameLocations(){
            this.locsTable = initializeLocations();
        }



    ///////////////////////////////////////////////
    // METHODS
    ///////////////////////////////////////////////

    //Uses random utility to get a random room coordinate x,y within the cave
    //returns int[] of the location
    public int[] getRandomLocation(){
        int xVal = random.nextInt(6);
        int yVal = random.nextInt(5);

        return new int[]{xVal, yVal};
    }

    // Interates through all in game objects to see if 1+ is in room x,y
    // Returns true if there is, false otherwise;
    public boolean somethingThere(int x, int y){
        for(int[][] type : locsTable){
            for(int[] instance : type){
                if(instance[0] == x && instance[1] == y) return true;
            }
        }
        return false;
    }

    // Gets a new random location, checks if there is already something there
    // Returns the random location if nothing is there, retries otherwise
    private int[] getNovelLocation(){
        int[] randLoc = getRandomLocation();
        if(somethingThere(randLoc[0], randLoc[1])) return getNovelLocation();
        return randLoc;
    }

    // Fills the location table with random, nonrepeating coordinates
    // Returns this new table;
    private int[][][] initializeLocations(){
        int[][][] randLocs = new int[][][]{};
        for(int type = 0; type < TYPES.length; type++){
            int instanceNum = (type >= 2)? 2 : 1;
            for(int instance = 0; instance < instanceNum; instance++){
                randLocs[type][instance] = getNovelLocation();
            }
        }
        return randLocs;
    }

    //reads the first layer of the 3D array and parses that int into a string 
    //that tells you the type of hazard that exists in the room
    public void getType(int x, int y){
       // locsTable
    }


}

