// Joshua Lennon
// Nick Lennon
// 2/12/2024
// Game Locations Object

//make an array representing each hexagonal room 
//tracking hazards and wumpus

import java.util.Random;
import java.util.ArrayList;

public class GameLocations {
    ///////////////////////////////////////////////
    // VARIABLES
    ///////////////////////////////////////////////

    // holds room location of all game types and instances
    // first d is the types; e.g. player, wumpus, pit
    // second d is intances - bat 1 or 2, ect.
    private int[][] locsTable;

    // maps the type of character to the locs table
    private final String[] TYPES = { "Player", "Wumpus", "Bat", "Pit" };

    private Cave cave;
    private Random random = new Random();

    // Two of the locations are randomly chosen to be "pits"
    // Two of the locations, aside from the two pits, are randomly chosen to be
    // "bats"

    ///////////////////////////////////////////////
    // CONSTRUCTORS
    ///////////////////////////////////////////////

    public GameLocations() {
        this.locsTable = initializeLocations();
        this.cave = new Cave();
    }

    ///////////////////////////////////////////////
    // METHODS
    ///////////////////////////////////////////////

    // Uses random utility to get a random room coordinate 0-29
    public int getRandomLocation() {
        int val = random.nextInt(30);
        return val;
    }

    // Interates through all in game objects to see if 1+ is in room x,y
    // Returns true if there is, false otherwise;
    public boolean somethingThere(int roomNum) {
        for (int[] type : locsTable) {
            for (int instance : type) {
                if (instance == roomNum)
                    return true;
            }
        }
        return false;
    }

    // Gets a new random location, checks if there is already something there
    // Returns the random location if nothing is there, retries otherwise
    private int getNovelLocation() {
        int randLoc = getRandomLocation();
        if (somethingThere(randLoc))
            return getNovelLocation();
        return randLoc;
    }

    // Fills the location table with random, nonrepeating rooms
    // Returns this new table;
    private int[][] initializeLocations() {
        int[][] randLocs = { new int[1], new int[1], new int[2], new int[2] };
        for (int type = 0; type < TYPES.length; type++) {
            int instanceNum = (type >= 2) ? 2 : 1;
            for (int instance = 0; instance < instanceNum; instance++) {
                randLocs[type][instance] = getNovelLocation();
            }
        }
        return randLocs;
    }

    // tells you the type of hazard(s) that exists in the room
    // returns the string reprisentation of those hazards
    public String[] getHazards(int room) {
        ArrayList<String> hazards = new ArrayList<String>();
        // Starts at one to skip player's location
        for (int type = 1; type < locsTable.length; type++) {
            for (int inst = 0; inst < locsTable[type].length; inst++) {
                if (locsTable[type][inst] == room)
                    hazards.add(TYPES[type]);
            }
        }
        return hazards.toArray(new String[hazards.size()]);
    }

    // tells you the type of hazard(s) that exist in the room the player is in
    // returns the string reprisentation of those hazards
    public String[] getHazards() {
        ArrayList<String> hazards = new ArrayList<String>();
        int playerLoc = getPlayerLoc();
        for (int type = 1; type < locsTable.length; type++) {
            for (int inst = 0; inst < locsTable[type].length; inst++) {
                if (locsTable[type][inst] == playerLoc)
                    hazards.add(TYPES[type]);
            }
        }
        return hazards.toArray(new String[hazards.size()]);
    }

    // looks through all rooms surrounding the player and returns the hazards
    // present in them
    public String[] checkForHazards() {
        int playerLoc = getPlayerLoc();
        ArrayList<String> hazardsPresent = new ArrayList<String>();
        int[] adjacentRooms = cave.possibleMoves(playerLoc);
        for (int room : adjacentRooms) {
            if (somethingThere(room)) {
                for (String hazard : getHazards(room)) {
                    hazardsPresent.add(hazard);
                }
            }
        }

        return hazardsPresent.toArray(new String[hazardsPresent.size()]);
    }

    public void moveWumpus() {

    }

    public int getWumpusLoc() {
        return locsTable[1][0];
    }

    public void setWumpusLoc(int room) {
        int wumpusPos = locsTable[1][0];
        if (cave.canMove(wumpusPos, room)) {
            locsTable[1][0] = room;
        }
    }

    public int getPlayerLoc() {
        return locsTable[0][0];
    }

    public boolean setPlayerLoc(int room) {
        int playerPos = locsTable[0][0];
        if (cave.canMove(playerPos, room)) {
            locsTable[0][0] = room;
            return true;
        }
        return false;
    }

    public Cave getCave() {
        return this.cave;
    }

}
