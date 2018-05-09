package squaregame.squares;

import squaregame.model.MagicSquare;
import squaregame.model.Player;
import squaregame.model.SquareAction;

import java.util.List;

/**
 * Created by Russell on 5/5/18.
 */
public abstract class SquareLogic {
    /**
     *
     * @param magicSquare {@link MagicSquare} Contains the player of the square and the starting square logic.
     * @param row row location
     * @param col location
     * @param view this is a view of everything around the square. (view.get(0) = NW, view.get(1) = N,
     *             view.get(2) = NE, etc}. It will always be size 8; It will be null if the square is empty.
     * @return {@link SquareAction} You have 4 different actions a square can take (Move, attack, replicate, wait}
     */
    public abstract SquareAction run(MagicSquare magicSquare, int row, int col, List<Player> view);

    /**
     * Give your square a name!
     * @return Name of your square.
     */
    public abstract String getSquareName();
}
