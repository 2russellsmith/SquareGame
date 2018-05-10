package squaregame.squares.thegreatwall;

import squaregame.model.Direction;
import squaregame.model.SquareAction;
import squaregame.model.SquareView;
import squaregame.squares.SquareLogic;

import java.util.Optional;

public class WallBuilder extends SquareLogic {

    private final boolean directionChange;
    private int generation;
    private double movesLeft;
    private Direction moveDirection;
    private int endSize = 128;

    public WallBuilder(int generation, Direction moveDirection, boolean directionChange) {
        this.generation = generation;
        this.movesLeft = endSize / (Math.pow(2, generation + 1)) - 1;
        this.moveDirection = moveDirection;
        this.directionChange = directionChange;
    }

    @Override
    public SquareAction run(SquareView squareView) {
        final Optional<Direction> direction = squareView.getEnemyDirections().stream().findAny();
        if (direction.isPresent()) {
            return SquareAction.attack(direction.get(), this);
        }
        if (endSize / (Math.pow(2, generation + 1)) - 1 < 0) {
            if (this.directionChange) {
                return SquareAction.wait(new SearchAndDestroy());
            }
            return SquareAction.replicate(getMovingDirection(),
                    new WallBuilder(1, getMovingDirection().getOppositeDirection(), true),
                    new WallBuilder(1, getMovingDirection(), true));
        }
        if (squareView.getLocation(this.moveDirection) == null) {
            if (movesLeft > 0) {
                movesLeft--;
                return SquareAction.move(this.moveDirection, this);
            } else {
                return SquareAction.replicate(this.moveDirection,
                        new WallBuilder(generation + 1, this.moveDirection.getOppositeDirection(), this.directionChange),
                        new WallBuilder(generation + 1, this.moveDirection, this.directionChange));
            }
        } else {
            return Wait();
        }
    }

    private Direction getMovingDirection() {
        return DefaultSquare.startingDirection.rotateClockwise(2);
    }

    @Override
    public String getSquareName() {
        return null;
    }
}
