package squaregame.utils;

import squaregame.model.Direction;
import squaregame.model.Player;

import java.util.ArrayList;
import java.util.List;

public class SquareLogicUtilities {
    public static List<Direction> getEmptyDirections(List<Player> view) {
        final List<Direction> result = new ArrayList<>();
        for (int i = 0; i < view.size(); i++) {
            if (view.get(i) == null) {
                result.add(Direction.values()[i]);
            }
        }
        return result;
    }

    public static List<Direction> getEnemyDirections(List<Player> view, Player player) {
        final List<Direction> result = new ArrayList<>();
        for (int i = 0; i < view.size(); i++) {
            if (view.get(i) != null && view.get(i) != player) {
                result.add(Direction.values()[i]);
            }
        }
        return result;
    }
}
