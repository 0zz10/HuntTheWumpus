package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * This class represents a Maze object which takes the total rows, total
 * columns, and the number of remaining walls as input. In this class, A
 * Wrapping Maze has also fields: remainingWallList for checking the Kruskal
 * algorithm, breakedWall Set to store the Wall object, cellConnected as
 * disjoint set to update connections, and 2D array gameMap to store all Cell
 * objects.
 * 
 * @author Daniel Zhou
 */
public class WrappingMaze implements Maze {
  private List<Wall> remainingWallList;
  private Set<Wall> breakedWallSet;
  private DisjointSets cellConnected;
  private int rows;
  private int columns;
  private int numOfRemainingWalls;
  private Cell[][] gameMap;

  /**
   * This class constructs a Room maze where number remaining walls is specified.
   * 
   * @param rows                total rows of this maze
   * @param columns             total columns of this maze
   * @param numOfRemainingWalls the number of remaining walls after construction
   * @throws IllegalArgumentException if attributes are invalid
   */
  public WrappingMaze(int rows, int columns, int numOfRemainingWalls) 
      throws IllegalArgumentException {
    if (rows < 0 || columns < 0) {
      throw new IllegalArgumentException("invalid map size! Cannot be negative");
    }
    if (numOfRemainingWalls < 0) {
      throw new IllegalArgumentException("invalid number of walls left! Cannot be negative");
    }
    if (numOfRemainingWalls > ((rows - 1) * (columns - 1))) {
      throw new IllegalArgumentException("invalid number of walls left! "
          + "should be in range 0 to " + ((rows - 1) * (columns - 1)));
    }
    this.rows = rows;
    this.columns = columns;
    this.numOfRemainingWalls = numOfRemainingWalls;
    this.remainingWallList = new ArrayList<>();
    this.breakedWallSet = new HashSet<>();
    this.gameMap = new Cell[rows][columns];
    this.cellConnected = new DisjointSets(rows, columns);
    initialize();
  }

  /**
   * Initialize a room maze in Kruskal algorithm.
   */
  @Override
  public void initialize() {
    buildCells();
    buildWalls();
    breakWallKruskal();
    buildWrapping();
    setCave();
    setLeadToCavesSet();
  }

