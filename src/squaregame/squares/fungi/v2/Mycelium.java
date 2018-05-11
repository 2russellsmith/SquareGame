package squaregame.squares.fungi.v2;

import squaregame.model.*;
import squaregame.squares.SquareLogic;
import squaregame.squares.fungi.DirectionUtils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Mycelium.
 */
public class Mycelium extends SquareLogic {

    //growth vars
    private Direction from;
    private static int FORK_RATE = 4;

    //Attack vars
    private int attackCountdown = 0;
    private int attackCountdownMax = 100;

    //spread vars
    private int COLINIZATION_RATE = 2;
    private int sporeCountdownMax = 100;

    /**
     * Constructor.
     * @param from the direction this came from.
     */
    public Mycelium(Direction from){
        this.from = from;
    }

    /**
     * Constructor.
     * @param from the direction this came from.
     * @param sporeCountdownMax optional override
     */
    public Mycelium(Direction from, int sporeCountdownMax){
        this.from = from;
        this.sporeCountdownMax = sporeCountdownMax;
    }

    @Override
    public SquareAction run(SquareView squareView) {

        //if there's a bad guy, attack one most away from "from"
        List<Direction> enemies = squareView.getEnemyDirections();
        if(!enemies.isEmpty()){
            //TODO attack the farthest from "from"
            return SquareAction.attack(preferredOrRandom(enemies,DirectionUtils.opposite(from)), this);
        }

        List<Direction> emptySquares = squareView.getEmptyDirections();
        //if there's only good guys, wait
        if(emptySquares.isEmpty()){
            attackCountdown = attackCountdownMax;
            return SquareAction.wait(this);
        }

        //if there's only one square and it's the from... fork.
        final List<Direction> friends = squareView.getFriendlyDirections();
        if(friends.size() == 1 && friends.contains(from)){

            //to fork or not to fork
            if(ThreadLocalRandom.current().nextInt(0, FORK_RATE) == 0){
                Direction to = DirectionUtils.fork(from, null);
                return SquareAction.replicate(to, this, new Mycelium(DirectionUtils.opposite(to)));
            }
            //myceleum running
            else {
                return SquareAction.replicate(DirectionUtils.opposite(from), this, new Mycelium(from));
            }
        }//if there's 2 and one is the from, and the other is in a fork position, fork.
        else if(friends.size() == 2 && friends.contains(from)){

                if(friends.contains(DirectionUtils.fork(from, null))) {
                    Direction to = DirectionUtils.fork(from, DirectionUtils.fork(from, null));
                    return SquareAction.replicate(to, this, new Mycelium(DirectionUtils.opposite(to)));
                }//if we've bumpped into someone
                else if(friends.contains(DirectionUtils.opposite(from))){
                    Direction to = preferredOrRandom(squareView.getEmptyDirections(), DirectionUtils.opposite(from));
                    return SquareAction.replicate(to, this, new Mycelium(DirectionUtils.opposite(to)));
                }//just keep moving along
                else {
                   return SquareAction.replicate(DirectionUtils.opposite(from), this, new Mycelium(from));
                }
        }

        //a square just opened nearby, attack it.
        if(emptySquares.size() > 0 && attackCountdown > 0){
            attackCountdown--;
            Direction attackDirection = preferredOrRandom(emptySquares, DirectionUtils.opposite(from));
            from = DirectionUtils.opposite(attackDirection);//start looking at the attack direction.
            return SquareAction.attack(attackDirection, this);
        }

        //Starting to get saturated
        if(emptySquares.size() == 3 || emptySquares.size() == 4) {
            //to colonize or not to colonize
            if (ThreadLocalRandom.current().nextInt(0, COLINIZATION_RATE) == 0) {
                Direction to = emptySquares.get(ThreadLocalRandom.current().nextInt(0, emptySquares.size()));
                return SquareAction.move(to, new SuicideSpore(to, sporeCountdownMax));
            }
        }

        //In saturated state, back off
        if(emptySquares.size() <= 5){
            if(ThreadLocalRandom.current().nextInt(0, 8) == 0){
                return SquareAction.replicate(emptySquares.get(0), this, new Mycelium(DirectionUtils.opposite(emptySquares.get(0))));
            }
            return SquareAction.wait(this);
        }

        Direction to = preferredOrRandom(squareView.getEmptyDirections(), DirectionUtils.opposite(from));
        return SquareAction.replicate(to, this, new Mycelium(DirectionUtils.opposite(to)));

    }

    @Override
    public String getSquareName() {
        return "Mycelium";
    }

    private Direction preferredOrRandom(List<Direction> emptySquares, Direction preferred){
        if(emptySquares.contains(preferred)){
            return preferred;
        }
        return emptySquares.get(ThreadLocalRandom.current().nextInt(0, emptySquares.size()));
    }
}
