package View;

// Change import to model.Maze;
import Model.*;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UI {
    public UI() { }
    public void displayGreeting() {
        System.out.println("----------------------------------------\n" +
                "Welcome to Cat and Mouse Maze Adventure!\n" +
                "by Paul Huang & Brandon Chattha\n" +
                "----------------------------------------");
    }

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

    public char getUserInput() {
        Scanner scanner = new Scanner(System.in);
        char result = scanner.next().charAt(0);
        scanner.nextLine();
        return result;
    }

    public boolean processUserInput(char input, Maze maze) {
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
            default -> throw new InvalidExpressionException(
                    "Please enter just A (left), S (down), D (right), or W (up).");
        }
        return false;
    }


    public void checkGameState(Maze maze) {
        switch (maze.getGameState()) {
            case 'W' -> displayWin(maze);
            case 'L' -> displayLoss(maze);
        }
    }

    public void displayWin(Maze maze) {
        System.out.println("YOU WIN!\n");
        maze.revealMaze();
        displayMaze(maze);
    }

    public void displayLoss(Maze maze) {
        System.out.println("You lose, better luck next time!\n");
        maze.revealMaze();
        displayMaze(maze);
    }
}
