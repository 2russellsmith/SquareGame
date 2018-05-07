package squaregame.view;

import squaregame.GameBoard;
import squaregame.SquareGameMain;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import static squaregame.SquareGameMain.BOARD_VISUAL_SIZE;

/**
 * Created by Russell on 5/7/18.
 */
public class GameBoardView extends JPanel {
    private GameBoard gameBoard;

    public GameBoardView(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    @Override
    public void paintComponent(Graphics g) {
        final Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillRect(-1,-1, BOARD_VISUAL_SIZE + 1, BOARD_VISUAL_SIZE + 1);
        this.gameBoard.draw(g2);
        setPreferredSize(new Dimension(BOARD_VISUAL_SIZE + 1, BOARD_VISUAL_SIZE + 1));
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }
}
