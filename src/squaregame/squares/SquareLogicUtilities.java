package squaregame.squares;

import java.util.ArrayList;
import java.util.List;

import squaregame.GameBoard;
import squaregame.Player;

public class SquareLogicUtilities {
    public static List<GameBoard.Direction> getEmptyDirections(List<Player> view) {
        List<GameBoard.Direction> result = new ArrayList<>();
        for (int i = 0; i < view.size(); i++) {
            if (view.get(i) == null) {
                result.add(GameBoard.Direction.values()[i]);
            }
        }
        return result;
    }

    public static List<GameBoard.Direction> getEnemyDirections(List<Player> view, Player player) {
        List<GameBoard.Direction> result = new ArrayList<>();
        for (int i = 0; i < view.size(); i++) {
            if (view.get(i) != null && view.get(i) != player) {
                result.add(GameBoard.Direction.values()[i]);
            }
        }
        return result;
    }
}
