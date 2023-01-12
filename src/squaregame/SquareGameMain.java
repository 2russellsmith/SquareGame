package squaregame;

import squaregame.controller.GameBoardController;

import java.awt.*;

import javax.swing.*;

public class SquareGameMain {

    public SquareGameMain() {
        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        mainFrame.setLayout(new CardLayout());
        mainFrame.getContentPane().setBackground(Color.BLACK);
        JPanel mainPanel = new JPanel();

        mainFrame.setLayout( new GridBagLayout() );
        mainFrame.add(mainPanel);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        new GameBoardController(mainPanel);
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