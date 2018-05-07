package squaregame;

/**
 * Created by Russell on 5/5/18.
 */
public class Location {
    int x;
    int y;
    public Location(int x, int y, GameBoard.Direction direction) {

        this.x = GameBoard.mod((direction.xOffset + x), SquareGameMain.BOARD_SIZE);
        this.y = GameBoard.mod((direction.yOffset + y), SquareGameMain.BOARD_SIZE);
    }
}
