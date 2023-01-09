package squaregame.model;

import squaregame.squares.SquareLogic;

import java.lang.reflect.InvocationTargetException;

public class AIOption {
    private final Class<SquareLogic> squareLogic;
    private final SquareLogic defaultSquareLogic;
    private final int id;

    public AIOption(Class<SquareLogic> squareLogic, int id) {
        this.squareLogic = squareLogic;
        this.id = id;
        try {
            this.defaultSquareLogic = (SquareLogic) squareLogic.getConstructors()[0].newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public SquareLogic getStartingSquareLogic() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        return (SquareLogic) squareLogic.getConstructors()[0].newInstance();
    }

    public String getId() {
        return defaultSquareLogic.getSquareName();
    }
    public SquareLogic getDefaultSquare() {
        return defaultSquareLogic;
    }

    @Override
    public String toString() {
        return defaultSquareLogic.getSquareName();
    }

}
