/*
 * Keeps track of player inventory: Arrows and Gold Coins
 * Keeps track of how many truns the player has taken
 * Computes  the ending score of player
 */

// Joshua Lennon
// Nick Lennon
// 2/12/2024
// Player Object

//Make player a static class (singleton class)
//Static variables have space in memory set aside
//So all classes are referencing the same object 
//and chainging it without needing to communicate with each other
//I made a static player object in player

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
    }

    ///////////////////////////////////////////////
    // METHODS
    ///////////////////////////////////////////////

    public Player getPlayer(){
        return this;
    }


    public void move(){
        turnsTaken++;
    }

    public void shootArrow(){
        this.arrows--;
    }

    public void purchaseArrow(){
        this.coins--;
        this.arrows++;
    }
 
    public void purchaseSecret(){

    }

    public int calcScore(){
        return 0;
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

