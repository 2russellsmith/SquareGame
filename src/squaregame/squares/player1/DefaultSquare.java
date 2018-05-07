package squaregame.squares.player1;

import java.util.List;

import squaregame.GameBoard;
import squaregame.Player;
import squaregame.model.Action;
import squaregame.model.Direction;
import squaregame.squares.SquareAction;
import squaregame.squares.SquareLogic;

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
            return new SquareAction(Action.REPLICATE, Direction.NE, this, new DefaultSquare(this.player));
        } else {
            return new SquareAction(Action.MOVE, Direction.SE, this, new DefaultSquare(this.player));
        }
    }
}
