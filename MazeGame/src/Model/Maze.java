package Model;

import Model.Maze_Objects.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Iterator;
import java.util.stream.IntStream;

/**
 * Game status (gameState) with the states: 'C'=Continue 'W'=Win 'L'=Loss. gameState should be kept uppercase
 * Represents the entire maze game.
 * Automatically performs valid movements for cats every turn.
 * Moves the mouse based on given input.
 * Repositions the cheese whenever the mouse gets it.
 * This class uses MazeBuilder to generate the random maze.
 */
public class Maze implements Iterable<Cat> {
    final static int NUM_OF_COLUMNS = 15;
    final static int NUM_OF_ROWS = 20;
    final static int CATS_SIZE = 3;
    private MazeObject[][] maze;
    private Cat[] cats;
    private Cheese cheese;
    private Mouse mouse;
    private int cheeseNeededToWin;
    public char gameState; // Keep as Upper case

    /**
     * Constructor for the maze
     * Creates the board and places all objects inside it
     * Sets gameState to Continue
     * Sets directly adjacent spaces to Mouse and edges to visible
     */
    public Maze () {
        cats = new Cat[CATS_SIZE];
        cheeseNeededToWin = 5;
        // Set state of game to continuous
        gameState = 'C';

        // Create board and fill in with walls
        maze = new MazeBuilder(NUM_OF_COLUMNS, NUM_OF_ROWS).getMaze();

        // Add cheese, cats, and mouse to board
        mouse = new Mouse(1, 1);
        maze[1][1] = mouse;
        initializeCats();
        int[] cheeseCoordinates = randomlyGenCoordinates();
        cheese = new Cheese(cheeseCoordinates[0], cheeseCoordinates[1]);
        maze[cheeseCoordinates[0]][cheeseCoordinates[1]] = cheese;
        updateVisibilityAroundMouse();

        // set the edges to visible
        Arrays.stream(maze[0]).forEach(MazeObject::setVisible);
        Arrays.stream(maze[NUM_OF_COLUMNS - 1]).forEach(MazeObject::setVisible);
        // ChatGPT helped with the start of the following line (IntStream.range(1, NUM_OF_COLUMNS - 1))
        IntStream.range(1, NUM_OF_COLUMNS - 1).forEach(i -> {
            maze[i][0].setVisible();
            maze[i][NUM_OF_ROWS - 1].setVisible();
        });
    }

    /**
     * Creates the Cats and puts them in the three corners where Mouse is not
     */
    private void initializeCats() {
        final int IN_BOUNDS_VALUE = 2;
        cats[0] = new Cat(1, NUM_OF_ROWS - IN_BOUNDS_VALUE);
        cats[1] = new Cat(NUM_OF_COLUMNS - IN_BOUNDS_VALUE, 1);
        cats[2] = new Cat(NUM_OF_COLUMNS - IN_BOUNDS_VALUE, NUM_OF_ROWS - IN_BOUNDS_VALUE);

        maze[1][NUM_OF_ROWS - IN_BOUNDS_VALUE] = cats[0];
        maze[NUM_OF_COLUMNS - IN_BOUNDS_VALUE][1] = cats[1];
        maze[NUM_OF_COLUMNS - IN_BOUNDS_VALUE][NUM_OF_ROWS - IN_BOUNDS_VALUE] = cats[2];
    }

    /**
     * Sets all spaces on the board to visible
     */
    public void revealMaze() {
        for (int col = 0; col < NUM_OF_COLUMNS; col++) {
            for (int row = 0; row < NUM_OF_ROWS; row++) {
                maze[col][row].setVisible();
            }
        }
    }

    /**
     * Creates a list of possible moves each at can take taking into account surrounding Walls and most recent move
     *      which is only left in the list if it's the only possible move remaining
     * Takes said list and randomly selects one and passes on decision to the method which actually moves the Cat
     */
    public void generateValidMovesForCats() {
        for (Cat cat : cats) {
            List<Integer> possibleDirections = new ArrayList<>();
            final int up = 0;
            final int right = 1;
            final int down = 2;
            final int left = 3;
            possibleDirections.add(up);
            possibleDirections.add(right);
            possibleDirections.add(down);
            possibleDirections.add(left);
            generateValidDirections(cat, possibleDirections, up, right, down, left);

            int randomVal = (int) (Math.random() * possibleDirections.size());
            switch (possibleDirections.get(randomVal)) {
                case 0 -> moveCat(cat, cat.getCol() - 1, cat.getRow(), up);
                case 1 -> moveCat(cat, cat.getCol(), cat.getRow() + 1, right);
                case 2 -> moveCat(cat, cat.getCol() + 1, cat.getRow(), down);
                case 3 -> moveCat(cat, cat.getCol(), cat.getRow() - 1, left);
            }
        }
    }

