package squaregame.model;

public class Score {
    private int score = 0;

    public void addPoint() {
        score++;
    }

    public int getScore() {
        return this.score;
    }

    public void resetScore() {
        this.score = 0;
    }
}
