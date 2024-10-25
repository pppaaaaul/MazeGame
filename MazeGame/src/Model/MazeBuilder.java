package Model;

import Model.Maze_Objects.*;

import java.util.*;

/**
 * This class creates a randomized maze, done in the follow steps :
 * Creates a 2d array full of walls except for the four corners which are set as open spaces.
 * Generates the maze using the randomized depth first traversal algorithm.
 * After the maze is generated, 10% of the walls is removed to add cycles and allow more paths in the maze.
 * The depth first traversal algorithm will prevent non-connected open cells and prevent both 2x2 squares of open cells
 * and 2x2 squares of walls.
 */
public final class MazeBuilder {

    // TODO: Paul, why are these necessary? Can't you call function in Maze to get these?
    private final int rows;
    private final int columns;
//    private MazeObject mouse;
//    private MazeObject[] cats;
    private MazeObject[][] maze;

    private final int MIN_ROWS = 4;
    private final int MIN_COLUMNS = 4;

    private MazeBuilder() {
        // Java FORCES the following two lines.
        this.rows = 0;
        this.columns = 0;
    }

    public MazeBuilder(int rows, int columns) {
        if(rows < MIN_ROWS || columns < MIN_COLUMNS) {
            throw new IllegalArgumentException("MazeBuilder was intended to create at least a " + MIN_ROWS + "x" + MIN_COLUMNS + " maze.");
        }

        this.rows = rows;
        this.columns = columns;

        generateMaze();

//        this.mouse = new MazeObject(1,1,true);
//        this.cats = new MazeObject[]{
//            new MazeObject(this.columns - 2,1, true),
//            new MazeObject(1,this.rows - 2, true),
//            new MazeObject(this.columns - 2,this.rows - 2, true)
//        };
    }

    public MazeObject[][] getMaze() {
        return this.maze;
    }

    private void generateMaze() {
        initializeMaze();
        randomizedDepthFirstTraversal();
        randomlyRemoveWalls();
    }

    /**
     * Can safely remove walls if there are two other parallel walls around it.
     * Safely meaning will not result in 2x2 open square.
     */
    private void randomlyRemoveWalls() {
        // The number of randomly walls removed is 10% of the total number of blocks, not including the outer wall
        int numOfWallsToRemove = (int)((this.rows - 2) * (this.columns - 2) * 0.1);

        // add all walls to list
        List<MazeObject> walls = new ArrayList<MazeObject>((this.columns - 2) * (this.rows - 2));
        for(int i = 1; i < this.rows - 1; i++) {
            for(int j = 1; j < this.columns - 1; j++) {
                if(!this.maze[i][j].isPassable()) {
                    walls.add(this.maze[i][j]);
                }
            }
        }
        Collections.shuffle(walls);

        int i = 0;
        int wallsRemoved = 0;

        while(wallsRemoved != numOfWallsToRemove) {
            MazeObject wall = walls.get(i);

            // Remove wall if removable
            if(isRemovable(wall)) {
                wall.setPassable(true);
                wallsRemoved++;
            }

            i++;
        }
    }

    /**
     * Checks if there are parallel walls around the given wall.
     */
    private boolean isRemovable(MazeObject wall) {
        int currX = wall.getRow();    
        int currY = wall.getCol();

        // check for parallel walls left and right
        if(!this.maze[currY][currX + 1].isPassable() && !this.maze[currY][currX - 1].isPassable()) {
            return true;
        }

        // check for parallel walls above and below
        if(!this.maze[currY + 1][currX].isPassable() && !this.maze[currY - 1][currX].isPassable()) {
            return true;
        }

        return false;
    }

