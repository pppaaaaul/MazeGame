package model.MazeObjects;

import java.util.Objects;

public abstract class MazeObject implements IsPassable {
    protected int x;
    protected int y;

    protected MazeObject() {}

    protected MazeObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x,this.y);
    }
}
