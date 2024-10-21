package model.MazeObjects;

public class Path extends MazeObject{

    private Path() {}

    public Path(int row, int col) {
        super(row,col);
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

        return this.row == path.row
                && this.col == path.col;
    }

    @Override
    public String getThisObject() {
        return "Path";
    }
}
