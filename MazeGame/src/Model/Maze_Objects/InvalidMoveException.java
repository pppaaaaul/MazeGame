package Model.Maze_Objects;

/**
 * RuntimeException for when the player tries to move the mouse into a wall (non-passable MazeObject)
 */
public class InvalidMoveException extends RuntimeException {
    public InvalidMoveException(String message) {
        super(message);
    }
}
