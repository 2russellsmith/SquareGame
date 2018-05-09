package squaregame.squares.laraza;

import squaregame.model.Direction;
import squaregame.model.MagicSquare;
import squaregame.model.Player;
import squaregame.model.SquareAction;
import squaregame.squares.SquareLogic;
import squaregame.utils.SquareLogicUtilities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Created by Russell on 5/5/18.
 */
public class DefaultSquare extends SquareLogic {

    private static class Dimension {
        private int row;
        private int col;

        private Dimension(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public int hashCode() {
            return this.row * 1000 + this.col;
        }

        public String toString() {
            return String.format("%s,%s ", this.row, this.col);
        }
    }

    private static Set<Dimension> current = new HashSet<>();
    private static Set<Dimension> previous = new HashSet<>();

    private static boolean FIRST_RUN = true;
    private static int INITIAL_ROW;
    private static int INITIAL_COL;
    private static Dimension lastDim = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);

    @Override
    public SquareAction run(MagicSquare magicSquare, int row, int col, List<Player> view) {
        if (FIRST_RUN) {
            INITIAL_ROW = row;
            INITIAL_COL = col;
            FIRST_RUN = false;
        }

        boolean newRound = row <= lastDim.row && col < lastDim.col;
        if (newRound) {
            previous = current;
            current = new HashSet<>();
        }

        lastDim.row = row;
        lastDim.col = col;

        current.add(new Dimension(row, col));

        final List<Direction> directions = SquareLogicUtilities.getEmptyDirections(view).stream()
                .filter(direction -> !current.contains(new Dimension(row + direction.getxOffset(), col + direction.getyOffset())))
                .filter(direction -> !previous.contains(new Dimension(row + direction.getxOffset(), col + direction.getyOffset())))
                .collect(Collectors.toList());
        if (directions.isEmpty()) {
            return SquareAction.wait(this);
        }

        Direction direction = directions.get(ThreadLocalRandom.current().nextInt(directions.size()));
        current.add(new Dimension(row + direction.getxOffset(), col + direction.getyOffset()));
        return SquareAction.replicate(direction, this, new DefaultSquare());
    }

    @Override
    public String getSquareName() {
        return "la raza";
    }
}
