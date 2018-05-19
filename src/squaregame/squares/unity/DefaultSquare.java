package squaregame.squares.unity;

import squaregame.model.Direction;
import squaregame.model.SquareAction;
import squaregame.model.SquareView;
import squaregame.squares.SquareLogic;

/**
 * Hive mind ideas
 *
 * Defensive attack direction (by vote) - and proportionally?(%)  or heatmap?   or by closeness?
 *
 * Swarm after hot spots? ... like you found a "bad guy" focus on them.
 *
 * run for the finish line.  (reproduce only when approaching last round(s))
 *
 * Random move threshold %.  let's all move in one direction.. unless the guy next to you is sleeping.(ad infinitum) {with an end case}
 * could be an attack/move combo... or just a move? (with a risk of self-stomp)
 *
 * try making a copy of penicilin with a "tell me what to do" method right at the start, and playing with
 * moving everyone in one direction... or attacking and moving, or duplicating on last round.
 *
 * track the rate of growth, and when it slows... then switch to attack mode
 *
 * attack mode... hunkerdown for 100 rounds (just keep attacking), then all move in one direction/attack alternating
 *
 * double check that peeps aren't moving to, replicating to, or attacking the same place.
 */
public class DefaultSquare extends SquareLogic {

    @Override
    public SquareAction run(SquareView squareView) {
        return HiveMind.think().whatToDo(new Mycelium(Direction.N), new ApprovedView(squareView));
    }

    /**
     * @return The name of the AI.
     */
    @Override
    public String getSquareName() {
        return "Unity Sanchez";
    }
}
