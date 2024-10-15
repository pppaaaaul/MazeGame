package model.MazeObjects;


public class Cheese extends MazeObject {
    public Cheese() {
        super();
        this.visible = true;
    }

    @Override
    public MazeObject getThisObject() {
        return this;
    }

    @Override
    public boolean isPassable() {
        return true;
    }
}
