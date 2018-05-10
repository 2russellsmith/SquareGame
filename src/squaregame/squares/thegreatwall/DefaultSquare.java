package squaregame.squares.thegreatwall;

import squaregame.model.Direction;
import squaregame.model.MagicSquare;
import squaregame.model.Player;
import squaregame.model.SquareAction;
import squaregame.squares.SquareLogic;
import squaregame.utils.SquareLogicUtilities;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class DefaultSquare extends SquareLogic {

    public static Direction startingDirection;

    @Override
    public SquareAction run(MagicSquare magicSquare, int row, int col, List<Player> view) {
        final Random random = new Random();
        startingDirection = Direction.values()[random.nextInt(8)];
        final Optional<Direction> direction = SquareLogicUtilities.getEnemyDirections(view, magicSquare.getPlayer()).stream().findAny();
        return direction.map(direction1 -> SquareAction.attack(direction1, this))
                .orElseGet(() -> SquareAction.replicate(startingDirection,
                        new WallBuilder(1, SquareLogicUtilities.getOppositeDirection(startingDirection), false),
                        new WallBuilder(1, startingDirection, false)));
    }

    @Override
    public String getSquareName() {
        return "The Great Wall";
    }
}
