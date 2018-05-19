package squaregame.view;

import squaregame.model.Player;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerView extends JPanel {
    private final JLabel score;
    private ScoreView scoreView;

    public PlayerView(JLabel score, Player p) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.score = score;
        this.setBackground(p.getColor());
        this.scoreView = new ScoreView(p);
        add(scoreView);
    }

    public void setGenerated(int generated) {
        this.scoreView.getGenerated().setText("Generated=" + generated);
    }

    public void setKills(int kills) {
        this.scoreView.getKills().setText("Kills=" + kills);
    }

    public void setCollisions(int collisions) {
        this.scoreView.getCollisions().setText("Collisions=" + collisions);
    }

    public void setEliminated(int eliminated) {
        this.scoreView.getEliminated().setText("Eliminated=" + eliminated);
    }

    public void setScore(int score) {
        this.score.setText("Score=" + score);
    }
}
