package model;

import model.maze_objects.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Maze {
    private int numOfCheeseToWin = 5;

    private final int NUM_OF_ROWS = 15;
    private final int NUM_OF_COLUMNS = 20;

    private MazeObject[][] maze;
    private List<Cat> cats;
    private Mouse mouse;
    private Cheese cheese;

    public Maze() {
        this.mouse = new Mouse(1,1);
        // initialize mouse here
        this.cats = Arrays.asList(
            new Cat(NUM_OF_COLUMNS - 2, 1),
            new Cat(NUM_OF_COLUMNS - 2, NUM_OF_ROWS - 2),
            new Cat(1, NUM_OF_ROWS - 2)
        );

        this.maze = new MazeBuilder(NUM_OF_ROWS, NUM_OF_COLUMNS).getMaze();
        putObjectsInMaze();
    }

    private void putObjectsInMaze() {

    }

}
