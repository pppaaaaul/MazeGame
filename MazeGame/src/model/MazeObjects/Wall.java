package model.MazeObjects;

import java.util.Objects;

public class Wall extends MazeObject{

    private Wall() {}

    public Wall(int x, int y) {
        super(x,y);
    }

    @Override
    public boolean isPassable() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null) {
            return false;
        }
        if(!(o instanceof Wall)) {
            return false;
        }

        Wall wall = (Wall)o;

        return this.x == wall.x
                && this.y == wall.y;
    }

}
