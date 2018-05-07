package squaregame.squares.assassin;

import java.util.List;
import java.util.Optional;

import squaregame.GameBoard;
import squaregame.Player;
import squaregame.model.Action;
import squaregame.model.Direction;
import squaregame.squares.SquareAction;
import squaregame.squares.SquareLogic;
import squaregame.squares.SquareLogicUtilities;

public class AssassinSquare extends SquareLogic {
    private Direction lastKill;
    private Direction search;

    public AssassinSquare(Player player, Direction search) {
        super(player);
        this.search = search;
    }

    @Override
    public SquareAction run(int row, int col, List<Player> view) {
        Optional<Direction> direction = SquareLogicUtilities.getEnemyDirections(view, this.player).stream().findAny();
        if (direction.isPresent()) {
            lastKill = direction.get();
            return new SquareAction(Action.ATTACK, direction.get(), this, new DefaultSquare(this.player));
        } else {
            if (lastKill != null) {
                return new SquareAction(Action.MOVE, lastKill, this, new DefaultSquare(this.player));
            } else {
                if (view.get(search.ordinal()) == null) {
                    return new SquareAction(Action.MOVE, search, this, new DefaultSquare(this.player));
                } else {
                    Optional<Direction> searchDirection = SquareLogicUtilities.getEmptyDirections(view).stream().findAny();
                    return searchDirection.map(direction1 -> new SquareAction(Action.MOVE, direction1, this, new DefaultSquare(this.player)))
                            .orElseGet(() -> new SquareAction(Action.WAIT, Direction.E, this, new DefaultSquare(this.player)));
                }
            }
        }
    }
}
