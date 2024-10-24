// Change imports to    View.InvalidExpressionException;
//                      model.Maze;
//                      View.UI;
//                      model.MazeObjects.InvalidMoveException;

import View.*;
import model.*;
import model.MazeObjects.*;

public class Main {

    public static void main(String[] args) {
        
        Maze maze = new Maze();

        UI.displayGreeting();
        gameLoop(maze);

    }

    public static void gameLoop(Maze maze) {
        UI.displayOptions();
        UI.displayMaze(maze);
        char input = UI.getUserInput();
        try {
            if (UI.processUserInput(input, maze)) {
                gameLoop(maze);
            }
        } catch (InvalidExpressionException | InvalidMoveException e) {
            System.out.println("Invalid move: " + e);
            processValidUserInputLoop(maze);
        }
        if (UI.checkGameState(maze)) {
            maze.generateValidMovesForCats();
            gameLoop(maze);
        } else {
            System.out.println("Thanks for playing!");
        }
    }

    public static void processValidUserInputLoop(Maze maze) {
        char input = UI.getUserInput();
        try {
            UI.processUserInput(input, maze);
        } catch (InvalidExpressionException | InvalidMoveException e) {
            System.out.println("Error: " + e);
            processValidUserInputLoop(maze);
        }
    }
}
