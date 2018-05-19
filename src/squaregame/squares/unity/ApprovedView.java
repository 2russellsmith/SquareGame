package squaregame.squares.unity;

import squaregame.model.Direction;
import squaregame.model.PlayerAllowedMetadata;
import squaregame.model.SquareView;

import java.util.List;

/**
 * ApprovedView.  Motherbrain must approve everything you view.
 */
public class ApprovedView {

    private SquareView view;

    public ApprovedView(SquareView view){
        this.view = view;
    }

    /**
     * @return non-collidable empty directions
     */
    public List<Direction> getEmptyDirections() {
        return HiveMind.think().collisionManager.addRemoveOccupied(view.getEmptyDirections(), this);
    }

    /**
     * @return non-collidable attack directions
     */
    public List<Direction> getAttackableDirections() {
        return view.getEnemyDirections().size() > 0 ?
            HiveMind.think().collisionManager.removeNonAttackable(view.getEnemyDirections(), this)
                : view.getEnemyDirections();
    }

    /**
     * TODO   or is this moot?
     * @return present and future-friendly directions
     */
    public List<Direction> getFriendlyDirections() {
        return view.getFriendlyDirections();
    }

    /**
     * Passthrough.
     * @return playerAllowedMetadata
     */
    public PlayerAllowedMetadata getPlayerAllowedMetadata(){
        return view.getPlayerAllowedMetadata();
    }

    public int getRow(){
        return view.getRow();
    }

    public int getCol(){
        return view.getCol();
    }

}
