package squaregame.view;

import squaregame.controller.GameBoardController;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Created by Russell on 5/10/18.
 */
public class ActiveGamePanel extends JPanel {

    private JTextArea gameStatePanel;
    private JTextArea leaderboardPanel;

    public ActiveGamePanel(GameBoardController gameBoardController)  {

        this.setLayout(new BorderLayout());
        final GameBoardView gameBoardView = new GameBoardView(gameBoardController);
        this.gameStatePanel = new JTextArea();

        this.gameStatePanel.setEditable(false);
        this.gameStatePanel.setLineWrap(true);
        this.gameStatePanel.setWrapStyleWord(true);
        this.gameStatePanel.setFocusable(false);
        this.gameStatePanel.setRows(0);
        this.gameStatePanel.invalidate();
        this.leaderboardPanel = new JTextArea();

        this.leaderboardPanel.setEditable(false);
        this.leaderboardPanel.setLineWrap(true);
        this.leaderboardPanel.setWrapStyleWord(true);
        this.leaderboardPanel.setFocusable(false);
        this.leaderboardPanel.setRows(0);
        this.leaderboardPanel.invalidate();

        this.gameStatePanel.setColumns(10);
        this.leaderboardPanel.setColumns(10);
        this.gameStatePanel.setLayout(new BorderLayout());
        this.leaderboardPanel.setLayout(new BorderLayout());
        this.gameStatePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        this.leaderboardPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        final AISelectorPanel aiSelectorPanel = new AISelectorPanel(gameBoardController);
        final ButtonPanel buttonPanel = new ButtonPanel(gameBoardController, aiSelectorPanel);
        this.add(buttonPanel, BorderLayout.PAGE_END);
        this.add(gameBoardView, BorderLayout.LINE_START);
        final JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.add(this.gameStatePanel, LEFT_ALIGNMENT);
        jPanel.add(aiSelectorPanel, LEFT_ALIGNMENT);
        jPanel.add(this.leaderboardPanel, LEFT_ALIGNMENT);
        this.add(jPanel, BorderLayout.LINE_END);
        gameBoardView.setPreferredSize(new Dimension(GameBoardView.MAX_BOARD_SIZE, GameBoardView.MAX_BOARD_SIZE));
    }

    public JTextArea getGameStatePanel() {
        return gameStatePanel;
    }

    public JTextArea getLeaderboardPanel() {
        return leaderboardPanel;
    }
}
