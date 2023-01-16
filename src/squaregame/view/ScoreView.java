package squaregame.view;

import lombok.Getter;
import squaregame.model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static squaregame.controller.GameBoardController.GLOBAL_FONT;

@Getter
public class ScoreView extends JPanel {

    private final JLabel playerName;
    private final JLabel generated;
    private final JLabel kills;
    private final JLabel collisions;
    private final JLabel eliminated;
    private final JLabel score;
    private final JLabel turnClock;

    public ScoreView() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = .25;
        gbc.weighty = 1;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.playerName = new JLabel("", SwingConstants.LEFT);
        add(playerName, gbc);

        gbc.gridheight = 1;
        gbc.gridx = 1;
        gbc.gridy = 0;
        this.score = new JLabel(":", SwingConstants.LEFT);
        add(score, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        this.generated = new JLabel("Generated=  ", SwingConstants.LEFT);
        add(generated, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        this.kills = new JLabel("Kills=  ", SwingConstants.LEFT);
        add(kills, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        this.turnClock = new JLabel("TurnClock=  ", SwingConstants.LEFT);
        add(turnClock, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        this.collisions = new JLabel("Collisions=  ", SwingConstants.LEFT);
        add(collisions, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        this.eliminated = new JLabel("Eliminated=  ", SwingConstants.LEFT);
        add(eliminated, gbc);
    }

    public void setDebugView(boolean debug) {
        this.collisions.setVisible(debug);
        this.eliminated.setVisible(debug);
        this.turnClock.setVisible(debug);
        this.kills.setVisible(debug);
        this.generated.setVisible(debug);
    }


    public void setColor(Player player) {
        Arrays.stream(this.getComponents()).forEach(comp -> {
            comp.setForeground(player.getTextColor());
            comp.setPreferredSize(new Dimension(2, 20));
            comp.setFont(GLOBAL_FONT);
        });
    }
}
