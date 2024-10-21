package model.MazeObjects;

import model.MazeObjects.MazeObject;

public class Cat extends MazeObject {
    private String lastMove;

    public Cat(int row, int col) {
        super(int row, int col);
        this.visible = true;
        lastMove = null;
    }

    public String getLastMove() {
        return lastMove;
    }

    @Override
    public String getThisObject() {
        return "Cat";
    }
}
