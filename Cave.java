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
        try {
            Scanner mapInterpreter = new Scanner(maps);
        } catch(FileNotFoundException e) {
            print("lmao dumbass");
        }
    }

    public boolean canMove(int[] curLoc, int[] newLoc){
        boolean canMove = false;
        String[] temp = new String[2];
        for (int i = 0; i < (map[curLoc[0]][curLoc[1]].split(";")).length; i++){
            temp = ((map[curLoc[0]][curLoc[1]]).split(";"))[i].split(",");
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
