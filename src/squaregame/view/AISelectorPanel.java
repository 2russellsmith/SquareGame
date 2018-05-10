package squaregame.view;

import squaregame.controller.GameBoardController;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class AISelectorPanel extends JPanel implements ActionListener {

    private GameBoardController gameBoardController;
    private List<AISelectorComboBox> comboBoxes;

    public AISelectorPanel(GameBoardController gameBoardController) {
        this.gameBoardController = gameBoardController;
        this.comboBoxes = new ArrayList<>();
        this.setLayout(new GridBagLayout());
        AtomicInteger y = new AtomicInteger();
        this.gameBoardController.getGameState().getPlayerList().forEach(p -> {
            final AISelectorComboBox aiSelectorComboBox = new AISelectorComboBox(gameBoardController);
            comboBoxes.add(aiSelectorComboBox);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Leaderboard".equals(e.getActionCommand())){
            final ArrayList<Integer> list = new ArrayList<>();
            for (int i = 1; i < this.gameBoardController.getGameState().getAiOptions().size(); i++) {
                list.add(i);
            }
            Collections.shuffle(list);
            Random random = new Random();
            for (int i = 0; i < random.nextInt(8); i++) {
                this.comboBoxes.get(random.nextInt(8)).setSelectedIndex(list.get(i));
            }
        }
    }

    public void disableAll() {

    }

    public List<AISelectorComboBox> getComboBoxes() {
        return comboBoxes;
    }
}
