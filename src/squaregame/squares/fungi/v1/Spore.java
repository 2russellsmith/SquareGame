package squaregame.squares.fungi.v1;

import squaregame.model.Direction;
import squaregame.model.SquareAction;
import squaregame.model.SquareView;
import squaregame.squares.SquareLogic;
import squaregame.squares.fungi.DirectionUtils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Spore.
 */
public class Spore extends SquareLogic {
    private int countDown;
    private Direction to;

    public Spore(Direction to, int countDownMax) {
        this.to = to;
        this.countDown = countDownMax > 10 ? ThreadLocalRandom.current().nextInt(10, countDownMax) : 10;
    }

    @Override
    public SquareAction run(SquareView squareView) {
        //move if clear
        if (countDown > 0 && squareView.getEmptyDirections().contains(to)) {
            countDown--;
            return SquareAction.move(to, this);
        }

        //start replicating
        return SquareAction.replicate(DirectionUtils.opposite(to),
                new Mycelium(DirectionUtils.opposite(to), countDown / 2),
                new Mycelium(to, countDown / 2));
    }

    @Override
    public String getSquareName() {
        return null;
    }
}
