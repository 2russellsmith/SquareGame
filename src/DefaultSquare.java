import java.util.List;

/**
 * Created by Russell on 5/5/18.
 */
public class DefaultSquare implements SquareLogic {

    @Override
    public SquareAction run(int row, int col, List<Player> view) {
        return new SquareAction(Action.REPLICATE, GameBoard.Direction.NE, this, new DefaultSquare2());
    }
}
