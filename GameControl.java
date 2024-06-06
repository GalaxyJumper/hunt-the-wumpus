// Joshua Lennon (cringe)
// Nick Lennon (less cringe)
// Aviral Mishra (the cool one)
// Toki
// 2/12/2024
// Game Control Object
// correct file!!!!!!!!!!!!!!
//taking in inputs for all classes and running the game
//connecting the ui to the game itself
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.awt.Color;
// TEMPORARY
import java.util.concurrent.TimeUnit;
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
    private String[][] questions = new String[5][7];
    private boolean[] answers = new boolean[5];
    private int currentQuestion = 0;
    private String[] hazards;
    
    // purchasing arrows - 0
    // purchasing secrets - 1
    // saving from pit - 2
    // escaping wumpus - 3
    // escaping bats - 4
    private int questionType = -1;
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
            gui = new Gui("HUNT THE WUMPUS - " + cave.getCaveName(), 2560, 1440, cave, this, gameLocs.getPlayerLoc()); 
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
    public String getHint(int questionNum){
        if (getCoins() >= 1){
            return questions[questionNum][6];
        }
        gui.updateActionText("No coins remaining", new Color(255,0,0));
        return null;
    }
    public void move(int playerInput){
        gui.move(playerInput);
        if (gameLocs.inNewRoom(playerInput))
                player.addCoins(1);
        String[] nearHazards = gameLocs.checkForHazards();
        for (String hazard : nearHazards){
            if (hazard.equals("Wumpus")){
                gui.updateActionText("The ship rocks with an ominous rumble...", new Color(155,73,186));
            } else if (hazard.equals("Pit")){
                gui.updateActionText("You notice the floor dropping away...", new Color(255,255,255));
            } else if (hazard.equals("Bat")) {
                gui.updateActionText("You feel a current pulling your ship...", new Color(255,255,255));
            }
        }
    }
    // 0 - 29 (inclusive) is a room number being moved to
    public void turn(int playerInput){
        //gui.updateActionText(Arrays.toString(cave.possibleMoves(playerInput)), new Color(255,255,255));
        if (gameLocs.setPlayerLoc(playerInput)){
            player.addTurnsTaken();
            move(playerInput);
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
                gui.openTriviaMenu("Wumpus confrontation", questions[0], 5);
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
        if (questionType != -1){
            int numCorrect = 0;
            for (int i = 0; i < 5; i++){
                if (answers[i]){
                    numCorrect++;
                }
            }
            triviaAction(numCorrect >= 3);
        }
        if (hazards == null){
            endTurn();
            return;
        }
        if (hazards[0].equals("Pit")){
            hazards = null;
            questionType = 2;
            createQuestions();
            gui.openTriviaMenu("Skirting the Abyss", questions[0], 5);
            return;
        }
        if (hazards[0].equals("Bat")){
            hazards = null;
            questionType = 4;
            createQuestions();
            gui.openTriviaMenu("Maintaining Steering Control", questions[0], 5);
            return;
        }

    }
    // 0 - 29 (inclusive) + true location receiving arrow
    // 0 + false - purchase arrow
    // 1 + false - purchase secret
    public void turn(int playerInput, boolean isShooting){
        System.out.println("I'm here1");
        if (isShooting){
            if (cave.canMove(gameLocs.getPlayerLoc(),playerInput) && player.getArrows() > 0){
                player.addTurnsTaken();
                player.addArrows(-1);
                // gui.updateTurnCounter(player.getTurnsTaken());
                String[] hazards = gameLocs.getHazards(playerInput);
                boolean wumpusShot = false;
                boolean missed = false;
                gui.updateActionText("Torpedo fired...", new Color(255,255,255));
                gui.updateActionText(player.getArrows() + " torpedoes left", new Color(255,255,255));
                if ((hazards.length > 0) && hazards[0].equals("Wumpus")){
                    if (Math.random() < 0.5)
                        wumpusShot = true;
                    else {
                        missed = true;
                    }
                }
                if (wumpusShot){
                    gui.updateActionText("Thou hath slain the Wumpus!", new Color(0,255,0));
                    gameEnd(true);
                } else if (missed) {
                    gui.updateActionText("You missed!", new Color(255,255,0));
                } else {
                    gui.updateActionText("Seems the Wumpus wasn't there...", new Color(255,255,0));
                }
            } else {
                if (player.getArrows() <= 0){
                    gui.updateActionText("No torpedoes remaining", new Color(255,0,0));
                } else {
                    gui.updateActionText("You can't get a good angle there...", new Color(255,255,255));
                }
            }
        } else {
            if (player.getCoins() >= 1){
                gui.updateActionText("One coin spent", new Color(255,255,255));
                questionType = playerInput;
                createQuestions();
                hazards = null;
                gui.openTriviaMenu((playerInput == 1)? "Purchase secret" : "Purchase torpedo", questions[0], 5);
            } else {
                gui.updateActionText("No coins left", new Color(255,0,0));
            }
        }
    }
    // response is "A", "B", "C", or "D"
    public void triviaAction(boolean triviaSuccess){
        if (questionType == 0){
            player.addCoins(-1);
            if (triviaSuccess){
                player.addArrows(1);
                gui.updateActionText("Torpedo gained", new Color(255,0,255));
            }
            // gui.updateTurnCounter(player.getTurnsTaken());
        } else if (questionType == 1){
            player.addCoins(-1);
            if (triviaSuccess){    
                gui.updateActionText("Purchase complete", new Color(0,255,0));
                gui.updateActionText(gameLocs.newSecret(), new Color(255,255,255));
            }
            // gui.updateTurnCounter(player.getTurnsTaken());
        } else if (questionType == 2){
            if (!triviaSuccess){
                gui.updateActionText("You fall away into the abyss...", new Color(255, 0 , 0));
                gameEnd(false);
            } else {
                gui.updateActionText("You successfully skirted the abyss!", new Color(0, 255, 0));
            }
        } else if (questionType == 3){
            if (!triviaSuccess){
                gui.updateActionText("You were killed by the Wumpus...", new Color(255, 0 , 0));
                gameEnd(false);
            } else {
                gui.updateActionText("The Wumpus is wounded!", new Color(0, 255, 0));
                gameLocs.fleeingWumpus(player.getTurnsTaken());
            }
        } else if (questionType == 4){
            if (!triviaSuccess){
                int newRoom = gameLocs.batTransport();
                move(newRoom);
                gui.updateActionText("The iron hull screams, and you lose control of the ship... You are now in room #" + (newRoom + 1) + "!", new Color(255,255,0));
            } else {
                gui.updateActionText("You fought the current and remained on course!", new Color(0, 255, 0));
            }
        }
        questionType = -1;
    }
    public int getCoins(){
        return player.getCoins();
    }
    public int getArrows(){
        return player.getArrows();
    }

    public int getTurn(){
        return player.getTurnsTaken();
    }

    public void gameEnd(boolean won){
        String[][] leaderboardInfo = scores.endOfGame();
        // closeTriviaMenu
        // display for win or lost
        // display leaderboard
        // call gameFullEnd
        //gui.gameEndSequence(won, leaderboardInfo);
        // TEMPORARY
        gui.updateActionText((won? "YOU WON!!!" : "You lost..."), new Color(won? 0 : 255,won? 255 : 0,0));
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception t){
            t.printStackTrace();
        }
    }
    public void gameFullEnd(){
        System.exit(0);
    }
}
