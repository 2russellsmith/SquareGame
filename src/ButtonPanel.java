import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/* 
 * ButtonDemo.java requires the following files:
 *   images/right.gif
 *   images/middle.gif
 *   images/left.gif
 */
public class ButtonPanel extends JPanel implements ActionListener {
    public JButton startButton, endButton;
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

        //Listen for actions on buttons 1 and 3.
        startButton.addActionListener(this);
        endButton.addActionListener(this);

        startButton.setToolTipText("Starts the game");
        endButton.setToolTipText("Stops the game");
        endButton.setEnabled(true);

        //Add Components to this container, using the default FlowLayout.
        add(startButton);
        add(endButton);
    }

    public void actionPerformed(ActionEvent e) {
        if ("StartGame".equals(e.getActionCommand())) {
            squareGameMain.startGame();
        } else if ("StopGame".equals(e.getActionCommand())){
            squareGameMain.timer.stop();
        }
    }

}