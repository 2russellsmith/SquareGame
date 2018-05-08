package squaregame;

import squaregame.controller.GameBoardController;
import squaregame.view.AISelectorPanel;
import squaregame.view.ButtonPanel;
import squaregame.view.GameBoardView;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class SquareGameMain extends JFrame {

    public SquareGameMain() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        final GameBoardController gameBoardController = new GameBoardController(this);
        final GameBoardView gameBoardView = new GameBoardView(gameBoardController);
        final JTextArea roundText = new JTextArea("In development");
        setTitle("SquareGame");
        final ButtonPanel buttonPanel = new ButtonPanel(gameBoardController);
        this.getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
        this.getContentPane().add(gameBoardView, BorderLayout.CENTER);
        this.getContentPane().add(new AISelectorPanel(gameBoardController), BorderLayout.EAST);
        this.getContentPane().add(roundText, BorderLayout.SOUTH);
        this.setSize(1000, 1000);
    }

    public static void main(String arg[]) {
        SwingUtilities.invokeLater(SquareGameMain::new);
    }
}