package squaregame.view;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import squaregame.SquareGameMain;

public class ButtonPanel extends JPanel implements ActionListener {
    public JButton startButton, endButton, resetButton;
    private SquareGameMain squareGameMain;

    public ButtonPanel(SquareGameMain squareGameMain) {
        this.squareGameMain = squareGameMain;
        startButton = new JButton("Start Game");
        startButton.setVerticalTextPosition(AbstractButton.CENTER);
        startButton.setHorizontalTextPosition(AbstractButton.CENTER);
        startButton.setMnemonic(KeyEvent.VK_S);
        startButton.setActionCommand("StartGame");

        endButton = new JButton("Stop Game");
        endButton.setVerticalTextPosition(AbstractButton.CENTER);
        endButton.setHorizontalTextPosition(AbstractButton.CENTER);
        endButton.setMnemonic(KeyEvent.VK_E);
        endButton.setActionCommand("StopGame");

        resetButton = new JButton("Reset Game");
        resetButton.setVerticalTextPosition(AbstractButton.CENTER);
        resetButton.setHorizontalTextPosition(AbstractButton.CENTER);
        resetButton.setMnemonic(KeyEvent.VK_R);
        resetButton.setActionCommand("ResetGame");

        startButton.addActionListener(this);
        endButton.addActionListener(this);
        resetButton.addActionListener(this);

        startButton.setToolTipText("Starts the game");
        endButton.setToolTipText("Stops the game");
        resetButton.setToolTipText("Resets the game");

        add(startButton);
        add(endButton);
        add(resetButton);
    }

    public void actionPerformed(ActionEvent e) {
        if ("StartGame".equals(e.getActionCommand())) {
            squareGameMain.startGame();
        } else if ("StopGame".equals(e.getActionCommand())){
            squareGameMain.getTimer().stop();
        } else if ("ResetGame".equals(e.getActionCommand())){
            squareGameMain.getTimer().stop();
            squareGameMain.resetGame();
        }
    }

}