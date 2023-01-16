package squaregame.squares.demo;

import squaregame.model.Direction;
import squaregame.model.SquareAction;
import squaregame.model.SquareView;
import squaregame.squares.SquareLogic;

public class DefaultSquare extends SquareLogic {
    @Override
    public SquareAction run(SquareView view) {
        Direction preferredDirection = Direction.N.rotateClockwise(view.getPlayerAllowedMetadata().getRoundNumber());
        if (!view.getEmptyDirections().contains(preferredDirection)) {
            return Wait();
        } else {
            return replicate(preferredDirection, new DefaultSquare());
        }
    }

    @Override
    public String getSquareName() {
        return "AWESOME DEMO";
    }
}
