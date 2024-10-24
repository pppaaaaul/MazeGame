package model.MazeObjects;

public class Mouse extends MazeObject {
    private int cheeseCollected;

    public Mouse(int row, int col) {
        super(false);
        this.row = row;
        this.col = col;
        this.visible = true;
        cheeseCollected = 0;
    }

    public int getCheeseCollected() {
        return cheeseCollected;
    }

    public void incCheeseCollected() {
        cheeseCollected++;
    }
}
