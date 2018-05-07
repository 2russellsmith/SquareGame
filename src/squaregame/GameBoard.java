package squaregame;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import squaregame.model.GameState;
import squaregame.model.Score;
import squaregame.squares.SquareAction;

/**
 * Created by Russell on 5/5/18.
 */
public class GameBoard {
    private final GameState gameState;
    private MagicSquare[][] squares;
    private final int INACTIVE_COUNT = 100;

    public GameBoard(MagicSquare[][] magicSquares, GameState gameState) {
        this.squares = magicSquares;
        this.gameState = gameState;
    }

    public void draw(Graphics2D g2){
        for (int i = 0; i < SquareGameMain.BOARD_SIZE; i++) {
            for (int j = 0; j < SquareGameMain.BOARD_SIZE; j++) {
                if (squares[i][j] != null) {
                    g2.setColor(squares[i][j].player.color);
                    g2.fillRect(i * SquareGameMain.SQUARE_SIZE, j * SquareGameMain.SQUARE_SIZE,
                            SquareGameMain.SQUARE_SIZE, SquareGameMain.SQUARE_SIZE);
                }
            }
        }
    }
    public void setStartingPositions(List<Player> playerList) {
        playerList.forEach(p -> {
            squares[MiscUtilities.getRandomNumber(SquareGameMain.BOARD_SIZE)][MiscUtilities.getRandomNumber(SquareGameMain.BOARD_SIZE)] =
                    new MagicSquare(p, p.startingLogic);
        });
    }

    public List<Player> getView(int i, int j) {
        List<Player> view = new ArrayList<>();
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
            return squares[mod((direction.xOffset + i), squares.length)][mod((direction.yOffset + j), squares.length)].player;
        } catch (NullPointerException e) {
            return null;
        }
    }

    private void set(int i, int j, Direction direction, MagicSquare[][] squaresMatrix, MagicSquare magicSquare) {
        squaresMatrix[mod((direction.xOffset + i), squaresMatrix.length)][mod((direction.yOffset + j), squaresMatrix.length)] = magicSquare;
    }
    private MagicSquare get(int i, int j, Direction direction, MagicSquare[][] squaresMatrix) {
        return squaresMatrix[mod((direction.xOffset + i), squaresMatrix.length)][mod((direction.yOffset + j), squaresMatrix.length)];
    }

    public void runAllTurns() {
        MagicSquare[][] squaresNextTurn = new MagicSquare[SquareGameMain.BOARD_SIZE][SquareGameMain.BOARD_SIZE];
        List<Location> squareDeletes = new ArrayList<>();
        Map<Player, Score> currentScore = new HashMap<>();
        this.gameState.playerList.forEach(p -> {
            currentScore.put(p, new Score());
        });
        for (int i = 0; i < SquareGameMain.BOARD_SIZE; i++) {
            for (int j = 0; j < SquareGameMain.BOARD_SIZE; j++) {
                if (squares[i][j] != null) {
                    currentScore.get(squares[i][j].player).addPoint();
                    if (squares[i][j].inactive > 0) {
                        if (get(i, j, Direction.CENTER, squaresNextTurn) == null) {
                            set(i, j, Direction.CENTER, squaresNextTurn, new MagicSquare(squares[i][j].player,
                                    squares[i][j].squareLogic, (squares[i][j].inactive - 1)));
                        } else {
                            squareDeletes.add(new Location(i, j, Direction.CENTER));
                        }
                    } else {
                        SquareAction squareAction = squares[i][j].squareLogic.run(i, j, getView(i, j));
                        switch (squareAction.action) {
                            case ATTACK:
                                if (get(i, j, Direction.CENTER, squaresNextTurn) == null) {
                                    set(i, j, Direction.CENTER, squaresNextTurn, new MagicSquare(squares[i][j].player, squareAction.squareLogic));
                                } else {
                                    squareDeletes.add(new Location(i, j, Direction.CENTER));
                                }
                                squareDeletes.add(new Location(i, j, squareAction.direction));
                                break;
                            case MOVE:
                                if (get(i, j, squareAction.direction, squaresNextTurn) == null) {
                                    set(i, j, squareAction.direction, squaresNextTurn, new MagicSquare(squares[i][j].player, squareAction.squareLogic));
                                } else {
                                    squareDeletes.add(new Location(i, j, squareAction.direction));
                                }
                                break;
                            case REPLICATE:
                                if (get(i, j, squareAction.direction, squaresNextTurn) == null) {
                                    set(i, j, squareAction.direction, squaresNextTurn, new MagicSquare(squares[i][j].player, squareAction.replicated, INACTIVE_COUNT));
                                } else {
                                    squareDeletes.add(new Location(i, j, squareAction.direction));
                                }
                                if (get(i, j, Direction.CENTER, squaresNextTurn) == null) {
                                    set(i, j, Direction.CENTER, squaresNextTurn, new MagicSquare(squares[i][j].player, squareAction.squareLogic, INACTIVE_COUNT));
                                } else {
                                    squareDeletes.add(new Location(i, j, Direction.CENTER));
                                }
                                break;
                            case WAIT:
                                if (get(i, j, Direction.CENTER, squaresNextTurn) == null) {
                                    set(i, j, Direction.CENTER, squaresNextTurn, new MagicSquare(squares[i][j].player, squareAction.squareLogic));
                                } else {
                                    squareDeletes.add(new Location(i, j, Direction.CENTER));
                                }
                        }
                    }
                }
            }
        }
        gameState.scoreBoard = currentScore;
        squares = squaresNextTurn;
        squareDeletes.forEach(location -> squares[location.x][location.y] = null);
    }
    public enum Direction {
        NW(-1, -1),
        N(0, -1),
        NE(1, -1),
        E(1, 0),
        SE(1, 1),
        S(0, 1),
        SW(-1, 1),
        W(-1, 0),
        CENTER(0, 0);
        final int xOffset;
        final int yOffset;
        Direction(int xOffset, int yOffset) {
            this.xOffset = xOffset;
            this.yOffset = yOffset;
        }
    }
    public static int mod(int x, int y)
    {
        int result = x % y;
        return result < 0? result + y : result;
    }
}
