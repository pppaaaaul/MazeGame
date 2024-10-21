package model.MazeObjects;


public class Cheese extends MazeObject {
    public Cheese() {
        super();
        this.visible = true;
    }

    @Override
    public String getThisObject() {
        return "Cheese";
    }
}
