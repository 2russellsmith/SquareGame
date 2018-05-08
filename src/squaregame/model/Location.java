package squaregame.model;

/**
 * Created by Russell on 5/5/18.
 */
public class Location {
    private int x;
    private int y;
    public Location(int x, int y, Direction direction) {

        this.x = Math.floorMod((direction.getxOffset() + x), 150);
        this.y = Math.floorMod((direction.getyOffset() + y), 150);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
