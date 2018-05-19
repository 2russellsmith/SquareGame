package squaregame.squares.unity;

import squaregame.model.SquareAction;
import squaregame.model.SquareView;
import squaregame.squares.SquareLogic;

/**
 * FilteredThought.
 */
public abstract class FilteredThought extends SquareLogic {

    /**
     * Let motherbrain have the option to tell me what to do, and always tell mother brain what i'm doing.
     * (even though she should always know)
     *
     * @param view this is a view of everything the square can see. (view.get(0) = NW, view.get(1) = N,
     *             view.get(2) = NE, etc}. It will always be size 8; It will be null if the square is empty.
     * @return
     */
    @Override
    public SquareAction run(SquareView view) {
        ApprovedView approvedView = new ApprovedView(view);
        return HiveMind.think().whatImUpTo(this, HiveMind.think().whatToDo(this, approvedView), approvedView);
    }

    public SquareAction run(ApprovedView view) {
        return HiveMind.think().whatImUpTo(this, HiveMind.think().whatToDo(this, view), view);
    }

    @Override
    public String getSquareName() {
        return null;
    }

    public abstract SquareAction thinkForMyself(ApprovedView view);
}
