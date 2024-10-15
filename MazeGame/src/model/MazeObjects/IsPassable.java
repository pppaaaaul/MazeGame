package model.MazeObjects;

/**
 * Used to determine if the current object in the maze is passable by other objects in the maze.
 */
public interface IsPassable {
    public abstract boolean isPassable();
}
