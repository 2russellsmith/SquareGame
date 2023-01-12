package squaregame.squares.test;

import squaregame.model.Direction;
import squaregame.model.SquareAction;
import squaregame.model.SquareView;
import squaregame.squares.SquareLogic;

import java.util.*;
import java.util.stream.Collectors;

import static squaregame.squares.test.DefaultSquare.blood_in_the_water;

public class Sharknado extends SquareLogic {

    public Direction nextDirection;

    @Override
    public SquareAction run(SquareView squareView) {
        final Optional<Direction> direction = squareView.getEnemyDirections().stream().findAny();
        if (direction.isPresent()) {
            foundBlood(squareView.getRow(), squareView.getCol(), direction.get());
            nextDirection = direction.get();
            return SquareAction.attack(direction.get(), this);
        } else if (nextDirection != null) {
            Direction compellingDirection = nextDirection;
            nextDirection = null;
            return SquareAction.move(compellingDirection, this);
        } else {
            noBlood(squareView.getRow(), squareView.getCol());
            Direction compellingDirection = mostCompellingDirection(squareView);
            if (squareView.getEmptyDirections().contains(compellingDirection)) {
                return SquareAction.move(compellingDirection, this);
            } else {
                final List<Direction> emptyDirections = squareView.getEmptyDirections();
                Collections.shuffle(emptyDirections);
                final Optional<Direction> searchDirection = emptyDirections.stream().findAny();
                return searchDirection.map(d -> SquareAction.move(d, this)).orElseGet(() -> SquareAction.wait(this));
            }
        }
    }

    private Direction mostCompellingDirection(SquareView squareView) {
        final List<Direction> emptyDirections = squareView.getEmptyDirections();
        Collections.shuffle(emptyDirections);
        Optional<Direction> bloodiestDirection = emptyDirections.stream()
                .max(Comparator.comparing(d -> sniffForBlood(squareView.getRow(), squareView.getCol(), d)));
        Optional<Direction> leastBloodDirection = emptyDirections.stream()
                .min(Comparator.comparing(d -> sniffForBlood(squareView.getRow(), squareView.getCol(), d)));
        if (Math.abs(sniffForBlood(squareView.getRow(), squareView.getCol(), bloodiestDirection.get())) >=
                Math.abs(sniffForBlood(squareView.getRow(), squareView.getCol(), leastBloodDirection.get()))) {
            return bloodiestDirection.get();
        } else {
            return leastBloodDirection.get().getOppositeDirection();
        }
    }

    private void noBlood(int row, int col) {
        blood_in_the_water[row][col] -= 2;
        Arrays.stream(Direction.values()).forEach(d -> noBloodSplatter(row, col, d));
    }
    private void noBloodSplatter(int r, int c, Direction direction) {
        int row = Math.floorMod((direction.getxOffset() + r), blood_in_the_water.length);
        int col = Math.floorMod((direction.getyOffset() + c), blood_in_the_water.length);
        blood_in_the_water[row][col] -= 1;
    }

    private int sniffForBlood(int r, int c, Direction direction) {
        int row = Math.floorMod((direction.getxOffset() + r), blood_in_the_water.length);
        int col = Math.floorMod((direction.getyOffset() + c), blood_in_the_water.length);
        return blood_in_the_water[row][col];
    }

    private void foundBlood(int r, int c, Direction direction) {
        int row = Math.floorMod((direction.getxOffset() + r), blood_in_the_water.length);
        int col = Math.floorMod((direction.getyOffset() + c), blood_in_the_water.length);
        blood_in_the_water[row][col] += 2;
        Arrays.stream(Direction.values()).forEach(d -> bloodSplatter(row, col, d));
    }
    private void bloodSplatter(int r, int c, Direction direction) {
        int row = Math.floorMod((direction.getxOffset() + r), blood_in_the_water.length);
        int col = Math.floorMod((direction.getyOffset() + c), blood_in_the_water.length);
        blood_in_the_water[row][col] += 1;
    }

    @Override
    public String getSquareName() {
        return "Sharknado";
    }
}
