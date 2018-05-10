package squaregame.squares.laraza;

import squaregame.model.Direction;
import squaregame.model.MagicSquare;
import squaregame.model.Player;
import squaregame.model.SquareAction;
import squaregame.model.SquareView;
import squaregame.squares.SquareLogic;
import squaregame.utils.SquareLogicUtilities;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Greedy attacker.
 */
public class DefaultSquare extends SquareLogic {

    @Override
    public SquareAction run(SquareView squareView) {

        // Kill if possible
        final List<Direction> attackDirections = squareView.getEnemyDirections();
        if (!attackDirections.isEmpty()) {
            Direction direction = attackDirections.get(ThreadLocalRandom.current().nextInt(attackDirections.size()));
            return SquareAction.attack(direction, this);
        }

        // Replicate if not possible
        final List<Direction> replicateDirections = squareView.getEmptyDirections();
        if (!replicateDirections.isEmpty()) {
            Direction direction = replicateDirections.get(ThreadLocalRandom.current().nextInt(replicateDirections.size()));
            return SquareAction.replicate(direction, this, new DefaultSquare());
        }

        // Nothing to kill or replicate to, hold position
        return SquareAction.wait(this);
    }

    @Override
    public String getSquareName() {
        return "La Raza";
    }
}
