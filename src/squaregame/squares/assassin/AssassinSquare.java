package squaregame.squares.assassin;

import squaregame.model.Direction;
import squaregame.model.MagicSquare;
import squaregame.model.Player;
import squaregame.model.SquareAction;
import squaregame.squares.SquareLogic;
import squaregame.utils.SquareLogicUtilities;

import java.util.List;
import java.util.Optional;

public class AssassinSquare extends SquareLogic {
    private Direction lastKill;
    private Direction search;

    public AssassinSquare(Direction search) {
        this.search = search;
    }

    @Override
    public SquareAction run(MagicSquare magicSquare, int row, int col, List<Player> view) {
        final Optional<Direction> direction = SquareLogicUtilities.getEnemyDirections(view, magicSquare.getPlayer()).stream().findAny();
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

    @Override
    public String getSquareName() {
        return "Assassin";
    }
}
