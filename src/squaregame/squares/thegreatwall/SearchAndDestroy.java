package squaregame.squares.thegreatwall;

import squaregame.model.Direction;
import squaregame.model.MagicSquare;
import squaregame.model.Player;
import squaregame.model.SquareAction;
import squaregame.squares.SquareLogic;
import squaregame.utils.SquareLogicUtilities;

import java.util.List;
import java.util.Optional;

public class SearchAndDestroy extends SquareLogic {

    private int turn = 0;

    @Override
    public SquareAction run(MagicSquare magicSquare, int row, int col, List<Player> view) {
        final Optional<Direction> direction = SquareLogicUtilities.getEnemyDirections(view, magicSquare.getPlayer()).stream().findAny();
        if (direction.isPresent()) {
            return SquareAction.attack(direction.get(), this);
        }
        final Direction moveDirection = SquareLogicUtilities.getRotatedDirection(DefaultSquare.startingDirection,
                Math.floorMod(turn++ / 150, 8) + 1);
        if (view.get(moveDirection.ordinal()) == null) {
            return SquareAction.move(moveDirection, this);
        } else {
            return SquareAction.wait(this);
        }
    }

    @Override
    public String getSquareName() {
        return null;
    }
}
