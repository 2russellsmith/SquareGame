package squaregame.squares.diamondpickaxe;

import squaregame.model.Direction;
import squaregame.model.SquareAction;
import squaregame.model.SquareView;
import squaregame.squares.SquareLogic;
import squaregame.utils.SquareLogicUtilities;

import java.util.Optional;

public class DefaultSquare extends SquareLogic {

    public static Direction startingDirection;

    @Override
    public SquareAction run(SquareView squareView) {
        startingDirection = Direction.NW;
        final Optional<Direction> direction = squareView.getEnemyDirections().stream().findAny();
        return direction.map(direction1 -> SquareAction.attack(direction1, this))
                .orElseGet(() -> SquareAction.replicate(startingDirection,
                        new DiamondPickaxe(1, SquareLogicUtilities.getOppositeDirection(startingDirection), false),
                        new DiamondPickaxe(1, startingDirection, false)));
    }

    @Override
    public String getSquareName() {
        return "Diamond Pickaxe";
    }
}
