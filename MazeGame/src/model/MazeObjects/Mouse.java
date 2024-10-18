package model.MazeObjects;

public class Mouse extends MazeObject{
    private int cheeseCollected;

    public Mouse() {
        super();
        this.visible = true;
        cheeseCollected = 0;
    }

    @Override
    public MazeObject getThisObject() {
        return this;
    }

    @Override
    public boolean isPassable() {
        return false;
    }
}
