package squaregame.model;

public class PlayerAllowedMetadata {

    private final int boardSize;

    public PlayerAllowedMetadata(int boardSize) {
        this.boardSize = boardSize;
    }

    public int getBoardSize() {
        return boardSize;
    }
}
