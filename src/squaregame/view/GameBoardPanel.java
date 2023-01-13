package squaregame.view;

import squaregame.controller.GameBoardController;

import java.awt.*;

import javax.swing.*;

import static squaregame.view.PlayerView.newColorWithAlpha;

/**
 * Created by Russell on 5/7/18.
 */
public class GameBoardPanel extends JPanel {

    public static Integer MAX_BOARD_SIZE = 900;
    private final GameBoardController gameBoardController;

    public GameBoardPanel(GameBoardController gameBoardController) {
        this.gameBoardController = gameBoardController;

        setPreferredSize(new Dimension(GameBoardPanel.MAX_BOARD_SIZE, GameBoardPanel.MAX_BOARD_SIZE));
        setBackground(newColorWithAlpha(Color.BLACK));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D) g;
        draw(g2);
    }

    public void draw(Graphics2D g2){
        final int squareSize = getSquareSize();
        for (int i = 0; i < this.gameBoardController.getGameBoard().getBoardSize(); i++) {
            for (int j = 0; j < this.gameBoardController.getGameBoard().getBoardSize(); j++) {
                if (this.gameBoardController.getGameBoard().get(i, j) != null) {
                    g2.setColor(this.gameBoardController.getGameBoard().get(i, j).getPlayer().getColor());
                    g2.fillRect(i * squareSize, j * squareSize, squareSize, squareSize);
                }
            }
        }
        this.setPreferredSize(new Dimension(squareSize * this.gameBoardController.getGameBoard().getBoardSize(),
                squareSize * this.gameBoardController.getGameBoard().getBoardSize()));
        this.setMinimumSize(new Dimension(squareSize * this.gameBoardController.getGameBoard().getBoardSize(),
                squareSize * this.gameBoardController.getGameBoard().getBoardSize()));
    }

    public int getSquareSize() {
        return MAX_BOARD_SIZE / this.gameBoardController.getGameBoard().getBoardSize();
    }
}
