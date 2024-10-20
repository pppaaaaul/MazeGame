package model.MazeObjects;

import model.MazeObjects.MazeObject;

public class Cat extends MazeObject {
    public Cat() {
        super();
        this.visible = true;
    }

    @Override
    protected MazeObject getThisObject() {
        return this;
    }

    @java.lang.Override
    public boolean isPassable() {
        return true;
    }
}
