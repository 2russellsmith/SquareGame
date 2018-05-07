package squaregame;

import squaregame.squares.SquareLogic;

import java.awt.*;

/**
 * Created by Russell on 5/5/18.
 */
public class Player {
    Color color;
    public String name;
    SquareLogic startingLogic;

    public Player (String name, Color color) {
        this.color = color;
        this.name = name;
    }

    public void setStartingLogic(SquareLogic startingLogic) {
        this.startingLogic = startingLogic;
    }
}
