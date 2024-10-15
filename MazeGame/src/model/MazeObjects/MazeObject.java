package model.MazeObjects;

public abstract class MazeObject implements IsPassable {
    protected int x;
    protected int y;

    protected MazeObject() {}

    protected MazeObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
