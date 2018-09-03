/*
 * Name: Aditi Kilambi, cse8bfbq
 * Date: December 3rd, 2017
 * File: Board.java
 * Sources of Help: Piazza, Stack Overflow, Discussion Section
 * 
 * Constructs a board object that is used for 2048. It contains
 * methods created to move and combine tiles on the board.
 * 
 */



/* Credit to startercode authors: */


/*
 * Name: Abena Bonsu
 * Login: cs8bwalo
 * Date: February 4, 2016
 * File: Board.java
 * Sources of Help: textbook, tutors
 *
 * Describe what the program does here:
 * This Class is used to construct a Board object to be used
 * for the simulation of the game 2048. It can create a fresh
 * board or load an already existing board. In addition this
 * class allows the user to save their current game to a new, 
 * specified file. The class also allows for the board to be 
 * rotated 90 degrees to the right or left. Baed on the direction
 * passed in by the user, this class will then move tiles
 * existing on the board in a certain direction, combining tiles
 * of the same value. The game is considered to be over when
 * the board cannot move in any direction.
 * /

//------------------------------------------------------------------//
// Board.java                                                       //
//                                                                  //
// Class used to represent a 2048 game board                        //
//                                                                  //
// Author:  W16-CSE8B-TA group                                      //
// Date:    1/17/16                                                 //
//------------------------------------------------------------------//


/**
 * Sample Board
 * <p/>
 * 0   1   2   3
 * 0   -   -   -   -
 * 1   -   -   -   -
 * 2   -   -   -   -
 * 3   -   -   -   -
 * <p/>
 * The sample board shows the index values for the columns and rows
 * Remember that you access a 2D array by first specifying the row
 * and then the column: grid[row][column]
 */

import java.util.ArrayList;
import java.util.Random;

public class Board {
    /**
     * Number of tiles showing when the game starts
     */
    public final int NUM_START_TILES = 2;

    /**
     * The probability (times 100) that a randomly
     * generated tile will be a 2 (vs a 4)
     */
    public final int TWO_PROBABILITY = 50;

    /**
     * The size of the grid
     */
    public final int GRID_SIZE;

    private int[][] grid;  // The grid of tile values
    private int score;     // The current score

    // You do not have to use these variables
    private final Random random;    // A random number generator (for testing)

    /**
     * Name: Board(Random random, int boardSize).
     * 
     * Purpose: The purpose of this method is to create or construct a fresh
     * board for the user with two random tiles places within the board. This
     * board will have a particular boardSize that the user sets, as well as a
     * random
     *
     * @param boardSize size of the 2048 game board to be used.
     * @param random    Random random represents the random number which 
     *                  be used to specific where (after every move) a 
     *                  new tile should be added to the board when playing.
     */
    public Board(Random random, int boardSize) {
        if (boardSize < 2) boardSize = 4;

        // initialize member variables
        this.random = random;
        this.GRID_SIZE = boardSize;
        this.grid = new int[boardSize][boardSize];
        this.score = 0;

        // loop through and add two initial tiles to the board randomly
        for (int index = 0; index < NUM_START_TILES; index++) {
            addRandomTile();
        }
    }


    /**
     * Constructor used to load boards for grading/testing
     * 
     * THIS IS USED FOR GRADING - DO NOT CHANGE IT
     *
     * @param random
     * @param inputBoard
     */
    public Board(Random random, int[][] inputBoard) {
        this.random = random;
        this.GRID_SIZE = inputBoard.length;
        this.grid = new int[GRID_SIZE][GRID_SIZE];
        for (int r = 0; r < GRID_SIZE; r++) {
            for (int c = 0; c < GRID_SIZE; c++) {
                this.grid[r][c] = inputBoard[r][c];
            }
        }
    }

    /**
     * return the tile value in a particular cell in the grid.
     *
     * @param row The row
     * @param col The column
     * @return The value of the tile at (row, col)
     */
    public int getTileValue(int row, int col) {
        return grid[row][col];
    }

    /**
     * Get the current score
     *
     * @return the current score of the game
     */
    public int getScore() {
        return score;
    }

