package model.MazeObjects;


public class Cheese extends MazeObject {

    public Cheese(int row, int col) {
        super(true);
        this.row = row;
        this.col = col;
        this.visible = true;
    }
}
