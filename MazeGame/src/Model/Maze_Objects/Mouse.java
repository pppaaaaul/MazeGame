package Model.Maze_Objects;

/**
 * Represents the mouse in the maze game and is used to keep track of the number of cheese collected.
 * member cheeseCollected: counts the number of cheese collected. Used to determine when the game is won
 */
public class Mouse extends MazeObject {
    private int cheeseCollected;

    /**
     * Constructor for the cheese object setting passable=false, visible=true, current column and current row
     * @param col current column of this Mouse in the maze
     * @param row current row of this Mouse in the maze
     */
    public Mouse(int col, int row) {
        super(col, row, false, true);
        cheeseCollected = 0;
    }

    /**
     * returns the cheese collected
     * @return cheeseCollected (amount of cheese collected)
     */
    public int getCheeseCollected() {
        return cheeseCollected;
    }

    /**
     * increments the cheese collected
     */
    public void incCheeseCollected() {
        cheeseCollected++;
    }
}
