package squaregame.view;

import squaregame.model.Player;

import javax.swing.*;
import java.awt.*;

public class PlayerView extends JPanel {
    private ScoreView scoreView;
    private Player p;


    public PlayerView(Player p) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBackground(p.getColor());
        this.p = p;
        this.scoreView = new ScoreView();
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
    public void setPlayerName(String name) {
        this.scoreView.getPlayerName().setText(name);
    }

    public void setDebug(boolean debugView) {
        this.scoreView.setDebugView(debugView);
    }

    public void setTurnClock(long avgTurnTime) {
        this.scoreView.getTurnClock().setText("TurnClock=" + avgTurnTime);
    }

    public void setColor(){
        this.scoreView.setOpaque(false);
        this.scoreView.setColor(this.p);

        this.setBackground(newColorWithAlpha(this.p.getColor()));
        this.setOpaque(true);
    }
    public void setDebugView(boolean debug) {
        this.scoreView.setDebugView(debug);
    }
    public static Color newColorWithAlpha(Color original, int alpha) {
        return new Color(original.getRed(), original.getGreen(), original.getBlue(), alpha);
    }
    public static Color newColorWithAlpha(Color original) {
        return newColorWithAlpha(original, 240);
    }
}
