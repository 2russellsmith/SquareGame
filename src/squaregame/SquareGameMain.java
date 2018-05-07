package squaregame;

import squaregame.model.GameState;
import squaregame.view.ButtonPanel;
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
    private GameState gameState;

    private GameBoard gameBoard;
    private GameBoardView gameBoardView;
    private Timer timer;
    private int round = 0;

    public SquareGameMain() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        this.gameBoardView = new GameBoardView(this.gameBoard);
        resetGame();
        this.roundText = new JTextArea(Integer.toString(this.round));
        setTitle("SquareGame");
        this.buttonPanel = new ButtonPanel(this);
        this.getContentPane().add(this.buttonPanel, BorderLayout.PAGE_END);
        this.getContentPane().add(this.gameBoardView, BorderLayout.CENTER);
        this.getContentPane().add(this.roundText, BorderLayout.EAST);
        this.setSize(1000, 1000);
        initializeGame();
    }


    private void initializeGame() {
        this.timer = new Timer(1, e -> {
            runRound();
            repaint();
        });
    }

    private void runTurns() {
        gameBoard.runAllTurns();
    }

    public static void main(String arg[]) {
        SwingUtilities.invokeLater(SquareGameMain::new);

    }

    public void startGame() {
        this.resetGame();
        this.gameBoard.setStartingPositions(this.gameState.getPlayerList());
        repaint();
        timer.start();
    }

    public void runRound() {
        this.runTurns();
        this.roundText.setText(this.gameState.printGameState());
        if (this.gameState.someoneWon() || this.gameState.getRoundNumber() >= 100000) {
            this.timer.stop();
            this.gameOver();
        }
        this.gameState.nextRound();
        this.repaint();
    }
    public void gameOver() {
        this.roundText.append("\n" + this.gameState.printEndGame());
    }

    public void resetGame() {
        this.gameState = new GameState();
        this.gameBoard = new GameBoard(new MagicSquare[BOARD_VISUAL_SIZE][BOARD_VISUAL_SIZE], this.gameState);
        this.gameBoardView.setGameBoard(this.gameBoard);
        this.repaint();
    }

    public Timer getTimer() {
        return this.timer;
    }
}