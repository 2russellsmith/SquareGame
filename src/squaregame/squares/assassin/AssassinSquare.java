package squaregame.squares.assassin;

import java.util.List;
import java.util.Optional;

import squaregame.Player;
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
        final Optional<Direction> direction = SquareLogicUtilities.getEnemyDirections(view, this.player).stream().findAny();
        if (direction.isPresent()) {
            lastKill = direction.get();
            return SquareAction.attack(direction.get(), this);
        } else {
            if (lastKill != null) {
                return SquareAction.move(lastKill, this);
            } else {
                if (view.get(search.ordinal()) == null) {
                    return SquareAction.move(search, this);
                } else {
                    final Optional<Direction> searchDirection = SquareLogicUtilities.getEmptyDirections(view).stream().findAny();
                    return searchDirection.map(d -> SquareAction.move(d, this))
                            .orElseGet(() -> SquareAction.wait(this));
                }
            }
        }
    }
}
