package squaregame.model;

import squaregame.squares.SquareLogic;

/**
 * Created by Russell on 5/5/18.
 */
public class MagicSquare {
    private SquareLogic squareLogic;
    private Player player;
    private int inactive;

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

    public SquareLogic getSquareLogic() {
        return squareLogic;
    }

    public Player getPlayer() {
        return player;
    }

    public int getInactive() {
        return inactive;
    }
}
