// Joshua Lennon
// Nick Lennon
// Aviral Mishra
// 06/07/2024
// Game Control Object
// Mr. Reiber AP CSA Periods 5 & 6

// Necessary Imports
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.awt.Color;

public  class GameControl {
    ///////////////////////////////////////////////
    // VARIABLES
    ///////////////////////////////////////////////

    // all class interactions happen through here, so all classes (excluding subclasses) are made here
    private GameLocations gameLocs;
    private Gui gui;
    private Player player;
    private Trivia trivia;
    private HighScore scores;
    private Cave cave;
    private SoundManager sManager;

    private String[][] questions = new String[5][7]; // 5 questions, 7 fields (question, 4 answers, key, hint)
    private boolean[] answers = new boolean[5]; // whether answer is correct
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
        // initializations
        cave = new Cave();
        player = new Player();
        gameLocs = new GameLocations(cave);
        trivia = new Trivia();
        scores = new HighScore(player, cave.getCaveName());
        sManager = new SoundManager();

        // GUI must contain this object because the GUI repaint method is buggy when called from outside the class. Code is therefore factored so the GUI calls gameControl methods 
        if (!GraphicsEnvironment.isHeadless()){
            gui = new Gui("HUNT THE WUMPUS - " + cave.getCaveName(), 2560, 1440, cave, this, gameLocs.getPlayerLoc()); 
        }
    }
    ///////////////////////////////////////////////
    // METHODS
    ///////////////////////////////////////////////

    // 0 - 29 (inclusive) + true location receiving arrow
    // 0 + false - purchase arrow
    // 1 + false - purchase secret
    public void turn(int playerInput, boolean isShooting){
        if (isShooting){
            // valid move and has arrows check
            if (cave.canMove(gameLocs.getPlayerLoc(),playerInput) && player.getArrows() > 0){
                // arrow shot counts as a turn, regardless of success
                player.addTurnsTaken();
                player.addArrows(-1);
                
                // possible scenarios
                boolean wumpusShot = false;
                boolean missed = false; // correct room, unlucky

                gui.updateActionText("Torpedo fired...", new Color(255,255,255));
                gui.updateActionText(player.getArrows() + " torpedoes left", new Color(255,255,255));
                if (gameLocs.getWumpusLoc() == playerInput){
                    // 50% chance to hit the wumpus
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
                    // shot room without wumpus
                    gui.updateActionText("Seems the Wumpus wasn't there...", new Color(255,255,0));
                }
            } else {
                if (player.getArrows() <= 0){
                    gui.updateActionText("No torpedoes remaining", new Color(255,0,0));
                } else {
                    // inaccessible room shot at
                    gui.updateActionText("You can't get a good angle there...", new Color(255,255,255));
                }
            }
        } else {
            if (player.getCoins() >= 1){
                // purchasing counts as a turn, regardless of success
                player.addTurnsTaken();

                gui.updateActionText("One coin spent", new Color(255,255,255));
                player.addCoins(-1);

                // info necessary for later triviaAction
                questionType = playerInput;

                createQuestions();
                hazards = null;
                gui.openTriviaMenu((playerInput == 1)? "Purchase secret" : "Purchase torpedo", questions[0], 5);
            } else {
                gui.updateActionText("No coins left", new Color(255,0,0));
            }
        }
        endTurn();
    }

    // 0 - 29 (inclusive) is a room number being moved to
    public void turn(int playerInput){
        // valid room move check
        if (gameLocs.setPlayerLoc(playerInput)){
            // moving counts as a turn
            player.addTurnsTaken();

            // necessary moving actions
            move(playerInput);

            // hazards of entered room
            hazards = gameLocs.getHazards();

            if (hazards.length == 0){
                // turn ending actions
                endTurn();
                return;
            }

            if (hazards[0].equals("Wumpus")){
                if (hazards.length == 1){
                    // "use up" hazards
                    hazards = null;
                } else {
                    // always refer to hazards[0]
                    hazards[0] = hazards[1];
                }

                // info necessary for later triviaAction
                questionType = 3;

                createQuestions();
                gui.openTriviaMenu("Wumpus confrontation", questions[0], 5);
                return;
            }
            continueTurn();
        }
    }

    // necessary moving actions
    public void move(int playerInput){
        // move on-screen
        gui.move(playerInput);

        // unique rooms earn coins
        if (gameLocs.inNewRoom(playerInput))
                player.addCoins(1);
        
        // hazard warnings
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

    public void continueTurn(){
        // do relevant actions for last trivia question
        if (questionType != -1){
            int numCorrect = 0;
            for (int i = 0; i < 5; i++){
                if (answers[i]){
                    numCorrect++;
                }
            }
            triviaAction(numCorrect >= 3);
        }

        // continue to end of turn if no hazards remaining
        if (hazards == null){
            endTurn();
            return;
        }

        // remaining hazard actions
        if (hazards[0].equals("Pit")){
            hazards = null;
            questionType = 2;
            createQuestions();
            gui.openTriviaMenu("Skirting the Abyss", questions[0], 5);
            return;
        } else if (hazards[0].equals("Bat")){
            hazards = null;
            questionType = 4;
            createQuestions();
            gui.openTriviaMenu("Maintaining Steering Control", questions[0], 5);
            return;
        }

    }

    public void endTurn(){
        // wumpus may move on certain turns
        gameLocs.moveWumpus(player.getTurnsTaken());

        // reset answers to false
        answers = new boolean[5];
    }

    // retrieve next questions from trivia class
    public void createQuestions(){
        for (int i = 0; i < 5; i++)
            questions[i] = trivia.getQandAandK();
    }

    // ask questions after the last question is answered
    public void questionAnswer(String answer){
        // check if answer is correct
        answers[currentQuestion] = answer.equals(questions[currentQuestion][5]);
        currentQuestion++;

        // fence-post for last question
        if (currentQuestion == 5){
            gui.nextTriviaQuestion(answers[4], new String[] {"", "", "", "", ""}, true, 5);
            currentQuestion = 0;
            return;
        }

        // info necessary for animations
        gui.nextTriviaQuestion(answers[currentQuestion - 1], questions[currentQuestion], false, currentQuestion - 1);
    }

    // get a hint for the current question for one coin
    public String getHint(int questionNum){
        if (getCoins() >= 1){
            player.addCoins(-1);
            return questions[questionNum][6];
        }

        gui.updateActionText("No coins remaining", new Color(255,0,0));
        return "";
    }
    
    // take an action based on success and purpose of trivia
    // refer to questionType table further up
    public void triviaAction(boolean triviaSuccess){
        if (questionType == 0){
            if (triviaSuccess){
                player.addArrows(1);
                gui.updateActionText("Torpedo gained", new Color(255,0,255));
            }
        } else if (questionType == 1){
            if (triviaSuccess){    
                gui.updateActionText("Purchase complete", new Color(0,255,0));
                gui.updateActionText(gameLocs.newSecret(), new Color(255,255,255));
            }
        } else if (questionType == 2){
            if (!triviaSuccess){
                gui.updateActionText("You sink away into the abyss...", new Color(255, 0 , 0));
                gameEnd(false);
            } else {
                gui.updateActionText("You successfully skirted the abyss!", new Color(0, 255, 0));
            }
        } else if (questionType == 3){
            if (!triviaSuccess){
                gui.updateActionText("Your ship is torn asunder by the Wumpus...", new Color(255, 0 , 0));
                gameEnd(false);
            } else {
                gui.updateActionText("The Wumpus is wounded!", new Color(0, 255, 0));
                gameLocs.fleeingWumpus(player.getTurnsTaken());
            }
        } else if (questionType == 4){
            if (!triviaSuccess){
                // transportation
                int newRoom = gameLocs.batTransport();
                move(newRoom);
                gui.updateActionText("The iron hull screams and you lose control of the ship... You are now in room #" + (newRoom + 1) + "!", new Color(255,255,0));
            } else {
                gui.updateActionText("You fought the current and remained on course!", new Color(0, 255, 0));
            }
        }
        questionType = -1;
    }

    // gui methods for side menu through player class
    public int getCoins(){
        return player.getCoins();
    }

    public int getArrows(){
        return player.getArrows();
    }

    public int getTurn(){
        return player.getTurnsTaken();
    }

    // leaderboard display sequence
    public void gameEnd(boolean won){
        String[][] leaderboardInfo = scores.endOfGame();
        // String playerID;
        // closeTriviaMenu
        // display for win or lost
        // display leaderboard
        // call gameFullEnd
        // gui.gameEndSequence(won, leaderboardInfo);
        gui.updateActionText((won? "YOU WON!!!" : "You lost..."), new Color(won? 0 : 255,won? 255 : 0,0));
        gui.updateActionText("Close to continue", new Color(255,255,255));
    }
}
