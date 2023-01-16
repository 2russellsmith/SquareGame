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
        this.addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()){
                final AISelectorComboBox aiSelectorComboBox = (AISelectorComboBox)event.getSource();
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
