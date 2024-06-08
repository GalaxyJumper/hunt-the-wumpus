// Player Object
// Kavya Tayal
// Nick Lennon
// Josh Lennon
// 06/07/2024
// Mr. Reiber AP CSA Periods 5 & 6

public class Player{

    ///////////////////////////////////////////////
    // VARIABLES
    ///////////////////////////////////////////////

    private String name;
    private int turnsTaken;
    private int coins;
    private int arrows;
    private int score;
    private String cave;

    ///////////////////////////////////////////////
    // CONSTRUCTORS
    ///////////////////////////////////////////////

    public Player(){
        this.name = makeName();
        this.arrows = 0;
        this.coins = 0;
        this.turnsTaken = 0;
        this.score = 0;
    }


    //ignore this constructor, used for highscore only!
    public Player(String[] pLine){
        name = pLine[0];
        score = Integer.parseInt(pLine[1]); 
        turnsTaken = Integer.parseInt(pLine[2]);
        coins = Integer.parseInt(pLine[3]);
        arrows = Integer.parseInt(pLine[4]);
        cave = pLine[5];

    }

    ///////////////////////////////////////////////
    // METHODS
    ///////////////////////////////////////////////

    // generates a random player tag "player" + 5 nums or letters
    public String makeName(){
        String name = "player";
        for(int i = 0; i < 5; i++){
            if(Math.random() > 0.5) name += (char) ((int) (Math.random()*26 + 'A'));
            else name += "" + (int) (Math.random()*10);
        }
        return name;
    }

    //This method will be called in gameControl to check the validity of a move
    //and also update the location of the player on the CSV
    public void move(){
        coins++;
    }

    //yay calculates score
    public int calcScore(boolean wumpusKilled){
        this.score = 100 - turnsTaken + coins + (5 * arrows) + ((wumpusKilled)? 50 : 0);
        return this.score;
    }

    ////////////////////////////////////
    //GETTERS AND SETTERS
    ////////////////////////////////////
    public int getArrows() {
        return arrows;
    }

    public void addArrows(int arrows){
        this.arrows += arrows;
    }

    public int getCoins() {
        return coins;
    }

    public void addCoins(int coins){
        this.coins += coins;
    }

    public int getTurnsTaken() {
        return turnsTaken;
    }

    public void addTurnsTaken() {
        this.turnsTaken++;
    }

    public String getName(){
        return name;
    }

    public int getScore(){
        return score;
    }

    public String toString() {
    return name + "," + score + "," + turnsTaken + "," + coins + "," + arrows + "," + cave;
    }
}

