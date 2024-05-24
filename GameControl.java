// Joshua Lennon (cringe)
// Nick Lennon (less cringe)
// Aviral Mishra (the cool one)
// Toki
// 2/12/2024
// Game Control Object

//taking in inputs for all classes and running the game
//connecting the ui to the game itself
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
    private HighScore scores;
    private Cave cave;

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

    private int questionType = -1;
    private int numRight = 0;

    ///////////////////////////////////////////////
    // CONSTRUCTORS
    ///////////////////////////////////////////////
    public GameControl() throws FontFormatException, IOException{
        cave = new Cave();
        player = new Player();
        gameLocs = new GameLocations(cave);
        trivia = new Trivia();
        scores = new HighScore(player);
        
        if (!GraphicsEnvironment.isHeadless()){
            gui = new Gui("HUNT THE WUMPUS", 2560, 1440, gameLocs); 
        }
    }

    ///////////////////////////////////////////////
    // METHODS
    ///////////////////////////////////////////////

    // 0 - 29 (inclusive) is a room number being moved to
    public void turn(int playerInput){
        if (gameLocs.setPlayerLoc(playerInput)){
            player.addTurnsTaken();
            //gui.updateTurnCounter();
            String[] hazards = gameLocs.getHazards();
            if (hazards.length == 0){
                return;
            }
            if (hazards[0].equals("Wumpus")){
                // gui.drawSplashText("You Encountered The Wumpus", new Color(255, 255, 0));
                triviaTime();
                questionType = 3;
                if (hazards.length == 1){
                    return;
                }
                hazards[0] = hazards[1];
            }

            if (hazards[0] == "Pit"){
                // gui.drawSplashText("You Teeter On The Precipice Of A Bottomless Cliff!", new Color(255, 255, 0));
                triviaTime();
                questionType = 2;
                return;
            }
            if (hazards[0] == "Bats"){
                // gui.drawSplashText("You Found Bats!", new Color(255, 255, 0));
                triviaTime();
                questionType = 4;
                return;
            }
        }
        gameLocs.moveWumpus(player.getTurnsTaken());
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
                boolean missed = false;
                for (String hazard : hazards){
                    if (hazard.equals("Wumpus")){
                        if (Math.random() < 0.5)
                            wumpusShot = true;
                        else {
                            missed = true;
                        }
                        break;
                    }
                }
                if (wumpusShot){
                    // gui.drawSplashText("You Won!", new Color(0,255,0));
                    gameEnd();
                } else if (missed) {
                    // gui.drawSplashText("You Missed!", new Color(255,255,0));
                } else {
                    // gui.drawSplashText("Seems The Wumpus Wasn't There...!", new Color(255,255,0));
                }
            }
        } else {
            questionType = playerInput;
            triviaTime();
        }
        gameLocs.moveWumpus(player.getTurnsTaken());
    }

    public void triviaTime(){
        // first is a list of trivia questions with all their info, there are always 5 questions at a time
        // each one corresponds to a second list with indexes organized as such:
        // index 0 - question (Q)
        // index 1 - 4 - answers (A)
        // index 5 - correct answer as a single letter (K)
        String[][] questions = new String[5][6];
        for (int i = 0; i < 5; i++){
            // questions[i] = trivia.getQandAandK();
        }
        // gui.openTriviaMenu(questions);
        
    }

    public void updateNumRight(){
        numRight++;
    }

    public void allQuestionsAsked(){
        triviaAction(numRight > 3);
        numRight = 0;
    }

    // response is "A", "B", "C", or "D"
    public void triviaAction(boolean triviaSuccess){
        if (questionType == 0){
           /** if (triviaSuccess){
            
            player.purchaseArrow();
            gui.drawSplashText("Arrow Gained", new Color(255,0,255));

            }
            **/
            player.addTurnsTaken();
            // gui.updateTurnCounter(player.getTurnsTaken());
        } else if (questionType == 1){
            // if (triviaSuccess){ 
            // gui.displaySecret(writeSecret((int) (Math.random() * 10 + 1)));
            //}
            player.addTurnsTaken();
            // gui.updateTurnCounter(player.getTurnsTaken());
        } else if (questionType == 2){
            //if (!triviaSuccess){
                // gui.drawSplashText("You died!", new Color(255, 0 , 0));
                gameEnd();
            //} else {
                // gui.drawSplashText("You lived!", new Color(0, 255, 0));
            //}
        } else if (questionType == 3){
            //if (!triviaSuccess){
                // gui.drawSplashText("You died!", new Color(255, 0 , 0));
                gameEnd();
            //} else {
                // gui.drawSplashText("The Wumpus is Wounded!", new Color(0, 255, 0));
            //}
        } else if (questionType == 4){
            //if (!triviaSuccess){
                int newRoom = gameLocs.batTransport();
                // gui.drawSplashText("You Were Transported Into Room #" + newRoom + "!", new Color(255,255,0));
            //} else {
                // gui.drawSplashText("You Escaped The Bats!", new Color(0, 255, 0));
            //}
        }
        triviaSuccess = false;
    }

    public String writeSecret(int secretIndex){
        String secret = secrets[secretIndex];
        return secret;
    }

    public void gameEnd(){
        // String[][] leaderboardInfo = scores.endOfGame();
        // gui.displayLeaderboard(leaderboardInfo);
        System.exit(0);
    }
}