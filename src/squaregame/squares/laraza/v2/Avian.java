package squaregame.squares.laraza.v2;

import squaregame.model.Direction;
import squaregame.model.SquareAction;
import squaregame.model.SquareView;
import squaregame.squares.SquareLogic;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Greedy attacker.
 */
public class Avian extends SquareLogic {

    private Direction moveDirection;
    private int turn;
    private static Map<Point, AtomicInteger> replicateMap = new HashMap<>();

    public Avian(Direction moveDirection, int turn) {
        this.moveDirection = moveDirection;
        this.turn = turn;
    }

    @Override
    public SquareAction run(SquareView squareView) {

        // Kill if possible
        final List<Direction> attackDirections = squareView.getEnemyDirections();
        if (!attackDirections.isEmpty()) {
            Direction direction = attackDirections.get(ThreadLocalRandom.current().nextInt(attackDirections.size()));
            return SquareAction.attack(direction, thisNextTurn());
        }

        int row = squareView.getRow();
        int col = squareView.getCol();
        
        final List<Direction> emptyDirections = squareView.getEmptyDirections();
        if (!emptyDirections.isEmpty()) {

            if (this.turn % (squareView.getPlayerAllowedMetadata().getBoardSize()/35) == 1) {
                Optional<Direction> toReplicate = emptyDirections.stream().filter(direction -> replicateHere(direction, row, col)).findFirst();
                if (toReplicate.isPresent()) {
                    Point point = new Point(row, col);
                    replicateMap.computeIfAbsent(point, a -> new AtomicInteger(ThreadLocalRandom.current().nextInt(2)));
                    if (replicateMap.get(point).incrementAndGet() % 2 == 1) {
                        return SquareAction.move(toReplicate.get(), new Replicator(this.turn + 1));
                    }
                    emptyDirections.remove(toReplicate.get());
                }
            }

            if (emptyDirections.contains(this.moveDirection)) {
                return SquareAction.move(this.moveDirection, thisNextTurn());
            }
            Direction direction = emptyDirections.get(ThreadLocalRandom.current().nextInt(emptyDirections.size()));
            if (emptyDirections.contains(direction)) {
                return SquareAction.move(direction, thisNextTurn());
            }

            if (emptyDirections.contains(direction.rotateClockwise(1))) {
                return SquareAction.move(direction.rotateClockwise(1), thisNextTurn());
            }

            if (emptyDirections.contains(direction.rotateClockwise(7))) {
                return SquareAction.move(direction.rotateClockwise(7), thisNextTurn());
            }

            if (emptyDirections.contains(direction.rotateClockwise(2))) {
                return SquareAction.move(direction.rotateClockwise(2), thisNextTurn());
            }

            if (emptyDirections.contains(direction.rotateClockwise(6))) {
                return SquareAction.move(direction.rotateClockwise(6), thisNextTurn());
            }

            if (emptyDirections.contains(direction.getOppositeDirection())) {
                return SquareAction.move(direction.getOppositeDirection(), thisNextTurn());
            }
        }

        // Nothing to kill or replicate to, hold position
        return SquareAction.wait(this);
    }

    private Avian thisNextTurn() {
        return new Avian(this.moveDirection, this.turn + 1);
    }

    @Override
    public String getSquareName() {
        return "H1N1";
    }

    private boolean replicateHere(Direction direction, int row, int col) {
        return Math.floorMod(row + direction.getxOffset(), 3) == 0 && Math.floorMod(col + direction.getxOffset(), 2) == 0;
    }
}
