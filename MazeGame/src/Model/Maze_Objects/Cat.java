package Model.Maze_Objects;

/**
 *
 */
public class Cat extends MazeObject {
    private int lastPosition;
    private boolean occupiedSpaceVisible = false;

    public Cat(int col, int row) {
        super(col, row, true, true);
        lastPosition = -1; // no move made yet
    }

    public int getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
    }

    public boolean isOccupiedSpaceVisible() {
        return occupiedSpaceVisible;
    }
    public void setOccupiedSpaceVisible(boolean occupiedSpaceVisible) {
        this.occupiedSpaceVisible = occupiedSpaceVisible;
    }

}
