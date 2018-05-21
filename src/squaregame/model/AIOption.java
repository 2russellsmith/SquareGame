package squaregame.model;

import squaregame.squares.SquareLogic;

public class AIOption {
    private SquareLogic squareLogic;
    private int id;

    public AIOption(SquareLogic squareLogic, int id) {
        this.squareLogic = squareLogic;
        this.id = id;
    }

    public SquareLogic getSquareLogic() {
        return squareLogic;
    }

    public String getId() {
        return squareLogic.getSquareName();
    }

    @Override
    public String toString() {
        return squareLogic.getSquareName();
    }

}
