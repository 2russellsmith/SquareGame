package squaregame;

import squaregame.squares.SquareLogic;

/**
 * Created by Russell on 5/5/18.
 */
public class MagicSquare {
    SquareLogic squareLogic;
    Player player;
    public int inactive;

    public MagicSquare(Player player, SquareLogic squareLogic, int inactive) {
        this.player = player;
        this.squareLogic = squareLogic;
        this.inactive = inactive;
    }

    public MagicSquare(Player player, SquareLogic squareLogic) {
        this.player = player;
        this.squareLogic = squareLogic;
        this.inactive = 0;
    }

}
