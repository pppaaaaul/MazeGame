package Model.Maze_Objects;

public class Mouse extends MazeObject {
    private int cheeseCollected;


    public Mouse(int row, int col) {
        super(row, col, false, true);
        cheeseCollected = 0;
    }

    public int getCheeseCollected() {
        return cheeseCollected;
    }

    public void incCheeseCollected() {
        cheeseCollected++;
    }
}
