package squaregame.squares.unity;

import squaregame.model.Direction;
import squaregame.model.SquareAction;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Mycelium.
 */
public class Mycelium extends FilteredThought {

    //growth vars
    private Direction from;

    //Attack vars
    private int attackCountdown = 0;
    private int attackCountdownMax = 100;

    //spread vars
    private int COLINIZATION_RATE = 100;

    /**
     * Constructor.
     * @param from the direction this came from.
     */
    public Mycelium(Direction from){
        this.from = from;
    }

    @Override
    public SquareAction thinkForMyself(ApprovedView squareView) {

        //if there's a bad guy, attack one most away from "from"
        List<Direction> enemies = squareView.getAttackableDirections();
        if(!enemies.isEmpty()){
            //TODO attack the farthest from "from"
            return SquareAction.attack(preferredOrRandom(enemies, from.getOppositeDirection()), this);
        }

        List<Direction> emptySquares = squareView.getEmptyDirections();
        //if there's only good guys, wait
        if(emptySquares.isEmpty()){
            attackCountdown = attackCountdownMax;
            return SquareAction.wait(this);
        }

        //a square just opened nearby, attack it.
        if(emptySquares.size() > 0 && attackCountdown > 0){
            attackCountdown--;
            Direction attackDirection = preferredOrRandom(emptySquares, from.getOppositeDirection());
            from = attackDirection.getOppositeDirection();//start looking at the attack direction.
            return SquareAction.attack(attackDirection, this);
        }

        //Starting to get saturated
        if(emptySquares.size() == 6 || emptySquares.size() == 5) {
            //to colonize or not to colonize
            if (ThreadLocalRandom.current().nextInt(0, 101) <= COLINIZATION_RATE) {
                Direction to = emptySquares.get(ThreadLocalRandom.current().nextInt(0, emptySquares.size()));
                return SquareAction.move(to, new SuicideSpore(to, HiveMind.think().knowledge.getSporeCountdownMax()));
            }
        }

        Direction to = preferredOrRandom(squareView.getEmptyDirections(), from.getOppositeDirection());
        return SquareAction.replicate(to, this, new Mycelium(to.getOppositeDirection()));

    }

    private Direction preferredOrRandom(List<Direction> emptySquares, Direction preferred){
        if(emptySquares.contains(preferred)){
            return preferred;
        }
        return emptySquares.get(ThreadLocalRandom.current().nextInt(0, emptySquares.size()));
    }
}
