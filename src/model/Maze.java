package model;

import java.util.List;

/**
 * The Maze interface provides all common methods declared for any Maze object.
 * The common functions are getRemainingWallList(), getGameMap(), 
 * 
 * @author Daniel Zhou
 */
public interface Maze {
  
  /**
   * Set up the maze, overridden by different maze types.
   */
  void initialize();
  
  /**
   * Get the remaining wall list after initialization.
   */
  List<Wall> getRemainingWallList();
  
  /**
   * Get a game map with 2D array after initialization.
   */
  Cell[][] getGameMap();
  
  /**
   * Print all Cells stored in the Maze object.
   */
  void printMaze();
}
