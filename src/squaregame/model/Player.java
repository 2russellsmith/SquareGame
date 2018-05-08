package squaregame.model;

import squaregame.squares.SquareLogic;

import java.awt.Color;

/**
 * Created by Russell on 5/5/18.
 */
public class Player {
    private Color color;
    private String name;
    private SquareLogic startingLogic;

    public Player (String name, Color color) {
        this.color = color;
        this.name = name;
    }

    public void setStartingLogic(SquareLogic startingLogic) {
        this.startingLogic = startingLogic;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public SquareLogic getStartingLogic() {
        return startingLogic;
    }
}
