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
    private String name;
    private SquareLogic startingLogic;

    public Player (String name, Color color, SquareLogic squareLogic) {
        this.color = color;
        this.name = name;
        this.startingLogic = squareLogic;
    }

    public Player (Player player) {
        replace(player);
    }

    public void replace(Player player) {
        this.name = player.name;
        this.startingLogic = player.startingLogic;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public SquareLogic getStartingLogic() {
        return startingLogic;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final AISelectorComboBox aiSelectorComboBox = (AISelectorComboBox)e.getSource();
        final SquareLogic squareLogic = (SquareLogic)aiSelectorComboBox.getSelectedItem();
        this.name = startingLogic.getClass().getSimpleName();
        this.startingLogic = squareLogic;
    }
}
