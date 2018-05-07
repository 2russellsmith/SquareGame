package squaregame;

import squaregame.squares.SquareLogic;

import java.awt.*;

/**
 * Created by Russell on 5/5/18.
 */
public class Player {
    Color color;
    int score;
    public String name;
    SquareLogic startingLogic;

    public Player (String name, Color color, SquareLogic startingSquare) {
        this.color = color;
        score = 1;
        this.name = name;
        this.startingLogic = startingSquare;
    }

    public void resetScore() {
        this.score = 0;
    }

    public int getScore() {
        return this.score;
    }

    public void addPoint() {
        this.score ++;
    }

    public void removePoint(){
        this.score--;
    }

    public String printScore() {
        return name + ": " + score;
    }
}
