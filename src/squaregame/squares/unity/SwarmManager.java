package squaregame.squares.unity;

import squaregame.model.Action;
import squaregame.model.Direction;
import squaregame.model.SquareAction;
import squaregame.squares.SquareLogic;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * SwarmManager.  Control ALL pixels at once.
 */
public class SwarmManager {

    private int round = 0;
    private boolean hunkerdown = false;

    private boolean swarm = false;
    private int swarmDistanceRemaining = 10;
    private int swarmDistancePower = 10;
    private Direction swarmDirection = Direction.N;
    private int swarmStartRound = 1650;

    public SwarmManager(){
    }

    public void updateRound(int round){
        if (this.round != round) {
            this.round = round;
            if (swarm)
                swarmDistanceRemaining--;
            updateState();
        }
    }

    /**
     * TODO if at > 50%, then hunkerdown? (try counting the number of peeps we see, logging that with round... and emperically pick a number
     * after they add the ability to see how many peeps you got... we can calc a %
     */
    private void updateState(){
            if (round >= swarmStartRound) {
                if (!hunkerdown && !swarm) {
                    hunkerdown = true;
                    swarmStartRound = round;
                }
            }
            if(round > (swarmStartRound + 100)){
                if (hunkerdown == true){
                    hunkerdown = false;
                    swarm = true;

                    /**
                     * TODO Why the hell do some of the directions not work?
                     * good: W, SW, NW, N
                     * bad: S, NE, SE, E
                     */
                    swarmDirection = Stream.of(Direction.values())
                            .filter(direction -> direction != Direction.CENTER && direction != Direction.S &&
                                    direction != Direction.NE && direction != Direction.SE && direction != Direction.E)
                            .collect(Collectors.toList()).get(ThreadLocalRandom.current().nextInt(0, 4));
                }
                else if (swarmDistanceRemaining <= 0 ){
                    swarm = false;
                    swarmStartRound = round + 200;
                    swarmDistancePower *= 2;
                    swarmDistanceRemaining = swarmDistancePower;
                }
            }
    }

    public boolean swarming() {
        return hunkerdown || swarm;
    }

    /**
     * @param squareAction
     * @param view
     * @return
     */
    public SquareAction getSwarmAction(SquareLogic squareLogic, SquareAction squareAction, ApprovedView view){
        if (!swarming()){
            return squareAction;
        }

        if (hunkerdown){
            if (squareAction.getAction() == Action.ATTACK) {
                return squareAction;
            }

            return attackOrWait(squareLogic, view);

        } else if (swarm) {

            //alternate attack and move
            if( swarmDistanceRemaining % 2 == 0 ) {
                //all move in sync, unless it's clear behind you... then spread out
                if(view.getEmptyDirections().contains(swarmDirection.getOppositeDirection())) {
                    attackOrWait(squareLogic, view);
                } else {
                    return SquareAction.move(swarmDirection, squareLogic);
                }
            }
            else {
                //attack with the swarm if there's not a good guy there.
                if(view.getFriendlyDirections().contains(swarmDirection) || !view.getAttackableDirections().contains(swarmDirection)){
                    return SquareAction.wait(squareLogic);
                }

                return SquareAction.attack(swarmDirection, squareLogic);
            }
        }

        return squareAction;
    }


    private SquareAction attackOrWait (SquareLogic squareLogic, ApprovedView view){

        if (!view.getAttackableDirections().isEmpty()){
            return SquareAction.attack(view.getAttackableDirections().get(0), squareLogic);
        }

        List<Direction> directions = view.getEmptyDirections();
        if (directions.isEmpty()){
            return SquareAction.wait(squareLogic);
        }
        else {
            //TODO be smart about who to attack
            return SquareAction.attack(directions.get(0), squareLogic);
        }
    }
}
