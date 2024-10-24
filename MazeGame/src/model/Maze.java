package model;

import model.maze_objects.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

// implements Iterable<MazeObject>
public class Maze implements Iterable<Cat> {
    final static int NUM_OF_COLUMNS = 15; // TODO: Set to 15
    final static int NUM_OF_ROWS = 20; // TODO: Set to 20
    final static int CATS_SIZE = 3;
    static private MazeObject[][] maze;
    private static Cat[] cats;
    public static Cheese cheese;
    static private Mouse mouse;
    private int cheeseNeededToWin;
    public static char gameState; // Keep as Upper case
    // 'C' = continue, 'W' = win, 'L' = loss



    public Maze () {
        maze = new MazeObject[NUM_OF_ROWS][NUM_OF_COLUMNS];
        cats = new Cat[CATS_SIZE];
        initializeCats();
        int[] cheeseCoordinates;
        cheeseCoordinates = randomlyGenCoordinates();
        cheese = new Cheese(cheeseCoordinates[0], cheeseCoordinates[1]);
        mouse = new Mouse(1, 1);
        cheeseNeededToWin = 5;
        gameState = 'C';
        maze[1][1] = mouse;
        maze = new MazeBuilder(NUM_OF_ROWS, NUM_OF_COLUMNS).getMaze();
        putObjectsInMaze();
    }

    private void initializeCats() {
        final int IN_BOUNDS_VALUE = 2;
        cats[0] = new Cat(1, NUM_OF_COLUMNS - IN_BOUNDS_VALUE);
        cats[1] = new Cat(NUM_OF_ROWS - IN_BOUNDS_VALUE, 1);
        cats[2] = new Cat(NUM_OF_ROWS - IN_BOUNDS_VALUE, NUM_OF_COLUMNS - IN_BOUNDS_VALUE);
    }

    public void revealMaze() {
        // iterate through maze
        for (int row = 0; row < NUM_OF_ROWS; row++) {
            for (int col = 0; col < NUM_OF_COLUMNS; col++) {
                maze[row][col].setVisible();
            }
        }
    }

    public void generateValidMovesForCats() {
        List<Integer> possibleDirections = new ArrayList<>();
        final int up = 0;
        final int right = 1;
        final int down = 2;
        final int left = 3;
        possibleDirections.add(up);
        possibleDirections.add(right);
        possibleDirections.add(down);
        possibleDirections.add(left);

        for (Cat cat : cats) {
            possibleDirections = generateValidDirections(cat, possibleDirections, up, right, down, left);

            int randomVal = (int) (Math.random() * possibleDirections.size());
            switch (randomVal) {
                case 0 -> moveCat(cat, cat.getRow() - 1, cat.getCol());
                case 1 -> moveCat(cat, cat.getRow(), cat.getCol() + 1);
                case 2 -> moveCat(cat, cat.getRow() + 1, cat.getCol());
                case 3 -> moveCat(cat, cat.getRow(), cat.getCol() - 1);
            }
        }
    }

    private List<Integer> generateValidDirections(Cat cat, List<Integer> possibleDirections, final int up,
                                                  final int right, final int down, final int left) {
        int row = cat.getRow();
        int col = cat.getCol();
        // TODO: Double check this after merge
        if (row - 1 < 0 || !maze[row - 1][col].isPassable()) {
            possibleDirections.remove(up);
        }
        // TODO: Double check this after merge
        if (col + 1 > NUM_OF_COLUMNS || !maze[row][col + 1].isPassable()) {
            possibleDirections.remove(right);
        }
        // TODO: Double check this after merge
        if (row + 1 > NUM_OF_ROWS || !maze[row + 1][col].isPassable()) {
            possibleDirections.remove(down);
        }
        // TODO: Double check this after merge
        if (col - 1 < 0 || !maze[row][col - 1].isPassable()) {
            possibleDirections.remove(left);
        }

        if (possibleDirections.size() > 1 && cat.getLastMove() != -1) {
            // TODO: Validate this doesn't need to fist check if the value exists
            for (int i = 0; i < possibleDirections.size(); i++) {
                if (possibleDirections.get(i) == cat.getLastMove()) {
                    possibleDirections.remove(cat.getLastMove());
                    break;
                }
            }
        }
        return possibleDirections;
    }

    private void moveCat(Cat cat, int row, int col) {
        // Replace old cat
        maze[cat.getRow()][cat.getCol] = new MazeObject(true);

        // Create new cat
        maze[row][col] = cat;

        // Update information inside cat
        cat.setRow(row);
        cat.setCol(col);
    }

    private void placeCheese() {
        int[] cheeseCoordinates;
        cheeseCoordinates = randomlyGenCoordinates();

        // remove old cheese
        maze[cheese.getRow()][cheese.getCol()] = new MazeObject(true);

        // add new cheese
        maze[cheeseCoordinates[0]][cheeseCoordinates[1]] = cheese;

        // Update information inside cheese
        cheese.setRow(cheeseCoordinates[0]);
        cheese.setCol(cheeseCoordinates[1]);
    }

