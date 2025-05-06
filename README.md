# MazeGame

## Objective
Start with a near empty maze with a mouse (you) in the top left corner and three cats (simple tracing bots) in each remaining corner. The goal is to collect 5 cheese wedges around the maze while avoiding the cats.

## Mechanics
### General
Each round, the current map state will be displayed along with input options. Entering '?' as opposed to one of the directed moves: WASD, will offer additional input options.
### Cheese
Only 1 cheese is on the board at a time. As soon as it is collected by the player, a new one will randomly spawn. Cheese wedges cannot spawn on walls, but can spawn under cats in which case the wedge will be invisible to the player until the cat moves off it (next turn). Whenever a cat walks over the cheese, it will disappear until next turn when the cat walks off.
### Cats
Apon moving onto the space occupied by the mouse, the game will result in a loss. After the mouse moves, each cat will move to a space. The cats are programmed to avoid backtracking as much as possible: if a cat moves off of square A3 onto A4, it will only move back onto A3 in the event that all other directions off of A4 are walls, i.e, there is no other space to move to. Here, backtracking is the only available move in which it will be taken.
### Mouse
Every turn, the player can move 1 square in any unblocked direction. Moving onto a cat will result in a loss, and moving onto the 5th cheese will result in a win.
### Maze
To being, most of the map will be hidden as a space will only be shown if the player has been adjacent to it at some point during the current game. Unrevealed spaces will be displayed as '?', revealed but empty spaces as ' ', walls as '#', cats as '!', the mouse as '@', and the cheese as '$'. Regardless of whether the player has discovered the space a cat or the cheese is on, it will always be visible on the map. Additionally, the outer boarder will always be shown.
