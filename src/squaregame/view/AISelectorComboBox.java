package squaregame.view;

import squaregame.controller.GameBoardController;
import squaregame.model.AIOption;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;

public class AISelectorComboBox extends JComboBox<AIOption> {

    private final GameBoardController gameBoardController;

    public AISelectorComboBox(GameBoardController gameBoardController) {
        super(gameBoardController.getGameState().getAiOptions().toArray(new AIOption[0]));
        this.gameBoardController = gameBoardController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Leaderboard".equals(e.getActionCommand())){
            this.setSelectedIndex(0);
        }
    }

}
