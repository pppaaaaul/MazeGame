package model.MazeObjects;

import java.util.Objects;

public class MazeObject {
    protected int row;
    protected int col;
    protected boolean visible;

    protected MazeObject() {}
    // What's this used for? Walls and Empty squares?

    protected MazeObject(int row, int col) {
        this.row = row;
        this.col = col;
        this.visible = false;
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
        return this.row;
    }
    public int getCol() {
        return this.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.row,this.col);
    }

    public String getThisObject() {
        return null;
    }
}
