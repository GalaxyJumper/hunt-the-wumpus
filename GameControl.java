// Joshua Lennon
// Nick Lennon
// 2/12/2024
// Game Control Object

//taking in inputs for all classes and running the game
//connecting the ui to the game itself
import java.util.Scanner;

public  class GameControl {
    ///////////////////////////////////////////////
    // VARIABLES
    ///////////////////////////////////////////////

    private boolean playerInTrap = false;
    private GameLocations gameLocs;
    private Cave cave;
    private Gui gui;

    ///////////////////////////////////////////////
    // CONSTRUCTORS
    ///////////////////////////////////////////////
    public GameControl(){
        startNewGame();
        Scanner scan = new Scanner(System.in);

        Cave.println("Is this program running in Github Codepaces? (y/n)");
        boolean workingInCodespaces = scan.next().equals("y");
        if (!workingInCodespaces){ 
            gui = new Gui("HUNT THE WUMPUS", 2560, 1440, gameLocs); 
        }

        scan.close();
    }

    ///////////////////////////////////////////////
    // METHODS
    ///////////////////////////////////////////////

    // start-up
    public void startNewGame(){
        gameLocs = new GameLocations();
        cave = new Cave();
    }

    public void getCave(){
        
    }

    // background
    public void passTurn(){
        // player input for where to move
        // move(location)
        // what happens in new room
        // actions
    }

    public void gameEnd(){

    }

    // player input
    public void move(int playerInput){
        if(cave.canMove(gameLocs.getPlayerLoc(), playerInput)){
            int oldLoc = gameLocs.getPlayerLoc();
            gameLocs.setPlayerLoc(playerInput);
            //gui.playerMove(oldLoc, gameLocs.getPlayerLoc());
        } else {
            //gui.invalidMove();
        }
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

