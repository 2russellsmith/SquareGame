package squaregame.squares.assassin;

import squaregame.model.Direction;
import squaregame.model.Player;
import squaregame.squares.SquareAction;
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

    public DefaultSquare(Player player) {
        super(player);
    }

    @Override
    public SquareAction run(int row, int col, List<Player> view) {

        final Optional<Direction> direction =  SquareLogicUtilities.getEmptyDirections(view).stream().filter(VALID_DIRECTIONS::contains).findAny();

        return direction.map(d -> SquareAction.replicate(d, this, new AssassinSquare(this.player, Direction.values()[aliveTurn++ % 8])))
                .orElseGet(() -> SquareAction.wait(this));
    }

}
