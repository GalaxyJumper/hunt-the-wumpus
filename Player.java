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
    private boolean wumpusKilled;

    ///////////////////////////////////////////////
    // CONSTRUCTORS
    ///////////////////////////////////////////////

    public Player(){
        this.name = makeName();
        this.arrows = 3;
        this.coins = 0;
        this.turnsTaken = 0;
        this.score = 0;
        this.wumpusKilled = false;
    }


    //ignore this constructor, used for highscore only!
    public Player(String[] pLine){
        name = pLine[0];
        score = Integer.parseInt(pLine[1]); 
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

    //This method will be called in gameControl to determine the validity of a shot
    //And check if the wumpus is in the adjacent room
    //If the wumpus is in the other room, gameControl will then call another method relating to a hit
    public void shootArrow(){
        if (this.arrows > 0){
            this.arrows--;
        } else {
            System.out.println("My quiver is empty!");
        }

    }

    //This method will be called in gameControl through an input in store.
    //The effect is just exchanging a coin for an arrow
    //We need more complex stuff in the GUI
    public void purchaseArrow(){
        if(this.coins > 0){
            this.coins--;
            this.arrows++;
        } else {
            System.out.println("You don't have enough coins!");
        }
        
    }
 
    //We need our trivia class to be fully complete for this method to work
    //TBD
    public void purchaseSecret(){
        coins--;
    }

    //yay calculates score
    public int calcScore(boolean wumpusKilled){
        this.score = 100 - turnsTaken + coins + (5 * arrows) + ((wumpusKilled)? 50 : 0);
        return this.score;
    }

////////////////////////////////////
//GETTERS AND SETTERS
////////////////////////////////////

    public Player getPlayer(){
        return this;
    }

    public int getArrows() {
        return arrows;
    }

    public void addArrows(int arrows){
        this.arrows += arrows;
    }

    public void setArrows(int arrows) {
        this.arrows = arrows;
    }

    public int getCoins() {
        return coins;
    }

    public void addCoins(int coins){
        this.coins += coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
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

    public String getCave(){
        return cave;
    }

    public void setCave(String cave){
        this.cave = cave;
    }

    public boolean getWumpusKilled(){
        return this.wumpusKilled;
    }

    public void setWumpusKilled(boolean b){
        this.wumpusKilled = b;
    }

}

