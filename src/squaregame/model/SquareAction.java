package squaregame.model;

import squaregame.squares.SquareLogic;

/**
 * Created by Russell on 5/5/18.
 */
public class SquareAction {
    private final Action action;
    private final Direction direction;
    private final SquareLogic squareLogic;
    private final SquareLogic replicated;

    private SquareAction(Action action, Direction direction, SquareLogic squareLogic, SquareLogic replicated) {
        this.action = action;
        this.direction = direction;
        this.squareLogic = squareLogic;
        this.replicated = replicated;
    }

    public static SquareAction move(Direction direction, SquareLogic squareLogic) {
        return new SquareAction(Action.MOVE, direction, squareLogic, null);
    }

    public static SquareAction replicate(Direction direction, SquareLogic squareLogic, SquareLogic replicated) {
        return new SquareAction(Action.REPLICATE, direction, squareLogic, replicated);
    }

    public static SquareAction wait(SquareLogic squareLogic) {
        return new SquareAction(Action.WAIT, null, squareLogic, null);
    }

    public static SquareAction attack(Direction direction, SquareLogic squareLogic) {
        return new SquareAction(Action.ATTACK, direction, squareLogic, null);
    }

    public Action getAction() {
        return action;
    }

    public Direction getDirection() {
        return direction;
    }

    public SquareLogic getSquareLogic() {
        return squareLogic;
    }

    public SquareLogic getReplicated() {
        return replicated;
    }
}
