import java.awt.*;

/**
 * Created by Russell on 5/5/18.
 */
public class Player {
    Color color;
    int score;

    public Player (Color color) {
        this.color = color;
        score = 1;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getScore() {
        return this.score;
    }
    public void addPoint() {
        this.score ++;
    }
    public void removePoint(){
        this.score--;
    }
}