    /**
     * Name: addRandomTile()
     * 
     * Purpose: The purpose of this method is to add a random tile of either
     * value 2 or 4 to a random empty space on the 2048
     * board. The place where this tile is added is dependent on the random
     * value associated with each board object. If no tiles are empty, it
     * returns without changing the board.
     */
    public void addRandomTile() {
        int count = 0;
        // loop through grid keeping count of every empty space on board
        for (int rowI = 0; rowI < grid.length; rowI++) {
            for (int colI = 0; colI < grid[rowI].length; colI++) {
                if (grid[rowI][colI] == 0) {
                    count++;
                }
            }
        }

        // if count is still 0 after loop, no empty spaces, return
        if (count == 0) {
            System.out.println("There are no empty spaces!");
            return;
        }

        // keep track of where on board random tile should be placed
        int location = random.nextInt(count);
        int value = random.nextInt(100);

        // reset count
        count = 0;
        // loop through grid checking where grid is 0 & incrementing count
        for (int rowI = 0; rowI < grid.length; rowI++) {
            for (int colI = 0; colI < grid[rowI].length; colI++) {
                if (grid[rowI][colI] == 0) {
                    // if count equals random location generated, place tile
                    if (count == location) {
                        System.out.println("Adding a tile to location " + rowI + ", " + colI);
                        if (value < TWO_PROBABILITY) {
                            grid[rowI][colI] = 2;
                        } else {
                            grid[rowI][colI] = 4;
                        }
                    }
                    count++;
                }
            }
        }
    }

    /**
     * Name: isGameOver()
     * <p>
     * Purpose: The purpose of this method is to check whether or not the game
     * in play is over. The game is officially over once there are no longer any
     * valid moves that can be made in any direction. If the game is over, this
     * method will return true and print the words: "Game Over!" This method
     * will be checked before any movement is ever made.
     *
     * @return true if the game is over, and false if the game isn't over
     */
    public boolean isGameOver() {
        return (!canMoveLeft() && !canMoveRight() && !canMoveUp()
                && !canMoveDown());
    }


    /**
     * Name: canMove(Direction direction)
     * 
     * Purpose: The purpose of this method is to check to see if the movement of
     * the tiles in any direction can actually take place. It does not move the
     * tiles, but at every index of the grid, checks to see if there is a tile
     * above, below, to the left or right that has the same value. If this is
     * the case, then that tile can be moved. It also checks if there is an
     * empty (0) tile at a specified index, as this also indicates that movement
     * can be possible. This method is called within move() so that that method
     * can determine whether or not tiles should be moved.
     *
     * @param direction direction the tiles will move (if possible)
     * @return true if the movement can be done and false if it cannot
     */
    public boolean canMove(Direction direction) {
        // utilize helper methods to check if movement in a particular
        // direction is possible
     	if(direction == null) return false; 

        switch (direction) {
            case UP:
                return canMoveUp();
            case RIGHT:
                return canMoveRight();
            case DOWN:
                return canMoveDown();
            case LEFT:
                return canMoveLeft();
            default:
                // If we got here, something went wrong, so return false
                return false;
        }
    }

    /**
     * Name: move(Direction direction)
     * 
     * Purpose: The purpose of this method is to move the tiles in the game
     * board by a specified direction passed in as a parameter. If the movement
     * cannot be done, the method returns false. If the movement can be done, it
     * moves the tiles and returns true. This method relies on the help of four
     * other helper methods to perform the game play.
     *
     * @param direction direction the tiles will move (if possible)
     * @return true if the movement can be done and false if it cannot
     */
    public boolean move(Direction direction) {
        // if canMove is false, exit and don't move tiles
        if (!canMove(direction)) return false;

        // move in relationship to the direction passed in
        switch (direction) {
            case UP:
                moveUp();
                break;
            case RIGHT:
                moveRight();
                break;
            case DOWN:
                moveDown();
                break;
            case LEFT:
                moveLeft();
                break;
            default:
                // This should never happen
                return false;
        }

        return true;
    }

