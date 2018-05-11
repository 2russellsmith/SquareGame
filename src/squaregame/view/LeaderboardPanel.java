package squaregame.view;

import com.sun.deploy.panel.JSmartTextArea;
import squaregame.controller.GameBoardController;
import squaregame.model.GameState;

import javax.swing.*;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Russell on 5/10/18.
 */
public class LeaderboardPanel extends JPanel {

    JSmartTextArea oneVsOneLeaderboard;
    JSmartTextArea freeForAllLeaderboard;

    public LeaderboardPanel(GameBoardController gameBoardController) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.oneVsOneLeaderboard = new JSmartTextArea();
        this.add(oneVsOneLeaderboard);
        this.freeForAllLeaderboard = new JSmartTextArea();
        this.add(freeForAllLeaderboard);
    }

    public void update(GameState gameState) {
        String status = gameState.getAiOptions().stream()
                .filter(Objects::nonNull)
                .sorted((a1, a2) -> Double.compare(gameState.getLeaderboard().getWinRate(a2.getId()), gameState.getLeaderboard().getWinRate(a1.getId())))
                .map(aiOption -> aiOption.getSquareLogic().getSquareName() + ": " +
                        gameState.getLeaderboard().getWins(aiOption.getId()) + "/" + gameState.getLeaderboard().getGamesPlayed(aiOption.getId()) +
                        " (" + gameState.getLeaderboard().getWinRate(aiOption.getId()) + "%)")
                .collect(Collectors.joining("\n"));
        this.oneVsOneLeaderboard.setText("One Vs One Standings \n\n\n" + status);
        String freeForAll = gameState.getAiOptions().stream()
                .filter(Objects::nonNull)
                .sorted((a1, a2) -> Double.compare(gameState.getFreeForAllLeaderboard().getWinRate(a2.getId()), gameState.getFreeForAllLeaderboard().getWinRate(a1.getId())))
                .map(aiOption -> aiOption.getSquareLogic().getSquareName() + ": " +
                        gameState.getFreeForAllLeaderboard().getWins(aiOption.getId()) + "/" + gameState.getFreeForAllLeaderboard().getGamesPlayed(aiOption.getId()) +
                        "(" + gameState.getFreeForAllLeaderboard().getWinRate(aiOption.getId()) + "%)")
                .collect(Collectors.joining("\n"));
        this.freeForAllLeaderboard.setText("Free For All Standings \n\n\n" + freeForAll);
    }
}
