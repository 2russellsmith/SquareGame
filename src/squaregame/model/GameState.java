package squaregame.model;

import org.reflections.Reflections;
import squaregame.squares.SquareLogic;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Russell on 5/5/18.
 */
public class GameState {
    private int roundNumber;
    private int MAX_PLAYERS = 8;

    private List<Player> playerList;
    private List<SquareLogic> aiOptions;

    private Map<Player, Score> scoreBoard;
    public GameState() {
        roundNumber = 0;
        final Reflections reflections = new Reflections("squaregame.squares");
        final Set<Class<? extends SquareLogic>> classes = reflections.getSubTypesOf(SquareLogic.class);
        aiOptions = new ArrayList<>();
        aiOptions.add(null);
        classes.forEach(c -> {
            if (c.getSimpleName().equals("DefaultSquare")) {
                try {
                    aiOptions.add(c.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        playerList = new ArrayList<>();
        playerList.add(new Player(Color.RED, aiOptions.get(0)));
        playerList.add(new Player(Color.GREEN, aiOptions.get(0)));
        playerList.add(new Player(Color.BLUE, aiOptions.get(0)));
        playerList.add(new Player(Color.PINK, aiOptions.get(0)));
        playerList.add(new Player(Color.YELLOW, aiOptions.get(0)));
        playerList.add(new Player(Color.ORANGE, aiOptions.get(0)));
        playerList.add(new Player(Color.CYAN, aiOptions.get(0)));
        playerList.add(new Player(Color.MAGENTA, aiOptions.get(0)));

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
