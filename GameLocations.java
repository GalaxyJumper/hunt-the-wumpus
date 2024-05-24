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

    // holds room location of all game instances
    // first d is the types; e.g. player, wumpus, pit
    // second d is intances - bat 0 or 1, ect.
    // There is one play, one wumpus, two bats, and two pits
    // Wumpus and Player have moving ability in the game
    private final int[][] locsTable = { new int[1], new int[1], new int[2], new int[2] };

    // maps the type name to the locs table
    private final String[] TYPES = { "Player", "Wumpus", "Bat", "Pit" };

    private Cave cave;
    private Random random;

    ///////////////////////////////////////////////
    // CONSTRUCTORS
    ///////////////////////////////////////////////

    public GameLocations(Cave cave) {
        this.cave = cave;
        this.random = new Random();
        initializeLocations();
        
    }

    ///////////////////////////////////////////////
    // METHODS
    ///////////////////////////////////////////////

    // Uses random utility to get a random room coordinate 0-29
    public int getRandomLocation() {
        int val = random.nextInt(30);
        return val;
    }

    // Interates through all in game types to see if 1+ is in room x,y
    // Returns true if there is, false otherwise;
    public boolean somethingThere(int roomNum) {

        for (int type = 0; type < locsTable.length; type++) {
            for (int instance = 0; instance < locsTable[type].length; instance++) {
                if (locsTable[type][instance] == roomNum)
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
    private void initializeLocations() {
        for (int type = 0; type < TYPES.length; type++) {
            int instanceNum = (type >= 2) ? 2 : 1;
            for (int instance = 0; instance < instanceNum; instance++) {
                this.locsTable[type][instance] = getNovelLocation();
            }
        }
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
        //Starts at one to skip player location
        for(int type = 1; type < locsTable.length; type++){
            for(int inst = 0; inst < locsTable[type].length; inst++){
                if(locsTable[type][inst] == playerLoc) hazards.add(TYPES[type]);
            }
        }
        return hazards.toArray(new String[hazards.size()]);
    }

    // looks through all rooms surrounding the player 
    // return string array of all the hazards present in them
    public String[] checkForHazards(){
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

    // picks a random room from the possible rooms to move to
    // moves the wumpus to that room
    // returns the Wumpus' new location
    public int moveWumpus(int turnNum) {
        int[] possibleLocs = this.cave.possibleMoves(getWumpusLoc());
        int rand = random.nextInt(possibleLocs.length);
        int loc = possibleLocs[rand];
        setWumpusLoc(loc);
        return loc;
    }

    // makes the Wumpus move randomly 2-4 times after being "injured"
    // return's their final position
    public int fleeingWumpus() {
        int origin = getWumpusLoc();
        int spacesRan = random.nextInt(2, 5);
        int loc = getWumpusLoc();
        for(int i = 0; i < spacesRan; i++){
            loc = moveWumpus();
        }
        if(origin == loc) return fleeingWumpus();
        return loc;
    }

    // A bat transfers the player to a random location in the cave
    // Returns the player's new location
    public int batTransport() {
        int rand = getRandomLocation();
        setPlayerLoc(rand);
        return rand;
    }

    public int getWumpusLoc(){
        return locsTable[1][0];
    }

    public boolean setWumpusLoc(int room) {
        int wumpusPos = locsTable[1][0];
        if (cave.canMove(wumpusPos, room)) {
            locsTable[1][0] = room;
            return true;
        }
        return false;
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

    public int getBatLoc(int inst){
        return this.locsTable[2][inst];
    }

    public int getRandomBatLoc(){
        return this.locsTable[2][random.nextInt(2)];
    }

    public int getPitLoc(int inst){
        return this.locsTable[3][inst];
    }

    public int getRandomPitLoc(){
        return this.locsTable[3][random.nextInt(2)];
    }

    public Cave getCave() {
        return this.cave;
    }

}
