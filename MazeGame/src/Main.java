import View.*;
import Model.Maze;
import Model.Maze_Objects.InvalidMoveException;

/**
 * Where the program begins
 * Contains the starting function, the loop that keeps the game running, and a loop that repeatedly asks for user input
 *                          if theirs is invalid
 */
public class Main {

    /**
     * Function to start off the game
     * @param args: Useless inputs from the user
     */
    public static void main(String[] args) {
        Maze maze = new Maze();
        UI ui = new UI();
        ui.displayGreeting();
        gameLoop(maze, ui);
    }

    /**
     * Recursively loops until the game ends: when game state changes to either L or W
     * @param maze: The Maze at the centre of the game
     * @param ui: The UI to interact with the user
     */
    public static void gameLoop(Maze maze, UI ui) {
        ui.displayOptions();
        ui.displayMaze(maze);
        char input = ui.getUserInput();
        try {
            ui.processUserInput(input, maze);
            gameLoop(maze, ui);
        } catch (InvalidExpressionException | InvalidMoveException e) {
            System.out.println("Invalid move: " + e);
            processValidUserInputLoop(maze, ui);
        }
        if (!ui.checkGameState(maze)) {
            return;
        }
        maze.generateValidMovesForCats();
        if (!ui.checkGameState(maze)) {
            return;
        }
        gameLoop(maze, ui);
    }

    /**
     * Recursively loops until a valid input is given by the user
     * @param maze: The maze that a valid input will alter
     * @param ui: The UI to interact with the user
     */
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
