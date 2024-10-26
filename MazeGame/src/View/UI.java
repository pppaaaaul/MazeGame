package View;

import Model.Maze;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Class for interacting with the user; getting information from and displaying information to the user
 * Displays the maze to user and prompts the user for input to play the maze game.
 */
public class UI {

    /**
     * Generic constructor to instantiate the UI
     */
    public UI() { }

    /**
     * Displays a greeting to the user
     * Contains the name of the game and creators names
     */
    public void displayGreeting() {
        System.out.println("----------------------------------------\n" +
                "Welcome to Cat and Mouse Maze Adventure!\n" +
                "by Paul Huang & Brandon Chattha\n" +
                "----------------------------------------");
    }

    /**
     * Displays possible inputs user can use (according to the website) and what they do
     */
    public void displayOptions() {
        System.out.println("DIRECTIONS:\n" +
                "\tFind 5 cheese before a cat eats you!\n" +
                "LEGEND:\n" +
                "\t#: Wall\n" +
                "\t@: You (a mouse)\n" +
                "\t!: Cat\n" +
                "\t$: Cheese\n" +
                "\t.: Unexplored space\n" +
                "MOVES: \n" +
                "\tUse W (up), A (left), S (down) and D (right) to move.\n" +
                "\t(You must press enter after each move).");
    }

    /**
     * Displays the maze with determinants like what objects are where and whether they're visible to the player
     * @param maze The maze as it is
     */
    public void displayMaze(Maze maze) {
        // ChatGPT helped with converting the 2D array to one that is usable in a stream (lines 35 & 36)
        String mazeRepresentation = Arrays.stream(maze.getMaze())
                .map(row -> Arrays.stream(row)
                        .map(maze::mazeObjectRepresentation)
                        .map(String::valueOf)
                        .collect(Collectors.joining("")))
                .collect(Collectors.joining("\n"));
        System.out.println(mazeRepresentation);

        System.out.println("Cheese collected: " + maze.getCheeseCollected() + " out of " + maze.getCheeseNeededToWin());
    }

    /**
     * Gets the user input from the keyboard
     * Only a single character as per the Assignment instructions
     * @return the user's input
     */
    public char getUserInput() {
        Scanner scanner = new Scanner(System.in);
        char result = scanner.next().charAt(0);
        scanner.nextLine();
        return result;
    }

    /**
     * Processes the user's input and throws an exception if an invalid input was given
     * @param input: The user's input from the keyboard
     * @param maze: The Maze where the Mouse will be moved or board will be revealed
     */
    public void processUserInput(char input, Maze maze) {
        input = Character.toUpperCase(input);
        switch (input) {
            case 'W', 'A', 'S', 'D' -> maze.moveMouse(input);
            case 'M' -> maze.revealMaze();
            case 'C' -> maze.setCheeseNeededToOne();
            case '?' -> displayOptions();
            default -> throw new InvalidExpressionException(
                    "Please enter just A (left), S (down), D (right), or W (up).");
        }
    }

    /**
     * Checks the state of the game 'C'=continue 'L'=Loss 'W'=Win
     * @param maze: The maze where the game state is held
     */
    public boolean checkGameState(Maze maze) {
        switch (maze.getGameState()) {
            case 'W' -> {
                displayWin(maze);
                return false;
            }
            case 'L' -> {
                displayLoss(maze);
                return false;
            }
            default -> {
                return true;
            }
        }
    }

    /**
     * Displays the win screen: Win message, reveals and displays the entire maze
     * @param maze The to-be-revealed board
     */
    public void displayWin(Maze maze) {
        System.out.println("YOU WIN!\n");
        maze.revealMaze();
        displayMaze(maze);
    }

    /**
     * Displays the loss screen: Loss message, reveals and displays the entire maze
     * @param maze: The to-be-revealed maze
     */
    public void displayLoss(Maze maze) {
        System.out.println("You lose, better luck next time!\n");
        maze.revealMaze();
        displayMaze(maze);
    }
}
