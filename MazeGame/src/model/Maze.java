package model;

import model.MazeObjects.*;

import java.util.Iterator;

// implements Iterable<MazeObject>
public class Maze {
    final static int COLUMN_SIZE = 18;
    final static int ROW_SIZE = 13;
    final static int catsSize = 3;
    private MazeObject[][] maze;
    private Cat[] cats;
    public Cheese cheese;
    private Mouse mouse;



    public Maze () {
        maze = new MazeObject[ROW_SIZE][COLUMN_SIZE];
        cats = new Cat[catsSize];
        cheese = new Cheese();
        mouse = new Mouse();

        maze[0][0] = mouse;
    }

    public void revealMaze() {
        // iterate through maze
        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < COLUMN_SIZE; j++) {
                maze[i][j].setVisible(true);
            }
        }
    }

    private void moveCats() {
        // Use randomizer to move cats
    }

    private MazeObject getSpaceOccupier(int xLoc, int yLoc) {
        return maze[xLoc][yLoc];
    }

    private void placeCheese() {
        // Use randomizer to place cheese
    }

    public void moveMouse(char key) {
        switch (key) {
            case 'W' -> {
                // if (getSpaceOccupier(mouse.getX(), mouse.getY() + 1) == Wall) { error message }
                // else if (getSpaceOccupier(mouse.getX(), mouse.getY() + 1) == Cat) { Game over }
                // else if (getSpaceOccupier(mouse.getX(), mouse.getY() + 1) == Cheese) { Inc cheese counter, place cheese }
                // else if (getSpaceOccupier(mouse.getX(), mouse.getY() + 1) == Path) { Move mouse }

            }
            case 'A' -> {
                // if (getSpaceOccupier(mouse.getX() - 1, mouse.getY()) == Wall) { error message }
                // else if (getSpaceOccupier(mouse.getX() - 1, mouse.getY()) == Cat) { Game over }
                // else if (getSpaceOccupier(mouse.getX() - 1, mouse.getY()) == Cheese) { Inc cheese counter, place cheese }
                // else if (getSpaceOccupier(mouse.getX() - 1, mouse.getY()) == Path) { Move mouse }

            }
            case 'S' -> {
                // if (getSpaceOccupier(mouse.getX(), mouse.getY() - 1) == Wall) { error message }
                // else if (getSpaceOccupier(mouse.getX(), mouse.getY() - 1) == Cat) { Game over }
                // else if (getSpaceOccupier(mouse.getX(), mouse.getY() - 1) == Cheese) { Inc cheese counter, place cheese }
                // else if (getSpaceOccupier(mouse.getX(), mouse.getY() - 1) == Path) { Move mouse }

            }
            case 'D' -> {
                // if (getSpaceOccupier(mouse.getX() + 1, mouse.getY()) == Wall) { error message }
                // else if (getSpaceOccupier(mouse.getX() + 1, mouse.getY()) == Cat) { Game over }
                // else if (getSpaceOccupier(mouse.getX() + 1, mouse.getY()) == Cheese) { Inc cheese counter, place cheese }
                // else if (getSpaceOccupier(mouse.getX() + 1, mouse.getY()) == Path) { Move mouse }

            }
        }
    }

//    @Override
//    public Iterator<MazeObject> iterator() {
//        return MazeObject.iterator();
//    }
}
