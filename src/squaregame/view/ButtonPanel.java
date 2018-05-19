package squaregame.view;

import squaregame.controller.GameBoardController;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel implements ActionListener {
    public JButton startButton, stopButton, resetButton, leaderboardButton;
    public AISelectorPanel aiSelectorPanel;
    private GameBoardController gameBoardController;

    public ButtonPanel(GameBoardController gameBoardController, AISelectorPanel aiSelectorPanel) {
        this.aiSelectorPanel = aiSelectorPanel;
        this.gameBoardController = gameBoardController;
        this.setBackground(Color.black);
        startButton = new JButton("Start Game");
        startButton.setVerticalTextPosition(AbstractButton.CENTER);
        startButton.setHorizontalTextPosition(AbstractButton.CENTER);
        startButton.setMnemonic(KeyEvent.VK_S);
        startButton.setActionCommand("StartGame");
        startButton.addActionListener(this);
        startButton.setToolTipText("Starts the game");
        add(startButton);

        stopButton = new JButton("Stop Game");
        stopButton.setVerticalTextPosition(AbstractButton.CENTER);
        stopButton.setHorizontalTextPosition(AbstractButton.CENTER);
        stopButton.setMnemonic(KeyEvent.VK_E);
        stopButton.setActionCommand("StopGame");
        stopButton.addActionListener(this);
        stopButton.setToolTipText("Stops the game");
        stopButton.setEnabled(false);
        add(stopButton);

        resetButton = new JButton("Reset Game");
        resetButton.setVerticalTextPosition(AbstractButton.CENTER);
        resetButton.setHorizontalTextPosition(AbstractButton.CENTER);
        resetButton.setMnemonic(KeyEvent.VK_R);
        resetButton.setActionCommand("ResetGame");
        resetButton.addActionListener(this);
        resetButton.setToolTipText("Resets the game");
        add(resetButton);

        leaderboardButton = new JButton("Leaderboard Start");
        leaderboardButton.setVerticalTextPosition(AbstractButton.CENTER);
        leaderboardButton.setHorizontalTextPosition(AbstractButton.CENTER);
        leaderboardButton.setMnemonic(KeyEvent.VK_R);
        leaderboardButton.setActionCommand("Leaderboard");
        leaderboardButton.addActionListener(this);
        leaderboardButton.setToolTipText("Starts Leaderboard");
        this.gameBoardController.setRunLeaderboardRoundButton(leaderboardButton);

        leaderboardButton.addActionListener(aiSelectorPanel);
        aiSelectorPanel.getComboBoxes().forEach(comboBox -> leaderboardButton.addActionListener(comboBox));
        add(leaderboardButton);
    }

    public void actionPerformed(ActionEvent e) {
        if ("StartGame".equals(e.getActionCommand())) {
            this.gameBoardController.startGame();
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
        } else if ("StopGame".equals(e.getActionCommand())){
            this.gameBoardController.stopGame();
            stopButton.setEnabled(false);
            startButton.setEnabled(true);
        } else if ("ResetGame".equals(e.getActionCommand())){
            this.gameBoardController.resetGame(false);
            stopButton.setEnabled(false);
            startButton.setEnabled(true);
        } else if ("Leaderboard".equals(e.getActionCommand())){
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            this.gameBoardController.resetGame(true);
            this.gameBoardController.startGame();
        }
    }

}