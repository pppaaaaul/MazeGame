package model.MazeObjects;

public class Mouse extends MazeObject {
    private int cheeseCollected;

    public Mouse() {
        super();
        this.visible = true;
        cheeseCollected = 0;
    }

    public int getCheeseCollected() {
        return cheeseCollected;
    }

    public void incCheeseCollected() {
        cheeseCollected++;
    }

    @Override
    public String getThisObject() {
        return "Mouse";
    }
}
