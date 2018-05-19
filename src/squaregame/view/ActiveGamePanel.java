package squaregame.view;

import lombok.Getter;
import squaregame.controller.GameBoardController;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * Created by Russell on 5/10/18.
 */
@Getter
public class ActiveGamePanel extends JPanel {

    private AISelectorPanel aiSelectorPanel;

    public ActiveGamePanel(GameBoardController gameBoardController)  {

        this.setLayout(new BorderLayout());
        this.setBackground(Color.black);
        final GameBoardView gameBoardView = new GameBoardView(gameBoardController);

        this.aiSelectorPanel = new AISelectorPanel(gameBoardController);
        final ButtonPanel buttonPanel = new ButtonPanel(gameBoardController, aiSelectorPanel);
        this.add(buttonPanel, BorderLayout.PAGE_END);
        this.add(gameBoardView, BorderLayout.LINE_START);
        this.add(aiSelectorPanel, BorderLayout.LINE_END);
        gameBoardView.setPreferredSize(new Dimension(GameBoardView.MAX_BOARD_SIZE, GameBoardView.MAX_BOARD_SIZE));
    }

}
