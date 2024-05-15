// Joshua Lennon
// Nick Lennon
// Toki
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
    private Player player;

    ///////////////////////////////////////////////
    // CONSTRUCTORS
    ///////////////////////////////////////////////
    public GameControl(){
        startNewGame();
        Scanner scan = new Scanner(System.in);

        Cave.println("Is this program running in Github Codepaces? (y/n)");
        boolean workingInCodespaces = scan.next().equals("y");
        if (!workingInCodespaces){ 
            gui = new Gui("HUNT THE WUMPUS", 2560, 1440); 
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
        player = new Player();
    }

    public Cave getCave(){
        return this.cave;
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

    // Checks if the romm exists and if it does depletes arrow by one. the effects
    // are undecided in where they should go. Is the wumpus in the room? etc.
    public void shootArrow(){
        if(isValid()){
            player.setArrows(player.getArrows()-1);
        }
    }

    public void getStore(){

    }

    public void buyArrows(int num){
        this.player.setArrows(player.getArrows()+num);
    }

    public void openHintUI(){

    }

    public void openTriviaUI(){

    }

    public boolean isValid(){
        /*
         * In reality, this should go like this: the scanner sees where the player is
         * Checks if player's direction is facing an open door
         * if it is, the method should return true.
         * An alternative is to somehow use a gui, but that's known but to god.
        */
        return true;
    }

    public void checkIfPlayerInTrap(){
        this.playerInTrap = true;
    }


   







}

