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
        "There are *BOOLEAN* Hazards in the room with you.",
        "There are *BOOLEAN* Hazards in the rooms around you.",
        "You are in room *LOCATION*."
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
            String hazard = gl.getTYPES()[(int) Math.ceil(Math.random()*3)];
            base.replace("*HAZARD*", hazard);
        } 
        
        
        return "";
    }

}
