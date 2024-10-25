// Change imports to    View.InvalidExpressionException;
//                      Model.Maze;
//                      View.UI;
//                      Model.MazeObjects.InvalidMoveException;

import View.*;
import Model.*;
import Model.Maze_Objects.*;

public class Main {

    public static void main(String[] args) {
        
//        Maze maze = new Maze();
        UI ui = new UI();
        ui.displayGreeting();
//        gameLoop(maze, ui);
//        ui.displayMaze(maze);




        int x = 20;
        int y = 15;
        MazeObject[][] maze = new MazeBuilder(x,y).getMaze();
        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                String c = maze[i][j].isPassable() ? " " : "#";
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public static void gameLoop(Maze maze, UI ui) {
        ui.displayOptions();
        ui.displayMaze(maze);
        char input = ui.getUserInput();
        try {
            if (ui.processUserInput(input, maze)) {
                gameLoop(maze, ui);
            }
        } catch (InvalidExpressionException | InvalidMoveException e) {
            System.out.println("Invalid move: " + e);
            processValidUserInputLoop(maze, ui);
        }
        if (ui.checkGameState(maze)) {
            maze.generateValidMovesForCats();
            gameLoop(maze, ui);
        } else {
            System.out.println("Thanks for playing!");
        }
    }

    public static void processValidUserInputLoop(Maze maze, UI ui) {
        char input = ui.getUserInput();
        try {
            ui.processUserInput(input, maze);
        } catch (InvalidExpressionException | InvalidMoveException e) {
            System.out.println("Error: " + e);
            processValidUserInputLoop(maze, ui);
        }
    }
}
