package squaregame.squares.player2;

import squaregame.model.Direction;
import squaregame.model.Player;
import squaregame.squares.SquareAction;
import squaregame.squares.SquareLogic;
import squaregame.utils.SquareLogicUtilities;

import java.util.List;

/**
 * Created by Russell on 5/5/18.
 */
public class DefaultSquare2 extends SquareLogic {

    public DefaultSquare2(Player player) {
        super(player);
    }

    @Override
    public SquareAction run(int row, int col, List<Player> view) {
        final List<Direction> directions = SquareLogicUtilities.getEmptyDirections(view);
        if (directions.isEmpty()) {
            return SquareAction.wait(this);
        }
        return SquareAction.replicate(directions.get(0), this, new DefaultSquare2(this.player));
    }
}
