package squaregame.controller;

import squaregame.SquareGameMain;
import squaregame.model.Direction;
import squaregame.model.GameBoard;
import squaregame.model.GameState;
import squaregame.model.KillEvent;
import squaregame.model.Location;
import squaregame.model.MagicSquare;
import squaregame.model.Player;
import squaregame.model.PlayerAllowedMetadata;
import squaregame.model.Score;
import squaregame.model.SquareAction;
import squaregame.model.SquareView;
import squaregame.squares.SquareLogic;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameBoardController {

    private static final int INACTIVE_COUNT = 100;

    private Timer timer;

    private GameBoard gameBoard;
    private GameState gameState;
    private SquareGameMain squareGameMain;
    private boolean isLeaderBoardMode = false;
    private JButton runLeaderboardRoundButton;

    public GameBoardController(SquareGameMain squareGameMain) {
        this.squareGameMain = squareGameMain;
        this.timer = new Timer(0, e -> {
            runRound();
            squareGameMain.repaint();
        });
        this.gameBoard = new GameBoard(150);
        this.gameState = new GameState();
        resetGame(false);
    }

    public void runRound() {
        runAllTurns();
        this.squareGameMain.updateGameScore(this.gameState);
        if (this.gameState.gameOver()) {
            this.gameOver();
        } else {
            this.gameState.nextRound();
        }
    }

    private void gameOver() {
        this.timer.stop();
        if (isLeaderBoardMode) {
            this.gameState.finalRank();
            this.squareGameMain.updateLeaderboards(this.gameState);
            this.runLeaderboardRoundButton.doClick();
        }

    }

    public void startGame() {
        if (this.gameState.getRoundNumber() == 0) {
            if (gameState.getPlayerList().stream().filter(Player::isPlaying).count() > 3) {
                this.gameBoard = new GameBoard(300);
                this.gameState.setTotalRounds(3000);
            } else {
                this.gameBoard = new GameBoard(150);
                this.gameState.setTotalRounds(2000);
            }
            setStartingPositions();
            if (isLeaderBoardMode) {
                this.timer = new Timer(0, e -> {
                    runRound();
                    squareGameMain.repaint();
                });
            } else {
                this.timer = new Timer(0, e -> {
                    runRound();
                    squareGameMain.repaint();
                });
            }
        }
        this.timer.start();
    }

    public void resetGame(boolean isLeaderBoardMode) {
        this.isLeaderBoardMode = isLeaderBoardMode;
        this.gameBoard = new GameBoard(300);
        this.timer.stop();
        this.gameState.reset();
        squareGameMain.repaint();
    }

    public void setStartingPositions() {
        final Random random = new Random();
        this.gameState.getPlayerList().stream().filter(Player::isPlaying).forEach(p -> {
            this.gameBoard.set(random.nextInt(this.gameBoard.getBoardSize()),
                    random.nextInt(this.gameBoard.getBoardSize()), new MagicSquare(p, p.getStartingLogic()));
            this.gameState.getScoreBoard().get(p).addGenerated();
        });
    }

    public void runAllTurns() {
        final GameBoard updatedGameBoard = new GameBoard(this.gameBoard.getBoardSize());
        final List<KillEvent> squareKills = new ArrayList<>();
        final List<Location> squareCollisions = new ArrayList<>();
        this.gameState.getPlayerList().forEach(p -> this.gameState.getScoreBoard().get(p).resetScore());
        for (int x = 0; x < this.gameBoard.getBoardSize(); x++) {
            for (int y = 0; y < this.gameBoard.getBoardSize(); y++) {
                if (this.gameBoard.get(x, y) != null) {
                    this.gameState.getScoreBoard().get(this.gameBoard.get(x, y).getPlayer()).addPoint();
                    if (this.gameBoard.get(x, y).getInactive() > 0) {
                        checkForCollisions(x, y, Direction.CENTER, squareCollisions, updatedGameBoard, new MagicSquare(this.gameBoard.get(x, y).getPlayer(),
                                this.gameBoard.get(x, y).getSquareLogic(), this.gameBoard.get(x, y).getInactive() - 1));
                    } else {
                        final SquareAction squareAction = this.gameBoard.get(x, y).getSquareLogic()
                                .run(new SquareView(this.gameBoard.getView(x, y),
                                        this.gameBoard.getPlayer(x, y, Direction.CENTER), x, y,
                                        new PlayerAllowedMetadata(this.gameBoard.getBoardSize(), this.gameState.getRoundNumber())));
                        switch (squareAction.getAction()) {
                            case ATTACK:
                                checkForCollisions(x, y, Direction.CENTER, squareCollisions, updatedGameBoard, new MagicSquare(this.gameBoard.get(x, y).getPlayer(), squareAction.getSquareLogic()));
                                squareKills.add(new KillEvent(this.gameBoard.get(x, y).getPlayer(), new Location(x, y, squareAction.getDirection(), this.gameBoard.getBoardSize())));
                                break;
                            case MOVE:
                                checkForCollisions(x, y, squareAction.getDirection(), squareCollisions, updatedGameBoard, new MagicSquare(this.gameBoard.get(x, y).getPlayer(), squareAction.getSquareLogic()));
                                break;
                            case REPLICATE:
                                this.gameState.getScoreBoard().get(this.gameBoard.get(x, y).getPlayer()).addGenerated();
                                checkForCollisions(x, y, squareAction.getDirection(), squareCollisions, updatedGameBoard, new MagicSquare(this.gameBoard.get(x, y).getPlayer(), squareAction.getReplicated(), INACTIVE_COUNT));
                                checkForCollisions(x, y, Direction.CENTER, squareCollisions, updatedGameBoard, new MagicSquare(this.gameBoard.get(x, y).getPlayer(), squareAction.getSquareLogic(), INACTIVE_COUNT));
                                break;
                            case WAIT:
                                checkForCollisions(x, y, Direction.CENTER, squareCollisions, updatedGameBoard, new MagicSquare(this.gameBoard.get(x, y).getPlayer(), squareAction.getSquareLogic()));
                        }
                    }
                }
            }
        }
        this.gameState.rankNewDeadPlayers();
        this.gameBoard = updatedGameBoard;
        squareCollisions.forEach(location -> {
            if (this.gameBoard.get(location.getX(), location.getY()) != null) {
                this.gameState.getScoreBoard().get(this.gameBoard.get(location.getX(), location.getY()).getPlayer()).addCollisions();
                this.gameBoard.set(location.getX(), location.getY(), null);
            }
        });
        squareKills.forEach(killEvent -> {
            if (this.gameBoard.get(killEvent.getLocation().getX(), killEvent.getLocation().getY()) != null) {
                this.gameState.getScoreBoard().get(killEvent.getAttacker()).addKilled();
                this.gameState.getScoreBoard().get(this.gameBoard.get(killEvent.getLocation().getX(), killEvent.getLocation().getY()).getPlayer()).addEliminated();
                this.gameBoard.set(killEvent.getLocation().getX(), killEvent.getLocation().getY(), null);
            }
        });
    }

    public void checkForCollisions(int x, int y, Direction direction, List<Location> collisions, GameBoard updatedGameBoard, MagicSquare magicSquare) {

        if (updatedGameBoard.get(x, y, direction) == null) {
            updatedGameBoard.set(x, y, direction, magicSquare);
        } else {
            this.gameState.getScoreBoard().get(this.gameBoard.get(x, y).getPlayer()).addCollisions();
            collisions.add(new Location(x, y, direction, this.gameBoard.getBoardSize()));
        }
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

    public void setRunLeaderboardRoundButton(JButton runLeaderboardRoundButton) {
        this.runLeaderboardRoundButton = runLeaderboardRoundButton;
    }
}
