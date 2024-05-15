/*
 * Keeps track of player inventory: Arrows and Gold Coins
 * Keeps track of how many turns the player has taken
 * Computes the ending score of player
 */

// Joshua Lennon
// Nick Lennon
// 2/12/2024
// Player Object

public class Player{
    
    ///////////////////////////////////////////////
    // VARIABLES
    ///////////////////////////////////////////////

    private String name;
    private int turnsTaken;
    private int coins;
    private int arrows;
    private int score;

    ///////////////////////////////////////////////
    // CONSTRUCTORS
    ///////////////////////////////////////////////

    public Player(){
        this.arrows = 3;
        this.coins = 0;
        this.turnsTaken = 0;
        this.score = 0;
    }

    ///////////////////////////////////////////////
    // METHODS
    ///////////////////////////////////////////////

    //This method will be called in gameControl to check the validity of a move
    //and also update the location of the player on the CSV
    public void move(){
        turnsTaken++;
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
        this.coins--;
        this.arrows++;
    }
 
    //We need our trivia class to be fully complete for this method to work
    //TBD
    public void purchaseSecret(){

    }

    //Currently the score of a player is determined by multiplying coins by 10
    //TBD
    public int calcScore(){
    this.score = this.coins * 10;
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

    public void setTurnsTaken(int turnsTaken) {
        this.turnsTaken = turnsTaken;
    }

    public String getName(){
        return name;
    }

}

