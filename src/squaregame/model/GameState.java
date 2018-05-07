package squaregame.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import squaregame.Player;
import squaregame.squares.assassin.DefaultSquare;
import squaregame.squares.player2.DefaultSquare2;

/**
 * Created by Russell on 5/5/18.
 */
public class GameState {
    public int roundNumber;
    public List<Player> playerList;
    public Map<Player, Score> scoreBoard;
    public GameState() {
        roundNumber = 0;
        playerList = new ArrayList<>(
                Arrays.asList(new Player("Assassin", new Color(50, 100, 255)),
                        new Player("Player2", new Color(255,0, 0))));
        playerList.get(0).setStartingLogic(new DefaultSquare(playerList.get(0)));
        playerList.get(1).setStartingLogic(new DefaultSquare2(playerList.get(1)));

    }

    public String printGameState() {
        return "Round: " + roundNumber + "\n" + printScore();
    }
    public String printEndGame() {
        return playerList.stream().max(Comparator.comparing(p -> scoreBoard.get(p).score)).get().name + " WINS THE GAME!!!";
    }

    public boolean someoneWon() {
        return playerList.stream().filter(p -> scoreBoard.get(p).score > 0).count() < 2;
    }

    public String printScore() {
        return playerList.stream().map(p -> p.name + ": " + scoreBoard.get(p).score).collect(Collectors.joining("\n"));
    }
}
