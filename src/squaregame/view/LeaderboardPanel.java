package squaregame.view;

import squaregame.controller.GameBoardController;
import squaregame.model.GameState;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.swing.*;

import static squaregame.controller.GameBoardController.GLOBAL_FONT;
import static squaregame.view.PlayerView.newColorWithAlpha;

/**
 * Created by Russell on 5/10/18.
 */
public class LeaderboardPanel extends JPanel {

    private final JPanel oneVsOneLeaderboard;
    private final JPanel freeForAllLeaderboard;

    public LeaderboardPanel() {
        this.setLayout(new GridBagLayout());
        this.setOpaque(false);
        this.oneVsOneLeaderboard = new JPanel();
        this.freeForAllLeaderboard = new JPanel();
        this.oneVsOneLeaderboard.setLayout(new GridBagLayout());
        this.freeForAllLeaderboard.setLayout(new GridBagLayout());
        this.oneVsOneLeaderboard.setBackground(newColorWithAlpha(Color.BLACK));
        this.freeForAllLeaderboard.setBackground(newColorWithAlpha(Color.BLACK));
        this.oneVsOneLeaderboard.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        this.freeForAllLeaderboard.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(oneVsOneLeaderboard, gbc);
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(freeForAllLeaderboard, gbc);
    }

    public void update(GameState gameState) {
        Font scoreboardFont = new Font(GLOBAL_FONT.getFontName(), GLOBAL_FONT.getStyle(), GLOBAL_FONT.getSize());
        this.oneVsOneLeaderboard.removeAll();
        AtomicInteger row = new AtomicInteger();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = row.getAndIncrement();
        JLabel title = new JLabel("One Vs One Standings");
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        this.oneVsOneLeaderboard.add(title, gbc);
        gameState.getAiOptions().stream()
                .filter(Objects::nonNull)
                .sorted((a1, a2) -> Double.compare(gameState.getLeaderboard().getScore(a2.getId()), gameState.getLeaderboard().getScore(a1.getId())))
                .map(aiOption -> aiOption.getId() + ": " +
                        gameState.getLeaderboard().getScore(aiOption.getId()))
                .forEach(s -> {
                    gbc.gridy = row.getAndIncrement();
                    JLabel playerData = new JLabel(s);
                    playerData.setForeground(Color.WHITE);
                    this.oneVsOneLeaderboard.add(playerData, gbc);
                });
        Arrays.stream(this.oneVsOneLeaderboard.getComponents()).forEach(c -> c.setFont(scoreboardFont));
        title.setFont(new Font(GLOBAL_FONT.getFontName(), Font.ITALIC, GLOBAL_FONT.getSize() + 5));

        this.freeForAllLeaderboard.removeAll();
        row.set(0);
        gbc.gridy = row.getAndIncrement();
        JLabel title2 = new JLabel("Free for All Standings");
        title2.setForeground(Color.WHITE);
        title2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        this.freeForAllLeaderboard.add(title2, gbc);
        gameState.getAiOptions().stream()
                .filter(Objects::nonNull)
                .sorted((a1, a2) -> Double.compare(gameState.getFreeForAllLeaderboard().getScore(a2.getId()), gameState.getFreeForAllLeaderboard().getScore(a1.getId())))
                .map(aiOption -> aiOption.getId() + ": " +
                        gameState.getFreeForAllLeaderboard().getScore(aiOption.getId()))
                .forEach(s -> {
                    gbc.gridy = row.getAndIncrement();
                    JLabel playerData = new JLabel(s);
                    playerData.setForeground(Color.WHITE);
                    this.freeForAllLeaderboard.add(playerData, gbc);
                });
        Arrays.stream(this.freeForAllLeaderboard.getComponents()).forEach(c -> c.setFont(scoreboardFont));
        title2.setFont(new Font(GLOBAL_FONT.getFontName(), Font.ITALIC, GLOBAL_FONT.getSize() + 5));
    }
}
