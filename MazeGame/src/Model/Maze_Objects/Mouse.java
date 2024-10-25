package Model.Maze_Objects;

public class Mouse extends MazeObject {
    private int cheeseCollected;


    public Mouse(int col, int row) {
        super(col, row, false, true);
        cheeseCollected = 0;
    }

    public int getCheeseCollected() {
        return cheeseCollected;
    }

    public void incCheeseCollected() {
        cheeseCollected++;
    }
}
