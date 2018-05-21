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
import java.util.concurrent.ThreadLocalRandom;

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
        this.gameState.getPlayerList().stream().filter(Player::isPlaying).forEach(p -> {
            final int x = ThreadLocalRandom.current().nextInt(this.gameBoard.getBoardSize());
            final int y = ThreadLocalRandom.current().nextInt(this.gameBoard.getBoardSize());
            this.gameBoard.set(x, y, new MagicSquare(p, p.getStartingLogic(), new Location(x, y, Direction.CENTER, this.gameBoard.getBoardSize())));
            this.gameState.getScoreBoard().get(p).addGenerated();
        });
    }

    public void runAllTurns() {
        final GameBoard updatedGameBoard = new GameBoard(this.gameBoard.getBoardSize());
        final List<KillEvent> squareKills = new ArrayList<>();
        final List<Location> squareCollisions = new ArrayList<>();
        final Map<Player, List<MagicSquare>> playerSquareMap = new HashMap<>();
        final Map<Player, Score> scoreBoard = new HashMap<>(this.gameState.getScoreBoard());
        this.gameState.getPlayerList().forEach(p -> this.gameState.getScoreBoard().get(p).resetScore());
        for (int x = 0; x < this.gameBoard.getBoardSize(); x++) {
            for (int y = 0; y < this.gameBoard.getBoardSize(); y++) {
                final MagicSquare squareTakingTurn = this.gameBoard.get(x, y);
                if (squareTakingTurn != null) {
                    this.gameState.getScoreBoard().get(squareTakingTurn.getPlayer()).addPoint();
                    if (squareTakingTurn.getInactive() > 0) {
                        final Location location = new Location(x, y, Direction.CENTER, this.gameBoard.getBoardSize());
                        checkForCollisions(squareCollisions, updatedGameBoard, new MagicSquare(squareTakingTurn.getPlayer(),
                                squareTakingTurn.getSquareLogic(), location, squareTakingTurn.getInactive() - 1));
                    } else {
                        playerSquareMap.putIfAbsent(squareTakingTurn.getPlayer(), new ArrayList<>());
                        playerSquareMap.get(squareTakingTurn.getPlayer()).add(squareTakingTurn);
                    }
                }
            }
        }
        playerSquareMap.forEach((key, value) -> {

            final long startTime = System.nanoTime();
            executeSquareActions(value, updatedGameBoard, squareCollisions, squareKills, scoreBoard);
            final long endTime = System.nanoTime();

            final long output = (endTime - startTime) / value.size();
            this.gameState.getScoreBoard().get(key).setAvgTurnTime(output);
        });
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

    public void executeSquareActions(List<MagicSquare> magicSquares, GameBoard updatedGameBoard, List<Location> squareCollisions, List<KillEvent> squareKills, Map<Player, Score> scoreboard) {
        magicSquares.forEach(squareTakingTurn -> {
            final SquareAction squareAction = squareTakingTurn.getSquareLogic()
                    .run(new SquareView(this.gameBoard.getView(squareTakingTurn.getLocation().getX(), squareTakingTurn.getLocation().getY()),
                            squareTakingTurn.getPlayer(), squareTakingTurn.getLocation().getX(), squareTakingTurn.getLocation().getY(),
                            new PlayerAllowedMetadata(this.gameBoard.getBoardSize(), this.gameState.getRoundNumber(), new HashMap<>(scoreboard))));
            switch (squareAction.getAction()) {
                case ATTACK:
                    final Location attackLocation = squareTakingTurn.getLocation();
                    checkForCollisions(squareCollisions, updatedGameBoard, new MagicSquare(squareTakingTurn.getPlayer(), squareAction.getSquareLogic(), attackLocation));
                    squareKills.add(new KillEvent(squareTakingTurn.getPlayer(), new Location(squareTakingTurn.getLocation().getX(), squareTakingTurn.getLocation().getY(), squareAction.getDirection(), this.gameBoard.getBoardSize())));
                    break;
                case MOVE:
                    final Location moveLocation = new Location(squareTakingTurn.getLocation().getX(), squareTakingTurn.getLocation().getY(), squareAction.getDirection(), this.gameBoard.getBoardSize());
                    checkForCollisions(squareCollisions, updatedGameBoard, new MagicSquare(squareTakingTurn.getPlayer(), squareAction.getSquareLogic(), moveLocation));
                    break;
                case REPLICATE:
                    final Location replicateLocation1 = new Location(squareTakingTurn.getLocation().getX(), squareTakingTurn.getLocation().getY(), squareAction.getDirection(), this.gameBoard.getBoardSize());
                    final Location replicateLocation2 = squareTakingTurn.getLocation();
                    this.gameState.getScoreBoard().get(squareTakingTurn.getPlayer()).addGenerated();
                    checkForCollisions(squareCollisions, updatedGameBoard, new MagicSquare(squareTakingTurn.getPlayer(), squareAction.getReplicated(), replicateLocation1, INACTIVE_COUNT));
                    checkForCollisions(squareCollisions, updatedGameBoard, new MagicSquare(squareTakingTurn.getPlayer(), squareAction.getSquareLogic(), replicateLocation2, INACTIVE_COUNT));
                    break;
                case WAIT:
                    final Location waitLocation = squareTakingTurn.getLocation();
                    checkForCollisions(squareCollisions, updatedGameBoard, new MagicSquare(squareTakingTurn.getPlayer(), squareAction.getSquareLogic(), waitLocation));
            }
        });
    }

    public void checkForCollisions(List<Location> collisions, GameBoard updatedGameBoard, MagicSquare magicSquare) {

        if (updatedGameBoard.get(magicSquare.getLocation().getX(), magicSquare.getLocation().getY()) == null) {
            updatedGameBoard.set(magicSquare.getLocation().getX(), magicSquare.getLocation().getY(), magicSquare);
        } else {
            this.gameState.getScoreBoard().get(magicSquare.getPlayer()).addCollisions();
            collisions.add(magicSquare.getLocation());
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
