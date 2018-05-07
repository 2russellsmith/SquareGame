package squaregame.model;

import squaregame.Player;
import squaregame.squares.player1.DefaultSquare;
import squaregame.squares.player2.DefaultSquare2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Russell on 5/5/18.
 */
public class GameState {
    public int roundNumber;
    public static List<Player> playerList = new ArrayList<>(
            Arrays.asList(new Player("Player1", new Color(50, 100, 255), new DefaultSquare()),
                    new Player("Player2", new Color(255,0, 0), new DefaultSquare2())));
    public GameState() {
        roundNumber = 0;

    }

    public String printGameState() {
        return "Round: " + roundNumber + "\n" + playerList.get(0).printScore() + "\n" + playerList.get(1).printScore();
    }
    public String printEndGame() {
        return playerList.stream().max(Comparator.comparing(Player::getScore)).get().name + " WINS THE GAME!!!";
    }
}
