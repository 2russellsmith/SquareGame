package squaregame.squares.fungi.v2;

import squaregame.model.Direction;
import squaregame.model.SquareAction;
import squaregame.model.SquareView;
import squaregame.squares.SquareLogic;
import squaregame.squares.fungi.DirectionUtils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Spore.
 */
public class SuicideSpore extends SquareLogic {
    private int countDown;
    private Direction to;

    public SuicideSpore(Direction to, int countDownMax){
        this.to = to;
        this.countDown = countDownMax > 10 ? ThreadLocalRandom.current().nextInt(10, countDownMax) :10;
    }

    @Override
    public SquareAction run(SquareView squareView) {
        //if there's a bad guy, attack one most away from "from"
        List<Direction> enemies = squareView.getEnemyDirections();
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
            return SquareAction.replicate(DirectionUtils.opposite(to), new Mycelium(DirectionUtils.opposite(to)),
                    new Mycelium(DirectionUtils.opposite(to)));
        }

        //start replicating
        return SquareAction.replicate(Direction.SE, new StarterCulture(1), new StarterCulture(2));
    }

    @Override
    public String getSquareName() {
        return null;
    }
}
