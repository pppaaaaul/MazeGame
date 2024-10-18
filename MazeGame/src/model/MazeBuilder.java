package model;

import model.MazeObjects.*;

import java.util.*;

public class MazeBuilder {

    // Why does MazeBuilder have all these fields?
    private int rows;
    private int columns;
    private MazeObject[][] maze;

    private final int MIN_ROWS = 4;
    private final int MIN_COLUMNS = 4;



    public MazeBuilder() {
        // What's all this for?
        if (rows < MIN_ROWS || columns < MIN_COLUMNS) {
            throw new IllegalArgumentException("MazeBuilder was intended to create at least a " + MIN_ROWS + "x" + MIN_COLUMNS + " maze.");
        }
        this.rows = rows;
        this.columns = columns;


        initializeMaze();
        generateMaze();
    }

    /**
     * Creates a maze full of walls.
     */
    private void initializeMaze() {
        this.maze = new MazeObject[this.rows][this.columns];

        for(int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.columns; j++) {
                maze[i][j] = new Wall(j, i);
            }
       }

        // Spawn locations of mouse and cats
        this.maze[1][1] = new Path(1,1);
        this.maze[1][this.columns - 2] = new Path(this.columns - 2, 1);
        this.maze[this.rows - 2][1] = new Path(1, this.rows - 2);
        this.maze[this.rows - 2][this.columns - 2] = new Path(this.columns - 2, this.rows - 2);
    }

    private void generateMaze() {
        HashSet<MazeObject> visited = new HashSet<>();

        Stack<MazeObject> stack = new Stack<>();

        // (1,1) is starting location of player
        stack.push(maze[1][1]);

        while(!stack.empty()) {

            MazeObject currentPoint = stack.pop();

            visited.add(currentPoint);

            // get all adjacent points that have not been visited
            List<MazeObject> nonVisitedAdjacentPoints = getNonVisitedAdjacentPoints(currentPoint, visited);

            Collections.shuffle(nonVisitedAdjacentPoints);

            for(MazeObject nonVisitedAdjacentPoint : nonVisitedAdjacentPoints) {
                stack.push(nonVisitedAdjacentPoint);
            }

            if(!nonVisitedAdjacentPoints.isEmpty()) {
                // connect currentPoint to next point in stack
                connectPointsWithPath(currentPoint,stack.peek());
            }
        }
    }

    /**
     *
     */
    private List<MazeObject> getNonVisitedAdjacentPoints(MazeObject currentPoint, Set<MazeObject> visited) {
        int currX = currentPoint.getX();
        int currY = currentPoint.getY();
        int maximumNumberOfAdjacentPaths = 4;

        List<MazeObject> adjacentPaths = new ArrayList<>(maximumNumberOfAdjacentPaths);

        if(validPath(currX + 2, currY) && !visited.contains(maze[currY][currX + 2])) {
            adjacentPaths.add(maze[currY][currX + 2]);
        }
        if(validPath(currX - 2, currY) && !visited.contains(maze[currY][currX - 2])) {
            adjacentPaths.add(maze[currY][currX - 2]);
        }
        if(validPath(currX, currY + 2) && !visited.contains(maze[currY + 2][currX])) {
            adjacentPaths.add(maze[currY + 2][currX]);
        }
        if(validPath(currX, currY - 2) && !visited.contains(maze[currY - 2][currX])) {
            adjacentPaths.add(maze[currY - 2][currX]);
        }

        return adjacentPaths;
    }

    /**
     * If (x,y) is within the bounds of the 4 walls of the maze.
     */
    private boolean validPath(int x, int y) {
        int firstColumn = 1;
        int lastColumn = this.columns - 2;

        int firstRow = 1;
        int lastRow = this.rows - 2;

        return x >= firstColumn && x <= lastColumn
                && y >= firstRow && y <= lastRow;
    }

    private void connectPointsWithPath(MazeObject pointA, MazeObject pointB) {
        if(pointA.getX() + 2 == pointB.getX()) {
            maze[pointA.getY()][pointA.getX() + 1] = new Path(pointA.getX() + 1, pointA.getY());

        } else if(pointA.getX() - 2 == pointB.getX()) {
            maze[pointA.getY()][pointA.getX() - 1] = new Path(pointA.getX() - 1, pointA.getY());

        } else if(pointA.getY() + 2 == pointB.getY()) {
            maze[pointA.getY() + 1][pointA.getX()] = new Path(pointA.getX(), pointA.getY() + 1);

        } else {
            maze[pointA.getY() - 1][pointA.getX()] = new Path(pointA.getX(), pointA.getY() - 1);
        }

        maze[pointB.getY()][pointB.getX()] = new Path(pointB.getX(), pointB.getY());
    }

    public MazeObject[][] getMaze() {
        return this.maze;
    }
}
