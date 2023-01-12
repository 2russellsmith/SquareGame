package squaregame.controller;

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
import squaregame.view.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.*;

public class GameBoardController {

    private static final int INACTIVE_COUNT = 100;
    private final LeaderboardPanel leaderBoardPanel;
    private final ButtonPanel buttonPanel;
    private final AISelectorPanel aiSelectorPanel;

    private Timer timer;

    private GameBoard gameBoard;
    private final GameState gameState;
    private final JPanel mainPanel;
    private final JLayeredPane gamePanel;
    private boolean isLeaderBoardMode = false;
    private JButton runLeaderboardRoundButton;

    public GameBoardController(JPanel mainPanel) {
        this.mainPanel = mainPanel;
        this.gamePanel = new JLayeredPane();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        this.mainPanel.add(gamePanel, gbc);
        this.gameBoard = new GameBoard(150);
        this.gameState = new GameState();
        this.aiSelectorPanel = new AISelectorPanel(this);
        this.aiSelectorPanel.setMinimumSize(mainPanel.getMaximumSize());
        GridBagConstraints gbcAI = new GridBagConstraints();
        gbcAI.fill = GridBagConstraints.HORIZONTAL;
        gbcAI.gridy = 0;
        this.mainPanel.add(this.aiSelectorPanel, gbcAI);
        this.buttonPanel = new ButtonPanel(this);
        this.leaderBoardPanel = new LeaderboardPanel(this);
        final GameBoardPanel gameBoardPanel = new GameBoardPanel(this);
//        gameBoardPanel.setPreferredSize(new Dimension(GameBoardPanel.MAX_BOARD_SIZE, GameBoardPanel.MAX_BOARD_SIZE));
        gameBoardPanel.setMinimumSize(mainPanel.getMaximumSize());
        this.gamePanel.setBackground(Color.BLACK);
        this.gamePanel.setLayout(new GridBagLayout());
        this.gamePanel.add(gameBoardPanel, gbc);
        this.gamePanel.setMinimumSize(mainPanel.getMaximumSize());
//        this.gamePanel.add(gameBoardPanel, 1);
        this.gamePanel.setMinimumSize(new Dimension(1000, 1000));
        this.gamePanel.setVisible(false);
        this.mainPanel.setLayout( new GridBagLayout() );
        this.mainPanel.add(this.aiSelectorPanel, gbc);
        GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.fill = GridBagConstraints.HORIZONTAL;
        gbcButtons.gridy = 1;
        this.mainPanel.add(this.buttonPanel, gbcButtons);
        this.mainPanel.setVisible(true);
        resetGame(false);
    }

    public void runRound() {
        runAllTurns();
        this.updateGameScore();
        if (this.gameState.gameOver()) {
            this.gameOver();
        } else {
            this.gameState.nextRound();
        }
        this.gamePanel.repaint();
    }

    private void gameOver() {
        this.timer.stop();
        if (isLeaderBoardMode) {

            this.gameState.finalRank();
            this.updateLeaderboards();
            this.runLeaderboardRoundButton.doClick();
        }

    }

    public void startGame() {
        this.gamePanel.setVisible(true);
        this.aiSelectorPanel.setVisible(false);

        if (this.gameState.getRoundNumber() == 0) {
            if (gameState.getPlayerList().stream().filter(Player::isPlaying).count() > 3) {
                this.gameBoard = new GameBoard(300);
                this.gameState.setTotalRounds(3000);
            } else {
                this.gameBoard = new GameBoard(150);
                this.gameState.setTotalRounds(2000);
            }
            setScoreBoard();
            setStartingPositions();
            if (isLeaderBoardMode) {
                this.timer = new Timer(10, e -> {
                    runRound();
                });
            } else {
                this.timer = new Timer(10, e -> {
                    runRound();
                });
            }
        }
        this.timer.start();
    }

    public void resetGame(boolean isLeaderBoardMode) {
        this.gamePanel.setVisible(false);
        this.aiSelectorPanel.setVisible(true);
        this.isLeaderBoardMode = isLeaderBoardMode;
        this.gameBoard = new GameBoard(300);
        if (this.timer != null) {
            this.timer.stop();
        }
        this.gameState.reset();
        this.gamePanel.repaint();
    }

    public void setScoreBoard() {
        for (int i = 1; i < this.gamePanel.getComponentCount(); i++){
            this.gamePanel.remove(i);
        }
        int numberOfActivePlayers = (int) this.gameState.getPlayerList().stream().filter(Player::isPlaying).count();
        JPanel scoreboardPanel = new JPanel();
        this.gameState.getPlayerList().stream().filter(Player::isPlaying).forEach(p -> {
            scoreboardPanel.add(this.aiSelectorPanel.getPlayerViewMap().get(p));
        });
//        this.gamePanel.add(scoreboardPanel, new GridBagConstraints());
//        this.gamePanel.add(scoreboardPanel, 2);
        this.gamePanel.repaint();
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
    public void oneRound() {
        this.timer.stop();
        this.runRound();
    }

    public void updateLeaderboards() {
        this.leaderBoardPanel.update(gameState);
    }

    public void updateGameScore() {
        this.aiSelectorPanel.getRoundLabel().setText("ROUND: " + gameState.getRoundNumber());
        gameState.getPlayerList().forEach(p -> {
            final Score score = gameState.getScoreBoard().get(p);
            this.aiSelectorPanel.getPlayerViewMap().get(p).setScore(score.getScore());
            this.aiSelectorPanel.getPlayerViewMap().get(p).setCollisions(score.getCollisions());
            this.aiSelectorPanel.getPlayerViewMap().get(p).setEliminated(score.getEliminated());
            this.aiSelectorPanel.getPlayerViewMap().get(p).setGenerated(score.getGenerated());
            this.aiSelectorPanel.getPlayerViewMap().get(p).setKills(score.getKilled());
            this.aiSelectorPanel.getPlayerViewMap().get(p).setTurnClock(score.getAvgTurnTime());
            this.aiSelectorPanel.getPlayerViewMap().get(p).setColor();
        });
    }

    public void setRunLeaderboardRoundButton(JButton runLeaderboardRoundButton) {
        this.runLeaderboardRoundButton = runLeaderboardRoundButton;
    }
}
