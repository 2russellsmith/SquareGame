package squaregame;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import squaregame.model.Direction;
import squaregame.model.GameState;
import squaregame.model.Location;
import squaregame.model.Score;
import squaregame.squares.SquareAction;

/**
 * Created by Russell on 5/5/18.
 */
public class GameBoard {
    private final GameState gameState;
    private MagicSquare[][] squares;
    private static final int INACTIVE_COUNT = 100;

    public GameBoard(MagicSquare[][] magicSquares, GameState gameState) {
        this.squares = magicSquares;
        this.gameState = gameState;
    }

    public void draw(Graphics2D g2){
        for (int i = 0; i < SquareGameMain.BOARD_SIZE; i++) {
            for (int j = 0; j < SquareGameMain.BOARD_SIZE; j++) {
                if (squares[i][j] != null) {
                    g2.setColor(squares[i][j].getPlayer().getColor());
                    g2.fillRect(i * SquareGameMain.SQUARE_SIZE, j * SquareGameMain.SQUARE_SIZE,
                            SquareGameMain.SQUARE_SIZE, SquareGameMain.SQUARE_SIZE);
                }
            }
        }
    }
    public void setStartingPositions(List<Player> playerList) {
        playerList.forEach(p -> {
            squares[MiscUtilities.getRandomNumber(SquareGameMain.BOARD_SIZE)][MiscUtilities.getRandomNumber(SquareGameMain.BOARD_SIZE)] =
                    new MagicSquare(p, p.getStartingLogic());
        });
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
            return squares[mod((direction.getxOffset() + i), squares.length)][mod((direction.getyOffset() + j), squares.length)].getPlayer();
        } catch (NullPointerException e) {
            return null;
        }
    }

    private void set(int i, int j, Direction direction, MagicSquare[][] squaresMatrix, MagicSquare magicSquare) {
        squaresMatrix[mod((direction.getxOffset() + i), squaresMatrix.length)][mod((direction.getyOffset() + j), squaresMatrix.length)] = magicSquare;
    }
    private MagicSquare get(int i, int j, Direction direction, MagicSquare[][] squaresMatrix) {
        return squaresMatrix[mod((direction.getxOffset() + i), squaresMatrix.length)][mod((direction.getyOffset() + j), squaresMatrix.length)];
    }

    public void runAllTurns() {
        final MagicSquare[][] squaresNextTurn = new MagicSquare[SquareGameMain.BOARD_SIZE][SquareGameMain.BOARD_SIZE];
        final List<Location> squareDeletes = new ArrayList<>();
        final Map<Player, Score> currentScore = new HashMap<>();
        this.gameState.getPlayerList().forEach(p -> {
            currentScore.put(p, new Score());
        });
        for (int i = 0; i < SquareGameMain.BOARD_SIZE; i++) {
            for (int j = 0; j < SquareGameMain.BOARD_SIZE; j++) {
                if (squares[i][j] != null) {
                    currentScore.get(squares[i][j].getPlayer()).addPoint();
                    if (squares[i][j].getInactive() > 0) {
                        if (get(i, j, Direction.CENTER, squaresNextTurn) == null) {
                            set(i, j, Direction.CENTER, squaresNextTurn, new MagicSquare(squares[i][j].getPlayer(),
                                    squares[i][j].getSquareLogic(), (squares[i][j].getInactive() - 1)));
                        } else {
                            squareDeletes.add(new Location(i, j, Direction.CENTER));
                        }
                    } else {
                        final SquareAction squareAction = squares[i][j].getSquareLogic().run(i, j, getView(i, j));
                        switch (squareAction.getAction()) {
                            case ATTACK:
                                if (get(i, j, Direction.CENTER, squaresNextTurn) == null) {
                                    set(i, j, Direction.CENTER, squaresNextTurn, new MagicSquare(squares[i][j].getPlayer(), squareAction.getSquareLogic()));
                                } else {
                                    squareDeletes.add(new Location(i, j, Direction.CENTER));
                                }
                                squareDeletes.add(new Location(i, j, squareAction.getDirection()));
                                break;
                            case MOVE:
                                if (get(i, j, squareAction.getDirection(), squaresNextTurn) == null) {
                                    set(i, j, squareAction.getDirection(), squaresNextTurn, new MagicSquare(squares[i][j].getPlayer(),
                                            squareAction.getSquareLogic()));
                                } else {
                                    squareDeletes.add(new Location(i, j, squareAction.getDirection()));
                                }
                                break;
                            case REPLICATE:
                                if (get(i, j, squareAction.getDirection(), squaresNextTurn) == null) {
                                    set(i, j, squareAction.getDirection(), squaresNextTurn, new MagicSquare(squares[i][j].getPlayer(),
                                            squareAction.getReplicated(), INACTIVE_COUNT));
                                } else {
                                    squareDeletes.add(new Location(i, j, squareAction.getDirection()));
                                }
                                if (get(i, j, Direction.CENTER, squaresNextTurn) == null) {
                                    set(i, j, Direction.CENTER, squaresNextTurn, new MagicSquare(squares[i][j].getPlayer(), squareAction.getSquareLogic(), INACTIVE_COUNT));
                                } else {
                                    squareDeletes.add(new Location(i, j, Direction.CENTER));
                                }
                                break;
                            case WAIT:
                                if (get(i, j, Direction.CENTER, squaresNextTurn) == null) {
                                    set(i, j, Direction.CENTER, squaresNextTurn, new MagicSquare(squares[i][j].getPlayer(), squareAction.getSquareLogic()));
                                } else {
                                    squareDeletes.add(new Location(i, j, Direction.CENTER));
                                }
                        }
                    }
                }
            }
        }
        gameState.setScoreBoard(currentScore);
        squares = squaresNextTurn;
        squareDeletes.forEach(location -> squares[location.getX()][location.getY()] = null);
    }

    public static int mod(int x, int y)
    {
        final int result = x % y;
        return result < 0? result + y : result;
    }
}
