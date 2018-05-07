package squaregame;

import squaregame.model.GameState;
import squaregame.view.GameBoardView;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;

public class SquareGameMain extends JFrame {

    public static final Integer BOARD_SIZE = 150;
    public static final Integer SQUARE_SIZE = 4;
    public static final Integer BOARD_VISUAL_SIZE = SQUARE_SIZE * BOARD_SIZE;
    private final ButtonPanel buttonPanel;
    private final JTextArea roundText;
    private final GameState gameState;

    GameBoard gameBoard = new GameBoard(new MagicSquare[BOARD_VISUAL_SIZE][BOARD_VISUAL_SIZE]);
    public Timer timer;
    private int round = 0;

    public SquareGameMain() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        JPanel gameBoardView = new GameBoardView(this.gameBoard);
        gameState = new GameState();
        roundText = new JTextArea(Integer.toString(round));
        setTitle("SquareGame");
        this.buttonPanel = new ButtonPanel(this);
        this.getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
        this.getContentPane().add(gameBoardView, BorderLayout.CENTER);
        this.getContentPane().add(roundText, BorderLayout.EAST);
        this.setSize(1000,1000);
        initializeGame();
    }


    private void initializeGame() {
        gameBoard.setStartingPositions(GameState.playerList);
        this.timer = new Timer(2, e -> {
            runRound();
            repaint();
        });
    }

    private void runTurns() {
        gameBoard.runAllTurns();
    }

    public static void main(String arg[]) throws InterruptedException {
        SwingUtilities.invokeLater(SquareGameMain::new);

    }

    public void startGame() {
        timer.start();
    }

    public void runRound() {
        this.runTurns();
        this.roundText.setText(this.gameState.printGameState());
        if (this.gameState.roundNumber++ >= 2500) {
            this.timer.stop();
            this.gameOver();
        }
        this.repaint();
    }
    public void gameOver() {
        this.roundText.append("\n" + this.gameState.printEndGame());
    }

}