package model;

import model.MazeObjects.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// implements Iterable<MazeObject>
public class Maze implements Iterable<Cat> {
    final static int COLUMN_SIZE = 13;
    final static int ROW_SIZE = 18;
    final static int CATS_SIZE = 3;
    final private MazeObject[][] maze;
    private final Cat[] cats;
    public Cheese cheese;
    final private Mouse mouse;
    private int cheeseNeededToWin;
    public static char gameState; // Keep as Upper case
    // 'C' = continue, 'W' = win, 'L' = loss



    public Maze () {
        maze = new MazeObject[ROW_SIZE][COLUMN_SIZE];
        cats = new Cat[CATS_SIZE];
        // TODO: place cats in array and in maze

        cheese = new Cheese();
        mouse = new Mouse();
        cheeseNeededToWin = 5;
        gameState = 'C';

        maze[0][0] = mouse;
    }

    public void revealMaze() {
        // iterate through maze
        for (int row = 0; row < ROW_SIZE; row++) {
            for (int col = 0; col < COLUMN_SIZE; col++) {
                maze[row][col].setVisible();
            }
        }
    }

    public void moveCats() {
        // TODO: Backtracking is still allowed. Add something at the end to stop it
        List<Integer> directions = new ArrayList<>();
        directions.add(0);
        directions.add(1);
        directions.add(2);
        directions.add(3);
        int upIdx = 0;
        int rightIdx = 1;
        int downIdx = 2;
        int leftIdx = 3;

        for (Cat cat : cats) {
            int row = cat.getRow();
            int col = cat.getCol();

            if ((row - 1 < 0 || maze[col][row - 1].getThisObject().equals("Wall"))) {
                directions.remove(upIdx);
            }
            if ((col + 1 > COLUMN_SIZE || maze[col + 1][row].getThisObject().equals("Wall"))) {
                directions.remove(rightIdx);
            }
            if ((row + 1 > ROW_SIZE || maze[col][row + 1].getThisObject().equals("Wall"))) {
                directions.remove(downIdx);
            }
            if ((col - 1 < 0 || maze[col - 1][row].getThisObject().equals("Wall"))) {
                directions.remove(leftIdx);
            }

            int randomVal = (int) (Math.random() * directions.size());

            switch (randomVal) {
                // TODO: Fill out possible directional movements
            }

        }
    }

    private void moveCat(Cat cat, int row, int col) {
        // TODO: Fill out function
    }

    private void placeCheese() {
        // Use randomizer to place cheese
        int randomRowVal = 0;
        int randomColVal = 0;
        boolean cheesePlaced = false;
        while (!cheesePlaced) {
            randomRowVal = (int) (Math.random() * ROW_SIZE);
            randomColVal = (int) (Math.random() * COLUMN_SIZE);
            String desiredSpaceOccupier = maze[randomRowVal][randomColVal].getThisObject();
            if (!desiredSpaceOccupier.equals("Wall") && !desiredSpaceOccupier.equals("Cat")) {
                maze[randomRowVal][randomColVal] = cheese;
                cheesePlaced = true;
            }
        }
    }

    public void moveMouse(char key) {
        int row = mouse.getRow();
        int col = mouse.getCol();
        switch (key) {
            case 'W' -> {
                if ( mouseMoved(row, col - 1) ) {
                    maze[row][col - 1] = mouse;
                    maze[row][col] = new Path(row, col);
                }
            }
            case 'A' -> {
                if ( mouseMoved(row - 1, col) ) {
                    maze[row - 1][col] = mouse;
                    maze[row][col] = new Path(row, col);
                }
            }
            case 'S' -> {
                if (mouseMoved(row, col + 1)) {
                    maze[row][col + 1] = mouse;
                    maze[row][col] = new Path(row, col);
                }
            }
            case 'D' -> {
                if (mouseMoved(row + 1, col)) {
                    maze[row + 1][col] = mouse;
                    maze[row][col] = new Path(row, col);
                }
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

    private boolean mouseMoved(int row, int col) {
        if (row >= ROW_SIZE || row < 0 || col >= COLUMN_SIZE || col < 0) {
            throw new InvalidMoveException("You cannot move through walls!\n");
        }

        String spaceOccupier = maze[row][col].getThisObject();
        if (spaceOccupier.equals("Wall")) {
            throw new InvalidMoveException("You cannot move through walls!\n");
        } else if (spaceOccupier.equals("Cat")) {
            gameState = 'L';
        } else if (spaceOccupier.equals("Cheese")) {
            mouse.incCheeseCollected();
            maze[row][col] = new Path(row, col);
            if (mouse.getCheeseCollected() < cheeseNeededToWin) {
                placeCheese();
            } else {
                gameState = 'W';
            }
        } else {
            return true;
        }
        return false;
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
        return COLUMN_SIZE;
    }

    public int getRowSize() {
        return ROW_SIZE;
    }

    public MazeObject[][] getMaze() {
        return maze;
    }

    // TODO: Verify this can be static
    public static char getMazeObjectRepresentation(int row, int col) {
        MazeObject mazeObject = maze[row][col];
        if (!mazeObject.isVisible()) {
            return '.';
        }
        if (mazeObject.getThisObject().equals("Wall")) {
            return '#';
        }
        if (mazeObject.getThisObject().equals("Path")) {
            return ' ';
        }
        if (mazeObject.getThisObject().equals("Cat")) {
            return '!';
        }
        if (mazeObject.getThisObject().equals("Mouse")) {
            return '@';
        }
        return '$';
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

//    @Override
//    public Iterator<MazeObject> iterator() {
//        return MazeObject.iterator();
//    }
}
