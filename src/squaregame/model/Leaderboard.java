package squaregame.model;

import java.util.Arrays;

public class Leaderboard {

    private final int[][] scoreboard;

    public Leaderboard(int numberOfCompetingAis) {
        this.scoreboard = new int[numberOfCompetingAis][numberOfCompetingAis];
    }

    public void addScore(int winner, int loser) {
        this.scoreboard[winner][loser]++;
    }

    public int getWins(int aiId) {
        return Arrays.stream(this.scoreboard[aiId]).sum();
    }

    public double getWinRate(int aiId) {
        final int wins = getWins(aiId);
        final int losses = getLosses(aiId);
        if (wins + losses != 0) {
            return (double) Math.round((double) wins / (wins + losses) * 10000) / 100;
        } else {
            return 0;
        }
    }

    public int getGamesPlayed(int aiId) {
        return getWins(aiId) + getLosses(aiId);
    }

    private int getLosses(int aiId) {
        return Arrays.stream(this.scoreboard).mapToInt(loss -> loss[aiId]).sum();
    }

}
