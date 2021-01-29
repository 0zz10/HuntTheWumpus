package model;

/**
 * This class represents a Wall class which takes cellOne and cellTwo Cell Object as inputs.
 * The Wall class can be instantiated in the RoomMaze class 
 * and it described a relation between two Cells.
 * Getter and Setter enable this class to be updated in other class.
 * 
 * @author Daniel Zhou
 */
public class Wall {
  private Cell cellOne;
  private Cell cellTwo;

  /**
   * Construct a Wall between two cells.
   * @param cellOne cellOne
   * @param cellTwo another cell
   */
  public Wall(Cell cellOne, Cell cellTwo) {
    this.cellOne = cellOne;
    this.cellTwo = cellTwo;
  }

  /**
   * Get the cell object of cellOne.
   * @return the cellOne CellOne
   */
  public Cell getCellOne() {
    return cellOne;
  }

  /**
   * Set the cell object of cellOne.
   * @param cellOne the cellOne to set
   */
  public void setCellOne(Cell cellOne) {
    this.cellOne = cellOne;
  }

  /**
   * Get the cell object of cellTwo.
   * @return the cellTwo CellTwo
   */
  public Cell getCellTwo() {
    return cellTwo;
  }

  /**
   * Set the cell object of cellTwo.
   * @param cellTwo the cellTwo to set
   */
  public void setCellTwo(Cell cellTwo) {
    this.cellTwo = cellTwo;
  }

  @Override
  public String toString() {
    return String.format("(%d, %d)|(%d, %d)", this.getCellOne().getX(), this.getCellOne().getY(),
        this.getCellTwo().getX(), this.getCellTwo().getY());
  }
}
