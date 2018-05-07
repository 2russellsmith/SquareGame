package squaregame.squares.player2;

import squaregame.Action;
import squaregame.GameBoard;
import squaregame.Player;
import squaregame.squares.SquareAction;
import squaregame.squares.SquareLogic;

import java.util.List;

/**
 * Created by Russell on 5/5/18.
 */
public class DefaultSquare2 implements SquareLogic {

    @Override
    public SquareAction run(int row, int col, List<Player> view) {
        for (int i = 0; i < view.size(); i++) {
            if(view.get(i) == null) {
                return new SquareAction(Action.REPLICATE, GameBoard.Direction.values()[i], this, new DefaultSquare2());
            }
        }
        return new SquareAction(Action.WAIT, GameBoard.Direction.CENTER, this, new DefaultSquare2());
    }
}
