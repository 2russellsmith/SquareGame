package squaregame.model;

import lombok.Getter;
import squaregame.squares.SquareLogic;

/**
 * Created by Russell on 5/5/18.
 */
@Getter
public class MagicSquare {
    private SquareLogic squareLogic;
    private Player player;
    private int inactive;
    private Location location;

    public MagicSquare(Player player, SquareLogic squareLogic, Location location, int inactive) {
        this.player = player;
        this.squareLogic = squareLogic;
        this.location = location;
        this.inactive = inactive;
    }

    public MagicSquare(Player player, SquareLogic squareLogic, Location location) {
        this.player = player;
        this.squareLogic = squareLogic;
        this.location = location;
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