    /**
     * Takes the list of possible moves (Up, Right, Down, Left) and removes any that don't work because of a Wall in
     *      the way or to prevent backtracking (when possible)
     * @param cat: The Cat being moved
     * @param possibleDirections: List of directions (Up, Right, Down, Left) for the Cat to move
     * @param up: Constant Integer=0 which is how Up is stored in possibleDirections. Used to remove Up from List
     * @param right: Same as "Up" but Integer=1
     * @param down: Same as "Up" but Integer=2
     * @param left: Same as "Up" but Integer=3
     */
    private void generateValidDirections(Cat cat, List<Integer> possibleDirections, final Integer up,
                                                  final Integer right, final Integer down, final Integer left) {
        int col = cat.getCol();
        int row = cat.getRow();
        if (col - 1 <= 0 || !maze[col - 1][row].isPassable()) {
            possibleDirections.remove(up);
        }
        if (row + 1 >= NUM_OF_ROWS || !maze[col][row + 1].isPassable()) {
            possibleDirections.remove(right);
        }
        if (col + 1 >= NUM_OF_COLUMNS || !maze[col + 1][row].isPassable()) {
            possibleDirections.remove(down);
        }
        if (row - 1 <= 0 || !maze[col][row - 1].isPassable()) {
            possibleDirections.remove(left);
        }

        if (possibleDirections.size() > 1 && cat.getLastPosition() != -1) {
            for (int i = 0; i < possibleDirections.size(); i++) {
                if (possibleDirections.get(i) == cat.getLastPosition()) {
                    Integer lastMove = cat.getLastPosition();
                    possibleDirections.remove(lastMove);
                    break;
                }
            }
        }
    }

    /**
     * Moves Cat based on the direction decided in the above two functions
     * Sets Cat's lastPlace opposite of the direction it moved
     * @param cat: Cat to be moved
     * @param col: Column the Cat will be moved to (May be same as current)
     * @param row: Row the Cat will be moved to (May be same as current)
     * @param lastPlace: Direction the Cat is moving
     */
    private void moveCat(Cat cat, int col, int row, int lastPlace) {
        // Replace old cat
        if (cat.getCol() == cheese.getCol() && cat.getRow() == cheese.getRow()) {
            maze[cat.getCol()][cat.getRow()] = cheese;
            maze[cat.getCol()][cat.getRow()].setVisible();
        } else {
            if (cat.isOccupiedSpaceVisible()) {
                maze[cat.getCol()][cat.getRow()] = new MazeObject(true, true);
            } else {
                maze[cat.getCol()][cat.getRow()] = new MazeObject(true);
            }
        }

        cat.setOccupiedSpaceVisible(maze[col][row].isVisible());
        maze[col][row] = cat;

        cat.setCol(col);
        cat.setRow(row);
        int lastMove = (lastPlace + 2) % 4;
        cat.setLastPosition(lastMove);

        if (cat.getCol() == mouse.getCol() && cat.getRow() == mouse.getRow()) {
            gameState = 'L';
        }

        updateVisibilityAroundMouse();
    }

    /**
     * Places Cheese when the Mouse collects Cheese
     * Makes sure not to place the Cheese on a Wall nor the Mouse
     */
    private void placeCheese() {
        int[] cheeseCoordinates = randomlyGenCoordinates();

        // remove old cheese
        System.out.println("**************\n" +
                "Cheese was at: (" + cheese.getCol() + ", " + cheese.getRow() + ")\n" +
                "**************\n");
        maze[cheese.getCol()][cheese.getRow()] = new MazeObject(true);

        // add new cheese
        maze[cheeseCoordinates[0]][cheeseCoordinates[1]] = cheese;
        System.out.println("**************\n" +
                "Cheese is now at: (" + cheeseCoordinates[0] + ", " + cheeseCoordinates[1] + ")\n" +
                "**************\n");

        // Update information inside cheese
        cheese.setCol(cheeseCoordinates[0]);
        cheese.setRow(cheeseCoordinates[1]);
    }

    /**
     * Randomly generates coordinates to place the Cheese
     * @return an array of size 2. Holds the column and row of the generated coordinates
     */
    private int[] randomlyGenCoordinates() {
        while (true) {
            int randomColVal = 0;
            int randomRowVal = 0;
            randomColVal = (int) (Math.random() * NUM_OF_COLUMNS);
            randomRowVal = (int) (Math.random() * NUM_OF_ROWS);
            MazeObject desiredSpaceOccupier = maze[randomColVal][randomRowVal];
            if (desiredSpaceOccupier.isPassable() && desiredSpaceOccupier != cheese && desiredSpaceOccupier != mouse) {
                return new int[]{randomColVal, randomRowVal};
            }
        }
    }

