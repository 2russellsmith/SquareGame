package squaregame.model;

public enum Direction {
    NW(-1, -1),
    N(0, -1),
    NE(1, -1),
    E(1, 0),
    SE(1, 1),
    S(0, 1),
    SW(-1, 1),
    W(-1, 0),
    CENTER(0, 0);
    private final int xOffset;
    private final int yOffset;

    Direction(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public int getxOffset() {
        return xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    public Direction getOppositeDirection() {
        return rotateClockwise(4);
    }

    public Direction rotateClockwise(int turns) {
        return Direction.values()[(Math.floorMod(this.ordinal() + turns, 8))];
    }
}