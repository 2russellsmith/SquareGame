package squaregame.model;

import java.util.ArrayList;
import java.util.List;

public class SquareView {

    private final List<Player> view;
    private final Player player;
    private final int row;
    private final int col;
    private final PlayerAllowedMetadata playerAllowedMetadata;

    public SquareView(List<Player> view, Player player, int col, int row, PlayerAllowedMetadata playerAllowedMetadata) {
        this.view = view;
        this.player = player;
        this.row = row;
        this.col = col;
        this.playerAllowedMetadata = playerAllowedMetadata;
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

    public List<Direction> getOccupiedDirections() {
        List<Direction> enemy = getEnemyDirections();
        enemy.addAll(getFriendlyDirections());
        return enemy;
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

    public PlayerAllowedMetadata getPlayerAllowedMetadata() {
        return playerAllowedMetadata;
    }
}
