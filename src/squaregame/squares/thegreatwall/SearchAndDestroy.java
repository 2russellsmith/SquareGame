package squaregame.squares.thegreatwall;

import squaregame.model.Direction;
import squaregame.model.SquareAction;
import squaregame.model.SquareView;
import squaregame.squares.SquareLogic;
import squaregame.utils.SquareLogicUtilities;

import java.util.Optional;

public class SearchAndDestroy extends SquareLogic {

    private int turn = 0;

    @Override
    public SquareAction run(SquareView squareView) {
        final Optional<Direction> direction = squareView.getEnemyDirections().stream().findAny();
        if (direction.isPresent()) {
            return SquareAction.attack(direction.get(), this);
        }
        final Direction moveDirection = SquareLogicUtilities.getRotatedDirection(DefaultSquare.startingDirection,
                Math.floorMod(turn++ / 150, 8) + 1);
        if (squareView.getLocation(moveDirection) == null) {
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
