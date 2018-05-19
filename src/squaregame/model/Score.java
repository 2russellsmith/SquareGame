package squaregame.model;

import lombok.Getter;

@Getter
public class Score {
    private int score = 0;
    private int generated = 1;
    private int killed = 0;
    private int collisions = 0;
    private int eliminated = 0;

    public void addPoint() {
        score++;
    }
    public void addGenerated() {
        generated++;
    }
    public void addKilled() {
        killed++;
    }
    public void addCollisions() {
        collisions++;
    }
    public void addEliminated() {
        eliminated++;
    }

    public int getScore() {
        return this.score;
    }

    public void resetScore() {
        this.score = 0;
    }

}
