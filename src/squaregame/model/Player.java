package squaregame.model;

import lombok.Getter;
import squaregame.squares.SquareLogic;
import squaregame.view.AISelectorComboBox;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Russell on 5/5/18.
 */
@Getter
public class Player implements ActionListener {
    private Color color;
    private Color textColor;
    private AIOption aiOption;

    public Player (Color color, Color textColor, AIOption aiOption) {
        this.color = color;
        this.aiOption = aiOption;
        this.textColor = textColor;
    }

    public Player (Player player) {
        replace(player);
    }

    public void replace(Player player) {
        this.aiOption = player.aiOption;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return aiOption.getSquareLogic().getSquareName();
    }

    public SquareLogic getStartingLogic() {
        return aiOption.getSquareLogic();
    }

    public AIOption getAiOption() {
        return this.aiOption;
    }

    public boolean isPlaying() {
        return aiOption != null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final AISelectorComboBox aiSelectorComboBox = (AISelectorComboBox)e.getSource();
        this.aiOption = (AIOption)aiSelectorComboBox.getSelectedItem();
    }
}
