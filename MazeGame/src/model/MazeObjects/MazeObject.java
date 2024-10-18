package model.MazeObjects;

import java.util.Objects;

public class MazeObject {
    protected int x;
    protected int y;
    protected boolean visible;

    protected MazeObject() {}
    // What's this used for? Walls and Empty squares?

    protected MazeObject(int x, int y) {
        this.x = x;
        this.y = y;
        this.visible = false;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Used to figure out what object is occupying a space
     * If a space is occupied by a cat, then a mouse entering will end the game
     *
     * @return the object occupying that space
     */
    protected MazeObject getThisObject() {
        return null;
    }

    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x,this.y);
    }
}
