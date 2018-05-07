package squaregame.model;

import squaregame.GameBoard;
import squaregame.SquareGameMain;

/**
 * Created by Russell on 5/5/18.
 */
public class Location {
    private int x;
    private int y;
    public Location(int x, int y, Direction direction) {

        this.x = GameBoard.mod((direction.getxOffset() + x), SquareGameMain.BOARD_SIZE);
        this.y = GameBoard.mod((direction.getyOffset() + y), SquareGameMain.BOARD_SIZE);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
