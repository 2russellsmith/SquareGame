package squaregame.view;

import lombok.Getter;
import lombok.Setter;
import squaregame.model.Player;

import java.awt.*;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JPanel;

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
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.WEST;
        this.playerName = new JLabel();
        playerName.setPreferredSize(new Dimension(200, 20));
        add(playerName, gbc);


        gbc.gridheight = 1;
        gbc.gridx = 1;
        gbc.gridy = 0;
        this.score = new JLabel("Score=  ");
        score.setPreferredSize(new Dimension(200, 20));
        add(score, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        this.generated = new JLabel("Generated=  ");
        generated.setPreferredSize(new Dimension(200, 20));
        add(generated, gbc);


        gbc.gridx = 3;
        gbc.gridy = 0;
        this.kills = new JLabel("Kills=  ");
        kills.setPreferredSize(new Dimension(200, 20));
        add(kills, gbc);


        gbc.gridx = 1;
        gbc.gridy = 1;
        this.turnClock = new JLabel("TurnClock=  ");
        turnClock.setPreferredSize(new Dimension(200, 20));
        add(turnClock, gbc);


        gbc.gridx = 2;
        gbc.gridy = 1;
        this.collisions = new JLabel("Collisions=  ");
        collisions.setPreferredSize(new Dimension(200, 20));
        add(collisions, gbc);


        gbc.gridx = 3;
        gbc.gridy = 1;
        this.eliminated = new JLabel("Eliminated=  ");
        eliminated.setPreferredSize(new Dimension(200, 20));
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
        });
    }
}
