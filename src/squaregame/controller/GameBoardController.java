package squaregame.controller;

import squaregame.model.*;
import squaregame.view.*;

import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static squaregame.view.PlayerView.newColorWithAlpha;

public class GameBoardController {

    private static final int INACTIVE_COUNT = 100;
    public static Font GLOBAL_FONT = new Font(Font.DIALOG, Font.PLAIN, 24);
    private final LeaderboardPanel leaderBoardPanel;
    private final ButtonPanel buttonPanel;
    private final AISelectorPanel aiSelectorPanel;
    private final GameState gameState;
    private final JPanel mainPanel;
    private final JPanel gamePanel;
    private Timer timer;
    private GameBoard gameBoard;
    private boolean isLeaderBoardMode = false;
    private JButton runLeaderboardRoundButton;
    private JPanel scoreBoardPanel;
    private JLabel roundLabel;
    private boolean debugEnabled = false;
    private final Map<Player, PlayerView> playerViewMap;
    private int timerSpeed = 10;

    public GameBoardController(JPanel mainPanel) {
        this.mainPanel = mainPanel;
        this.mainPanel.setLayout(new GridBagLayout());
        GLOBAL_FONT = getGlobalFont(this.mainPanel.getHeight() / 60);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        this.gamePanel = new JPanel();
        this.mainPanel.add(gamePanel, gbc);
        this.gameBoard = new GameBoard(150);
        this.gameState = new GameState();
        this.playerViewMap = new HashMap<>();
        this.aiSelectorPanel = new AISelectorPanel(this);
        gbc.insets = new Insets(150, 150, 0, 150);
        this.mainPanel.add(this.aiSelectorPanel, gbc);
        this.buttonPanel = new ButtonPanel(this);
        this.runLeaderboardRoundButton.addActionListener(this.aiSelectorPanel);
        this.leaderBoardPanel = new LeaderboardPanel();
        this.updateLeaderboards();
        final GameBoardPanel gameBoardPanel = new GameBoardPanel(this);
        this.gamePanel.setOpaque(false);
        this.gamePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcGamePanel = new GridBagConstraints();
        gbcGamePanel.fill = GridBagConstraints.BOTH;
        gbcGamePanel.weightx = .5;
        this.gamePanel.add(this.leaderBoardPanel, gbcGamePanel);
        gbcGamePanel.weightx = 0;
        this.gamePanel.add(gameBoardPanel, gbcGamePanel);
        this.gamePanel.setMinimumSize(mainPanel.getMaximumSize());
        this.gamePanel.setVisible(false);
        GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.fill = GridBagConstraints.HORIZONTAL;
        gbcButtons.gridy = 1;
        gbc.weighty = .1;
        this.mainPanel.add(this.buttonPanel, gbcButtons);
        this.mainPanel.setVisible(true);
        this.aiSelectorPanel.resize();
        resetGame(false);
    }

    public static Font getGlobalFont(int size) {
        return new Font(GLOBAL_FONT.getFontName(), GLOBAL_FONT.getStyle(), size);
    }

    public static Font getGlobalFont() {
        return new Font(GLOBAL_FONT.getFontName(), GLOBAL_FONT.getStyle(), GLOBAL_FONT.getSize());
    }

