package squaregame.model;

import squaregame.squares.assassin.DefaultSquare;
import squaregame.squares.player2.DefaultSquare2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Russell on 5/5/18.
 */
public class GameState {
    private int roundNumber;
    private List<Player> playerList;

    private Map<Player, Score> scoreBoard;
    public GameState() {
        roundNumber = 0;
        playerList = new ArrayList<>(
                Arrays.asList(new Player("Assassin", new Color(251, 255, 89)),
                        new Player("Player2", new Color(255,0, 0))));
        playerList.get(0).setStartingLogic(new DefaultSquare(playerList.get(0)));
        playerList.get(1).setStartingLogic(new DefaultSquare2(playerList.get(1)));

    }

    public String printGameState() {
        return "Round: " + roundNumber + "\n" + printScore();
    }
    public String printEndGame() {
        return playerList.stream().max(Comparator.comparing(p -> scoreBoard.get(p).getScore())).get().getName() + " WINS THE GAME!!!";
    }

    public boolean someoneWon() {
        return playerList.stream().filter(p -> scoreBoard.get(p).getScore() > 0).count() < 2;
    }

    public String printScore() {
        return playerList.stream().map(p -> p.getName() + ": " + scoreBoard.get(p).getScore()).collect(Collectors.joining("\n"));
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void nextRound() {
        this.roundNumber++;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setScoreBoard(Map<Player, Score> scoreBoard) {
        this.scoreBoard = scoreBoard;
    }
}
