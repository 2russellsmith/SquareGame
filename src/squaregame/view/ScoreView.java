package squaregame.view;

import lombok.Getter;
import squaregame.model.Player;

import java.awt.Dimension;
import java.awt.GridLayout;

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

    public ScoreView(Player player) {
        this.setLayout(new GridLayout(2, 3));
        this.setBackground(player.getColor());

        this.score = new JLabel("Score=  ");
        score.setBackground(player.getColor());
        score.setForeground(player.getTextColor());
        score.setPreferredSize(new Dimension(200, 20));
        add(score);

        this.generated = new JLabel("Generated=  ");
        generated.setBackground(player.getColor());
        generated.setForeground(player.getTextColor());
        generated.setPreferredSize(new Dimension(200, 20));
        add(generated);

        this.kills = new JLabel("Kills=  ");
        kills.setBackground(player.getColor());
        kills.setForeground(player.getTextColor());
        kills.setPreferredSize(new Dimension(200, 20));
        add(kills);

        this.turnClock = new JLabel("TurnClock=  ");
        turnClock.setBackground(player.getColor());
        turnClock.setForeground(player.getTextColor());
        turnClock.setPreferredSize(new Dimension(200, 20));
        add(turnClock);

        this.collisions = new JLabel("Collisions=  ");
        collisions.setBackground(player.getColor());
        collisions.setForeground(player.getTextColor());
        collisions.setPreferredSize(new Dimension(200, 20));
        add(collisions);

        this.eliminated = new JLabel("Eliminated=  ");
        eliminated.setBackground(player.getColor());
        eliminated.setForeground(player.getTextColor());
        eliminated.setPreferredSize(new Dimension(200, 20));
        add(eliminated);

    }
}
