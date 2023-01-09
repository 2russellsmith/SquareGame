package squaregame;

import squaregame.controller.GameBoardController;
import squaregame.model.GameState;
import squaregame.model.Player;
import squaregame.model.Score;
import squaregame.view.ActiveGamePanel;
import squaregame.view.LeaderboardPanel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.plaf.ColorUIResource;

public class SquareGameMain extends JFrame {

    private ActiveGamePanel activeGamePanel;
    private LeaderboardPanel leaderboardPanel;

    public SquareGameMain() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        this.setLayout(new CardLayout());
        this.setBackground(Color.black);
        final JTabbedPane tabbedPane = new JTabbedPane();

        final GameBoardController gameBoardController = new GameBoardController(this);
        this.activeGamePanel = new ActiveGamePanel(gameBoardController);
        this.leaderboardPanel = new LeaderboardPanel(gameBoardController);

        tabbedPane.addTab("Active Game", this.activeGamePanel);
        tabbedPane.addTab("Leaderboards", this.leaderboardPanel);

        this.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        this.setMinimumSize(new Dimension(1370, 700));
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
        this.leaderboardPanel.update(gameState);
    }

    public void updateGameScore(GameState gameState) {
        this.activeGamePanel.getAiSelectorPanel().getRoundLabel().setText("ROUND: " + gameState.getRoundNumber());
        gameState.getPlayerList().forEach(p -> {
            final Score score = gameState.getScoreBoard().get(p);
            this.activeGamePanel.getAiSelectorPanel().getPlayerViewMap().get(p).setScore(score.getScore());
            this.activeGamePanel.getAiSelectorPanel().getPlayerViewMap().get(p).setCollisions(score.getCollisions());
            this.activeGamePanel.getAiSelectorPanel().getPlayerViewMap().get(p).setEliminated(score.getEliminated());
            this.activeGamePanel.getAiSelectorPanel().getPlayerViewMap().get(p).setGenerated(score.getGenerated());
            this.activeGamePanel.getAiSelectorPanel().getPlayerViewMap().get(p).setKills(score.getKilled());
            this.activeGamePanel.getAiSelectorPanel().getPlayerViewMap().get(p).setTurnClock(score.getAvgTurnTime());
            this.activeGamePanel.getAiSelectorPanel().getPlayerViewMap().get(p).setColor();
        });
    }
}