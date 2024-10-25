package Model.Maze_Objects;

public class Cat extends MazeObject {
    private int lastMove;

    public Cat(int row, int col) {
        super(row, col, true, true);
        lastMove = -1; // no move made yet
    }

    public int getLastMove() {
        return lastMove;
    }

    public void setLastMove(int lastMove) {
        this.lastMove = lastMove;
    }

}
