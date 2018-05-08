package squaregame.squares.player1;

import squaregame.model.Direction;
import squaregame.model.MagicSquare;
import squaregame.model.Player;
import squaregame.model.SquareAction;
import squaregame.squares.SquareLogic;

import java.util.List;

/**
 * Created by Russell on 5/5/18.
 */
public class DefaultSquare extends SquareLogic {

    private int alive = 0;

    @Override
    public SquareAction run(MagicSquare magicSquare, int row, int col, List<Player> view) {

        if (alive++ % 20 == 0) {
            return SquareAction.replicate(Direction.NE, this, new DefaultSquare());
        } else {
            return SquareAction.move(Direction.SE, this);
        }
    }

    @Override
    public String getSquareName() {
        return "Rep and Move";
    }
}
