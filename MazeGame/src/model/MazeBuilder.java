package model;

import java.util.ArrayList;
import java.util.List;

public class MazeBuilder {

    private int rows;
    private int columns;
    private List<List<MazeObject>> maze;

    private final int MIN_ROWS = 4;
    private final int MIN_COLUMNS = 4;

    private MazeBuilder() {}

    public MazeBuilder(int rows, int columns) {
        if(rows < MIN_ROWS || columns < MIN_COLUMNS) {
            throw new IllegalArgumentException("MazeBuilder was intended to create at least a " + MIN_ROWS + "x" + MIN_COLUMNS + " maze.");
        }
        this.rows = rows;
        this.columns = columns;
        initializeMaze();
    }

    private void initializeMaze() {
        this.maze = new ArrayList<>(this.rows);

        // create top wall
        ArrayList<MazeObject> topRow = new ArrayList<>(this.columns);
        for(int i = 0; i < this.columns; i++) {
            topRow.add(new MazeObject(i,0, false));
        }
        this.maze.add(topRow);

        // create middle rows
        for(int i = 1; i < this.rows - 1; i++) {
            ArrayList<MazeObject> currentRow = new ArrayList<>(this.columns);

            currentRow.add(new MazeObject(0, i, false));

            // create the columns of each row
            for(int j = 1; j < this.columns - 1; j++) {
                currentRow.add(new MazeObject(j, i, true));
            }

            currentRow.add(new MazeObject(this.columns - 1, i, false));

            this.maze.add(currentRow);
        }

        // create bottom wall
        ArrayList<MazeObject> bottomRow = new ArrayList<>(this.columns);
        for(int i = 0; i < this.columns; i++) {
            bottomRow.add(new MazeObject(i, this.columns - 1, false));
        }
        this.maze.add(bottomRow);
    }

    public List<List<MazeObject>> getMaze() {
        return this.maze;
    }
}
