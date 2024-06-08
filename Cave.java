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

    // the map
    private final int[][] map = new int[30][];
    private String caveName;

    public Cave(){
        // map reader
        JSONParser parser = new JSONParser();
        
        try {
            // get json object
            JSONObject a = (JSONObject) parser.parse(new FileReader("Maps.json"));

            // choose random cave
            int caveNumber = 1 + (int) (Math.random() * a.size());
            caveName = "Cave " + (caveNumber);

            // gave cave info
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
            // list of possible rooms as a json array
            JSONArray possibleRooms = (JSONArray) cave.get(i + "");

            // transfer into map array
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

    // returns the array of possible moves from a specific room
    public int[] possibleMoves(int curLoc){
        return map[curLoc];
    }
}