package model.MazeObjects;

public class Cat extends MazeObject {
    private int lastMove;

    public Cat(int row, int col) {
        super(true);
        this.row = row;
        this.col = col;
        this.visible = true;
        lastMove = -1; // error code
    }

    public int getLastMove() {
        return lastMove;
    }

    public void setLastMove(int lastMove) {
        this.lastMove = lastMove;
    }
}
