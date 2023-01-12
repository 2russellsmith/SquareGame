package squaregame.squares.test;

import squaregame.model.Direction;
import squaregame.model.SquareAction;
import squaregame.model.SquareView;
import squaregame.squares.SquareLogic;

public class DefaultSquare extends SquareLogic {

    protected static int[][] blood_in_the_water;

    @Override
    public SquareAction run(SquareView squareView) {
        blood_in_the_water = new int[squareView.getPlayerAllowedMetadata().getBoardSize()][squareView.getPlayerAllowedMetadata().getBoardSize()];
        return SquareAction.replicate(Direction.NW, new Sharknado(), new Sharknado());
    }

    @Override
    public String getSquareName() {
        return "Sharknado";
    }
}
