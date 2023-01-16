package squaregame;

import squaregame.controller.GameBoardController;
import squaregame.view.BackgroundPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SquareGameMain {

    public SquareGameMain() throws IOException {
        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        mainFrame.setLayout(new CardLayout());
        JPanel mainPanel = new JPanel();

        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        BackgroundPanel backgroundPanel = new BackgroundPanel(ImageIO.read(new File("src/squaregame/background.png")));
        backgroundPanel.add(mainPanel);
        mainFrame.add(backgroundPanel);
        mainPanel.setMinimumSize(mainFrame.getSize());
        new GameBoardController(mainPanel);
    }

    public static void main(String arg[]) {
        try {
            SwingUtilities.invokeLater(() -> {
                try {
                    new SquareGameMain();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            // handle exception
        }
    }
}