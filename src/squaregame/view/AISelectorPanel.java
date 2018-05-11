package squaregame.view;

import squaregame.controller.GameBoardController;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
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
            final ArrayList<Integer> aiOptions = new ArrayList<>();
            for (int i = 1; i < this.gameBoardController.getGameState().getAiOptions().size(); i++) {
                aiOptions.add(i);
            }
            Collections.shuffle(aiOptions);
            final int numberOfPlayers = ThreadLocalRandom.current().nextInt(1, 9);
            final List<Integer> comboBoxIndex = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7);
            Collections.shuffle(comboBoxIndex);
            for (int i = 0; i < numberOfPlayers && i < aiOptions.size(); i++) {
                final AISelectorComboBox comboBox = this.comboBoxes.get(comboBoxIndex.get(i));
                comboBox.setSelectedIndex(aiOptions.get(i));
            }
        }
    }

    public void disableAll() {

    }

    public List<AISelectorComboBox> getComboBoxes() {
        return comboBoxes;
    }
}
