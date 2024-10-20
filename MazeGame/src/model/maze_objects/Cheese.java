package model.maze_objects;


public class Cheese extends MazeObject {

    public Cheese(int x, int y) {
        super(x, y, true, true);
    }

    @Override
    public MazeObject getThisObject() {
        return this;
    }

}
