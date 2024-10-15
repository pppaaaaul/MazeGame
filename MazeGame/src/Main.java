import model.MazeBuilder;
import model.MazeObjects.MazeObject;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        MazeBuilder mazeBuilder = new MazeBuilder(15,20);
        printMaze(mazeBuilder.getMaze());
    }

    public static void printMaze(MazeObject[][] maze) {
        for(int i = 0; i < maze.length; i++) {
            for(int j = 0; j < maze[i].length; j++) {
                MazeObject obj = maze[i][j];
                char value = obj.isPassable() ? '.' : '#';
                System.out.printf("%s", value);
            }
            System.out.print("\n");
        }
    }
}
