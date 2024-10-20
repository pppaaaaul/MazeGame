package model.maze_objects;

import java.util.Objects;

public class MazeObject implements IsPassable {
    private int x;
    private int y;
    private boolean isPassable;

    private MazeObject() {}

    public MazeObject(int x, int y, boolean isPassable) {
        this.x = x;
        this.y = y;
        this.isPassable = isPassable;
    }

    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }

    public void setPassable(boolean isPassable) {
        this.isPassable = isPassable;
    }

    @Override
    public boolean isPassable() {
        return isPassable;
    }

    @Override
    public boolean equals(Object o){
        if(o == null) {
            return false;
        }
        if(o == this) {
            return true;
        }
        if(!(o instanceof MazeObject)) {
            return false;
        }
        MazeObject mazeObject = (MazeObject) o;

        return this.x == mazeObject.x
                && this.y == mazeObject.y
                && this.isPassable == mazeObject.isPassable;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y, this.isPassable);
    }
}
