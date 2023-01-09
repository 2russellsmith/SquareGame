package squaregame.squares.nick;
import squaregame.model.Direction;
import squaregame.model.SquareAction;
import squaregame.model.SquareView;
import squaregame.squares.SquareLogic;
public class DefaultSquare extends SquareLogic {
    private boolean drawn = false;
    @Override
    public SquareAction run(SquareView squareView) {
        if (drawn) return SquareAction.wait(this);
        drawn = true;
        return SquareAction.replicate(Direction.N, this, new DrawerSquare(0));
    }
    @Override
    public String getSquareName() {
        return "8==D";
    }
}