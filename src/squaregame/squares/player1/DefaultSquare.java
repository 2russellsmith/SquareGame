package squaregame.squares.player1;

import squaregame.model.Direction;
import squaregame.model.Player;
import squaregame.squares.SquareAction;
import squaregame.squares.SquareLogic;

import java.util.List;

/**
 * Created by Russell on 5/5/18.
 */
public class DefaultSquare extends SquareLogic {

    private int alive = 0;

    public DefaultSquare(Player player) {
        super(player);
    }

    @Override
    public SquareAction run(int row, int col, List<Player> view) {

        if (alive++ % 20 == 0) {
            return SquareAction.replicate(Direction.NE, this, new DefaultSquare(this.player));
        } else {
            return SquareAction.move(Direction.SE, this);
        }
    }
}
