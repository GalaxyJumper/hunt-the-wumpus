/*
 * Keeps track of player inventory: Arrows and Gold Coins
 * Keeps track of how many truns the player has taken
 * Computes  the ending score of player
 */

// Joshua Lennon
// Nick Lennon
// 2/12/2024
// Player Object

public class Player{
    ///////////////////////////////////////////////
    // VARIABLES
    ///////////////////////////////////////////////

    private int arrows;
    private int coins;

    private int turnsTaken;

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

}

