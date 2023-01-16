package squaregame.view;

import squaregame.controller.GameBoardController;
import squaregame.model.Leaderboard;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.*;

import static squaregame.view.PlayerView.newColorWithAlpha;

public class ButtonPanel extends JPanel implements ActionListener {
    public JButton startButton, stopButton, resetButton, leaderboardButton, oneRoundButton, debugModeButton;
    private GameBoardController gameBoardController;

    public ButtonPanel(GameBoardController gameBoardController) {
        this.gameBoardController = gameBoardController;
        this.setLayout(new GridBagLayout());
        this.setOpaque(true);
        this.setBackground(new Color(105, 166, 201));
        startButton = createButton("src/squaregame/play.png", "StartGame", "Starts the game");
        stopButton = createButton("src/squaregame/stop.png", "StopGame", "Stops the game");
        oneRoundButton = createButton("src/squaregame/oneRound.png", "OneRound", "Moves the game one round");
        resetButton = createButton("src/squaregame/reset.png", "ResetGame", "Resets the game");
        leaderboardButton = createButton("src/squaregame/leaderboardMode.png", "Leaderboard", "Starts Leaderboard Mode");
        debugModeButton = createButton("src/squaregame/debug.png", "DebugMode", "Toggle Debug Mode");
        gameBoardController.setRunLeaderboardRoundButton(this.leaderboardButton);
        this.stopButton.setVisible(false);
        this.oneRoundButton.setVisible(false);
        this.debugModeButton.setVisible(false);
        this.resetButton.setVisible(false);
    }

    public JButton createButton(String image, String actionCommand, String toolTip) {
        try {
            JButton results = new JButton(new ImageIcon(ImageIO.read(new File(image))));
            results.setActionCommand(actionCommand);
            results.setToolTipText(toolTip);
            results.setContentAreaFilled(false);
            results.setBorderPainted(false);
            results.setFocusPainted(false);
            results.addActionListener(this);
            add(results);
            return results;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void actionPerformed(ActionEvent e) {
        this.repaint();
        if ("StartGame".equals(e.getActionCommand())) {
            this.gameBoardController.startGame();
            startButton.setVisible(false);
            stopButton.setVisible(true);
            this.oneRoundButton.setVisible(true);
            this.debugModeButton.setVisible(true);
            this.resetButton.setVisible(true);
        } else if ("StopGame".equals(e.getActionCommand())){
            this.gameBoardController.stopGame();
            stopButton.setVisible(false);
            startButton.setVisible(true);
        } else if ("OneRound".equals(e.getActionCommand())){
            this.gameBoardController.oneRound();
            stopButton.setVisible(false);
            startButton.setVisible(true);
        } else if ("ResetGame".equals(e.getActionCommand())){
            this.gameBoardController.resetGame(false);
            stopButton.setVisible(false);
            startButton.setVisible(true);
            this.oneRoundButton.setVisible(false);
            this.debugModeButton.setVisible(false);
            this.resetButton.setVisible(false);
        } else if ("Leaderboard".equals(e.getActionCommand())){
            startButton.setVisible(false);
            stopButton.setVisible(true);
            this.oneRoundButton.setVisible(true);
            this.debugModeButton.setVisible(true);
            this.gameBoardController.resetGame(true);
            this.gameBoardController.startGame();
            this.resetButton.setVisible(true);
        } else if ("DebugMode".equals(e.getActionCommand())){
            this.gameBoardController.toggleDebug();
            this.gameBoardController.updateLeaderboards();
        }
    }

}