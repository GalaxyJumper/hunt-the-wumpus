// Aviral Mishra

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Cave {

    private final String[][] map = new String[5][6];
    // sample string:
    // loc1x,loc1y;loc2x,loc2y;loc3x,loc3y
    // you can stop at one or two locs

    public Cave() {
        File maps = new File("maps.csv");
        Scanner mapInterpreter = new Scanner(System.in);
        try {
            mapInterpreter = new Scanner(maps);
        } catch(FileNotFoundException e) {
            print("lmao dumbass");
            System.exit(0);
        }
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
        int[] curLoc = oneToTwoD(curLoc1);
        int[] newLoc = oneToTwoD(newLoc1);
        boolean canMove = false;
        String[] curLocInfo = (map[curLoc[0]][curLoc[1]]).split(";"); // get list of valid moves
        String[] temp = new String[2];
        
        // check if newLoc matches valid moves
        for (int i = 0; i < (map[curLoc[0]][curLoc[1]].split(";")).length; i++){

            temp = (curLocInfo[i]).split(",");

            if ((Integer.parseInt(temp[0]) == newLoc[0]) && (Integer.parseInt(temp[1]) == newLoc[1])){
                canMove = true;
                break;
            }
        }
        return canMove;
    }

    public String[][] initialCave(){
        return map;
    }

    public int[] oneToTwoD(int oneDCoord){
        int[] twoD = new int[]{oneDCoord / 6, oneDCoord % 6};
        return twoD;
    }

    // i ain't sys-outing s***
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