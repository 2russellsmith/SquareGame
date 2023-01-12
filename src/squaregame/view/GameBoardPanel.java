package squaregame.view;

import squaregame.controller.GameBoardController;

import java.awt.*;

import javax.swing.*;

/**
 * Created by Russell on 5/7/18.
 */
public class GameBoardPanel extends JPanel {

    public static final Integer MAX_BOARD_SIZE = 800;
    private final GameBoardController gameBoardController;

    public GameBoardPanel(GameBoardController gameBoardController) {
        this.gameBoardController = gameBoardController;
    }

    @Override
    public void paintComponent(Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.drawRect(-1, -1, MAX_BOARD_SIZE + 1, MAX_BOARD_SIZE + 1);
        draw(g2);
        this.setMinimumSize(new Dimension(MAX_BOARD_SIZE + 1, MAX_BOARD_SIZE + 1));
    }

    public void draw(Graphics2D g2){
        final int squareSize = MAX_BOARD_SIZE / this.gameBoardController.getGameBoard().getBoardSize();
        for (int i = 0; i < this.gameBoardController.getGameBoard().getBoardSize(); i++) {
            for (int j = 0; j < this.gameBoardController.getGameBoard().getBoardSize(); j++) {
                if (this.gameBoardController.getGameBoard().get(i, j) != null) {
                    g2.setColor(this.gameBoardController.getGameBoard().get(i, j).getPlayer().getColor());
                    g2.fillRect(i * squareSize, j * squareSize, squareSize, squareSize);
                }
            }
        }
    }
}
