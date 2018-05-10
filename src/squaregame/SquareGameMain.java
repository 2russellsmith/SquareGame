package squaregame;

import squaregame.controller.GameBoardController;
import squaregame.view.AISelectorPanel;
import squaregame.view.ButtonPanel;
import squaregame.view.GameBoardView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class SquareGameMain extends JFrame {

    private JTextArea gameStatePanel;
    private JTextArea leaderboardPanel;

    public SquareGameMain() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        this.setLayout(new BorderLayout());
        final GameBoardController gameBoardController = new GameBoardController(this);
        final GameBoardView gameBoardView = new GameBoardView(gameBoardController);
        this.gameStatePanel = new JTextArea();
        this.leaderboardPanel = new JTextArea();
        this.gameStatePanel.setLayout(new BorderLayout());
        this.leaderboardPanel.setLayout(new BorderLayout());
        setTitle("SquareGame");
        this.gameStatePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        this.leaderboardPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        final AISelectorPanel aiSelectorPanel = new AISelectorPanel(gameBoardController);
        final ButtonPanel buttonPanel = new ButtonPanel(gameBoardController, aiSelectorPanel);
        this.getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
        this.getContentPane().add(gameBoardView, BorderLayout.LINE_START);
        final JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.add(this.gameStatePanel, LEFT_ALIGNMENT);
        jPanel.add(aiSelectorPanel, LEFT_ALIGNMENT);
        jPanel.add(this.leaderboardPanel, LEFT_ALIGNMENT);
        this.getContentPane().add(jPanel, BorderLayout.LINE_END);
        gameBoardView.setPreferredSize(new Dimension(GameBoardView.MAX_BOARD_SIZE, GameBoardView.MAX_BOARD_SIZE));
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

    public JTextArea getGameStatePanel() {
        return gameStatePanel;
    }

    public JTextArea getLeaderboardPanel() {
        return leaderboardPanel;
    }
}