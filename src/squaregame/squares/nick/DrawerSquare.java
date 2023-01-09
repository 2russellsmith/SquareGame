package squaregame.squares.nick;
import squaregame.model.Direction;
import squaregame.model.SquareAction;
import squaregame.model.SquareView;
import squaregame.squares.SquareLogic;

public class DrawerSquare extends SquareLogic {
    private final int index;
    private boolean drawn = false;
    private static final Direction[] drawing = {
            Direction.N,
            Direction.E,
            Direction.N,
            Direction.E,
            Direction.N,
            Direction.E,
            Direction.N,
            Direction.N,
            Direction.N,
            Direction.N,
            Direction.N,
            Direction.W,
            Direction.W,
            Direction.N,
            Direction.N,
            Direction.E,
            Direction.N,
            Direction.E,
            Direction.N,
            Direction.E,
            Direction.E,
            Direction.S,
            Direction.NE,
            Direction.E,
            Direction.S,
            Direction.E,
            Direction.S,
            Direction.E,
            Direction.S,
            Direction.S,
            Direction.W,
            Direction.W,
            Direction.S,
            Direction.S,
            Direction.S,
            Direction.S,
            Direction.S,
            Direction.E,
            Direction.S,
            Direction.E,
            Direction.S,
            Direction.E,
            Direction.S,
            Direction.S,
            Direction.W,
            Direction.S,
            Direction.W,
            Direction.S,
            Direction.W,
            Direction.W,
            Direction.N,
            Direction.W,
            Direction.N,
            Direction.SW,
            Direction.S,
            Direction.W,
            Direction.W,
            Direction.N,
            Direction.W,
            Direction.N,
    };
    public DrawerSquare(int index) {
        this.index = index;
    }
    @Override
    public SquareAction run(SquareView squareView) {
        if (index >= drawing.length) return SquareAction.wait(this);
        if (drawn) return SquareAction.wait(this);
        drawn = true;
        return SquareAction.replicate(drawing[index], this, new DrawerSquare(index + 1));
    }
    @Override
    public String getSquareName() {
        return "8==D";
    }
}