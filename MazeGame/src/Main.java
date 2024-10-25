// Change imports to    View.InvalidExpressionException;
//                      Model.Maze;
//                      View.UI;
//                      Model.MazeObjects.InvalidMoveException;

import View.*;
import Model.*;
import Model.Maze_Objects.*;

public class Main {

    public static void main(String[] args) {
        
        Maze maze = new Maze();
        UI ui = new UI();
        ui.displayGreeting();
        gameLoop(maze, ui);
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
        ui.checkGameState(maze);
        if (maze.getGameState() != 'C') {
           return;
        }
        maze.generateValidMovesForCats();
        gameLoop(maze, ui);
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
