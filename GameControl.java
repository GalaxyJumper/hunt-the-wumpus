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
import java.util.Arrays;

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
    private SoundManager sManager;

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

    private String[][] questions = new String[5][6];
    private boolean[] answers = new boolean[5];
    private int currentQuestion = 0;

    private String[] hazards;
    
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
        sManager = new SoundManager();
        
        if (!GraphicsEnvironment.isHeadless()){
            gui = new Gui("HUNT THE WUMPUS", 2560, 1440, cave, this, gameLocs.getPlayerLoc()); 
        }

        //gui.updateActionText(gameLocs.getBatLoc(0) + "", new Color(255,255,255));
        //gui.updateActionText(gameLocs.getBatLoc(1) + "", new Color(255,255,255));
        //gui.updateActionText(gameLocs.getWumpusLoc() + "", new Color(255,255,255));
        //gui.updateActionText(gameLocs.getPitLoc(0) + "", new Color(255,255,255));
        //gui.updateActionText(gameLocs.getPitLoc(1) + "", new Color(255,255,255));
    }

    ///////////////////////////////////////////////
    // METHODS
    ///////////////////////////////////////////////

    public void move(int playerInput){
        gui.move(playerInput);
        String[] hazards = gameLocs.checkForHazards();
        for (String hazard : hazards){
            if (hazard.equals("Wumpus")){
                gui.updateActionText("The ship rocks with an ominous rumble...");
            } else if (hazard.equals("Pit")){
                gui.updateActionText("You feel a current pulling your ship...");
            } else if (hazards.equals("Bat")) {
                gui.updateActionText("BATMANNNNN...");
            }
        }
    }

    // 0 - 29 (inclusive) is a room number being moved to
    public void turn(int playerInput){
        //gui.updateActionText(Arrays.toString(cave.possibleMoves(playerInput)), new Color(255,255,255));
        System.out.println("I'm here8");
        if (gameLocs.setPlayerLoc(playerInput)){
            if (gameLocs.inNewRoom(playerInput)){
                player.addCoins(1);
            }
            player.addTurnsTaken();
            System.out.println("I'm here9");
            move(playerInput);
            System.out.println("I'm here10");

            hazards = gameLocs.getHazards();
            //gui.updateActionText(Arrays.toString(hazards), new Color(255,255,255));
            
            if (hazards.length == 0){
                endTurn();
                return;
            }

            if ((hazards.length >= 1) && (hazards[0].equals("Wumpus"))){
                if (hazards.length == 1){
                    hazards = null;
                } else {
                    hazards[0] = hazards[1];
                }
                questionType = 3;
                createQuestions();
                gui.openTriviaMenu("Wumpus Encounter", questions[0], 5);
                return;
            }

            continueTurn();
        }
    }

    public void createQuestions(){
        for (int i = 0; i < 5; i++)
            questions[i] = trivia.getQandAandK();
    }

    public void endTurn(){
        gameLocs.moveWumpus(player.getTurnsTaken());
        answers = new boolean[5];
        //gui.updateTurnCounter();
    }

    public void questionAnswer(String answer){
        answers[currentQuestion] = answer.equals(questions[currentQuestion][5]);
        currentQuestion++;
        if (currentQuestion == 5){
            gui.nextTriviaQuestion(answers[4], new String[] {"", "", "", "", ""}, true, 5);
            currentQuestion = 0;
            return;
        }
        gui.nextTriviaQuestion(answers[currentQuestion - 1], questions[currentQuestion], false, currentQuestion - 1);
    }

    public void continueTurn(){
        int numCorrect = 0;
        for (int i = 0; i < 5; i++){
            if (answers[i]){
                numCorrect++;
            }
        }
        triviaAction(numCorrect >= 3);
        

        if (hazards == null){
            endTurn();
            return;
        }
        if (hazards[0].equals("Pit")){
            hazards = null;
            questionType = 2;
            createQuestions();
            gui.openTriviaMenu("Pit Encounter", questions[0], 5);
            return;
        }
        if (hazards[0].equals("Bat")){
            hazards = null;
            questionType = 4;
            createQuestions();
            gui.openTriviaMenu("Bat Encounter", questions[0], 5);
            return;
        }
    }

    // 0 - 29 (inclusive) + true location receiving arrow
    // 0 + false - purchase arrow
    // 1 + false - purchase secret
    public void turn(int playerInput, boolean isShooting){
        if (isShooting){
            if (cave.canMove(gameLocs.getPlayerLoc(),playerInput)){
                player.addTurnsTaken();
                player.addArrows(-1);
                // gui.updateTurnCounter(player.getTurnsTaken());
                String[] hazards = gameLocs.getHazards(playerInput);
                boolean wumpusShot = false;
                boolean missed = false;
                if (hazards[0].equals("Wumpus")){
                    if (Math.random() < 0.5)
                        wumpusShot = true;
                    else {
                        missed = true;
                    }
                }
                if (wumpusShot){
                    gui.updateActionText("You Won!", new Color(0,255,0));
                    gameEnd();
                } else if (missed) {
                    gui.updateActionText("You Missed!", new Color(255,255,0));
                } else {
                    gui.updateActionText("Seems The Wumpus Wasn't There...!", new Color(255,255,0));
                }
            }
        } else {
            questionType = playerInput;
            createQuestions();
            hazards = null;
            gui.openTriviaMenu((playerInput == 1) "Purchase Secret" : "Purchase Arrow", questions[0], 5);
        }
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
            player.addCoins(-1);
            if (triviaSuccess){
                player.addArrows(1);
                gui.updateActionText("Arrow Gained", new Color(255,0,255));
            }
            // gui.updateTurnCounter(player.getTurnsTaken());
        } else if (questionType == 1){
            player.addCoins(-1);
            if (triviaSuccess){                
                // gui.displaySecret(writeSecret((int) (Math.random() * 10 + 1)));
            }
            // gui.updateTurnCounter(player.getTurnsTaken());
        } else if (questionType == 2){
            if (!triviaSuccess){
                gui.updateActionText("You died!", new Color(255, 0 , 0));
                gameEnd();
            } else {
                gui.updateActionText("You lived!", new Color(0, 255, 0));
            }
        } else if (questionType == 3){
            if (!triviaSuccess){
                gui.updateActionText("You died!", new Color(255, 0 , 0));
                gameEnd();
            } else {
                gui.updateActionText("The Wumpus is Wounded!", new Color(0, 255, 0));
            }
        } else if (questionType == 4){
            if (!triviaSuccess){
                int newRoom = gameLocs.batTransport();
                move(newRoom);
                gui.updateActionText("You Were Transported Into Room #" + newRoom + "!", new Color(255,255,0));
            } else {
                gui.updateActionText("You Escaped The Bats!", new Color(0, 255, 0));
            }
        }
        triviaSuccess = false;
    }

    public String writeSecret(int secretIndex){
        String secret = secrets[secretIndex];
        return secret;
    }

    public void gameEnd(){
        gui.updateActionText("Game Over", new Color(255,255,255));
        try {
            String[][] leaderboardInfo = scores.endOfGame();
        } catch (IOException io) {
            io.printStackTrace();
        }
        // gui.displayLeaderboard(leaderboardInfo);
        System.exit(0);
    }
}


/*
 * game control:
 * method 1
 * check hazards
 * wumpus
 * open trivia (new question)
 * gui:
 * method 2
 * mouseclicked on answer b
 * game control:
 * method 3
 * ok, here's the next question
 * 
 * loop until eventually:
 * ok, close the menu now
 * calls finish
 */