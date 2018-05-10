package squaregame.model;

import org.reflections.Reflections;
import squaregame.squares.SquareLogic;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by Russell on 5/5/18.
 */
public class GameState {
    private int roundNumber;
    private int totalRounds = 3000;

    private List<Player> playerList;
    private Map<Player, Set<Player>> whoPlayersBeat;
    private List<AIOption> aiOptions;
    private Leaderboard leaderboard;

    private Map<Player, Score> scoreBoard;
    public GameState() {
        roundNumber = 0;
        final Reflections reflections = new Reflections("squaregame.squares");
        final Set<Class<? extends SquareLogic>> classes = reflections.getSubTypesOf(SquareLogic.class);
        aiOptions = new ArrayList<>();
        aiOptions.add(null);
        final AtomicInteger aiId = new AtomicInteger(0);
        classes.forEach(c -> {
            if (c.getSimpleName().equals("DefaultSquare")) {
                try {
                    aiOptions.add(new AIOption(c.newInstance(), aiId.getAndIncrement()));
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        this.whoPlayersBeat = new HashMap<>();
        this.leaderboard = new Leaderboard(aiOptions.size());
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

    public void setTotalRounds(int totalRounds) {
        this.totalRounds = totalRounds;
    }

    public String printGameState() {
        return "Round: " + roundNumber + "\n" + printScore();
    }

    public boolean someoneWon() {
        return playerList.stream().filter(p -> scoreBoard.get(p).getScore() > 0).count() < 2;
    }

    public void finalRank() {
        rankNewDeadPlayers();
        final Comparator<Player> comp = Comparator.comparingInt(p1 -> this.scoreBoard.get(p1).getScore());
        playerList.stream()
                .filter(Player::isPlaying)
                .filter(p -> this.scoreBoard.get(p).getScore() != 0)
                .sorted(comp)
                .forEach(loser -> this.whoPlayersBeat.putIfAbsent(loser, new HashSet<>(this.whoPlayersBeat.keySet())));
        this.whoPlayersBeat.forEach((key, value) -> value.forEach(loser -> this.leaderboard.addScore(key.getAiOption().getId(), loser.getAiOption().getId())));

    }

    public void rankNewDeadPlayers() {
        playerList.stream()
                .filter(Player::isPlaying)
                .filter(p -> this.scoreBoard.get(p).getScore() == 0)
                .forEach(loser -> this.whoPlayersBeat.putIfAbsent(loser, new HashSet<>(this.whoPlayersBeat.keySet())));
    }

    public String printScore() {
        return playerList.stream().filter(Player::isPlaying).map(p -> p.getName() + ": " + scoreBoard.get(p).getScore()).collect(Collectors.joining("\n"));
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void reset() {
        roundNumber = 0;
        this.whoPlayersBeat = new HashMap<>();
    }

    public void nextRound() {
        this.roundNumber++;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public List<AIOption> getAiOptions() {
        return aiOptions;
    }

    public void setScoreBoard(Map<Player, Score> scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    public Leaderboard getLeaderboard() {
        return leaderboard;
    }

    public String printLeaderBoard() {
        final List<AIOption> results = aiOptions.stream()
                .filter(Objects::nonNull)
                .sorted((a1, a2) -> Double.compare(this.leaderboard.getWinRate(a2.getId()), this.leaderboard.getWinRate(a1.getId())))
                .collect(Collectors.toList());
        return results.stream().filter(Objects::nonNull)
                .map(aiOption -> aiOption.getSquareLogic().getSquareName() + ": " +
                this.leaderboard.getWins(aiOption.getId()) +
                        "(" + this.leaderboard.getWinRate(aiOption.getId()) + "%)")
                .collect(Collectors.joining("\n"));
    }

    public Map<Player, Set<Player>> getWhoPlayersBeat() {
        return this.whoPlayersBeat;
    }

    public boolean gameOver() {
        return totalRounds < this.roundNumber || this.someoneWon();
    }
}
