package squaregame.squares.unity;

import squaregame.model.Direction;
import squaregame.model.SquareAction;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Spore.
 */
public class SuicideSpore extends FilteredThought {
    private int countDown;
    private Direction to;

    public SuicideSpore(Direction to, int countDownMax){
        this.to = to;
        this.countDown = countDownMax > 10 ? ThreadLocalRandom.current().nextInt(10, countDownMax) :10;
    }

    @Override
    public SquareAction thinkForMyself(ApprovedView squareView) {
        //if there's a bad guy, attack one most away from "from"
        List<Direction> enemies = squareView.getAttackableDirections();
        if(!enemies.isEmpty()){
            Direction enemy = enemies.get(ThreadLocalRandom.current().nextInt(0, enemies.size()));
            to = enemy;
            return SquareAction.attack(enemy, this);
        }

        //move if clear
        if(countDown > 0 && squareView.getEmptyDirections().contains(to)){
            countDown--;
            return SquareAction.move(to, this);
        }
        else if (squareView.getFriendlyDirections().contains(to)){
            return SquareAction.replicate(to.getOppositeDirection(), new Mycelium(to.getOppositeDirection()),
                    new Mycelium(to.getOppositeDirection()));
        }

        //put down roots
        return new Mycelium(to.getOppositeDirection()).thinkForMyself(squareView);
    }

}
