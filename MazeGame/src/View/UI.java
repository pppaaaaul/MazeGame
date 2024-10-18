package View;

import model.MazeObjects.MazeObject;

public class UI {
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
                "MOVES:\n" +
                "\tUse W (up), A (left), S (down) and D (right) to move.\n" +
                "\t(You must press enter after each move).");
    }

    public void displayMaze(MazeObject[][] maze) {
        // Iterate over the MazeObjects
    }


}
