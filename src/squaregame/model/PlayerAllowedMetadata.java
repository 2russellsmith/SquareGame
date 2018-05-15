package squaregame.model;

public class PlayerAllowedMetadata {

    private final int boardSize;
    private final int roundNumber;

    public PlayerAllowedMetadata(int boardSize, int roundNumber) {
        this.boardSize = boardSize;
        this.roundNumber = roundNumber;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int getRoundNumber() {
        return this.roundNumber;
    }
}
