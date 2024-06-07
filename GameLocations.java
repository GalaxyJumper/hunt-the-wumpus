// Game Locations Object
// Josh Lennon
// 06/07/2024
// Mr. Reiber AP CSA Periods 5 & 6

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GameLocations {
    ///////////////////////////////////////////////
    // VARIABLES
    ///////////////////////////////////////////////

    // Holds room location of all game instances
    // First dimension represents types; e.g., player, wumpus, bat, pit
    // Second dimension represents instances of those types- e.g., bat 0 or 1, etc.
    // There is one player, one wumpus, two bats, and two pits according to spec
    private final int[][] locsTable = { new int[1], new int[1], new int[2], new int[2] };

    // Maps the type name to the locs table
    private final String[] TYPES = { "Player", "Wumpus", "Bat", "Pit" };
    
    private final String[] TYPESFRONT = {"Player", "Wumpus", "Current", "Abyss"};

    private Cave cave;
    private SecretsManager secrets;
    private Set<Integer> occupiedLocations;
    private Set<Integer> visitedRooms;

    private int wumpusAwakenedTurn = -1;

    ///////////////////////////////////////////////
    // CONSTRUCTORS
    ///////////////////////////////////////////////

    public GameLocations(Cave cave) {
        this.cave = cave;
        this.secrets = new SecretsManager(this);
        this.occupiedLocations = new HashSet<>();
        this.visitedRooms = new HashSet<>();
        initializeLocations();
    }

    ///////////////////////////////////////////////
    // METHODS
    ///////////////////////////////////////////////

    // Uses the secret manager to make a new secret from the current game state
    // Returns the filled in secret as a string
    public String newSecret(){
        return secrets.makeSecret();
    }

    // Returns a random room coordinate between 0 and 29
    public int getRandomLocation() {
        return (int) (Math.random() * 30);
    }

    // Checks if there is any type of entity in the specified room
    // Returns true if there is, false otherwise
    public boolean somethingThere(int roomNum) {
        return occupiedLocations.contains(roomNum);
    }

    public boolean inNewRoom(int roomNum){
        return !visitedRooms.contains(roomNum);
    }

    // Gets a new random location that is not already occupied
    // Returns the random location if nothing is there, retries otherwise
    private int getNovelLocation() {
        int randLoc;
        do {
            randLoc = getRandomLocation();
        } while (somethingThere(randLoc));
        return randLoc;
    }

    // Initializes the locations table with random, non-repeating rooms
    private void initializeLocations() {
        for (int type = 0; type < TYPES.length; type++) {
            int instanceNum = (type >= 2) ? 2 : 1;
            for (int instance = 0; instance < instanceNum; instance++) {
                int loc = getNovelLocation();
                locsTable[type][instance] = loc;
                occupiedLocations.add(loc);
            }
        }
    }

    // Returns the types of hazards that exist in the specified room
    public String[] getHazards(int room) {
        ArrayList<String> hazards = new ArrayList<>();
        for (int type = 1; type < locsTable.length; type++) { // Start at 1 to skip player's location
            for (int loc : locsTable[type]) {
                if (loc == room) {
                    hazards.add(TYPES[type]);
                }
            }
        }
        return hazards.toArray(new String[0]);
    }

    // Returns the types of hazards that exist in the player's room
    public String[] getHazards() {
        return getHazards(getPlayerLoc());
    }

    // Checks the rooms adjacent to the player for hazards
    // Returns a list of hazards present in the adjacent rooms
    public String[] checkForHazards() {
        int playerLoc = getPlayerLoc();
        ArrayList<String> hazardsPresent = new ArrayList<>();
        int[] adjacentRooms = cave.possibleMoves(playerLoc);
        for (int room : adjacentRooms) {
            if (somethingThere(room)) {
                for (String hazard : getHazards(room)) {
                    hazardsPresent.add(hazard);
                }
            }
        }
        return hazardsPresent.toArray(new String[0]);
    }

    // Moves the Wumpus randomly to a new room
    // Returns the Wumpus's new location
    public int moveWumpus(int turnNum) {
        if (((turnNum - wumpusAwakenedTurn) % 20 < 3) && (wumpusAwakenedTurn >= 0)) {
            return moveWumpus();
        }
        return getWumpusLoc();
    }

    // Moves the Wumpus to a random adjacent room
    public int moveWumpus() {
        int[] possibleLocs = cave.possibleMoves(getWumpusLoc());
        int loc = possibleLocs[(int) (Math.random() * possibleLocs.length)];
        setWumpusLoc(loc);
        return loc;
    }

    // Moves the Wumpus randomly 2-4 times after being "injured"
    // Returns the Wumpus's final position
    public int fleeingWumpus(int currentTurn) {
        if (wumpusAwakenedTurn < 0){
            wumpusAwakenedTurn = currentTurn;
        }
        int origin = getWumpusLoc();
        int spacesRan = (int) (Math.random() * 3) + 2; // Random number between 2 and 4
        int loc = origin;
        for (int i = 0; i < spacesRan; i++) {
            loc = moveWumpus();
        }
        return (origin == loc) ? fleeingWumpus(currentTurn) : loc; // Ensure Wumpus moves
    }

    // Transports the player to a random location in the cave
    // Returns the player's new location
    public int batTransport() {
        int rand = getNovelLocation();
        occupiedLocations.remove(locsTable[0][0]);
        visitedRooms.add(locsTable[0][0]);
        locsTable[0][0] = rand;
        occupiedLocations.add(rand);
        return rand;
    }

    // Returns the Wumpus's current location
    public int getWumpusLoc() {
        return locsTable[1][0];
    }

    // Sets the Wumpus's location if the move is valid
    // Returns true if the move is successful, false otherwise
    public boolean setWumpusLoc(int room) {
        if (cave.canMove(getWumpusLoc(), room)) {
            occupiedLocations.remove(locsTable[1][0]);
            locsTable[1][0] = room;
            occupiedLocations.add(room);
            return true;
        }
        return false;
    }

    // Returns the player's current location
    public int getPlayerLoc() {
        return locsTable[0][0];
    }

    // Sets the player's location if the move is valid
    // Returns true if the move is successful, false otherwise
    public boolean setPlayerLoc(int room) {
        if (cave.canMove(getPlayerLoc(), room)) {
            occupiedLocations.remove(locsTable[0][0]);
            visitedRooms.add(locsTable[0][0]);
            locsTable[0][0] = room;
            occupiedLocations.add(room);
            return true;
        }
        return false;
    }

    // Returns the location of a specific bat instance
    public int getBatLoc(int inst) {
        return locsTable[2][inst];
    }

    // Returns the location of a random bat
    public int getRandomBatLoc() {
        return locsTable[2][(int) (Math.random() * 2)];
    }

    // Returns the location of a specific pit instance
    public int getPitLoc(int inst) {
        return locsTable[3][inst];
    }

    // Returns the location of a random pit
    public int getRandomPitLoc() {
        return locsTable[3][(int) (Math.random() * 2)];
    }

    // Returns the cave instance
    public Cave getCave() {
        return this.cave;
    }

    // Returns the array of type names
    public String[] getTYPES() {
        return this.TYPES;
    }

    public String[] getTYPESFRONT(){
        return this.TYPESFRONT;
    }

    // Returns the locations table
    public int[][] getLocsTable() {
        return locsTable;
    }

}