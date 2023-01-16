package squaregame.squares.fungi;

import squaregame.model.Direction;

/**
 * DirectionUtils.
 */
public class DirectionUtils {

    public static final Direction opposite(Direction direction) {
        switch (direction) {
            case N:
                return Direction.S;
            case S:
                return Direction.N;
            case E:
                return Direction.W;
            case W:
                return Direction.E;
            case NE:
                return Direction.SW;
            case SW:
                return Direction.NE;
            case SE:
                return Direction.NW;
            case NW:
                return Direction.SE;
        }
        return Direction.S;
    }

    /**
     * Determine the direction to fork to.
     *
     * @param from         direction to fork from
     * @param existingFork have we already forked one way? else null
     * @return direction to fork
     */
    public static final Direction fork(Direction from, Direction existingFork) {
        switch (from) {
            case N:
                return existingFork != null ? Direction.SW : Direction.SE;
            case S:
                return existingFork != null ? Direction.NE : Direction.NW;
            case E:
                return existingFork != null ? Direction.NW : Direction.SW;
            case W:
                return existingFork != null ? Direction.SE : Direction.NE;
            case NE:
                return existingFork != null ? Direction.W : Direction.S;
            case SW:
                return existingFork != null ? Direction.E : Direction.N;
            case SE:
                return existingFork != null ? Direction.N : Direction.W;
            case NW:
                return existingFork != null ? Direction.S : Direction.E;
        }
        return Direction.S;
    }
}
