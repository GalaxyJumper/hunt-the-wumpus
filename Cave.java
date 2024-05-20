// Aviral Mishra

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Cave {

    private final String[][] map = new String[5][6];
    // sample string:
    // loc1x,loc1y;loc2x,loc2y;loc3x,loc3y
    // you can stop at one or two locs

    // cave constructor 
    public Cave() {
        // get map and map reader
        File maps = new File("maps.csv");
        Scanner mapInterpreter = new Scanner(System.in);
        try {
            mapInterpreter = new Scanner(maps);
        } catch(FileNotFoundException e) {
            print("lmao dumbass");
            System.exit(0);
        }

        // get a map 
        interpretFile(mapInterpreter);
    }

    // get file info
    private void interpretFile(Scanner mapInterpreter){
        for (int i = 0; i < 30; i++){
            map[i / 6][i % 6] = mapInterpreter.next();
        }
    }

    // check if move is valid
    public boolean canMove(int curLoc1, int newLoc1){
        // convert inputs to two dimensions
        int[] curLoc = oneToTwoD(curLoc1);
        int[] newLoc = oneToTwoD(newLoc1);
        boolean canMove = false;

        // get relevant info from map 2d array
        String[] curLocInfo = (map[curLoc[0]][curLoc[1]]).split(";"); // get list of valid moves
        String[] temp = new String[2];
        
        // check if newLoc matches valid moves
        for (int i = 0; i < (map[curLoc[0]][curLoc[1]].split(";")).length; i++){

            // check if specific move is possible from 2d map array data
            temp = (curLocInfo[i]).split(",");

            if ((Integer.parseInt(temp[0]) == newLoc[0]) && (Integer.parseInt(temp[1]) == newLoc[1])){
                canMove = true;
                break;
            }
        }

        // true if door between two inputted doors
        return canMove;
    }

    // returns the array of possible moves frmo a specific room
    public int[] possibleMoves(int curLoc1){
        int[] curLoc = oneToTwoD(curLoc1);
        String[] curLocInfo = (map[curLoc[0]][curLoc[1]]).split(";"); // get list of valid moves
        int[] possibleMoves = new int[curLocInfo.length];

        // reads map data to find all possible moves
        for (int i = 0; i < curLocInfo.length; i++){
            String[] temp1 = curLocInfo[i].split(",");
            int[] temp2 = new int[]{Integer.parseInt(temp1[0]), Integer.parseInt(temp1[1])};
            possibleMoves[i] = twoToOneD(temp2);
        }

        // array of all possible moves, size ranging 1 - 3 (inclusive)
        return possibleMoves;
    }

    // internal code curerntly works in two dimensions while every other class else indexes rooms 0 - 29
    private int[] oneToTwoD(int oneDCoord){
        int[] twoD = new int[]{oneDCoord / 6, oneDCoord % 6};
        return twoD;
    }

    private int twoToOneD(int[] twoDCoords){
        return twoDCoords[1] + 6 * twoDCoords[0];
    }

    // coding speed stuff, delete later
    public static void println(String string){
        System.out.println(string);
    }

    public static void print(String string){
        System.out.println(string);
    }

    public static void println(){
        System.out.println();
    }
}