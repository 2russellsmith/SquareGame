package squaregame.squares.diamondpickaxe;

import squaregame.model.Direction;
import squaregame.model.MagicSquare;
import squaregame.model.Player;
import squaregame.model.SquareAction;
import squaregame.model.SquareView;
import squaregame.squares.SquareLogic;
import squaregame.utils.SquareLogicUtilities;

import java.util.List;
import java.util.Optional;

public class DiamondPickaxe extends SquareLogic {

    private final boolean directionChange;
    private int generation;
    private double movesLeft;
    private Direction moveDirection;
    private int endSize = 32;

    public DiamondPickaxe(int generation, Direction moveDirection, boolean directionChange) {
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
                return SquareAction.wait(new Swing());
            }
            return SquareAction.replicate(getMovingDirection(),
                    new DiamondPickaxe(1, SquareLogicUtilities.getOppositeDirection(getMovingDirection()), true),
                    new DiamondPickaxe(1, getMovingDirection(), true));
        }
        if (squareView.getLocation(this.moveDirection) == null) {
            if (movesLeft > 0) {
                movesLeft--;
                return SquareAction.move(this.moveDirection, this);
            } else {
                return SquareAction.replicate(this.moveDirection,
                        new DiamondPickaxe(generation + 1, SquareLogicUtilities.getOppositeDirection(this.moveDirection), this.directionChange),
                        new DiamondPickaxe(generation + 1, this.moveDirection, this.directionChange));
            }
        } else {
            return SquareAction.wait(this);
        }
    }

    private Direction getMovingDirection() {
        return Direction.values()[DefaultSquare.startingDirection.ordinal() - (DefaultSquare.startingDirection.ordinal() % 2) + 1];
    }

    @Override
    public String getSquareName() {
        return null;
    }
}
