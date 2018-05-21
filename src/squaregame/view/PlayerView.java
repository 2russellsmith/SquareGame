package squaregame.view;

import squaregame.model.Player;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class PlayerView extends JPanel {
    private ScoreView scoreView;

    public PlayerView(Player p) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
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
        this.scoreView.getScore().setText("Score=" + score);
    }

    public void setTurnClock(long avgTurnTime) {
        this.scoreView.getTurnClock().setText("TurnClock=" + avgTurnTime);
    }
}
