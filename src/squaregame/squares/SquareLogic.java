package squaregame.squares;

import squaregame.Player;

import java.util.List;

/**
 * Created by Russell on 5/5/18.
 */
public abstract class SquareLogic {
    protected final Player player;
    public SquareLogic(Player player) {
        this.player = player;
    }
    public abstract SquareAction run(int row, int col, List<Player> view);
}
