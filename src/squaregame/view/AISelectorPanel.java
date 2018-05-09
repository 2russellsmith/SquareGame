package squaregame.view;

import squaregame.controller.GameBoardController;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class AISelectorPanel extends JPanel {

    private GameBoardController gameBoardController;

    public AISelectorPanel(GameBoardController gameBoardController) {
        this.gameBoardController = gameBoardController;
        this.setLayout(new GridBagLayout());
        AtomicInteger y = new AtomicInteger();
        this.gameBoardController.getGameState().getPlayerList().forEach(p -> {
            final AISelectorComboBox aiSelectorComboBox = new AISelectorComboBox(gameBoardController);
            final GridBagConstraints c = new GridBagConstraints();
            c.gridx = 1;
            c.gridy = y.getAndIncrement();
            add(aiSelectorComboBox, c);
            final GridBagConstraints c2 = new GridBagConstraints();
            c2.gridx = 2;
            c2.gridy = c.gridy;
            final JLabel playerColorLabel = new JLabel("-----");
            playerColorLabel.setForeground(p.getColor());
            add(playerColorLabel, c2);
            final GridBagConstraints c3 = new GridBagConstraints();
            c3.gridx = 0;
            c3.gridy = c.gridy;
            final JLabel playerColorLabel1 = new JLabel("-----");
            playerColorLabel1.setForeground(p.getColor());
            add(playerColorLabel1, c3);
            aiSelectorComboBox.addActionListener(p);
        });
    }
}
