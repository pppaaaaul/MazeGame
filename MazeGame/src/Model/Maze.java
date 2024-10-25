package Model;

import Model.Maze_Objects.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Iterator;
import java.util.stream.IntStream;

// implements Iterable<MazeObject>
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
    // 'C' = continue, 'W' = win, 'L' = loss

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

    private void initializeCats() {
        final int IN_BOUNDS_VALUE = 2;
        cats[0] = new Cat(1, NUM_OF_ROWS - IN_BOUNDS_VALUE);
        cats[1] = new Cat(NUM_OF_COLUMNS - IN_BOUNDS_VALUE, 1);
        cats[2] = new Cat(NUM_OF_COLUMNS - IN_BOUNDS_VALUE, NUM_OF_ROWS - IN_BOUNDS_VALUE);

        maze[1][NUM_OF_ROWS - IN_BOUNDS_VALUE] = cats[0];
        maze[NUM_OF_COLUMNS - IN_BOUNDS_VALUE][1] = cats[1];
        maze[NUM_OF_COLUMNS - IN_BOUNDS_VALUE][NUM_OF_ROWS - IN_BOUNDS_VALUE] = cats[2];
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


        // Create new cat
        cat.setOccupiedSpaceVisible(maze[col][row].isVisible());
        maze[col][row] = cat;

        // Update information inside cat
        cat.setCol(col);
        cat.setRow(row);
        int lastMove = (lastPlace + 2) % 4;
        cat.setLastPosition(lastMove);
        updateVisibilityAroundMouse();
    }

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

    public void moveMouse(char key) {
        int col = mouse.getCol();
        int row = mouse.getRow();
        // TODO: Col and Row are swapped for  A (left) and W (Up)
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

    private void updateMouse(int col, int row) {
        maze[col][row] = mouse;

        maze[mouse.getCol()][mouse.getRow()] = new MazeObject(true);

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

    public MazeObject[][] getMaze() {
        return maze;
    }

    public char getMazeObjectRepresentation(int col, int row) {
        MazeObject mazeObject = maze[col][row];
        if (mazeObject == cheese) {
            mazeObject.setVisible();
        }
        if (col == 0 || col == NUM_OF_COLUMNS - 1 || row == NUM_OF_ROWS - 1 || row == 0) {
            return '#';
        }
        return mazeObjectRepresentation(mazeObject);
    }

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
