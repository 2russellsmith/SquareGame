package squaregame.view;

import lombok.Getter;
import squaregame.controller.GameBoardController;
import squaregame.model.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import static squaregame.view.PlayerView.newColorWithAlpha;

@Getter
public class AISelectorPanel extends JPanel implements ActionListener {

    private GameBoardController gameBoardController;
    private List<AISelectorComboBox> comboBoxes;
    private Map<Player, PlayerView> playerViewMap;

    public AISelectorPanel(GameBoardController gameBoardController) {
        this.playerViewMap = new HashMap<>();
        this.gameBoardController = gameBoardController;
        this.comboBoxes = new ArrayList<>();
        this.setLayout(new GridBagLayout());
        final AtomicInteger row = new AtomicInteger();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridy = row.getAndIncrement();
        JLabel squareChoiceLabel = new JLabel("Select Your Fighting Squares:");
        squareChoiceLabel.setOpaque(true);
        squareChoiceLabel.setForeground(Color.WHITE);
        squareChoiceLabel.setBackground(newColorWithAlpha(Color.BLACK));
        add(squareChoiceLabel);
        this.setBackground(Color.BLACK);
        final int borderSize = 2;
        this.gameBoardController.getGameState().getPlayerList().forEach(p -> {

            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.BOTH;
            c.gridx = 0;
            c.gridy = row.getAndIncrement();
            final AISelectorComboBox aiSelectorComboBox = new AISelectorComboBox(gameBoardController);
            aiSelectorComboBox.setEditable(true);
            aiSelectorComboBox.setBorder(BorderFactory.createMatteBorder(borderSize, borderSize * 2, borderSize * 2, 0, p.getColor().darker()));
            aiSelectorComboBox.getEditor().getEditorComponent().setBackground(p.getColor());
            aiSelectorComboBox.getEditor().getEditorComponent().setForeground(p.getTextColor());

            comboBoxes.add(aiSelectorComboBox);
            add(aiSelectorComboBox, c);

            c.gridx = 1;
            final PlayerView playerView = new PlayerView(p);
            aiSelectorComboBox.addActionListener(p);
            playerView.setVisible(false);
            add(playerView, c);
            playerViewMap.put(p, playerView);
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
            final int numberOfPlayers;
            if (ThreadLocalRandom.current().nextBoolean()) {
                this.gameBoardController.getGameState().setIsFreeForAll(false);
                numberOfPlayers = 2;
            } else {
                this.gameBoardController.getGameState().setIsFreeForAll(true);
                numberOfPlayers = ThreadLocalRandom.current().nextInt(3, 9);
            }
            comboBoxes.forEach(c -> c.setSelectedIndex(0));
            final List<Integer> comboBoxIndex = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7);
            Collections.shuffle(comboBoxIndex);
            for (int i = 0; i < numberOfPlayers && i < aiOptions.size(); i++) {
                final AISelectorComboBox comboBox = this.comboBoxes.get(comboBoxIndex.get(i));
                comboBox.setSelectedIndex(aiOptions.get(i));
            }
        }
    }

    public List<AISelectorComboBox> getComboBoxes() {
        return comboBoxes;
    }
}
