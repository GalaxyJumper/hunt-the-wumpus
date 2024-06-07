// Secrets Manager Object
// Josh Lennon
// 06/07/2024
// Mr. Reiber AP CSA Periods 5 & 6

import java.util.*;

public class SecretsManager {
    ///////////////////////////////////////////////////
    // VARIABLES
    ///////////////////////////////////////////////////

    private final String[] SECRETS = {
        "The *HAZARD* is in room *LOCATION*.",
        "The *HAZARD* is *DISTANCE* rooms away.",
        "There is a *HAZARD* in this cave...",
        "There *NUMBER* *HAZARD* in this cave.",
        "There are *BOOLEAN*Hazards within *AMOUNT*."
    };

    private GameLocations gl;

    ///////////////////////////////////////////////////
    // CONSTRUCTORS
    ///////////////////////////////////////////////////
    
    // Constructor to initialize the SecretsManager with a GameLocations object
    public SecretsManager(GameLocations gl){
        this.gl = gl;
    }

    ///////////////////////////////////////////////////
    // METHODS
    ///////////////////////////////////////////////////

    // Randomly picks a secret template from SECRETS and fills in the necessary information accordingly
    // Returns the finished secret as a string
    public String makeSecret(){
        // Select a random base template
        String base = SECRETS[(int) (Math.random() * SECRETS.length)];
        StringBuilder result = new StringBuilder(base);

        // If the template contains *HAZARD*, replace it with a random type
        if (base.contains("*HAZARD*")) {
            int type = (int) (Math.random() * gl.getTYPES().length);
            String typeName = gl.getTYPESFRONT()[type];
            replace(result, "*HAZARD*", typeName);

            // Replace *LOCATION* with the location of the hazard
            if (base.contains("*LOCATION*")) {
                int loc = getHazardLoc(type);
                replace(result, "*LOCATION*", String.valueOf(loc));
            }

            // Replace *DISTANCE* with the distance to the hazard
            if (base.contains("*DISTANCE*")) {
                int dist = (type == 0) ? 0 : getDistance(typeName);
                replace(result, "*DISTANCE*", String.valueOf(dist));
            }

            // Replace *NUMBER* with the number of such hazards
            if (base.contains("*NUMBER*")) {
                int num = getHazardNum(type);
                replace(result, "*NUMBER*", (num == 1 ? "is " : "are ") + num);
            }
        } else {
            // If the template contains *BOOLEAN*, replace it with true/false based on the presence of hazards
            if (base.contains("*BOOLEAN*")) {
                int amount = (int) (Math.random() * 4);
                boolean bool = areThereHazards(amount);
                replace(result, "*BOOLEAN*", bool ? "" : "not ");
                replace(result, "*AMOUNT*", (amount == 0) ? "this room" : amount + (amount == 1 ? " room" : " rooms"));
            }
        }
        return result.toString();
    }

    // Helper method to replace all occurrences of a target string within a StringBuilder
    private void replace(StringBuilder sb, String target, String replacement) {
        int start = sb.indexOf(target);
        while (start != -1) {
            sb.replace(start, start + target.length(), replacement);
            start = sb.indexOf(target, start + replacement.length());
        }
    }

    // Returns the current location of the specified hazard type
    private int getHazardLoc(int type) {
        switch(type) {
            case 0: return gl.getPlayerLoc();
            case 1: return gl.getWumpusLoc();
            case 2: return gl.getRandomBatLoc();
            case 3: return gl.getRandomPitLoc();
            default: return -1;
        }
    }

    // Returns the number of instances of a given hazard type
    private int getHazardNum(int type) {
        return gl.getLocsTable()[type].length;
    }

    // Uses Breadth-First Search (BFS) to find the distance from the player to the first room that contains the specified hazard type
    private int getDistance(String type) {
        Queue<Integer> toVisit = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        toVisit.add(gl.getPlayerLoc());
        int distance = 0;

        while (!toVisit.isEmpty()) {
            int size = toVisit.size();
            for (int i = 0; i < size; i++) {
                int room = toVisit.poll();
                if (visited.contains(room)) continue;
                visited.add(room);

                // Check if the room contains the hazard type
                String[] hazards = gl.getHazards(room);
                for (String hazard : hazards) {
                    if (hazard.equals(type)) return distance;
                }

                // Add connected rooms to the queue
                int[] connects = gl.getCave().possibleMoves(room);
                for (int nextRoom : connects) {
                    if (!visited.contains(nextRoom)) {
                        toVisit.add(nextRoom);
                    }
                }
            }
            distance++;
        }
        return -1; // In case no hazard is found
    }

    // Uses BFS to check for the presence of hazards within a certain distance from the player's location
    private boolean areThereHazards(int dist) {
        Queue<Integer> toVisit = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();
        toVisit.add(gl.getPlayerLoc());

        while (!toVisit.isEmpty() && dist >= 0) {
            int size = toVisit.size();
            for (int i = 0; i < size; i++) {
                int room = toVisit.poll();
                if (visited.contains(room)) continue;
                visited.add(room);

                // Check if the room contains any hazards
                if (gl.getHazards(room).length > 0) return true;

                // Add connected rooms to the queue
                int[] connects = gl.getCave().possibleMoves(room);
                for (int nextRoom : connects) {
                    if (!visited.contains(nextRoom)) {
                        toVisit.add(nextRoom);
                    }
                }
            }
            dist--;
        }
        return false;
    }
}
