// Joshua Lennon (cringe)
// Nick Lennon (less cringe)
// Aviral Mishra (the cool one)
// Toki
// 2/12/2024
// Game Control Object

//taking in inputs for all classes and running the game
//connecting the ui to the game itself
import java.util.Scanner;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

public  class GameControl {
    ///////////////////////////////////////////////
    // VARIABLES
    ///////////////////////////////////////////////

    private GameLocations gameLocs;
    private Gui gui;

    ///////////////////////////////////////////////
    // CONSTRUCTORS
    ///////////////////////////////////////////////
    public GameControl() throws FontFormatException, IOException{
        gameLocs = new GameLocations();
        Scanner scan = new Scanner(System.in);

        
        if (!GraphicsEnvironment.isHeadless()){ 
            gui = new Gui("HUNT THE WUMPUS", 2560, 1440, gameLocs); 
        }

        scan.close();
    }

    ///////////////////////////////////////////////
    // METHODS
    ///////////////////////////////////////////////

    // start-up
    public void getCave(){
        
    }

    // background
    public void passTurn(){
        int input = 0;
        // player input for where to move
        move(input);
        //String[] checkForHazards = gameLocs.checkForHazards();
        String[] hazards = gameLocs.getHazards();
        for (String hazard : hazards){
            if (hazard.equals("Wumpus"))
                wumpusEncounter();
            else if (hazard.equals("Bat"))
                batEncounter();
            else if (hazard.equals("Pit")){
                pitEncounter();
            }

        }
        // what happens in new room
        // actions
    }

    public void gameEnd(){

    }

    private void wumpusEncounter(){}

    private void batEncounter(){}

    private void pitEncounter(){}

    //player input from gui
    //moves the player's location
    public void move(int playerInput){
        if (gameLocs.setPlayerLoc(playerInput))
            gui.move(playerInput);
        else
            gui.failMove(playerInput);
        
    }

    public void shootArrow(){

    }

    public void getStore(){

    }

    public void buyArrows(){

    }

    public void openHintUI(){

    }

    public void openTriviaUI(){

    }
}

