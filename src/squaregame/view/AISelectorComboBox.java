package squaregame.view;

import squaregame.model.AIOption;
import squaregame.model.GameState;
import squaregame.model.Player;

import javax.swing.*;
import java.awt.*;

public class AISelectorComboBox extends JList<AIOption> {

    public final Player player;

    public AISelectorComboBox(GameState gameState, Player player) {
        super(gameState.getAiOptions().toArray(new AIOption[0]));
        this.player = player;
        this.setOpaque(false);
        this.setCellRenderer(new MyCellRenderer());
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setSelectionModel(new DefaultListSelectionModel() {
            boolean gestureStarted = false;
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if(!gestureStarted){
                    if (index0==index1) {
                        if (isSelectedIndex(index0)) {
                            removeSelectionInterval(index0, index0);
                            return;
                        }
                    }
                    super.setSelectionInterval(index0, index1);
                }
                gestureStarted = true;
            }

            @Override
            public void addSelectionInterval(int index0, int index1) {
                if (index0==index1) {
                    if (isSelectedIndex(index0)) {
                        removeSelectionInterval(index0, index0);
                        return;
                    }
                    super.addSelectionInterval(index0, index1);
                }
            }

            @Override
            public void setValueIsAdjusting(boolean isAdjusting) {
                if (!isAdjusting) {
                    gestureStarted = false;
                }
            }
        });
        this.addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                final AISelectorComboBox aiSelectorComboBox = (AISelectorComboBox) event.getSource();
                AIOption aiOption = aiSelectorComboBox.getSelectedValue();
                if (aiOption != null) {
                    player.aiOption = aiOption;
                    player.color = aiOption.getDefaultSquare().getColor();
                    player.setTextColor();
                } else {
                    player.aiOption = null;
                    player.color = Color.WHITE;
                    player.textColor = Color.BLACK;
                }
            }
        });
    }
}
