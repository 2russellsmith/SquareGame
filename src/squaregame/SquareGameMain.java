package squaregame;

import squaregame.controller.GameBoardController;
import squaregame.view.AISelectorPanel;
import squaregame.view.ButtonPanel;
import squaregame.view.GameBoardView;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class SquareGameMain extends JFrame {

    public SquareGameMain() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        this.setLayout(new BorderLayout());
        final GameBoardController gameBoardController = new GameBoardController(this);
        final GameBoardView gameBoardView = new GameBoardView(gameBoardController);
        final JTextArea roundText = new JTextArea("In development");
        setTitle("SquareGame");
        final ButtonPanel buttonPanel = new ButtonPanel(gameBoardController);
        this.getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
        this.getContentPane().add(gameBoardView, BorderLayout.LINE_START);
        this.getContentPane().add(new AISelectorPanel(gameBoardController), BorderLayout.LINE_END);
        this.getContentPane().add(roundText, BorderLayout.CENTER);

        gameBoardView.setPreferredSize(new Dimension(600, 600));
        this.setSize(new Dimension(925, 670));
    }

    public static void main(String arg[]) {
        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            // handle exception
        }
        SwingUtilities.invokeLater(SquareGameMain::new);
    }
}