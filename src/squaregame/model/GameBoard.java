package squaregame.model;

import squaregame.model.Direction;
import squaregame.model.MagicSquare;
import squaregame.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Russell on 5/5/18.
 */
public class GameBoard {

    private MagicSquare[][] squares;

    public GameBoard(int boardSize) {
        this.squares = new MagicSquare[boardSize][boardSize];
    }

    public List<Player> getView(int i, int j) {
        final List<Player> view = new ArrayList<>();
        view.add(0, getPlayer(i, j, Direction.NW));
        view.add(1, getPlayer(i, j, Direction.N));
        view.add(2, getPlayer(i, j, Direction.NE));
        view.add(3, getPlayer(i, j, Direction.E));
        view.add(4, getPlayer(i, j, Direction.SE));
        view.add(5, getPlayer(i, j, Direction.S));
        view.add(6, getPlayer(i, j, Direction.SW));
        view.add(7, getPlayer(i, j, Direction.W));
        return view;
    }

    public Player getPlayer(int i, int j, Direction direction) {
        try {
            return get(i, j, direction).getPlayer();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public void set(int i, int j, Direction direction, MagicSquare magicSquare) {
        squares[Math.floorMod((direction.getxOffset() + i), squares.length)][Math.floorMod((direction.getyOffset() + j), squares.length)] = magicSquare;
    }

    public void set(int i, int j, MagicSquare magicSquare) {
        squares[Math.floorMod(i, squares.length)][Math.floorMod(j, squares.length)] = magicSquare;
    }

    public MagicSquare get(int i, int j, Direction direction) {
        return squares[Math.floorMod((direction.getxOffset() + i), squares.length)][Math.floorMod((direction.getyOffset() + j), squares.length)];
    }

    public MagicSquare get(int i, int j) {
        return squares[Math.floorMod(i, squares.length)][Math.floorMod(j, squares.length)];
    }

    public int getBoardSize() {
        return this.squares.length;
    }
}
