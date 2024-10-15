package model.MazeObjects;

public class Wall extends MazeObject{

    private Wall() {}

    public Wall(int x, int y) {
        super(x,y);
    }

    @Override
    public boolean isPassable() {
        return false;
    }
}
