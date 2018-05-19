package squaregame.model;

import lombok.Getter;

/**
 * Created by Russell on 5/5/18.
 */
@Getter
public class Location {
    private int x;
    private int y;
    
    public Location(int x, int y, Direction direction, int boardSize) {

        this.x = Math.floorMod((direction.getxOffset() + x), boardSize);
        this.y = Math.floorMod((direction.getyOffset() + y), boardSize);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
