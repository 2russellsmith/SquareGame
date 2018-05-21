package squaregame.view;

import squaregame.controller.GameBoardController;
import squaregame.model.GameState;

import java.util.Objects;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Created by Russell on 5/10/18.
 */
public class LeaderboardPanel extends JPanel {

    private JTextArea oneVsOneLeaderboard;
    private JTextArea freeForAllLeaderboard;

    public LeaderboardPanel(GameBoardController gameBoardController) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.oneVsOneLeaderboard = new JTextArea();
        this.freeForAllLeaderboard = new JTextArea();

        this.oneVsOneLeaderboard.setEditable(false);
        this.oneVsOneLeaderboard.setLineWrap(true);
        this.oneVsOneLeaderboard.setWrapStyleWord(true);
        this.oneVsOneLeaderboard.setFocusable(false);
        this.oneVsOneLeaderboard.setRows(0);
        this.oneVsOneLeaderboard.invalidate();

        this.freeForAllLeaderboard.setEditable(false);
        this.freeForAllLeaderboard.setLineWrap(true);
        this.freeForAllLeaderboard.setWrapStyleWord(true);
        this.freeForAllLeaderboard.setFocusable(false);
        this.freeForAllLeaderboard.setRows(0);
        this.freeForAllLeaderboard.invalidate();
        this.add(freeForAllLeaderboard);
        this.add(oneVsOneLeaderboard);
    }

    public void update(GameState gameState) {
        final String status = gameState.getAiOptions().stream()
                .filter(Objects::nonNull)
                .sorted((a1, a2) -> Double.compare(gameState.getLeaderboard().getScore(a2.getId()), gameState.getLeaderboard().getScore(a1.getId())))
                .map(aiOption -> aiOption.getSquareLogic().getSquareName() + ": " +
                        gameState.getLeaderboard().getScore(aiOption.getId()))
                .collect(Collectors.joining("\n"));
        this.oneVsOneLeaderboard.setText("One Vs One Standings \n\n\n" + status);
        final String freeForAll = gameState.getAiOptions().stream()
                .filter(Objects::nonNull)
                .sorted((a1, a2) -> Double.compare(gameState.getFreeForAllLeaderboard().getScore(a2.getId()), gameState.getFreeForAllLeaderboard().getScore(a1.getId())))
                .map(aiOption -> aiOption.getSquareLogic().getSquareName() + ": " +
                        gameState.getFreeForAllLeaderboard().getScore(aiOption.getId()))
                .collect(Collectors.joining("\n"));
        this.freeForAllLeaderboard.setText("Free For All Standings \n\n\n" + freeForAll);
    }
}
