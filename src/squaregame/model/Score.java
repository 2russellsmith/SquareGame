package squaregame.model;

import lombok.Getter;

@Getter
public class Score {
    private int score = 0;
    private int generated = 0;
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

    public void resetScore() {
        this.score = 0;
    }

}
