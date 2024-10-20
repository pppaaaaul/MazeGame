package model.maze_objects;

/**
 * Used to determine if the current object in the maze is passable by other objects in the maze.
 */
@FunctionalInterface
public interface IsPassable {
    public abstract boolean isPassable();
}
