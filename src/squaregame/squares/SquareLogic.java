package squaregame.squares;

import squaregame.model.Action;
import squaregame.model.Direction;
import squaregame.model.SquareAction;
import squaregame.model.SquareView;

/**
 * Created by Russell on 5/5/18.
 */
public abstract class SquareLogic {
    /**
     *
     * @param view this is a view of everything the square can see. (view.get(0) = NW, view.get(1) = N,
     *             view.get(2) = NE, etc}. It will always be size 8; It will be null if the square is empty.
     * @return {@link SquareAction} You have 4 different actions a square can take (Move, attack, replicate, wait}
     */
    public abstract SquareAction run(SquareView view);

    /**
     * Give your square a name!
     * @return Name of your square.
     */
    public abstract String getSquareName();

    public SquareAction move(Direction direction) {
        return SquareAction.move(direction, this);
    }
    public SquareAction replicate(Direction direction, SquareLogic replicated) {
        return SquareAction.replicate(direction, this, replicated);
    }
    public SquareAction Wait() {
        return SquareAction.wait(this);
    }
    public SquareAction attack(Direction direction) {
        return SquareAction.attack(direction, this);
    }
}
