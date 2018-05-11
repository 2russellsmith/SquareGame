package squaregame.squares.fungi.v2;

import squaregame.model.Direction;
import squaregame.model.SquareAction;
import squaregame.model.SquareView;
import squaregame.squares.SquareLogic;

/**
 * start intentionally with each new spawn.
 */
public class DefaultSquare extends SquareLogic {

    @Override
    public SquareAction run(SquareView view) {
        return SquareAction.replicate(Direction.SE, new StarterCulture(1), new StarterCulture(2));
    }

    /**
     * @return The name of the AI.
     */
    @Override
    public String getSquareName() {
        return "Penicillin";
    }
}
