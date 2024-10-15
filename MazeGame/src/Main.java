import model.MazeBuilder;
import model.MazeObject;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        MazeBuilder mazeBuilder = new MazeBuilder(15,20);
        printMaze(mazeBuilder.getMaze());
    }

    public static void printMaze(List<List<MazeObject>> maze) {
        for(List<MazeObject> row : maze) {
            for(MazeObject obj : row) {
                char value = obj.isPassable() ? '.' : '#';
                System.out.print(value + " ");
            }
            System.out.print("\n");
        }
    }
}