    /*
     * Method: Move Right
     * Purpose: To move the tiles on the board right.  If the values of the 
     *   tiles are equal, add them together.  If there are empty spaces,
     *   shift the tiles right. 
     */
    
    private void moveRight() {

    		//Loop through the tiles row by row
    	
    		for (int row = 0; row < GRID_SIZE; row++) {
    			
    			//Creating an array to keep the modified tile values
    			int[] combined = new int[GRID_SIZE];

    			//Extract all non-zero values and put them in an ArrayList
    			ArrayList<Integer> extractedValues = new ArrayList<Integer>();
    			
    			
    			for(int column = 0; column < GRID_SIZE; column++) {
    				if (grid[row][column] > 0) {
    					extractedValues.add(grid[row][column]);
    				}
    			}
    			
    			//Traverse extractedValues right to left and combine matching pairs
    			
    			int combinedIndex = GRID_SIZE-1;
    			
    			int j = extractedValues.size()-1;
    			
    			while ( j >= 1) { 
    				
    				//looking at jth and j-1 element
    				
    				//Refers to the right tile
    				int second = extractedValues.get(j); 
    				
    				//Refers to the left tile
    				int first = extractedValues.get(j-1);
    				
    				
    				if (second == first) {
    					combined[combinedIndex] = second*2;
    					j -=2; //combined, so can skip over j-1 element
    					score += second*2;
    				}
    				else {
    					combined[combinedIndex] = second;
    					j--;
    				}
    				
    			 combinedIndex--;
  
    			}	
    			
    			//Making sure to copy the first element of extractedValues
    			if (j == 0) {
    				combined[combinedIndex] = extractedValues.get(j);		
    			}
    		
    		//Copying the numbers from the integer array into the grid
    			
    		for (int k = 0; k < GRID_SIZE; k++) {
    			grid[row][k] = combined[k];    		
    		}   					    			
    	
    		}
   }
    
    /*
     * Method: Move Left
     * Purpose: To move the tiles on the board left.  If the values of the 
     *   tiles are equal, add them together.  If there are empty spaces,
     *   shift the tiles left. 
     */
    
    private void moveLeft() {
		
		//Loop through the tiles row by row
    	
		for (int row = 0; row < GRID_SIZE; row++) {
			
			//Creating an array to keep the modified tile values

			int[] combined = new int[GRID_SIZE];
			
			//Extract all non-zero values and put them in an ArrayList

			ArrayList<Integer> extractedValues = new ArrayList<Integer>();
			
			
			for(int column = 0; column < GRID_SIZE; column++) {
				int gridValue = grid[row][column];
				if (gridValue > 0) {
					extractedValues.add(gridValue);
				}
			}
			
			//traverse extractedValues left to right and combine matching pairs
			
			int combinedIndex = 0;
			
			int j = 0;
			
			while ( j < extractedValues.size()-1) { 
				
				//looking at jth and j+1 element
				
				//Refers to the left value
				int first = extractedValues.get(j);
				
				//Refers to the right value
				int second = extractedValues.get(j+1);
				
				
				if (first == second) {
					combined[combinedIndex] = first*2;
					j = j+2 ; //combined, so can skip over j+1 element
					score += first*2;
				}
				else {
					combined[combinedIndex] = first;
					j++;
				}
				
			 combinedIndex++;

			}	
			
			//Making sure to copy the last element of extractedValues
			if (j == extractedValues.size()-1) {
				combined[combinedIndex] = extractedValues.get(j);		
			}
		
    		//Copying the numbers from the integer array into the grid

		for (int k = 0; k < GRID_SIZE; k++) {
			grid[row][k] = combined[k];    		
		}   					    			
	
		}
    }

    /*
     * Method: Move Down
     * Purpose: To move the tiles on the board down.  If the values of the 
     *   tiles are equal, add them together.  If there are empty spaces,
     *   shift the tiles down. 
     */
    
