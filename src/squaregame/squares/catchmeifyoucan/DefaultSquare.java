package squaregame.squares.catchmeifyoucan;

import squaregame.model.Direction;
import squaregame.model.SquareAction;
import squaregame.model.SquareView;
import squaregame.squares.SquareLogic;

import java.util.List;

/**
 * Created by Russell on 5/12/18.
 */
public class DefaultSquare extends SquareLogic {
    @Override
    public SquareAction run(SquareView view) {
        List<Direction> enemyDirections = view.getEnemyDirections();
        if (enemyDirections.isEmpty()) {
            return Wait();
        } else if (enemyDirections.size() == 1) {
            return move(enemyDirections.get(0).getOppositeDirection());
        } else if (enemyDirections.size() == 2) {
            int runDirection = (int) Math.floor(enemyDirections.stream().mapToInt(Enum::ordinal).average().getAsDouble());
            if (Math.abs(enemyDirections.get(0).ordinal() - enemyDirections.get(1).ordinal()) > 4) {
                return move(Direction.values()[runDirection]);
            } else {
                return move(Direction.values()[runDirection].getOppositeDirection());
            }
        } else {
            return move(view.getEmptyDirections().get(0));
        }
    }

    @Override
    public String getSquareName() {
        return "Frank Abagnale Jr.";
    }
}
