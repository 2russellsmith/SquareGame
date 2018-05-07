package squaregame.utils;

import java.util.ArrayList;
import java.util.List;

import squaregame.model.Player;
import squaregame.model.Direction;

public class SquareLogicUtilities {
    public static List<Direction> getEmptyDirections(List<Player> view) {
        List<Direction> result = new ArrayList<>();
        for (int i = 0; i < view.size(); i++) {
            if (view.get(i) == null) {
                result.add(Direction.values()[i]);
            }
        }
        return result;
    }

    public static List<Direction> getEnemyDirections(List<Player> view, Player player) {
        List<Direction> result = new ArrayList<>();
        for (int i = 0; i < view.size(); i++) {
            if (view.get(i) != null && view.get(i) != player) {
                result.add(Direction.values()[i]);
            }
        }
        return result;
    }
}
