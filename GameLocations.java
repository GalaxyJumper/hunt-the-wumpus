// Joshua Lennon
// Nick Lennon
// 2/12/2024
// Game Locations Object

//make an array representing each hexagonal room 
//tracking hazards and wumpus

import java.util.ArrayList;
import java.util.Random;

public class GameLocations {
    ///////////////////////////////////////////////
    // VARIABLES
    ///////////////////////////////////////////////


    private final String[] TYPES = {"Player", "Wumpus", "Bat", "Pit"};
    private Cave cave = new Cave();
    private Random random = new Random();

    private int[][] locsTable;
    
    
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

    //Uses random utility to get a random room coordinate 0-30
    public int getRandomLocation(){
        int val = random.nextInt(30);
        return val;
    }

    // Interates through all in game objects to see if 1+ is in room x,y
    // Returns true if there is, false otherwise;
    public boolean somethingThere(int roomNum){
        for(int[] type : locsTable){
            for(int instance : type){
                if(instance == roomNum) return true;
            }
        }
        return false;
    }

    // Gets a new random location, checks if there is already something there
    // Returns the random location if nothing is there, retries otherwise
    private int getNovelLocation(){
        int randLoc = getRandomLocation();
        if(somethingThere(randLoc)) return getNovelLocation();
        return randLoc;
    }

    // Fills the location table with random, nonrepeating coordinates
    // Returns this new table;
    private int[][] initializeLocations(){
        int[][] randLocs = {{-1},
                            {-1},
                            {-1, -1},
                            {-1, -1}};
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
    public String getHazard(int room){
        //Starts at one to skip player's location
        for(int type = 1; type < locsTable.length; type++){
            for(int inst = 0; inst < locsTable[type].length; inst++){
                if(locsTable[type][inst] == room) return TYPES[type];
            }
        }
        return null;
    }


    //accesses and returns room number of the wumpus
    public int getWumpusLoc(){
        return locsTable[1][0];
    }

    public void setWumpusLoc(int room){
        locsTable[1][0] = room;
    }

    public int getPlayerLoc(){
        return locsTable[0][0];
    }

    public void setPlayerLoc(int room){
        locsTable[0][0] = room;
    }

}

