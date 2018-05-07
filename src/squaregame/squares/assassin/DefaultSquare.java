package squaregame.squares.assassin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import squaregame.Action;
import squaregame.GameBoard;
import squaregame.Player;
import squaregame.squares.SquareAction;
import squaregame.squares.SquareLogic;
import squaregame.squares.SquareLogicUtilities;

public class DefaultSquare extends SquareLogic {

    int aliveTurn = 0;

    public final Set<GameBoard.Direction> VALID_DIRECTIONS = new HashSet<>(Arrays.asList(GameBoard.Direction.W, GameBoard.Direction.S, GameBoard.Direction.SW));

    public DefaultSquare(Player player) {
        super(player);
    }

    @Override
    public SquareAction run(int row, int col, List<Player> view) {

        Optional<GameBoard.Direction> direction =  SquareLogicUtilities.getEmptyDirections(view).stream().filter(VALID_DIRECTIONS::contains).findAny();

        return direction.map(direction1 -> new SquareAction(Action.REPLICATE, direction1, this, new AssassinSquare(this.player, GameBoard.Direction.values()[aliveTurn++ % 8])))
                .orElseGet(() -> new SquareAction(Action.WAIT, GameBoard.Direction.CENTER, this, new DefaultSquare(this.player)));
    }

}
