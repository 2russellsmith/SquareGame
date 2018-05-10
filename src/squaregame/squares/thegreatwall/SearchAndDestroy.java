package squaregame.squares.thegreatwall;

import squaregame.model.Direction;
import squaregame.model.SquareAction;
import squaregame.model.SquareView;
import squaregame.squares.SquareLogic;

import java.util.Optional;

public class SearchAndDestroy extends SquareLogic {

    private int turn = 0;

    @Override
    public SquareAction run(SquareView squareView) {
        final Optional<Direction> direction = squareView.getEnemyDirections().stream().findAny();
        if (direction.isPresent()) {
            return attack(direction.get());
        }
        final Direction moveDirection = DefaultSquare.startingDirection.rotateClockwise(Math.floorMod(turn++ / 150, 8) + 1);
        if (squareView.getLocation(moveDirection) == null) {
            return move(moveDirection);
        } else {
            return Wait();
        }
    }

    @Override
    public String getSquareName() {
        return null;
    }
}
