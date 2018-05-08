package squaregame.squares.assassin;

import squaregame.model.Direction;
import squaregame.model.MagicSquare;
import squaregame.model.Player;
import squaregame.model.SquareAction;
import squaregame.squares.SquareLogic;
import squaregame.utils.SquareLogicUtilities;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DefaultSquare extends SquareLogic {

    private int aliveTurn = 0;

    private static final Set<Direction> VALID_DIRECTIONS = new HashSet<>(Arrays.asList(Direction.W, Direction.S, Direction.SW));

    @Override
    public SquareAction run(MagicSquare magicSquare, int row, int col, List<Player> view) {

        final Optional<Direction> direction =  SquareLogicUtilities.getEmptyDirections(view).stream().filter(VALID_DIRECTIONS::contains).findAny();

        return direction.map(d -> SquareAction.replicate(d, this, new AssassinSquare(Direction.values()[aliveTurn++ % 8])))
                .orElseGet(() -> SquareAction.wait(this));
    }

    @Override
    public String toString() {
        return "The Assassin Factory";
    }

}
