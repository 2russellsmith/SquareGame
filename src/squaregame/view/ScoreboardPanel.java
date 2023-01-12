package squaregame.view;

import squaregame.model.Player;

import javax.swing.*;
import java.util.List;

public class ScoreboardPanel extends JPanel {
    public ScoreboardPanel(List<Player> playerList) {
        this.add(new ScoreView());
    }
}