    /**
     * Creates a maze full of walls.
     */
    private void initializeMaze() {
        this.maze = new MazeObject[this.rows][this.columns];

        for(int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.columns; j++) {
                this.maze[i][j] = new MazeObject(j, i, false);
            }
       }
        this.maze[1][1].setPassable(true);
        this.maze[1][this.columns - 2].setPassable(true);
        this.maze[this.rows - 2][this.columns - 2].setPassable(true);
        this.maze[this.rows - 2][1].setPassable(true);

//        // Spawn location of mouse
//        this.maze[1][1] = this.mouse;
//
//        // Spawn locations of cats
//        this.maze[this.cats[0].getCol()][this.cats[0].getRow()] = this.cats[0];
//        this.maze[this.cats[1].getCol()][this.cats[1].getRow()] = this.cats[1];
//        this.maze[this.cats[2].getCol()][this.cats[2].getRow()] = this.cats[2];
    }

    /**
     * Use depth first traversal to create a maze.
     *
     * If we imagine the maze as a graph, let each vertex be an open cell and
     * is initially surrounded by 4 walls.
     *
     * Connect vertices by removing the wall between vertices.
     *
     * Algorithm is from https://en.wikipedia.org/wiki/Maze_generation_algorithm .
     */
    private void randomizedDepthFirstTraversal() {
        HashSet<MazeObject> visited = new HashSet<>();

        Stack<MazeObject> stack = new Stack<>();

        // (1,1) is starting location of player
        stack.push(this.maze[1][1]);
        visited.add(this.maze[1][1]);

        while(!stack.empty()) {

            MazeObject currentPoint = stack.pop();

            MazeObject nonVisitedAdjacentPoint = getRandomNonVisitedAdjacentPoint(currentPoint, visited);

            if(nonVisitedAdjacentPoint != null) {
                stack.push(currentPoint);

                // Remove wall between current and adjacent point
                removeWallBetweenPoints(currentPoint, nonVisitedAdjacentPoint);
                nonVisitedAdjacentPoint.setPassable(true);

                visited.add(nonVisitedAdjacentPoint);
                stack.push(nonVisitedAdjacentPoint);
            }
        }
    }


    private MazeObject getRandomNonVisitedAdjacentPoint(MazeObject currentPoint, HashSet<MazeObject> visited) {
        final int distance = 2;
        final int currX = currentPoint.getRow();
        final int currY = currentPoint.getCol();
        final int maxNumOfAdjacentPaths = 4;

        ArrayList<MazeObject> nonVisitedAdjacentPoints = new ArrayList<>(maxNumOfAdjacentPaths);

        // right
        if(validPoint(currX + distance, currY) && !visited.contains(this.maze[currY][currX + distance])) {
            nonVisitedAdjacentPoints.add(this.maze[currY][currX + distance]);
        }
        // left
        if(validPoint(currX - distance, currY) && !visited.contains(this.maze[currY][currX - distance])) {
            nonVisitedAdjacentPoints.add(this.maze[currY][currX - distance]);
        }
        // up
        if(validPoint(currX, currY + distance) && !visited.contains(this.maze[currY + distance][currX])) {
            nonVisitedAdjacentPoints.add(this.maze[currY + distance][currX]);
        }
        // down
        if(validPoint(currX, currY - distance) && !visited.contains(this.maze[currY - distance][currX])) {
            nonVisitedAdjacentPoints.add(this.maze[currY - distance][currX]);
        }

        final int min = 0;
        final int max = nonVisitedAdjacentPoints.size() - 1;
        int randomInt = (int) (Math.random() * (max - min + 1));

        return nonVisitedAdjacentPoints.isEmpty() ? null : nonVisitedAdjacentPoints.get(randomInt);
    }

    /**
     * If (x,y) is within the 4 outer walls of the maze.
     */
    private boolean validPoint(int x, int y) {
        final int firstColumn = 1;
        final int lastColumn = this.columns - 2;

        final int firstRow = 1;
        final int lastRow = this.rows - 2;

        return x >= firstColumn && x <= lastColumn
                && y >= firstRow && y <= lastRow;
    }

    /**
     * Removes the wall between two points in maze.
     */
    private void removeWallBetweenPoints(MazeObject pointA, MazeObject pointB) {
        if(pointA.getRow() + 2 == pointB.getRow()) {
            this.maze[pointA.getCol()][pointA.getRow() + 1].setPassable(true);

        } else if(pointA.getRow() - 2 == pointB.getRow()) {
            this.maze[pointA.getCol()][pointA.getRow() - 1].setPassable(true);

        } else if(pointA.getCol() + 2 == pointB.getCol()) {
            this.maze[pointA.getCol() + 1][pointA.getRow()].setPassable(true);

        } else {
            this.maze[pointA.getCol() - 1][pointA.getRow()].setPassable(true);
        }
    }
}
