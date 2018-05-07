package squaregame.squares;

import squaregame.GameBoard;
import squaregame.model.Action;
import squaregame.model.Direction;

/**
 * Created by Russell on 5/5/18.
 */
public class SquareAction {
    public Action action;
    public Direction direction;
    public SquareLogic squareLogic;
    public SquareLogic replicated;

    public SquareAction(Action action, Direction direction, SquareLogic squareLogic, SquareLogic replicated) {
        this.action = action;
        this.direction = direction;
        this.squareLogic = squareLogic;
        this.replicated = replicated;
    }
}
