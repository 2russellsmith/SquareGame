package squaregame.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Russell on 5/5/18.
 */
public class GameBoard {

    private final MagicSquare[][] squares;

    public GameBoard(int boardSize) {
        this.squares = new MagicSquare[boardSize][boardSize];
    }

    public List<Player> getView(int col, int row) {
        final List<Player> view = new ArrayList<>();
        view.add(0, getPlayer(col, row, Direction.NW));
        view.add(1, getPlayer(col, row, Direction.N));
        view.add(2, getPlayer(col, row, Direction.NE));
        view.add(3, getPlayer(col, row, Direction.E));
        view.add(4, getPlayer(col, row, Direction.SE));
        view.add(5, getPlayer(col, row, Direction.S));
        view.add(6, getPlayer(col, row, Direction.SW));
        view.add(7, getPlayer(col, row, Direction.W));
        return view;
    }

    public Player getPlayer(int col, int row, Direction direction) {
        try {
            return get(col, row, direction).getPlayer();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public void set(int col, int row, Direction direction, MagicSquare magicSquare) {
        squares[Math.floorMod((direction.getxOffset() + col), squares.length)][Math.floorMod((direction.getyOffset() + row), squares.length)] = magicSquare;
    }

    public void set(int col, int row, MagicSquare magicSquare) {
        squares[Math.floorMod(col, squares.length)][Math.floorMod(row, squares.length)] = magicSquare;
    }

    public MagicSquare get(int col, int row, Direction direction) {
        return squares[Math.floorMod((direction.getxOffset() + col), squares.length)][Math.floorMod((direction.getyOffset() + row), squares.length)];
    }

    public MagicSquare get(int col, int row) {
        return squares[Math.floorMod(col, squares.length)][Math.floorMod(row, squares.length)];
    }

    public int getBoardSize() {
        return this.squares.length;
    }
}
