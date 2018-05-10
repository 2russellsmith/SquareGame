package squaregame.squares.assassin;

import squaregame.model.Direction;
import squaregame.model.MagicSquare;
import squaregame.model.Player;
import squaregame.model.SquareAction;
import squaregame.model.SquareView;
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
    public SquareAction run(SquareView squareView) {
        final Optional<Direction> direction = squareView.getEnemyDirections().stream().findAny();
        if (direction.isPresent()) {
            lastKill = direction.get();
            return SquareAction.attack(direction.get(), this);
        } else {
            if (lastKill != null) {
                return SquareAction.move(lastKill, this);
            } else {
                if (squareView.getLocation(search) == null) {
                    return SquareAction.move(search, this);
                } else {
                    final Optional<Direction> searchDirection = squareView.getEmptyDirections().stream().findAny();
                    return searchDirection.map(d -> SquareAction.move(d, this)).orElseGet(() -> SquareAction.wait(this));
                }
            }
        }
    }

    @Override
    public String getSquareName() {
        return "Assassin";
    }
}
