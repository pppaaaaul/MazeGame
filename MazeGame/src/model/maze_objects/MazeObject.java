package Model.Maze_Objects;

import java.util.Objects;

public class MazeObject {
    protected int row;
    protected int col;
    protected boolean passable = true;
    protected boolean visible = false;

    private MazeObject() {}

    // Used to make Paths and Walls. Row and column aren't needed for either
    public MazeObject(boolean passable) {
        this.passable = passable;
    }

    public MazeObject(int row, int col, boolean passable) {
        this.row = row;
        this.col = col;
        this.passable = passable;
    }

    public MazeObject(int row, int col, boolean passable, boolean visible) {
        this.row = row;
        this.col = col;
        this.passable = passable;
        this.visible = visible;
    }

    public int getRow() {
        return this.row;
    }
    public int getCol() {
        return this.col;
    }

    public void setRow(int row) {
        this.row = row;
    }
    public void setCol(int col) {
        this.col = col;
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
        return Objects.hash(this.row, this.col, this.passable);
    }

    public void setPassable(boolean passable) {
        this.passable = passable;
    }
}
