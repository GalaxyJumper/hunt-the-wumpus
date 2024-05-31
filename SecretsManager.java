// Joshua Lennon
// 4 - 24 - 24
// Secrets Managing Object

public class SecretsManager {
    ///////////////////////////////////////////////////
    // VARIABLES
    ///////////////////////////////////////////////////

    private final String[] SECRETS = {
        "The *HAZARD* is in room *LOCATION*.",
        "The *HAZARD* is *DISTANCE* rooms away.",
        "There is a *HAZARD* in this cave...",
        "There is *NUMBER* *HAZARD* in this cave.",
        "There are *BOOLEAN* Hazards within 2 rooms.",
        "There are *BOOLEAN* Hazards within the room.",
        "There are *BOOLEAN* Hazards within 1 room.",
    };

    private GameLocations gl;

    ///////////////////////////////////////////////////
    // CONSTRUCTORS
    ///////////////////////////////////////////////////
    
    public SecretsManager(GameLocations gl){
        this.gl = gl;
    }

    ///////////////////////////////////////////////////
    // METHODS
    ///////////////////////////////////////////////////

    public String makeSecret(int[][] gameState){
        String base = SECRETS[(int) (Math.random()*SECRETS.length)];
        if(base.contains("*HAZARD*")){
            int type = (int) (Math.random()*gl.getTYPES().length);
            String hazard = gl.getTYPES()[type];

            base.replace("*HAZARD*", hazard);

            if(base.contains("*LOCATION*")){
                int loc = getHazardLoc(type);
                base.replace("*LOCATION*", ""+loc);
            } 
            if(base.contains("*DISTANCE*")) {
                int dist;
                if(type == 0) dist = 0;
                else dist = getDistance(type);
                base.replace("*DISTANCE*", ""+dist);
            } 
            if(base.contains("*NUMBER*")){
                int num = getHazardNum(type);
                base.replace("*NUMBER*", ""+num);

            }
        }  else {
            if(base.contains("*BOOLEAN*")){
                
            }
        }
        
        
        return base;
    }

    public int getHazardLoc(int type){
        if(type == 0) return gl.getPlayerLoc();
        else if(type == 1) return gl.getWumpusLoc();
        else if(type == 2) return gl.getRandomBatLoc();
        else if(type == 3) return gl.getRandomPitLoc();
        else return -1;
    }

    public int getHazardNum(int type){
        if(type < 2) return 1;
        else return 2;
    }

    public int getDistance(int type){
        return -1;
    }

}
