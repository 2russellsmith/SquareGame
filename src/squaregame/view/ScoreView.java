package squaregame.view;

import lombok.Getter;
import squaregame.model.Player;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

@Getter
public class ScoreView extends JPanel {

    private JLabel generated;
    private JLabel kills;
    private JLabel collisions;
    private JLabel eliminated;

    public ScoreView(Player player) {
        this.setLayout(new GridLayout(2, 2));
        this.setBackground(player.getColor());

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
