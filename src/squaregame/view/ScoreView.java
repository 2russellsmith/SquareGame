package squaregame.view;

import lombok.Getter;
import squaregame.model.Player;

import java.awt.*;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JPanel;

@Getter
public class ScoreView extends JPanel {

    private JLabel generated;
    private JLabel kills;
    private JLabel collisions;
    private JLabel eliminated;
    private JLabel score;
    private JLabel turnClock;

    public ScoreView() {
        this.setLayout(new GridLayout(2, 3));
        this.score = new JLabel("Score=  ");
        score.setPreferredSize(new Dimension(200, 20));
        add(score);

        this.generated = new JLabel("Generated=  ");
        generated.setPreferredSize(new Dimension(200, 20));
        add(generated);

        this.kills = new JLabel("Kills=  ");
        kills.setPreferredSize(new Dimension(200, 20));
        add(kills);

        this.turnClock = new JLabel("TurnClock=  ");
        turnClock.setPreferredSize(new Dimension(200, 20));
        add(turnClock);

        this.collisions = new JLabel("Collisions=  ");
        collisions.setPreferredSize(new Dimension(200, 20));
        add(collisions);

        this.eliminated = new JLabel("Eliminated=  ");
        eliminated.setPreferredSize(new Dimension(200, 20));
        add(eliminated);

    }

    public void setColor(Player player) {
        Arrays.stream(this.getComponents()).forEach(comp -> {
            comp.setForeground(player.getTextColor());
        });
        this.setBackground(player.getColor());
    }
}
