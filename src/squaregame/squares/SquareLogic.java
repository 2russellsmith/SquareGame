package squaregame.squares;

import squaregame.model.MagicSquare;
import squaregame.model.Player;
import squaregame.model.SquareAction;

import java.util.List;

/**
 * Created by Russell on 5/5/18.
 */
public abstract class SquareLogic {
    public abstract SquareAction run(MagicSquare magicSquare, int row, int col, List<Player> view);
    public abstract String getSquareName();
}
