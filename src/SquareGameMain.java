import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SquareGameMain extends JFrame {

    public static final Integer BOARD_SIZE = 150;
    public static final Integer SQUARE_SIZE = 4;
    public static final Integer BOARD_VISUAL_SIZE = SQUARE_SIZE * BOARD_SIZE;
    public static List<Player> playerList = new ArrayList<>(
            Arrays.asList(new Player(new Color(50, 100, 255)), new Player(new Color(255,0, 0))));
    private final ButtonPanel buttonPanel;

    GameBoard gameBoard = new GameBoard(new MagicSquare[BOARD_VISUAL_SIZE][BOARD_VISUAL_SIZE]);
    public Timer timer;

    public SquareGameMain() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        JPanel p = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);
                g2.fillRect(-1,-1, BOARD_VISUAL_SIZE + 1, BOARD_VISUAL_SIZE + 1);
                gameBoard.draw(g2);
                setPreferredSize(new Dimension(BOARD_VISUAL_SIZE + 1, BOARD_VISUAL_SIZE + 1));
            }
        };
        setTitle("SquareGame");
        this.buttonPanel = new ButtonPanel(this);
        this.getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
        this.getContentPane().add(p, BorderLayout.CENTER);
        this.setSize(1000,1000);
        initializeGame();
    }


    private void initializeGame() {
        gameBoard.setStartingPositions(playerList);
        this.timer = new Timer(20, e -> {
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
        this.repaint();
    }

}