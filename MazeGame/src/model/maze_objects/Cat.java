package model.maze_objects;

public class Cat extends MazeObject {

    public Cat(int x, int y) {
        super(x, y, true, true);
    }

    @Override
    protected MazeObject getThisObject() {
        return this;
    }

}
