package squaregame.view;

import squaregame.controller.GameBoardController;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class AISelectorPanel extends JPanel {

    private GameBoardController gameBoardController;

    public AISelectorPanel(GameBoardController gameBoardController) {
        this.gameBoardController = gameBoardController;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.gameBoardController.getGameState().getPlayerList().forEach(p -> {
            final AISelectorComboBox aiSelectorComboBox = new AISelectorComboBox(gameBoardController);
            add(aiSelectorComboBox);
            aiSelectorComboBox.addActionListener(p);
        });

    }


}
