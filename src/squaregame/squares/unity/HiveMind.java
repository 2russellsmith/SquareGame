package squaregame.squares.unity;

import squaregame.model.SquareAction;
import squaregame.squares.SquareLogic;

/**
 * HiveMind. TODO some package private fields will need to have getters if brain pulled into another package.
 */
class HiveMind {

    private static HiveMind hiveMind = new HiveMind();
    private SwarmManager swarmManager;
    Knowledge knowledge = new Knowledge();
    CollisionManager collisionManager;

    private boolean resetComplete = false;
    private boolean resetToggle = true;

    /**
     * Singleton constructor
     */
    private HiveMind(){}

    /**
     * getInstance
     * @return the {@link HiveMind}
     */
    static HiveMind think(){
        return hiveMind;
    }

    /**
     * Request action.  Mother brain will run your "run" for you if she thinks your should.
     * @return what's a square to do
     */
    SquareAction whatToDo(FilteredThought me, ApprovedView view){

        //very first round?... let a starter culture do it's thing.
        if ( view.getPlayerAllowedMetadata().getRoundNumber() == 0 && !resetComplete) {
            collisionManager = new CollisionManager(view.getPlayerAllowedMetadata().getBoardSize());
            swarmManager = new SwarmManager();
            knowledge = new Knowledge();
            resetComplete = true;
            resetToggle = true;
        }

        //Support resetting the world (since this is a singleton, it persists between matches)
        if (resetToggle && view.getPlayerAllowedMetadata().getRoundNumber() > 0){
            resetToggle = false;
            resetComplete = false;
        }

        //You have permission to think
        return me.thinkForMyself(view);
    }

    /**
     * We must share all actions we take with mother-brain.
     * @param squareAction
     * @return
     */
    SquareAction whatImUpTo(SquareLogic me, SquareAction squareAction, ApprovedView view){

        swarmManager.updateRound(view.getPlayerAllowedMetadata().getRoundNumber());

        if(this.swarmManager.swarming()){
            SquareAction action = swarmManager.getSwarmAction(me, squareAction, view);
            collisionManager.update(action, view);
            return action;
        }

        collisionManager.update(squareAction, view);
        return squareAction;
    }


}