    private int[] randomlyGenCoordinates() {
        int randomRowVal = 0;
        int randomColVal = 0;
        boolean validCoordinateFound = false;
        while (!validCoordinateFound) {
            randomRowVal = (int) (Math.random() * NUM_OF_ROWS);
            randomColVal = (int) (Math.random() * NUM_OF_COLUMNS);
            MazeObject desiredSpaceOccupier = maze[randomRowVal][randomColVal];
            if (desiredSpaceOccupier.isPassable() && (desiredSpaceOccupier != cheese)) {
                validCoordinateFound = true;
            }
        }
        return new int[]{randomRowVal, randomColVal};
    }

    public void moveMouse(char key) {
        int row = mouse.getRow();
        int col = mouse.getCol();
        switch (key) {
            case 'W' -> {
                validMouseMove(row - 1, col);
                // TODO: Double check this after merge
                maze[row][col] = new MazeObject(true);
            }
            case 'A' -> {
                validMouseMove(row, col - 1);
                // TODO: Double check this after merge
                maze[row][col] = new MazeObject(true);
            }
            case 'S' -> {
                validMouseMove(row, col + 1);
                // TODO: Double check this after merge
                maze[row][col] = new MazeObject(true);
            }
            case 'D' -> {
                validMouseMove(row + 1, col);
                // TODO: Double check this after merge
                maze[row][col] = new MazeObject(true);
            }
        }
        updateVisibilityAroundMouse();
    }

    private void updateVisibilityAroundMouse() {
        int mouseRow = mouse.getRow();
        int mouseCol = mouse.getCol();
        maze[mouseRow - 1][mouseCol + 1].setVisible(); // Top left
        maze[mouseRow][mouseCol + 1].setVisible(); // Top mid
        maze[mouseRow + 1][mouseCol + 1].setVisible(); // Top right
        maze[mouseRow - 1][mouseCol].setVisible(); // Mid left
        maze[mouseRow + 1][mouseCol].setVisible(); // Mid right
        maze[mouseRow - 1][mouseCol - 1].setVisible(); // Bot left
        maze[mouseRow][mouseCol - 1].setVisible(); // Bot mid
        maze[mouseRow + 1][mouseCol - 1].setVisible(); // Bot right
    }

    private void validMouseMove(int row, int col) {
        if (row >= NUM_OF_ROWS || row < 0 || col >= NUM_OF_COLUMNS || col < 0) {
            throw new InvalidMoveException("You cannot move through walls!\n");
        }

        MazeObject spaceOccupier = maze[row][col];
        // TODO: Double check this after merge
        if (!spaceOccupier.isPassable()) {
            throw new InvalidMoveException("You cannot move through walls!\n");
            // TODO: Double check this after merge
        } else if (spaceOccupier == cheese) {
            mouse.incCheeseCollected();
            if (mouse.getCheeseCollected() < cheeseNeededToWin) {
                placeCheese();
                maze[row][col] = new MazeObject(true);
            } else {
                gameState = 'W';
            }
        } else {
            for (Cat cat : cats) {
                // TODO: Double check this after merge
                if (spaceOccupier == cat) {
                    gameState = 'L';
                    break;
                }
            }
        }
        updateMouseLocation(row, col);
    }

    private void updateMouseLocation(int row, int col) {
        maze[row][col] = mouse;
        mouse.setRow(row);
        mouse.setCol(col);
    }

    public void setCheeseNeededToOne() {
        cheeseNeededToWin = 1;
        System.out.println("Cheese needed to win: " + cheeseNeededToWin);
    }

    public char getGameState() {
        return gameState;
    }

    public int getCheeseNeededToWin() {
        return cheeseNeededToWin;
    }

    public int getCheeseCollected() {
        return mouse.getCheeseCollected();
    }

    public int getColumnSize() {
        return NUM_OF_COLUMNS;
    }

    public int getRowSize() {
        return NUM_OF_ROWS;
    }

    public static char getMazeObjectRepresentation(int row, int col) {
        MazeObject mazeObject = maze[row][col];
        // TODO: Double check this after merge
        if (!mazeObject.isVisible()) {
            return '.';
        }
        // TODO: Double check this after merge
        if (mazeObject == mouse) {
            return '@';
        }
        // TODO: Double check this after merge
        if (!mazeObject.isPassable()) {
            return '#';
        }
        // TODO: Double check this after merge
        if (mazeObject == cheese) {
            return '$';
        }
        // TODO: Double check this after merge
        for (Cat cat : cats) {
            if (mazeObject == cat) {
                return '!';
            }
        }
        // TODO: Double check this after merge
        return ' ';
    }

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
