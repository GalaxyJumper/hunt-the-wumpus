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
import java.awt.Color;

public  class GameControl {
    ///////////////////////////////////////////////
    // VARIABLES
    ///////////////////////////////////////////////

    private GameLocations gameLocs;
    private Gui gui;
    private Player player;
    private Trivia trivia;

    private final String[] secrets = {
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
    };
    
    // purchasing arrows - 0
    // purchasing secrets - 1
    // saving from pit - 2
    // escaping wumpus - 3
    // escaping bats - 4

    private int questionType;

    ///////////////////////////////////////////////
    // CONSTRUCTORS
    ///////////////////////////////////////////////
    public GameControl() throws FontFormatException, IOException{
        player = new Player();
        gameLocs = new GameLocations();
        Scanner scan = new Scanner(System.in);
        trivia = new Trivia();
        
        if (!GraphicsEnvironment.isHeadless()){ 
            //                                   2560 X 1440 - DO NOT CHANGE
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
    //Get trivia input
    public String getNextTriviaInput(){
        return "a";
        //FOR LATER: return gui.getNextTriviaInput();
    }

    // 0 - 29 (inclusive) is a room number being moved to
    public void turn(int playerInput){

    }

    // 0 - 29 (inclusive) + true location receiving arrow
    // 0 + false - purchase arrow
    // 1 + false - purchase secret
    public void turn(int playerInput, boolean isShooting){
        if (isShooting){
            if (gameLocs.getCave().canMove(gameLocs.getPlayerLoc(),playerInput)){
                player.addTurnsTaken();
                player.addArrows(-1);
                // gui.updateTurnCounter(player.getTurnsTaken());
                String[] hazards = gameLocs.getHazards(playerInput);
                boolean wumpusShot = false;
                for (String hazard : hazards){
                    if (hazard.equals("Wumpus")){
                        wumpusShot = true;
                        break;
                    }
                }
                if (wumpusShot){
                    // gui.drawSplashText("You Won!", new Color(0,255,0));
                    gameEnd();
                }
            }
        } else {
            questionType = playerInput;
            // Index 0 - question
            // Index 1 - 4 - answers
            // String[] QandA = trivia.getQuestionAndAnswers();
            // gui.displayTrivia(QandA);
        }
    }

    // response is "A", "B", "C", or "D"
    public void triviaAnswerAction(String response){
        if (questionType == 0){
           /** if (trivia.isCorrect(response)){
            * 
            
            player.purchaseArrow();
            gui.drawSplashText("Arrow Gained", new Color(255,255,0));

            }
            **/
            player.addTurnsTaken();
            // gui.updateTurnCounter(player.getTurnsTaken());
            return;
        }
        if (questionType == 1){
            // if (trivia.isCorrect(response)){ 
            // gui.displaySecret(writeSecret((int) (Math.random() * 10 + 1)));
            //}
            player.addTurnsTaken();
            // gui.updateTurnCounter(player.getTurnsTaken());
            return;
        }
        if (questionType == 2){
            //if (!trivia.isCorrect(response)){
                // gui.drawSplashText("You died!", new Color(255, 0 , 0));
                gameEnd();
            //} else {
                // gui.drawSplashText("You lived!", new Color(0, 255, 0));
            //}
            return; 
        }
        if (questionType == 3){
            //if (!trivia.isCorrect(response)){
                // gui.drawSplashText("You died!", new Color(255, 0 , 0));
                gameEnd();
            //} else {
                // gui.drawSplashText("The Wumpus is Wounded!", new Color(0, 255, 0));
            //}
            return; 
        }
        if (questionType == 4){
            //if (!trivia.isCorrect(response)){
                // gui.drawSplashText("You died!", new Color(255, 0 , 0));
                gameEnd();
            //} else {
                // gui.drawSplashText("You Escaped The Bats!", new Color(255, 255, 0));
            //}
            return; 
        }
    }

    public String writeSecret(int secretIndex){
        String secret = secrets[secretIndex];
        return secret;
    }

    public void gameEnd(){

    }

    // player input
    public void move(int loc){
        if (gameLocs.setPlayerLoc(loc))
            gui.move(loc);
        else
            gui.failMove(loc);    
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