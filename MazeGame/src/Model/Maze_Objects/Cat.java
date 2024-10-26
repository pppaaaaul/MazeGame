package Model.Maze_Objects;

/**
 * Class for the three cats in the maze
 * Is-a MazeObject
 * member: lastPosition direction of last position relevant to current
 * member: occupiedSpaceVisible used to determine whether a space on the grid will show up as ' ' or '.' after the cat
 *                              moves off it
 */
public class Cat extends MazeObject {
    private int lastPosition;
    private boolean occupiedSpaceVisible = false;

    /**
     * Creates a visible and passable Cat object with no lastMove
     * @param col the column of this Cat object's position
     * @param row the row of this Cat object's position
     */
    public Cat(int col, int row) {
        super(col, row, true, true);
        lastPosition = -1;
    }

    /**
     * returns this Cat's last position (direction relevant to current position)
     * @return lastPosition (direction relevant to current position)
     */
    public int getLastPosition() {
        return lastPosition;
    }

    /**
     * sets this Cat's last position (direction relevant to current position)
     * @param lastPosition This Cat's last position relevant to current position
     */
    public void setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
    }

    /**
     * returns whether the space under this Cat object is visible to the player or not
     * @return occupiedSpaceVisible (Whether the space under this Cat object is visible to the player)
     */
    public boolean isOccupiedSpaceVisible() {
        return occupiedSpaceVisible;
    }

    /**
     * sets this Cat's underlying space to visible or not for the player
     * @param occupiedSpaceVisible Whether the underlying space was visible to the player before the Cat moved there
     */
    public void setOccupiedSpaceVisible(boolean occupiedSpaceVisible) {
        this.occupiedSpaceVisible = occupiedSpaceVisible;
    }

}
