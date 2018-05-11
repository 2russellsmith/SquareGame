package squaregame.squares.fungi.v2;

import squaregame.model.*;
import squaregame.squares.SquareLogic;
import squaregame.squares.fungi.DirectionUtils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * StarterCulture.  (Please ignore the unfortunate shape)
 *
 * Stage:    1     2     3
 *
 *               M  M   M MM
 *  1   1    13   13    M13
 *       2   42   42     42M
 *               M  M   MM M
 */
public class StarterCulture extends SquareLogic {

    private int id;
    private int stage = 1;


    public StarterCulture(int id, int stage){
        this.id = id;
        this.stage = stage;
    }

    public StarterCulture(int id){
        this.id = id;
    }

    @Override
    public SquareAction run(SquareView squareView) {

        //if there's a bad guy, attack one
        List<Direction> enemies = squareView.getEnemyDirections();
        if(!enemies.isEmpty()){
            Direction randEnemy = enemies.get(ThreadLocalRandom.current().nextInt(0, enemies.size()));
            return SquareAction.attack(randEnemy, new Mycelium(DirectionUtils.opposite(randEnemy)));
        }

        List<Direction> emptySquares = squareView.getEmptyDirections();

        //pre-checks direction and just turn to myc if busy.
        switch (id){
            case 1:
                if(stage == 1){
                    return emptySquares.contains(Direction.E) ?
                            SquareAction.replicate(Direction.E, this, new StarterCulture(3, 2)) :
                            (new Mycelium(Direction.W)).run(squareView);
                }
                else if(stage == 2){
                    stage ++;
                    return emptySquares.contains(Direction.NW) ?
                            SquareAction.replicate(Direction.NW, this, new Mycelium(Direction.SE)) :
                            (new Mycelium(Direction.SE)).run(squareView);
                }
                return emptySquares.contains(Direction.W) ?
                        SquareAction.replicate(Direction.W, new Mycelium(Direction.SE), new Mycelium(Direction.E)) :
                        (new Mycelium(Direction.E)).run(squareView);
            case 2:
                if(stage == 1){
                    stage ++;
                    return emptySquares.contains(Direction.W) ?
                            SquareAction.replicate(Direction.W, this, new StarterCulture(4, 2)) :
                            (new Mycelium(Direction.E)).run(squareView);
                }
                else if(stage == 2){
                    stage ++;
                    return emptySquares.contains(Direction.SE) ?
                        SquareAction.replicate(Direction.SE, this, new Mycelium(Direction.NW)) :
                        (new Mycelium(Direction.NW)).run(squareView);
                }
                return emptySquares.contains(Direction.E) ?
                    SquareAction.replicate(Direction.E, new Mycelium(Direction.NW), new Mycelium(Direction.W)) :
                        (new Mycelium(Direction.W)).run(squareView);
            case 3:
                if(stage == 2){
                    stage ++;
                    return emptySquares.contains(Direction.NE) ?
                        SquareAction.replicate(Direction.NE, this, new Mycelium(Direction.SW)) :
                        (new Mycelium(Direction.SW)).run(squareView);
                }
                return emptySquares.contains(Direction.N) ?
                    SquareAction.replicate(Direction.N, new Mycelium(Direction.SW), new Mycelium(Direction.S)) :
                        (new Mycelium(Direction.S)).run(squareView);
            case 4:
                if(stage == 2){
                    stage ++;
                    return emptySquares.contains(Direction.SW) ?
                        SquareAction.replicate(Direction.SW, this, new Mycelium(Direction.NE)) :
                        (new Mycelium(Direction.NE)).run(squareView);
                }
                return emptySquares.contains(Direction.S) ?
                    SquareAction.replicate(Direction.S, new Mycelium(Direction.NE), new Mycelium(Direction.N)) :
                    (new Mycelium(Direction.N)).run(squareView);
        }
    return SquareAction.wait(new Mycelium(Direction.N));

    }

    @Override
    public String getSquareName() {
        return null;
    }

}
