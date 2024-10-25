package Model;

import Model.Maze_Objects.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

// implements Iterable<MazeObject>
public class Maze implements Iterable<Cat> {
    final static int NUM_OF_COLUMNS = 20;
    final static int NUM_OF_ROWS = 15;
    final static int CATS_SIZE = 3;
    private MazeObject[][] maze;
    private final Cat[] cats;
    public final Cheese cheese;
    final private Mouse mouse;
    private int cheeseNeededToWin;
    public static char gameState; // Keep as Upper case
    // 'C' = continue, 'W' = win, 'L' = loss



    public Maze () {
        cats = new Cat[CATS_SIZE];
        cheeseNeededToWin = 5;
        // Set state of game to continuous
        gameState = 'C';

        // Create board and fill in with walls
        maze = new MazeBuilder(NUM_OF_ROWS, NUM_OF_COLUMNS).getMaze();

        // Add cheese, cats, and mouse to board
        int[] cheeseCoordinates;
        cheeseCoordinates = randomlyGenCoordinates();
        cheese = new Cheese(cheeseCoordinates[0], cheeseCoordinates[1]);
        initializeCats();
        mouse = new Mouse(1, 1);
        maze[1][1] = mouse;
    }

    private void initializeCats() {
        final int IN_BOUNDS_VALUE = 2;
        cats[0] = new Cat(1, NUM_OF_ROWS - IN_BOUNDS_VALUE);
        cats[1] = new Cat(NUM_OF_COLUMNS - IN_BOUNDS_VALUE, 1);
        cats[2] = new Cat(NUM_OF_COLUMNS - IN_BOUNDS_VALUE, NUM_OF_ROWS - IN_BOUNDS_VALUE);
    }

    public void revealMaze() {
        // iterate through maze
        for (int col = 0; col < NUM_OF_COLUMNS; col++) {
            for (int row = 0; row < NUM_OF_ROWS; row++) {
                maze[col][row].setVisible();
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
                case 0 -> moveCat(cat, cat.getRow() - 1, cat.getCol(), up);
                case 1 -> moveCat(cat, cat.getRow(), cat.getCol() + 1, right);
                case 2 -> moveCat(cat, cat.getRow() + 1, cat.getCol(), down);
                case 3 -> moveCat(cat, cat.getRow(), cat.getCol() - 1, left);
            }
        }
    }

    private List<Integer> generateValidDirections(Cat cat, List<Integer> possibleDirections, final int up,
                                                  final int right, final int down, final int left) {
        int col = cat.getCol();
        int row = cat.getRow();
        // TODO: Double check this after merge
        if (col - 1 < 0 || !maze[col - 1][row].isPassable()) {
            possibleDirections.remove(up);
        }
        // TODO: Double check this after merge
        if (row + 1 > NUM_OF_ROWS || !maze[col][row + 1].isPassable()) {
            possibleDirections.remove(right);
        }
        // TODO: Double check this after merge
        if (col + 1 > NUM_OF_COLUMNS || !maze[col + 1][row].isPassable()) {
            possibleDirections.remove(down);
        }
        // TODO: Double check this after merge
        if (row - 1 < 0 || !maze[col][row - 1].isPassable()) {
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

    private void moveCat(Cat cat, int col, int row, int lastMove) {
        // Replace old cat
        maze[cat.getCol()][cat.getRow()] = new MazeObject(true);

        // Create new cat
        maze[col][row] = cat;

        // Update information inside cat
        cat.setCol(col);
        cat.setRow(row);
        cat.setLastMove(lastMove);

    }

    private void placeCheese() {
        int[] cheeseCoordinates;
        cheeseCoordinates = randomlyGenCoordinates();

        // remove old cheese
        maze[cheese.getCol()][cheese.getRow()] = new MazeObject(true);

        // add new cheese
        maze[cheeseCoordinates[0]][cheeseCoordinates[1]] = cheese;

        // Update information inside cheese
        cheese.setCol(cheeseCoordinates[0]);
        cheese.setRow(cheeseCoordinates[1]);
    }

    private int[] randomlyGenCoordinates() {
        int randomColVal = 0;
        int randomRowVal = 0;
        boolean validCoordinateFound = false;
        while (!validCoordinateFound) {
            randomColVal = (int) (Math.random() * NUM_OF_COLUMNS);
            randomRowVal = (int) (Math.random() * NUM_OF_ROWS);
            MazeObject desiredSpaceOccupier = maze[randomColVal][randomRowVal];
            if (desiredSpaceOccupier != null && desiredSpaceOccupier.isPassable() && (desiredSpaceOccupier != cheese)) {
                validCoordinateFound = true;
            }
        }
        return new int[]{randomColVal, randomRowVal};
    }

    public void moveMouse(char key) {
        int col = mouse.getCol();
        int row = mouse.getRow();
        switch (key) {
            case 'W' -> {
                validMouseMove(col - 1, row);
                // TODO: Double check this after merge
                maze[col][row] = new MazeObject(true);
            }
            case 'A' -> {
                validMouseMove(col, row - 1);
                // TODO: Double check this after merge
                maze[col][row] = new MazeObject(true);
            }
            case 'S' -> {
                validMouseMove(col, row + 1);
                // TODO: Double check this after merge
                maze[col][row] = new MazeObject(true);
            }
            case 'D' -> {
                validMouseMove(col + 1, row);
                // TODO: Double check this after merge
                maze[col][row] = new MazeObject(true);
            }
        }
        updateVisibilityAroundMouse();
    }

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

    private void validMouseMove(int col, int row) {
        if (col >= NUM_OF_COLUMNS || col < 0 || row >= NUM_OF_ROWS || row < 0) {
            throw new InvalidMoveException("You cannot move through walls!\n");
        }

        MazeObject spaceOccupier = maze[col][row];
        // TODO: Double check this after merge
        if (!spaceOccupier.isPassable()) {
            throw new InvalidMoveException("You cannot move through walls!\n");
            // TODO: Double check this after merge
        } else if (spaceOccupier == cheese) {
            mouse.incCheeseCollected();
            if (mouse.getCheeseCollected() < cheeseNeededToWin) {
                placeCheese();
                maze[col][row] = new MazeObject(true);
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
        updateMouseLocation(col, row);
    }

    private void updateMouseLocation(int col, int row) {
        maze[col][row] = mouse;
        mouse.setCol(col);
        mouse.setRow(row);
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

    public char getMazeObjectRepresentation(int col, int row) {
        MazeObject mazeObject = maze[col][row];
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
