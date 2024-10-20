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

    // Move to Maze class?
    public void moveMouse(char direction) {
        switch (direction) {
            case 'W' -> {
                // get object above this one
            }
            case 'A' -> {
                // get object left of this one
            }
            case 'S' -> {
                // get object below this one
            }
            case 'D' -> {
                // get object right of this one
            }
        }
    }

    @Override
    public boolean isPassable() {
        return false;
    }
}
