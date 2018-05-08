package squaregame.model;

import squaregame.controller.SquareLogicClassLoader;
import squaregame.squares.SquareLogic;
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
    private List<SquareLogic> aiOptions;

    private Map<Player, Score> scoreBoard;
    public GameState() {
        roundNumber = 0;
        aiOptions = new ArrayList<>(Arrays.asList(new DefaultSquare(), new DefaultSquare2(), new squaregame.squares.player1.DefaultSquare()));
        final SquareLogicClassLoader squareLogicClassLoader = new SquareLogicClassLoader();
        squareLogicClassLoader.getAiOptions();
        playerList = new ArrayList<>();
        playerList.add(new Player("PLAYER1", Color.CYAN, aiOptions.get(0)));
        playerList.add(new Player("PLAYER2", Color.GREEN, aiOptions.get(0)));

    }

    public String printGameState() {
        return "Round: " + roundNumber + "\n" + printScore();
    }
    public String endGame() {
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

    public void reset() {
        roundNumber = 0;
    }

    public void nextRound() {
        this.roundNumber++;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public List<SquareLogic> getAiOptions() {
        return aiOptions;
    }

    public void setScoreBoard(Map<Player, Score> scoreBoard) {
        this.scoreBoard = scoreBoard;
    }
}
