package model.MazeObjects;

public class Path extends MazeObject{

    private Path() {}

    public Path(int x, int y) {
        super(x,y);
    }

    @Override
    public boolean isPassable() {
        return true;
    }


    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if(!(o instanceof Path)) {
            return false;
        }

        Path path = (Path)o;

        return this.x == path.x
                && this.y == path.y;
    }
}
