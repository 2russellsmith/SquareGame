package squaregame.view;

import squaregame.controller.GameBoardController;
import squaregame.squares.SquareLogic;

import javax.swing.JComboBox;

public class AISelectorComboBox extends JComboBox<SquareLogic> {

    private GameBoardController gameBoardController;

    public AISelectorComboBox(GameBoardController gameBoardController) {
        super(gameBoardController.getGameState().getAiOptions().toArray(new SquareLogic[0]));
        this.gameBoardController = gameBoardController;
    }
}
