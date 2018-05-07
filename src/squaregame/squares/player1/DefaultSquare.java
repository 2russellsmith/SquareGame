package squaregame.squares.player1;

import squaregame.Action;
import squaregame.GameBoard;
import squaregame.Player;
import squaregame.squares.SquareAction;
import squaregame.squares.SquareLogic;

import java.util.List;

/**
 * Created by Russell on 5/5/18.
 */
public class DefaultSquare implements SquareLogic {

    private int alive = 0;

    @Override
    public SquareAction run(int row, int col, List<Player> view) {

        if (alive++ % 2 != 0) {
            return new SquareAction(Action.REPLICATE, GameBoard.Direction.NE, this, new DefaultSquare());
        } else {
            return new SquareAction(Action.REPLICATE, GameBoard.Direction.E, this, new DefaultSquare());
        }
    }
}
