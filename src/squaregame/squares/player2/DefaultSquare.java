package squaregame.squares.player2;

import squaregame.model.Direction;
import squaregame.model.MagicSquare;
import squaregame.model.Player;
import squaregame.model.SquareAction;
import squaregame.squares.SquareLogic;
import squaregame.utils.SquareLogicUtilities;

import java.util.List;

/**
 * Created by Russell on 5/5/18.
 */
public class DefaultSquare extends SquareLogic {

    @Override
    public SquareAction run(MagicSquare magicSquare, int row, int col, List<Player> view) {
        final List<Direction> directions = SquareLogicUtilities.getEmptyDirections(view);
        if (directions.isEmpty()) {
            return SquareAction.wait(this);
        }
        return SquareAction.replicate(directions.get(0), this, new DefaultSquare());
    }

    @Override
    public String getSquareName() {
        return "Make like bunnies";
    }
}