    private void moveDown() {
    	
		//Loop through the tiles column by column

		for (int column = 0; column < GRID_SIZE; column++) {
			
			//Creating an array to keep the modified tile values

			int[] combined = new int[GRID_SIZE];

			
			//Extract all non-zero values in column and put them in 
			//extractedValues

			ArrayList<Integer> extractedValues = new ArrayList<Integer>();
			
			
			for(int row = 0; row < GRID_SIZE; row++) {
				if (grid[row][column] > 0) {
					extractedValues.add(grid[row][column]);
				}
			}
			
			//Traverse extractedValues right to left and combine matching pairs
			
			int combinedIndex = GRID_SIZE-1;
			
			int j = extractedValues.size()-1;
			
			while ( j >= 1) { 
				
				//looking at jth and j-1 element
				
				//Refers to bottom tile
				int second = extractedValues.get(j);
				
				//Refers to top tile
				int first = extractedValues.get(j-1);
				
				//
				if (second == first) {
					combined[combinedIndex] = second*2;
					j -=2; //combined, so can skip over j-1 element
					score += second*2;
				}
				else {
					combined[combinedIndex] = second;
					j--;
				}
				
				
			 combinedIndex--;

			}	
			
			//Making sure to copy the first element of extractedValues
			
			if (j == 0) {
				combined[combinedIndex] = extractedValues.get(j);		
			}
			
    		//Copying the numbers from the integer array into the grid

		for (int k = 0; k < GRID_SIZE; k++) {
			grid[k][column] = combined[k];    		
		}   					    			
	
		}
   
    }

    /*
     * Method: Move Up
     * Purpose: To move the tiles on the board up.  If the values of the 
     *   tiles are equal, add them together.  If there are empty spaces,
     *   shift the tiles up. 
     */
    
    private void moveUp() {
		
    		//Loop through the tiles column by column

    		for (int column = 0; column < GRID_SIZE; column++) {
    			
    			//Creating an array to keep the modified tile values
    			int[] combined = new int[GRID_SIZE];

    			//Extract all non-zero values in column and put them in 
    			//extractedValues

    			ArrayList<Integer> extractedValues = new ArrayList<Integer>();
    			
    			
    			for(int row = 0; row < GRID_SIZE; row++) {
    				if (grid[row][column] > 0) {
    					extractedValues.add(grid[row][column]);
    				}
    			}
    			
    			//Traverse extractedValues right to left and combine matching pairs
    			
    			int combinedIndex = 0;
    			
    			int j = 0;
    			
    			while ( j <= extractedValues.size()-2) { 
    				
    				//looking at jth and j+1 element
    				
    				//Refers to top tile
    				int first = extractedValues.get(j);
    				
    				//Refers to bottom tile
    				int second = extractedValues.get(j+1);
    				
    				if (first == second) {
    					combined[combinedIndex] = first*2;
    					j +=2; //combined, so can skip over j+1 element
    					score += first*2;
    				}
    				else {
    					combined[combinedIndex] = first;
    					j++;
    				}	
    			 combinedIndex++;

    			}	
    			
    			//Making sure to copy the first element of extractedValues
    			if (j == extractedValues.size()-1) {
    				combined[combinedIndex] = extractedValues.get(j);		
    			}
    		
        	//Copying the numbers from the integer array into the grid

	    		for (int k = 0; k < GRID_SIZE; k++) {
	    			grid[k][column] = combined[k];    		
	    		}   					    			
    	
    		}
    }

    /*
     * Method: Can Move Left
     * Purpose: To see if the tiles in the board can be moved left or not
     * Return: true if the board can be moved left, and false if it cannot
     * 
     */

    
    private boolean canMoveLeft() {
		
    		//Loops through tiles row by row
		for(int row = 0; row < GRID_SIZE; row++) {
			for (int column = 0; column < GRID_SIZE-1; column++) {
				
				int leftTile = grid[row][column];
				int rightTile = grid[row][column + 1];
				
				//If the right tile is nonzero, and the left tile is,
				//then the tile can move left
				
				if (rightTile > 0 && leftTile == 0) {
					return true;
				}
				
				//If the right tile is the same value as the left then the tile
				//can move left
				
				if (rightTile == leftTile && rightTile > 0) {
					return true;			
				}
				

				//If the right tile is nonzero, and the left tile is,
				//then the tile can move left
				
				if (leftTile == 0 && rightTile > 0) {
					return true;
				}
			}
		}
		return false;
    }

