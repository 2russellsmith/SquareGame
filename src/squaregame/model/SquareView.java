package squaregame.model;

import java.util.ArrayList;
import java.util.List;

public class SquareView {

    private List<Player> view;
    private Player player;
    private int row;
    private int col;
    private int roundNumber;

    public SquareView(List<Player> view, Player player, int row, int col, int roundNumber) {
        this.view = view;
        this.player = player;
        this.row = row;
        this.col = col;
        this.roundNumber = roundNumber;
    }

    public List<Direction> getEmptyDirections() {
        final List<Direction> result = new ArrayList<>();
        for (int i = 0; i < this.view.size(); i++) {
            if (this.view.get(i) == null) {
                result.add(Direction.values()[i]);
            }
        }
        return result;
    }

    public List<Direction> getEnemyDirections() {
        final List<Direction> result = new ArrayList<>();
        for (int i = 0; i < this.view.size(); i++) {
            if (this.view.get(i) != null && this.view.get(i) != this.player) {
                result.add(Direction.values()[i]);
            }
        }
        return result;
    }

    public List<Direction> getFriendlyDirections() {
        final List<Direction> result = new ArrayList<>();
        for (int i = 0; i < this.view.size(); i++) {
            if (this.view.get(i) != null && this.view.get(i) == this.player) {
                result.add(Direction.values()[i]);
            }
        }
        return result;
    }
    public Player getLocation(Direction direction) {
        return view.get(direction.ordinal());
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public int getRoundNumber() {
        return roundNumber;
    }
}
