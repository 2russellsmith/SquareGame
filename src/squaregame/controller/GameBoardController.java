package squaregame.controller;

import squaregame.model.GameBoard;
import squaregame.SquareGameMain;
import squaregame.model.Direction;
import squaregame.model.GameState;
import squaregame.model.Location;
import squaregame.model.MagicSquare;
import squaregame.model.Player;
import squaregame.model.Score;
import squaregame.model.SquareAction;
import squaregame.squares.SquareLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import javax.swing.Timer;

public class GameBoardController {

    private static final Integer BOARD_SIZE = 150;
    private static final int INACTIVE_COUNT = 100;

    private final Timer timer;

    private GameBoard gameBoard;
    private GameState gameState;
    private SquareGameMain squareGameMain;

    public GameBoardController(SquareGameMain squareGameMain) {
        this.squareGameMain = squareGameMain;
        this.timer = new Timer(1, e -> {
            runRound();
            squareGameMain.repaint();
        });
        this.gameState = new GameState();
        resetGame();
    }

    public void runRound() {
        runAllTurns();
        if (this.gameState.someoneWon() || this.gameState.getRoundNumber() >= 10000) {
            this.timer.stop();
        }
        this.gameState.nextRound();
    }

    public void startGame() {
        if (this.gameState.getRoundNumber() == 0) {
            setStartingPositions();
        }
        this.timer.start();
    }

    public void resetGame() {
        this.timer.stop();
        this.gameBoard = new GameBoard(BOARD_SIZE);
        this.gameState.reset();
        squareGameMain.repaint();
    }

    public void setStartingPositions() {
        final Random random = new Random();
        this.gameState.getPlayerList().stream().filter(p -> p.getStartingLogic() != null).forEach(p -> this.gameBoard.set(random.nextInt(BOARD_SIZE),
                random.nextInt(BOARD_SIZE), new MagicSquare(p, p.getStartingLogic())));
    }

    public void runAllTurns() {
        final GameBoard updatedGameBoard = new GameBoard(BOARD_SIZE);
        final List<Location> squareDeletes = new ArrayList<>();
        final Map<Player, Score> currentScore = new HashMap<>();
        this.gameState.getPlayerList().forEach(p -> {
            currentScore.put(p, new Score());
        });
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (this.gameBoard.get(i, j) != null) {
                    currentScore.get(this.gameBoard.get(i, j).getPlayer()).addPoint();
                    if (this.gameBoard.get(i, j).getInactive() > 0) {
                        if (updatedGameBoard.get(i, j, Direction.CENTER) == null) {
                            updatedGameBoard.set(i, j, Direction.CENTER, new MagicSquare(this.gameBoard.get(i, j).getPlayer(),
                                    this.gameBoard.get(i, j).getSquareLogic(), (this.gameBoard.get(i, j).getInactive() - 1)));
                        } else {
                            squareDeletes.add(new Location(i, j, Direction.CENTER));
                        }
                    } else {
                        final SquareAction squareAction = this.gameBoard.get(i, j).getSquareLogic()
                                .run(this.gameBoard.get(i, j), i, j, this.gameBoard.getView(i, j));
                        switch (squareAction.getAction()) {
                            case ATTACK:
                                if (updatedGameBoard.get(i, j, Direction.CENTER) == null) {
                                    updatedGameBoard.set(i, j, Direction.CENTER, new MagicSquare(this.gameBoard.get(i, j).getPlayer(), squareAction.getSquareLogic()));
                                } else {
                                    squareDeletes.add(new Location(i, j, Direction.CENTER));
                                }
                                squareDeletes.add(new Location(i, j, squareAction.getDirection()));
                                break;
                            case MOVE:
                                if (updatedGameBoard.get(i, j, squareAction.getDirection()) == null) {
                                    updatedGameBoard.set(i, j, squareAction.getDirection(), new MagicSquare(this.gameBoard.get(i, j).getPlayer(),
                                            squareAction.getSquareLogic()));
                                } else {
                                    squareDeletes.add(new Location(i, j, squareAction.getDirection()));
                                }
                                break;
                            case REPLICATE:
                                if (updatedGameBoard.get(i, j, squareAction.getDirection()) == null) {
                                    updatedGameBoard.set(i, j, squareAction.getDirection(), new MagicSquare(this.gameBoard.get(i, j).getPlayer(),
                                            squareAction.getReplicated(), INACTIVE_COUNT));
                                } else {
                                    squareDeletes.add(new Location(i, j, squareAction.getDirection()));
                                }
                                if (updatedGameBoard.get(i, j, Direction.CENTER) == null) {
                                    updatedGameBoard.set(i, j, Direction.CENTER, new MagicSquare(this.gameBoard.get(i, j).getPlayer(), squareAction.getSquareLogic(), INACTIVE_COUNT));
                                } else {
                                    squareDeletes.add(new Location(i, j, Direction.CENTER));
                                }
                                break;
                            case WAIT:
                                if (updatedGameBoard.get(i, j, Direction.CENTER) == null) {
                                    updatedGameBoard.set(i, j, Direction.CENTER, new MagicSquare(this.gameBoard.get(i, j).getPlayer(), squareAction.getSquareLogic()));
                                } else {
                                    squareDeletes.add(new Location(i, j, Direction.CENTER));
                                }
                        }
                    }
                }
            }
        }
        gameState.setScoreBoard(currentScore);
        this.gameBoard = updatedGameBoard;
        squareDeletes.forEach(location -> this.gameBoard.set(location.getX(), location.getY(), null));
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void stopGame() {
        this.timer.stop();
    }

}
