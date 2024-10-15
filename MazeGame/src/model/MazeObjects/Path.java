package model.MazeObjects;

public class Path extends MazeObject{

    private Path() {}

    public Path(int x, int y) {
        super(x,y);
    }

    @Override
    public boolean isPassable() {
        return true;
    }
}
