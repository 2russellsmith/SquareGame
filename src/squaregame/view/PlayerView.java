package squaregame.view;

import squaregame.model.Player;

import javax.swing.*;
import java.awt.*;

public class PlayerView extends JPanel {
    private final ScoreView scoreView;
    private final Player p;


    public PlayerView(Player p) {
        this.setLayout(new GridBagLayout());
        this.setBackground(newColorWithAlpha(p.getColor()));
        this.p = p;
        this.scoreView = new ScoreView();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(scoreView, gbc);
    }

    public static Color newColorWithAlpha(Color original, int alpha) {
        return new Color(original.getRed(), original.getGreen(), original.getBlue(), alpha);
    }

    public static Color newColorWithAlpha(Color original) {
        return newColorWithAlpha(original, 200);
    }

    public void setGenerated(int generated) {
        this.scoreView.getGenerated().setText("Generated:" + generated);
    }

    public void setKills(int kills) {
        this.scoreView.getKills().setText("Kills:" + kills);
    }

    public void setCollisions(int collisions) {
        this.scoreView.getCollisions().setText("Collisions:" + collisions);
    }

    public void setEliminated(int eliminated) {
        this.scoreView.getEliminated().setText("Eliminated:" + eliminated);
    }

    public void setScore(int score, boolean debugEnabled) {
        if (debugEnabled) {
            this.scoreView.getScore().setText("Score:  " + score);
        } else {
            this.scoreView.getScore().setText(String.valueOf(score));
        }
    }

    public void setPlayerName(String name) {
        this.scoreView.getPlayerName().setText(name);
    }

    public void setDebug(boolean debugView) {
        this.scoreView.setDebugView(debugView);
    }

    public void setTurnClock(long avgTurnTime) {
        this.scoreView.getTurnClock().setText("TurnClock:" + avgTurnTime);
    }

    public void setColor() {
        this.scoreView.setOpaque(false);
        this.scoreView.setColor(this.p);
        this.setBackground(newColorWithAlpha(this.p.getColor()));
    }

    public void setDebugView(boolean debug) {
        this.scoreView.setDebugView(debug);
    }
}
