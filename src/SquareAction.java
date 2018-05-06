/**
 * Created by Russell on 5/5/18.
 */
public class SquareAction {
    Action action;
    GameBoard.Direction direction;
    SquareLogic squareLogic;
    SquareLogic replicated;

    public SquareAction(Action action, GameBoard.Direction direction, SquareLogic squareLogic, SquareLogic replicated) {
        this.action = action;
        this.direction = direction;
        this.squareLogic = squareLogic;
        this.replicated = replicated;
    }
}