    public void runRound() {
        runAllTurns();
        this.updateGameScore();
        this.updateLeaderboards();
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
            this.updateLeaderboards();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.runLeaderboardRoundButton.doClick();
        }
    }

    public void startGame() {
        this.gamePanel.setVisible(true);
        this.aiSelectorPanel.setVisible(false);

        if (this.gameState.getRoundNumber() == 0) {
            if (gameState.getPlayerList().stream().filter(Player::isPlaying).count() > 3) {
                this.gameBoard = new GameBoard(300);
                this.gameState.setTotalRounds(5000);
            } else {
                this.gameBoard = new GameBoard(300);
                this.gameState.setTotalRounds(5000);
            }
            setScoreBoard();
            setStartingPositions();
            this.timer = new Timer(this.timerSpeed, e -> runRound());
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
        this.mainPanel.repaint();
    }

    public void toggleDebug() {
        this.debugEnabled = !this.debugEnabled;
    }

    public void setScoreBoard() {
        if (this.scoreBoardPanel == null) {
            this.scoreBoardPanel = new JPanel();
            this.scoreBoardPanel.setOpaque(false);
            this.scoreBoardPanel.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = .5;
            gbc.gridx = 2;
            gbc.gridy = 0;
            this.gamePanel.add(scoreBoardPanel, gbc);
            this.getGameState().getPlayerList().forEach(p -> {
                final PlayerView playerView = new PlayerView(p);
                playerViewMap.put(p, playerView);
            });
            this.roundLabel = new JLabel("ROUND: 0");
            this.roundLabel.setFont(GLOBAL_FONT);
            this.roundLabel.setForeground(Color.WHITE);
            this.roundLabel.setBackground(newColorWithAlpha(Color.BLACK));
            this.roundLabel.setOpaque(true);
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.weightx = 1;
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
            this.scoreBoardPanel.add(roundLabel, gridBagConstraints);
        }
        this.playerViewMap.values().forEach(pv -> pv.setVisible(false));
        final AtomicInteger row = new AtomicInteger(1);
        List<Player> sortedPlayers = this.gameState.getPlayerList().stream()
                .filter(Player::isPlaying)
                .sorted(Comparator.comparing(p -> this.gameState.getScoreBoard().get(p).getScore()).reversed())
                .collect(Collectors.toList());
        sortedPlayers.forEach(p -> {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridy = row.getAndIncrement();
            gbc.fill = GridBagConstraints.BOTH;
            gbc.anchor = GridBagConstraints.WEST;
            this.scoreBoardPanel.add(this.playerViewMap.get(p), gbc);
            this.playerViewMap.get(p).setVisible(true);
        });
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
        final Set<KillEvent> squareKills = new HashSet<>();
        final Set<Location> squareCollisions = new HashSet<>();
        final Map<Player, Set<MagicSquare>> playerSquareMap = new HashMap<>();
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
                        playerSquareMap.putIfAbsent(squareTakingTurn.getPlayer(), new HashSet<>());
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

    public void executeSquareActions(Set<MagicSquare> magicSquares, GameBoard updatedGameBoard, Set<Location> squareCollisions, Set<KillEvent> squareKills, Map<Player, Score> scoreboard) {
        magicSquares.forEach(squareTakingTurn -> {
            final SquareAction squareAction = squareTakingTurn.getSquareLogic()
                    .run(new SquareView(this.gameBoard.getView(squareTakingTurn.getLocation().getX(), squareTakingTurn.getLocation().getY()),
                            squareTakingTurn.getPlayer(), squareTakingTurn.getLocation().getX(), squareTakingTurn.getLocation().getY(),
                            new PlayerAllowedMetadata(this.gameBoard.getBoardSize(), this.gameState.getRoundNumber(), new HashMap<>(scoreboard))));
            switch (squareAction.getAction()) {
                case ATTACK -> {
                    final Location attackLocation = squareTakingTurn.getLocation();
                    checkForCollisions(squareCollisions, updatedGameBoard, new MagicSquare(squareTakingTurn.getPlayer(), squareAction.getSquareLogic(), attackLocation));
                    squareKills.add(new KillEvent(squareTakingTurn.getPlayer(), new Location(squareTakingTurn.getLocation().getX(), squareTakingTurn.getLocation().getY(), squareAction.getDirection(), this.gameBoard.getBoardSize())));
                }
                case MOVE -> {
                    final Location moveLocation = new Location(squareTakingTurn.getLocation().getX(), squareTakingTurn.getLocation().getY(), squareAction.getDirection(), this.gameBoard.getBoardSize());
                    checkForCollisions(squareCollisions, updatedGameBoard, new MagicSquare(squareTakingTurn.getPlayer(), squareAction.getSquareLogic(), moveLocation));
                }
                case REPLICATE -> {
                    final Location replicateLocation1 = new Location(squareTakingTurn.getLocation().getX(), squareTakingTurn.getLocation().getY(), squareAction.getDirection(), this.gameBoard.getBoardSize());
                    final Location replicateLocation2 = squareTakingTurn.getLocation();
                    this.gameState.getScoreBoard().get(squareTakingTurn.getPlayer()).addGenerated();
                    checkForCollisions(squareCollisions, updatedGameBoard, new MagicSquare(squareTakingTurn.getPlayer(), squareAction.getReplicated(), replicateLocation1, INACTIVE_COUNT));
                    checkForCollisions(squareCollisions, updatedGameBoard, new MagicSquare(squareTakingTurn.getPlayer(), squareAction.getSquareLogic(), replicateLocation2, INACTIVE_COUNT));
                }
                case WAIT -> {
                    final Location waitLocation = squareTakingTurn.getLocation();
                    checkForCollisions(squareCollisions, updatedGameBoard, new MagicSquare(squareTakingTurn.getPlayer(), squareAction.getSquareLogic(), waitLocation));
                }
            }
        });
    }

    public void checkForCollisions(Set<Location> collisions, GameBoard updatedGameBoard, MagicSquare magicSquare) {

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

    public void advanceTimerSpeed() {
        this.timer.stop();
        if (this.timerSpeed == 0) {
            this.timerSpeed = 10;
        } else if (timerSpeed == 1000) {
            this.timerSpeed = 1;
        } else {
            this.timerSpeed *= 10;
        }
        this.buttonPanel.setTimerSpeedButtonImage(this.timerSpeed);
        this.timer = new Timer(this.timerSpeed, e -> runRound());
        this.timer.start();
    }

    public void updateLeaderboards() {
        if (debugEnabled) {
            this.leaderBoardPanel.setVisible(false);
        } else {
            this.leaderBoardPanel.setVisible(true);
            this.leaderBoardPanel.update(gameState);
        }
        this.gamePanel.revalidate();
        this.gamePanel.repaint();
    }

    public void updateGameScore() {
        this.roundLabel.setText("ROUND: " + gameState.getRoundNumber());
        this.roundLabel.setFont(GLOBAL_FONT);
        setScoreBoard();
        gameState.getPlayerList().stream().filter(Player::isPlaying).forEach(p -> {
            final Score score = gameState.getScoreBoard().get(p);
            GLOBAL_FONT = new Font(GLOBAL_FONT.getFontName(), GLOBAL_FONT.getStyle(), this.mainPanel.getHeight() / 40);
            this.playerViewMap.get(p).setPlayerName(p.getName());
            this.playerViewMap.get(p).setDebug(this.debugEnabled);
            this.playerViewMap.get(p).setScore(score.getScore(), this.debugEnabled);
            this.playerViewMap.get(p).setCollisions(score.getCollisions());
            this.playerViewMap.get(p).setEliminated(score.getEliminated());
            this.playerViewMap.get(p).setGenerated(score.getGenerated());
            this.playerViewMap.get(p).setKills(score.getKilled());
            this.playerViewMap.get(p).setTurnClock(score.getAvgTurnTime());
            this.playerViewMap.get(p).setColor();
            this.playerViewMap.get(p).setDebugView(this.debugEnabled);
        });
    }

    public void setRunLeaderboardRoundButton(JButton runLeaderboardRoundButton) {
        this.runLeaderboardRoundButton = runLeaderboardRoundButton;
    }
}
