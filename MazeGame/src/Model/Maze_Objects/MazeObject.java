package Model.Maze_Objects;

import java.util.Objects;

public class MazeObject {
    protected int col;
    protected int row;
    protected boolean passable = true;
    protected boolean visible = false;

    private MazeObject() {}

    // Used to make Paths and Walls. Row and column aren't needed for either
    public MazeObject(boolean passable) {
        this.passable = passable;
    }

    public MazeObject(boolean passable, boolean visible) {
        this.passable = passable;
        this.visible = visible;
    }

    public MazeObject(int col, int row, boolean passable) {
        this.col = col;
        this.row = row;
        this.passable = passable;
    }

    public MazeObject(int col, int row, boolean passable, boolean visible) {
        this.col = col;
        this.row = row;
        this.passable = passable;
        this.visible = visible;
    }

    public int getCol() {
        return this.col;
    }
    public int getRow() {
        return this.row;
    }

    public void setCol(int col) {
        this.col = col;
    }
    public void setRow(int row) {
        this.row = row;
    }

    public boolean isPassable() {
        return passable;
    }

    public void setVisible() {
        this.visible = true;
    }
    public boolean isVisible() {
        return this.visible;
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

        return this.row == mazeObject.row
                && this.col == mazeObject.col
                && this.passable == mazeObject.passable;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.col, this.row, this.passable);
    }

    public void setPassable(boolean passable) {
        this.passable = passable;
    }

    // TODO: Remove the following method | Used for debugging
    public void setVisibleFalse() {
        this.visible = false;
    }
}
