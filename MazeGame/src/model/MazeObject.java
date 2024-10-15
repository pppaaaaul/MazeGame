package model;

public class MazeObject implements IsPassable {
    private int x;
    private int y;
    private boolean passible;

    public MazeObject(int x, int y, boolean passible) {
        this.x = x;
        this.y = y;
        this.passible = passible;
    }

    @Override
    public boolean isPassable() {
        return this.passible;
    }
}
