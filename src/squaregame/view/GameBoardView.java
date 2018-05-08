package squaregame.view;

import squaregame.controller.GameBoardController;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * Created by Russell on 5/7/18.
 */
public class GameBoardView extends JPanel {


    public static final Integer SQUARE_SIZE = 4;
    private GameBoardController gameBoardController;

    public GameBoardView(GameBoardController gameBoardController) {
        this.gameBoardController = gameBoardController;
    }

    @Override
    public void paintComponent(Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillRect(-1, -1, getBoardVisualSize() + 1, getBoardVisualSize() + 1);
        draw(g2);
        setPreferredSize(new Dimension(getBoardVisualSize() + 1, getBoardVisualSize() + 1));
    }

    public void draw(Graphics2D g2){
        for (int i = 0; i < this.gameBoardController.getGameBoard().getBoardSize(); i++) {
            for (int j = 0; j < this.gameBoardController.getGameBoard().getBoardSize(); j++) {
                if (this.gameBoardController.getGameBoard().get(i, j) != null) {
                    g2.setColor(this.gameBoardController.getGameBoard().get(i, j).getPlayer().getColor());
                    g2.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                }
            }
        }
    }

    public int getBoardVisualSize() {
        return SQUARE_SIZE * this.gameBoardController.getGameBoard().getBoardSize();
    }
}
