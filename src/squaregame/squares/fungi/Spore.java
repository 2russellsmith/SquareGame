package squaregame.squares.fungi;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import squaregame.model.Direction;
import squaregame.model.MagicSquare;
import squaregame.model.Player;
import squaregame.model.SquareAction;
import squaregame.squares.SquareLogic;
import squaregame.utils.SquareLogicUtilities;

/**
 * Spore.
 */
public class Spore extends SquareLogic {
    private int countDown;
    private Direction to;

    public Spore(Direction to, int countDownMax){
        this.to = to;
        this.countDown = countDownMax > 10 ? ThreadLocalRandom.current().nextInt(10, countDownMax) :10;
    }

    @Override
    public SquareAction run(MagicSquare magicSquare, int row, int col, List<Player> view) {
        //move if clear
        if(countDown > 0 && SquareLogicUtilities.getEmptyDirections(view).contains(to)){
            countDown--;
            return SquareAction.move(to, this);
        }

        //start replicating
        return SquareAction.replicate(DirectionUtils.opposite(to),
                new Mycelium(DirectionUtils.opposite(to), countDown/2),
                new Mycelium(to, countDown/2));
    }

    @Override
    public String getSquareName() {
        return null;
    }
}
