package squaregame.view;

import lombok.Getter;
import squaregame.controller.GameBoardController;
import squaregame.model.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.*;
import javax.swing.border.Border;

import static squaregame.controller.GameBoardController.GLOBAL_FONT;
import static squaregame.controller.GameBoardController.getGlobalFont;
import static squaregame.view.PlayerView.newColorWithAlpha;

@Getter
public class AISelectorPanel extends JPanel implements ActionListener {

    private final GameBoardController gameBoardController;
    private final List<AISelectorComboBox> comboBoxes;

    public AISelectorPanel(GameBoardController gameBoardController) {
        this.gameBoardController = gameBoardController;
        this.comboBoxes = new ArrayList<>();
        this.setOpaque(false);
        this.setLayout(new GridBagLayout());
        final AtomicInteger playerNumber = new AtomicInteger();
        this.gameBoardController.getGameState().getPlayerList()
            .forEach(p -> {
                GridBagConstraints c = new GridBagConstraints();
                c.fill = GridBagConstraints.BOTH;
                c.weightx = 1;
                c.weighty = 1;
                c.insets = new Insets(2, 2, 2, 2);
                final AISelectorComboBox aiSelectorComboBox = new AISelectorComboBox(this.gameBoardController.getGameState(), p);
                comboBoxes.add(aiSelectorComboBox);
                c.gridx = playerNumber.get() % 4;
                c.gridy = (playerNumber.get() / 4) * 2;
                JLabel fighterLabel = new JLabel("FIGHTER " + (playerNumber.get() + 1), SwingConstants.CENTER);
                fighterLabel.setBackground(newColorWithAlpha(Color.darkGray));
                fighterLabel.setForeground(new Color(105, 166, 201));
                fighterLabel.setFont(getGlobalFont(24));
                fighterLabel.setOpaque(true);

                add(fighterLabel, c);
                c.weighty = 4;
                c.gridy = (playerNumber.getAndIncrement() / 4) * 2 + 1;
                add(aiSelectorComboBox, c);
            });
    }

    public void resize() {
        this.setPreferredSize(new Dimension(this.getParent().getHeight(), this.getParent().getHeight()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Leaderboard".equals(e.getActionCommand())){
            final ArrayList<Integer> aiOptions = new ArrayList<>();
            for (int i = 1; i < this.gameBoardController.getGameState().getAiOptions().size(); i++) {
                aiOptions.add(i);
            }
            Collections.shuffle(aiOptions);
            final int numberOfPlayers;
            if (ThreadLocalRandom.current().nextBoolean()) {
                this.gameBoardController.getGameState().setIsFreeForAll(false);
                numberOfPlayers = 2;
            } else {
                this.gameBoardController.getGameState().setIsFreeForAll(true);
                numberOfPlayers = ThreadLocalRandom.current().nextInt(3, 9);
            }
            comboBoxes.forEach(JList::clearSelection);
            final List<Integer> comboBoxIndex = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7);
            Collections.shuffle(comboBoxIndex);
            for (int i = 0; i < numberOfPlayers && i < aiOptions.size(); i++) {
                this.comboBoxes.get(i).setSelectedIndex(aiOptions.get(i));
            }
        }
    }

    public List<AISelectorComboBox> getComboBoxes() {
        return comboBoxes;
    }
}
