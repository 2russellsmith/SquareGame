package squaregame;

import squaregame.controller.GameBoardController;
import squaregame.model.GameState;
import squaregame.model.Player;
import squaregame.view.ActiveGamePanel;
import squaregame.view.LeaderboardPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

public class SquareGameMain extends JFrame {

    private ActiveGamePanel activeGamePanel;
    private LeaderboardPanel leaderboardPanel;

    public SquareGameMain() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        this.setLayout(new CardLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        final GameBoardController gameBoardController = new GameBoardController(this);
        this.activeGamePanel = new ActiveGamePanel(gameBoardController);
        this.leaderboardPanel = new LeaderboardPanel(gameBoardController);
        updateLeaderboards(gameBoardController.getGameState());

        tabbedPane.addTab("Active Game", this.activeGamePanel);
        tabbedPane.addTab("Leaderboards", this.leaderboardPanel);

        this.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        this.setMinimumSize(new Dimension(830, 660));
    }

    public static void main(String arg[]) {
        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            // handle exception
        }
        SwingUtilities.invokeLater(SquareGameMain::new);
    }

    public void updateLeaderboards(GameState gameState) {
        this.activeGamePanel.getLeaderboardPanel().setText(gameState.getAiOptions().stream()
                .filter(Objects::nonNull)
                .sorted((a1, a2) -> Double.compare(gameState.getLeaderboard().getWinRate(a2.getId()), gameState.getLeaderboard().getWinRate(a1.getId())))
                .map(aiOption -> aiOption.getSquareLogic().getSquareName() + ": " +
                        gameState.getLeaderboard().getWins(aiOption.getId()) + "/" + gameState.getLeaderboard().getGamesPlayed(aiOption.getId()) +
                        "(" + gameState.getLeaderboard().getWinRate(aiOption.getId()) + "%)")
                .collect(Collectors.joining("\n")));
        this.leaderboardPanel.update(gameState);
    }

    public void updateGameScore(GameState gameState) {
        this.activeGamePanel.getGameStatePanel().setSelectionColor(Color.black);
        this.activeGamePanel.getGameStatePanel().setText("Round: " + gameState.getRoundNumber() + "\n");
        gameState.getPlayerList().stream().filter(Player::isPlaying)
                .sorted(Comparator.comparingInt(p1 -> gameState.getScoreBoard().get(p1).getScore()).reversed()).forEach(p -> {
                    this.activeGamePanel.getGameStatePanel().setCaretColor(p.getColor());
                    this.activeGamePanel.getGameStatePanel().append(p.getName() + ": " + gameState.getScoreBoard().get(p).getScore() + "\n");
        });
    }
}