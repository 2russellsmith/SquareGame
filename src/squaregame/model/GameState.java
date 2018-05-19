package squaregame.model;

import org.reflections.Reflections;
import squaregame.squares.SquareLogic;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Russell on 5/5/18.
 */
public class GameState {
    private int roundNumber;
    private int totalRounds = 3000;

    private List<Player> playerList;
    private Map<Player, Set<Player>> whoPlayersBeat;
    private List<AIOption> aiOptions;
    private boolean freeForAll;
    private Leaderboard freeForAllLeaderboard;
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
        this.freeForAllLeaderboard = new Leaderboard(aiOptions.size());
        this.scoreBoard = new HashMap<>();
        playerList = new ArrayList<>();
        playerList.add(new Player(new Color(255, 0, 0), Color.WHITE, aiOptions.get(0)));
        playerList.add(new Player(new Color(255, 255 / 2, 0), Color.BLACK, aiOptions.get(0)));
        playerList.add(new Player(new Color(0, 0, 255), Color.WHITE, aiOptions.get(0)));
        playerList.add(new Player(new Color(255, 255, 0), Color.BLACK, aiOptions.get(0)));
        playerList.add(new Player(new Color(128, 0, 128), Color.WHITE, aiOptions.get(0)));
        playerList.add(new Player(new Color(0, 255, 0), Color.BLACK, aiOptions.get(0)));
        playerList.add(new Player(new Color(100, 100, 100), Color.WHITE, aiOptions.get(0)));
        playerList.add(new Player(new Color(255, 255, 255), Color.BLACK, aiOptions.get(0)));

    }

    public void setTotalRounds(int totalRounds) {
        this.totalRounds = totalRounds;
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
        if (this.isFreeForAll()) {
            this.whoPlayersBeat.forEach((key, value) -> value.forEach(loser -> this.freeForAllLeaderboard.addScore(key.getAiOption().getId(), loser.getAiOption().getId())));
        } else {
            this.whoPlayersBeat.forEach((key, value) -> value.forEach(loser -> this.leaderboard.addScore(key.getAiOption().getId(), loser.getAiOption().getId())));
        }
    }

    public void rankNewDeadPlayers() {
        playerList.stream()
                .filter(Player::isPlaying)
                .filter(p -> this.scoreBoard.get(p).getScore() == 0)
                .forEach(loser -> this.whoPlayersBeat.putIfAbsent(loser, new HashSet<>(this.whoPlayersBeat.keySet())));
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void reset() {
        roundNumber = 0;
        this.whoPlayersBeat = new HashMap<>();
        getPlayerList().forEach(p -> scoreBoard.put(p, new Score()));
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

    public Leaderboard getLeaderboard() {
        return leaderboard;
    }

    public boolean gameOver() {
        return totalRounds < this.roundNumber || this.someoneWon();
    }

    public boolean isFreeForAll() {
        return this.freeForAll;
    }

    public void setIsFreeForAll(boolean freeForAll) {
        this.freeForAll = freeForAll;
    }

    public Leaderboard getFreeForAllLeaderboard() {
        return freeForAllLeaderboard;
    }

    public Map<Player, Score> getScoreBoard() {
        return scoreBoard;
    }
}
