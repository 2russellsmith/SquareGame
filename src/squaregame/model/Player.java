package squaregame.model;

import squaregame.squares.SquareLogic;
import squaregame.view.AISelectorComboBox;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

/**
 * Created by Russell on 5/5/18.
 */
public class Player implements ActionListener {
    private Color color;
    private SquareLogic startingLogic;

    public Player (Color color, SquareLogic squareLogic) {
        this.color = color;
        this.startingLogic = squareLogic;
    }

    public Player (Player player) {
        replace(player);
    }

    public void replace(Player player) {
        this.startingLogic = player.startingLogic;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return startingLogic.getSquareName();
    }

    public SquareLogic getStartingLogic() {
        return startingLogic;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final AISelectorComboBox aiSelectorComboBox = (AISelectorComboBox)e.getSource();
        this.startingLogic = (SquareLogic)aiSelectorComboBox.getSelectedItem();
    }
}
