import model.Maze;
import model.MazeBuilder;
import model.MazeObjects.MazeObject;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Maze maze = new Maze();

        // The Maze class is supposed to have a maze object
//        MazeBuilder mazeBuilder = new MazeBuilder(15,20);
//        printMaze(mazeBuilder.getMaze());
    }

    // Main shouldn't print anything to the screen
//    public static void printMaze(MazeObject[][] maze) {
//        for(int i = 0; i < maze.length; i++) {
//            for(int j = 0; j < maze[i].length; j++) {
//                MazeObject obj = maze[i][j];
//                char value = obj.isPassable() ? '.' : '#';
//                System.out.printf("%s", value);
//            }
//            System.out.print("\n");
//        }
//    }
}
