package squaregame.view;

import squaregame.model.AIOption;

import javax.swing.*;
import java.awt.*;

import static squaregame.controller.GameBoardController.getGlobalFont;

class MyCellRenderer extends JLabel implements ListCellRenderer<AIOption> {
    public MyCellRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends AIOption> list, AIOption value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.getId());

        Color background;
        Color foreground;

        // check if this cell represents the current DnD drop location
        JList.DropLocation dropLocation = list.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsert()
                && dropLocation.getIndex() == index) {

            background = Color.BLUE;
            foreground = Color.WHITE;

            // check if this cell is selected
        } else if (isSelected) {
            background = Color.darkGray;
            foreground = Color.WHITE;

            // unselected, and not the DnD drop location
        } else {
            background = new Color(105, 166, 201);
            foreground = Color.BLACK;
        };
        setFont(getGlobalFont(20));
        setBackground(background);
        setForeground(foreground);

        return this;
    }
}
