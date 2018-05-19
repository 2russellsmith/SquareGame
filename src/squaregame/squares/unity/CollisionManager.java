package squaregame.squares.unity;

import squaregame.model.Direction;
import squaregame.model.SquareAction;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * CollisionManager.
 */
public class CollisionManager {

    private int boardSize;
    private int round;

    private Set<Location> attacks;
    private Set<Location> futureFriends;
    private Set<Location> futureEmpties;

    public CollisionManager(int boardSize) {
        this.boardSize = boardSize;
        round = 0;
        attacks = new HashSet<>();
        futureFriends = new HashSet<>();
        futureEmpties = new HashSet<>();
    }

    /**
     * Remove directions that point to a place that a friend is planning to occupy already.
     * @param directions
     * @return
     */
    public List<Direction> removeOccupied(List<Direction> directions, ApprovedView view) {
        return directions.stream()
                .map(direction -> new AbstractMap.SimpleEntry<>(targetOf(view.getRow(), view.getCol(), direction), direction))
                .filter(locationDirectionSimpleEntry -> !futureFriends.contains(locationDirectionSimpleEntry.getKey()))
                .map(locationDirectionSimpleEntry -> locationDirectionSimpleEntry.getValue())
                .collect(Collectors.toList());
    }

    /**
     * Get all 8 directions, filter out all but what are in the futureEmpties and add those to the original list.
     * @param original
     * @return
     */
    public List<Direction> addUnOccupied(List<Direction> original, ApprovedView view) {
        List<Direction> updated = Stream.of(Direction.values())
                .filter(direction -> direction != Direction.CENTER)
                .map(direction -> new AbstractMap.SimpleEntry<>(targetOf(view.getRow(), view.getCol(), direction), direction))
                .filter(locationDirectionSimpleEntry -> futureEmpties.contains(locationDirectionSimpleEntry.getKey()))
                .map(locationDirectionSimpleEntry -> locationDirectionSimpleEntry.getValue())
                .collect(Collectors.toList());

        updated.addAll(original);
        return updated;
    }

    /**
     * Add squares moved out of, and remove squares being moved into.
     * @return
     */
    public List<Direction> addRemoveOccupied(List<Direction> original, ApprovedView view) {
        return removeOccupied(addUnOccupied(original, view), view);
    }

    /**
     * remove previously attacked and moved into directions
     * @param enemyDirections
     * @return
     */
    public List<Direction> removeNonAttackable(List<Direction> enemyDirections, ApprovedView view) {
        return removeAttacked(removeOccupied(enemyDirections, view), view);
    }

    /**
     * remove dirctions that point at a location that is already being attacked.
     * @param directions
     * @return
     */
    private List<Direction> removeAttacked(List<Direction> directions, ApprovedView view) {
        return directions.stream()
                .map(direction -> new AbstractMap.SimpleEntry<>(targetOf(view.getRow(), view.getCol(), direction), direction))
                .filter(locationDirectionSimpleEntry -> !attacks.contains(locationDirectionSimpleEntry.getKey()))
                .map(locationDirectionSimpleEntry -> locationDirectionSimpleEntry.getValue())
                .collect(Collectors.toList());
    }

    public void update(SquareAction action, ApprovedView view) {
        handleNewRound(view.getPlayerAllowedMetadata().getRoundNumber());

        switch (action.getAction()) {
            case MOVE:
                futureEmpties.add(new Location(view.getRow(), view.getCol()));
            case REPLICATE:
                futureFriends.add(targetOf(view.getRow(), view.getCol(), action.getDirection()));
                break;
            case ATTACK:
                attacks.add(targetOf(view.getRow(), view.getCol(), action.getDirection()));
                break;
            case WAIT:
        }
    }

    /**
     * check for and update if, new round.
     * @param round
     */
    private void handleNewRound(int round) {
        if (this.round < round) {
            this.round = round;

            this.attacks = new HashSet<>();
            this.futureFriends = new HashSet<>();
            this.futureEmpties = new HashSet<>();
        }
    }

    /**
     * Y is from South to North
     * X is from West to East
     */
    private class Location {
        int x;
        int y;

        public Location(int x, int y) {
            this.x = Math.floorMod(x, boardSize);
            this.y = Math.floorMod(y, boardSize);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Location)) return false;
            Location location = (Location) o;
            return x == location.x &&
                    y == location.y;
        }

        @Override
        public int hashCode() {

            return Objects.hash(x, y);
        }
    }

    public Location targetOf(int row, int col, Direction dir) {

        return new Location(dir.getxOffset() + row ,dir.getyOffset() + col);
    }

}
