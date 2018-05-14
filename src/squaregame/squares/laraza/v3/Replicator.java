package squaregame.squares.laraza.v3;

import squaregame.model.Direction;
import squaregame.model.SquareAction;
import squaregame.model.SquareView;
import squaregame.squares.SquareLogic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Greedy attacker.
 */
public class Replicator extends SquareLogic {

    private static final List<Direction> VALID_DIRECTIONS = new ArrayList<>(Arrays.asList(Direction.NW, Direction.NE, Direction.SW, Direction.SE));

    private static int VALID_DIRECTIONS_SIZE = VALID_DIRECTIONS.size();

    private int turn;

    Replicator(int turn) {
        this.turn = turn;
    }

    @Override
    public SquareAction run(SquareView squareView) {

        // Kill if possible
        final List<Direction> attackDirections = squareView.getEnemyDirections();
        if (!attackDirections.isEmpty()) {
            Direction direction = attackDirections.get(ThreadLocalRandom.current().nextInt(attackDirections.size()));
            return SquareAction.attack(direction, this);
        }

        final List<Direction> moveOrReplicate = squareView.getEmptyDirections();

        if (moveOrReplicate.contains(Direction.S)) {
            Direction avianDirection = VALID_DIRECTIONS.get(ThreadLocalRandom.current().nextInt(VALID_DIRECTIONS_SIZE));
            return SquareAction.replicate(Direction.S, this, new Avian(avianDirection, this.turn +1));
        }

        // Hold position
        return SquareAction.wait(this);

    }

    @Override
    public String getSquareName() {
        return "Who writes Poo on someone else's desk";
    }
}
