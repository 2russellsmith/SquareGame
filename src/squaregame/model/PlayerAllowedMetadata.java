package squaregame.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class PlayerAllowedMetadata {

    private final int boardSize;
    private final int roundNumber;
    private final Map<Player, Score> scoreBoard;

    public PlayerAllowedMetadata(int boardSize, int roundNumber, Map<Player, Score> scoreBoard) {
        this.boardSize = boardSize;
        this.roundNumber = roundNumber;
        this.scoreBoard = new HashMap<>(scoreBoard);
    }

}