    /*
     * Method: Can Move Right
     * Purpose: To see if the tiles in the board can be moved right or not
     * Return: true if the board can be moved right, and false if it cannot
     * 
     */
    private boolean canMoveRight() {
    		
    		//Loops through tiles row by row
    		for(int row = 0; row < GRID_SIZE; row++) {
    			for (int column = 0; column < GRID_SIZE - 1; column++) {
    				
    				//If the left tile is nonzero, and the right tile is,
    				//then the tile can move right
    				if (grid[row][column] > 0 && grid[row][column+1] == 0) {
    					return true;
    				}
    				
    				//If the left tile is the same value as the right then the tile
    				//can move right
    				
    				if (grid[row][column] == grid[row][column+1] && 
    						grid[row][column] > 0) {
    					return true;			
    				}
    			}
    		}
    		return false;  		
    }
    			

    /*
     * Method: Can Move Up
     * Purpose: To see if the tiles in the board can be moved up or not
     * Return: true if the board can be moved up, and false if it cannot
     * 
     */
    
    private boolean canMoveUp() {
		
    		//Loop through tiles column by column
    		for(int column = 0; column < GRID_SIZE; column++) {
			for (int row = 0; row < GRID_SIZE-1; row++) {
				
				int topTile = grid[row][column];
				int bottomTile = grid[row+1][column];
				
				//If the bottom tile is nonzero, and the top tile is,
				//then the tile can move up
				
				if (bottomTile > 0 && topTile == 0) {
					return true;
				}
				
				//If the top tile is the same value as the bottom then the tile
				//can move up
				
				if (bottomTile == topTile && topTile > 0) {
					return true;			
				}
			}
		}
		return false;


	}

    /*
     * Method: Can Move Down
     * Purpose: To see if the tiles in the board can be moved down or not
     * Return: true if the board can be moved down, and false if it cannot
     * 
     */
    private boolean canMoveDown() {
    	
		//Loop through tiles column by column
		for(int column = 0; column < GRID_SIZE; column++) {
			for (int row = 0; row < GRID_SIZE - 1; row++) {
				
				//If the top tile is nonzero, and the bottom tile is,
				//then the tile can move down
				
				if (grid[row][column] > 0 && grid[row+1][column] == 0) {
					return true;
				}
				
				//If the bottom tile is the same value as the top then the tile
				//can move down
				
				if (grid[row][column] == grid[row+1][column] && grid[row][column] > 0) {
					return true;			
				}
			}
		}
		
		return false;

	}


    @Override
    public String toString() {
        StringBuilder outputString = new StringBuilder();
        outputString.append(String.format("Score: %d\n", score));
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++)
                outputString.append(grid[row][column] == 0 ? "    -"
                        : String.format("%5d", grid[row][column]));

            outputString.append("\n");
        }
        return outputString.toString();
    }

    /**
     * Set the grid to a new value.  This method can be used for
     * testing and is used by our testing/grading script.
     * @param newGrid The values to set the grid to
     */
    public void setGrid(int[][] newGrid)
    {
        if (newGrid.length != GRID_SIZE ||
                newGrid[0].length != GRID_SIZE) {
            System.out.println("Attempt to set grid to incorrect size");
            return;
                }
        this.grid = new int[GRID_SIZE][GRID_SIZE];
        for (int r = 0; r < grid.length; r++)
        {
            for (int c = 0; c < grid[r].length; c++) {
                grid[r][c] = newGrid[r][c];
            }
        }
    }

    /**
     * get a copy of the grid
     * @return A copy of the grid
     */
    public int[][] getGrid()
    {
        int[][] gridCopy = new int[GRID_SIZE][GRID_SIZE];
        for (int r = 0; r < grid.length; r++)
        {
            for (int c = 0; c < grid[r].length; c++) {
                gridCopy[r][c] = grid[r][c];
            }
        }
        return gridCopy;
    }
}
