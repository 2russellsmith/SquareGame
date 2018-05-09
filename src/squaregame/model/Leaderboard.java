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

}
