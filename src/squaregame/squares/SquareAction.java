package squaregame.squares;

import squaregame.Action;
import squaregame.GameBoard;

/**
 * Created by Russell on 5/5/18.
 */
public class SquareAction {
    public Action action;
    public GameBoard.Direction direction;
    public SquareLogic squareLogic;
    public SquareLogic replicated;

    public SquareAction(Action action, GameBoard.Direction direction, SquareLogic squareLogic, SquareLogic replicated) {
        this.action = action;
        this.direction = direction;
        this.squareLogic = squareLogic;
        this.replicated = replicated;
    }
}
