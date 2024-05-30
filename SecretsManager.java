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
        "",
        "",
        ""
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
        return "";
    }

}
