package squaregame.view;

import com.sun.deploy.panel.JSmartTextArea;
import squaregame.controller.GameBoardController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Russell on 5/10/18.
 */
public class ActiveGamePanel extends JPanel {

    private JSmartTextArea gameStatePanel;
    private JSmartTextArea leaderboardPanel;

    public ActiveGamePanel(GameBoardController gameBoardController)  {

        this.setLayout(new BorderLayout());
        final GameBoardView gameBoardView = new GameBoardView(gameBoardController);
        this.gameStatePanel = new JSmartTextArea();
        this.leaderboardPanel = new JSmartTextArea();
        this.gameStatePanel.setColumns(10);
        this.gameStatePanel.setRows(10);
        this.leaderboardPanel.setColumns(10);
        this.leaderboardPanel.setRows(10);
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

    public JSmartTextArea getGameStatePanel() {
        return gameStatePanel;
    }

    public JSmartTextArea getLeaderboardPanel() {
        return leaderboardPanel;
    }
}
