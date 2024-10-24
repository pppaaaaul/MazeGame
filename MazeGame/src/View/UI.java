package View;

// Change import to model.Maze;
import model.*;

import java.util.Scanner;

public class UI {
    static public void displayGreeting() {
        System.out.println("----------------------------------------\n" +
                "Welcome to Cat and Mouse Maze Adventure!\n" +
                "by Paul Huang & Brandon Chattha\n" +
                "----------------------------------------");
    }

    static public void displayOptions() {
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

    static public void displayMaze(Maze maze) {
        // Iterate over the MazeObjects
//        for (MazeObject m : maze) {
//            if (m.getThisObject().equals())
//        }

        System.out.println("###############");
        for (int row = 0; row < maze.getRowSize(); row++) {
            StringBuilder screenOutput = new StringBuilder("#");
            for (int col = 0; col < maze.getColumnSize(); col++) {
                screenOutput.append(Maze.getMazeObjectRepresentation(row, col));
            }
            screenOutput.append("#\n");
            // TODO: Maybe add .toString() after screenOutput?
            System.out.println(screenOutput);
        }
        System.out.println("###############");
        System.out.println("Cheese collected: " + maze.getCheeseCollected() + " out of " + maze.getCheeseNeededToWin());
    }

    static public char getUserInput() {
        Scanner scanner = new Scanner(System.in);
        char result = scanner.next().charAt(0);
        scanner.nextLine();
        return result;
    }

    static public boolean processUserInput(char input, Maze maze) {
        input = Character.toUpperCase(input);
        switch (input) {
            case 'W', 'A', 'S', 'D' -> maze.moveMouse(input);
            case 'M' -> {
                maze.revealMaze();
                return true;
            }
            case 'C' -> {
                maze.setCheeseNeededToOne();
                return true;
            }
            case '?' -> {
                displayOptions();
                return true;
            }
            // TODO: Use provided Exceptions
            default -> throw new InvalidExpressionException(
                    "Please enter just A (left), S (down), D (right), or W (up).");
        }
        return false;
    }


    public static boolean checkGameState(Maze maze) {
        switch (maze.getGameState()) {
            case 'W' -> {
                return displayWin(maze);
            }
            case 'L' -> {
                return displayLoss(maze);
            }
            case 'C' -> {
                return true;
            }
        }
        return false;
    }

    public static boolean displayWin(Maze maze) {
        System.out.println("YOU WIN!\n");
        maze.revealMaze();
        displayMaze(maze);

        return displayPlayAgain();
    }

    public static boolean displayLoss(Maze maze) {
        System.out.println("You lose, better luck next time!\n");
        maze.revealMaze();
        displayMaze(maze);

        return displayPlayAgain();
    }

    public static boolean displayPlayAgain() {
        System.out.println("Play again (Y/N)?");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().equals("Y") || scanner.nextLine().equals("y");
    }

}
