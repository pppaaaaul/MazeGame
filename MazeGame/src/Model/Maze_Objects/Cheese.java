package Model.Maze_Objects;

/**
 * Class for the Cheese objects
 * Is-a MazeObject
 * Represents the cheese in the maze game.
 */
public class Cheese extends MazeObject {

    /**
     * Constructor for the Cheese objects
     * Sets them to both passable and visible
     * @param col the column of this Cheese object's position
     * @param row the row of this Cheese object's position
     */
    public Cheese(int col, int row) {
        super(col, row, true, true);
    }
}