    /**
     * Checks if the user's move is valid. If yes, Mouse is moved in that direction
     * @param key: The user's input
     */
    public void moveMouse(char key) {
        int col = mouse.getCol();
        int row = mouse.getRow();
        switch (key) {
            case 'W' -> {
                validMouseMove(col - 1, row);
                maze[col][row] = new MazeObject(true);
            }
            case 'A' -> {
                validMouseMove(col, row - 1);
                maze[col][row] = new MazeObject(true);
            }
            case 'S' -> {
                validMouseMove(col + 1, row);
                maze[col][row] = new MazeObject(true);
            }
            case 'D' -> {
                validMouseMove(col, row + 1);
                maze[col][row] = new MazeObject(true);
            }
        }
        updateVisibilityAroundMouse();
    }

    /**
     * Makes all directly adjacent squares visible to the player
     */
    private void updateVisibilityAroundMouse() {
        int mouseCol = mouse.getCol();
        int mouseRow = mouse.getRow();
        maze[mouseCol - 1][mouseRow + 1].setVisible(); // Top left
        maze[mouseCol][mouseRow + 1].setVisible(); // Top mid
        maze[mouseCol + 1][mouseRow + 1].setVisible(); // Top right
        maze[mouseCol - 1][mouseRow].setVisible(); // Mid left
        maze[mouseCol + 1][mouseRow].setVisible(); // Mid right
        maze[mouseCol - 1][mouseRow - 1].setVisible(); // Bot left
        maze[mouseCol][mouseRow - 1].setVisible(); // Bot mid
        maze[mouseCol + 1][mouseRow - 1].setVisible(); // Bot right
    }

    /**
     * Validates whether a move the player is attempting to make can be done. If the Mouse is not trying to enter a Wall
     * Takes corresponding action if this is true by moving the mouse, incrementing cheeseCollected, or ending the game
     *      as needed.
     * @param col: Column where the Mouse is trying to move
     * @param row: Row where the Mouse is trying to move
     */
    private void validMouseMove(int col, int row) {
        if (col == NUM_OF_COLUMNS || col == 0 || row == NUM_OF_ROWS || row == 0) {
            throw new InvalidMoveException("You cannot move through walls!\n");
        }

        MazeObject spaceOccupier = maze[col][row];
        if (spaceOccupier == cheese) {
            mouse.incCheeseCollected();
            if (mouse.getCheeseCollected() < cheeseNeededToWin) {
                placeCheese();
                updateMouse(col, row);
            } else {
                gameState = 'W';
            }
        } else if (!spaceOccupier.isPassable()) {
            throw new InvalidMoveException("You cannot move through walls!\n");
        } else {
            for (Cat cat : cats) {
                if (spaceOccupier == cat) {
                    gameState = 'L';
                    break;
                }
            }
            updateMouse(col, row);
        }
    }

    /**
     * Updates Mouse's position after move has been stated valid
     * @param col: Column where the Mouse will move
     * @param row: Row where the Mouse will move
     */
    private void updateMouse(int col, int row) {
        maze[col][row] = mouse;

        maze[mouse.getCol()][mouse.getRow()] = new MazeObject(true);

        mouse.setCol(col);
        mouse.setRow(row);
    }

    /**
     * Set win condition to only one Cheese
     * NOTE: if cheeseCounter is already at or above 1, another Cheese needs to be collected to win
     */
    public void setCheeseNeededToOne() {
        cheeseNeededToWin = 1;
        System.out.println("Cheese needed to win: " + cheeseNeededToWin);
    }

    /**
     * Gets the game state. Either 'C', 'L', or 'W'
     * @return gameState: Determine whether the game will continue for another loop or not
     */
    public char getGameState() {
        return gameState;
    }

    /**
     * Gets the cheeseNeededToWin: Win condition
     * @return cheeseNeededToWin: Win condition
     */
    public int getCheeseNeededToWin() {
        return cheeseNeededToWin;
    }

    /**
     * Gets the Cheese collected
     * @return the Cheese collected
     */
    public int getCheeseCollected() {
        return mouse.getCheeseCollected();
    }

    /**
     * Gets this maze board
     * @return this maze board
     */
    public MazeObject[][] getMaze() {
        return maze;
    }

    /**
     * Returns the MazeObject's representation as depicted in the assignment details
     * @param mazeObject: MazeObject to be interpreted (Wall, Path, Cheese, Mouse, or Cat)
     * @return The MazeObject representation as a char. Details as to what object -> which char shown below
     */
    public char mazeObjectRepresentation(MazeObject mazeObject) {
        if (!mazeObject.isVisible()) {
            return '.';
        }
        if (mazeObject == mouse) {
            return '@';
        }
        if (!mazeObject.isPassable()) {
            return '#';
        }
        if (mazeObject == cheese) {
            return '$';
        }
        for (Cat cat : cats) {
            if (mazeObject == cat) {
                return '!';
            }
        }
        return ' ';
    }

    /**
     * Returns an iterator for Cats
     * @return an iterator for Cats
     */
    @Override
    public Iterator<Cat> iterator() {
        return new Iterator<Cat>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < cats.length;
            }

            @Override
            public Cat next() {
                return cats[currentIndex++];
            }
        };
    }
}
