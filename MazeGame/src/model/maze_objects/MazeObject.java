package model.maze_objects;

import java.util.Objects;

public class MazeObject implements IsPassable {
    protected int x;
    protected int y;
    protected boolean isPassable;
    protected boolean isVisible;

    private MazeObject() {}

    public MazeObject(int x, int y, boolean isPassable) {
        this.x = x;
        this.y = y;
        this.isPassable = isPassable;
        this.isVisible = false;
    }

    public MazeObject(int x, int y, boolean isPassable, boolean isVisible) {
        this.x = x;
        this.y = y;
        this.isPassable = isPassable;
        this.isVisible = isVisible;
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

    public boolean isVisible() {
        return this.isVisible;
    }




    // still deciding on this ....
    protected MazeObject getThisObject(){
        return null;
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