  /**
   * Instantiate each cell object and save into the 2D array gameMap, total of
   * (rows * columns) cells.
   */
  private void buildCells() {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < columns; col++) {
        int index = row * (columns) + col + 1;
        Cell cell = new Cell(row, col);
        cell.setIndex(index);
        gameMap[row][col] = cell;
      }
    }
  }

  /**
   * Instantiate each wall object at set up, assign total walls to
   * remainingWallList.
   */
  private void buildWalls() {
    // construct the walls as inner borders and add to wallList
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < columns; col++) {
        if (row < rows - 1) {
          Wall wallDown = new Wall(gameMap[row][col], gameMap[row + 1][col]);
          this.remainingWallList.add(wallDown);
        }
        if (col < columns - 1) {
          Wall wallRight = new Wall(gameMap[row][col], gameMap[row][col + 1]);
          this.remainingWallList.add(wallRight);
        }
      }
    }
  }

  /**
   * breakWall until the target number reached, randomly choose a wall from wall
   * list, which is Kruskal algorithm.
   */
  private void breakWallKruskal() {
    Random rand = new Random();
    while (remainingWallList.size() > numOfRemainingWalls) {
      // Use Kruskal algorithm to randomly select wall
      Wall targetWall = remainingWallList.get(rand.nextInt(remainingWallList.size()));
      Cell cellOne = targetWall.getCellOne();
      Cell cellTwo = targetWall.getCellTwo();
      // if not breaked and not connected make it breaked or stored into savedWall
      // list
      if (!breakedWallSet.contains(targetWall)) {
        if (!cellConnected.isConnected(cellOne, cellTwo)) {
          cellConnected.union(cellOne, cellTwo);
        }
        this.remainingWallList.remove(targetWall);
        this.breakedWallSet.add(targetWall);
        cellOne.getNeighouberCellsSet().add(cellTwo);
        cellTwo.getNeighouberCellsSet().add(cellOne);
      } else {
        continue;
      }
    }
  }
  
  private void buildWrapping() {
    for (int row = 0; row < rows; row++) {
      gameMap[row][0].getNeighouberCellsSet().add(gameMap[row][columns - 1]);
      gameMap[row][columns - 1].getNeighouberCellsSet().add(gameMap[row][0]);
    }
    
    for (int col = 0; col < columns; col++) {
      gameMap[0][col].getNeighouberCellsSet().add(gameMap[rows - 1][col]);
      gameMap[rows - 1][col].getNeighouberCellsSet().add(gameMap[0][col]);
    }
    
  }

  /**
   * Print the Maze with its cells information.
   * Verification purpose.
   */
  @Override
  public void printMaze() {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < columns; col++) {
        System.out.println(gameMap[row][col] + " Connected: "
            + gameMap[row][col].getNeighouberCellsSet() + " Leads to:"
            + gameMap[row][col].getLeadToCavesSet() + gameMap[row][col].isCave());
        System.out.println("*****");
      }
    }
  }

  /**
   * Print all the pit/bat/wupus location.
   * Verification purpose.
   */
  public void printMonsters() {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < columns; col++) {
        if (gameMap[row][col].isBat()) {
          System.out.println("SuperBats:" + gameMap[row][col]);
        }
        if (gameMap[row][col].isPit()) {
          System.out.println("Pits:" + gameMap[row][col]);
        }
        if (gameMap[row][col].isWumpus()) {
          System.out.println("Wupus:" + gameMap[row][col]);
        }
      }
    }
  }

  /**
   * Set the cells in the Maze to Cave when it is not bi-direction tunnel,
   * otherwise Tunnel when bridging access have 2 connections.
   */
  private void setCave() {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < columns; col++) {
        Cell targetCell = gameMap[row][col];
        int numberOfNeighbours = targetCell.getNeighouberCellsSet().size();
        if (numberOfNeighbours == 2) {
          // flag as tunnel
          targetCell.setCave(false);
        } else {
          // flag as cave
          targetCell.setCave(true);
        }
      }
    }
  }

  /**
   * Set leadToCavesSet into each cells stored in the gameMap.
   */
  private void setLeadToCavesSet() {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < columns; col++) {
        findLeadToCaves(gameMap[row][col]);
      }
    }
  }

  /**
   * Use BFS method to find all the neighbour caves.
   */
  public void findLeadToCaves(Cell currentCell) {
    for (Cell neighbourCell : currentCell.getNeighouberCellsSet()) {
      if (neighbourCell.isCave()) {
        currentCell.getLeadToCavesSet().add(neighbourCell);
      } else {
        Set<Cell> visitedSet = new HashSet<>();
        visitedSet.add(neighbourCell);
        visitedSet.add(currentCell);
        currentCell.getLeadToCavesSet().add(findNextCave(neighbourCell, visitedSet));
      }
    }
  }

  /**
   * Recursively locate the leadToCaves.
   */
  private Cell findNextCave(Cell currentCell, Set<Cell> visited) {
    for (Cell neighbour : currentCell.getNeighouberCellsSet()) {
      if (visited.contains(neighbour)) {
        continue;
      }
      if (neighbour.isCave()) {
        return neighbour;
      } else {
        visited.add(neighbour);
        return findNextCave(neighbour, visited);
      }
    }
    System.out.println("should not arrive here ");
    return currentCell;
  }

  /**
   * Get the totalWallList.
   * @return the totalWallList total wall list
   */
  @Override
  public List<Wall> getRemainingWallList() {
    return remainingWallList;
  }

  /**
   * Get the 2D array gameMap.
   * @return the gameMap the 2D array gameMap
   */
  public Cell[][] getGameMap() {
    return gameMap;
  }

  /**
   * Get the breakedWallSet.
   * @return the breakedWallSet store breakedWallSet for Kruskal algorithm
   */
  public Set<Wall> getBreakedWallSet() {
    return breakedWallSet;
  }

}
