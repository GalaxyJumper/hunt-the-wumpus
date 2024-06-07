// Cave Object
// Aviral Mishra
// 06/07/2024
// Mr. Reiber AP CSA Periods 5 & 6

import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Cave {

    private final int[][] map = new int[30][];
    private String caveName;

    // cave constructor 
    public Cave(){
        // get map and map reader
        JSONParser parser = new JSONParser();
        
        try {
            JSONObject a = (JSONObject) parser.parse(new FileReader("Maps.json"));
            int caveNumber = 1 + (int) (Math.random() * 3);
            caveName = "Cave " + (caveNumber);
            interpretFile((JSONObject) a.get(caveName));
        } catch (IOException io) {
            io.printStackTrace();
        } catch (ParseException p) {
            p.printStackTrace();
        }
    }

    public String getCaveName(){
        return caveName;
    }

    // get file info
    private void interpretFile(JSONObject cave){
        for (int i = 0; i < 30; i++){
            JSONArray possibleRooms = (JSONArray) cave.get(i + "");
            map[i] = new int[possibleRooms.size()];
            for (int j = 0; j < possibleRooms.size(); j++){
                map[i][j] = Math.toIntExact((long) possibleRooms.get(j));
            }
        }
    }

    // check if move is valid
    public boolean canMove(int curLoc, int newLoc){
        for (int i = 0; i < map[curLoc].length; i++){
            if (map[curLoc][i] == newLoc){
                return true;
            }
        }
        return false;
    }

    // returns the array of possible moves frmo a specific room
    public int[] possibleMoves(int curLoc){
        return map[curLoc];
    }

    // internal code curerntly works in two dimensions while every other class else indexes rooms 0 - 29
    /**private int[] oneToTwoD(int oneDCoord){
        int[] twoD = new int[]{oneDCoord / 6, oneDCoord % 6};
        return twoD;
    }**/

    public int twoToOneD(int[] twoDCoords){
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