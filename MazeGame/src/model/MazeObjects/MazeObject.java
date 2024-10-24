package model.MazeObjects;

import java.util.Objects;

public class MazeObject {
    protected int row;
    protected int col;
    protected boolean visible = false;
    protected boolean passable = true;

    public MazeObject(boolean passable) {
        this.passable = passable;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setVisible() {
        visible = true;
    }

    public boolean isVisible() {
        return visible;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isPassable() {
        return passable;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.row,this.col);
    }

}
