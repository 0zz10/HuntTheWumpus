package model;

import java.awt.Point;

/**
 * This class represents a hunter who has a current Position, and number of
 * arrows to shoot. Hunter can move to any position as the getter and setter are
 * accessible from game class and its position is updated after each moveTo
 * command.
 * 
 * @author Daniel Zhou
 *
 */
public class Hunter {
  private Point currentPos;
  private int numOfArrows;
  private Point arrowPos;

  /**
   * This class constructs a Hunter with current position specified.
   * 
   * @param currentPos current position of this hunter
   * @throws IllegalArgumentException if position is invalid
   */
  public Hunter(Point currentPos) throws IllegalArgumentException {
    if (currentPos.x < 0 || currentPos.y < 0) {
      throw new IllegalArgumentException("invalid position for a hunter");
    }
    this.currentPos = currentPos;
    this.arrowPos = currentPos;
  }

  /**
   * Get the current position of this Hunter.
   * 
   * @return the currentPos the current position of Hunter
   */
  public Point getCurrentPos() {
    return currentPos;
  }

  /**
   * Set the current position of this Hunter.
   * 
   * @param currentPos the currentPos to set
   */
  public void setCurrentPos(Point currentPos) {
    this.currentPos = currentPos;
  }

  /**
   * Get the number of arrows left for this Hunter.
   * 
   * @return the numberOfArrows the number of arrows left
   */
  public int getNumberOfArrows() {
    return numOfArrows;
  }

  /**
   * Set the number of arrows left for this Hunter.
   * 
   * @param numberOfArrows the numberOfArrows to set
   */
  public void setNumberOfArrows(int numberOfArrows) {
    this.numOfArrows = numberOfArrows;
  }

  @Override
  public String toString() {
    return String.format("Hunter at (%d, %d), with %d arrows left to shoot.",
        this.getCurrentPos().x, this.getCurrentPos().y, this.getNumberOfArrows());
  }

  /**
   * Get the arrow Position of this Hunter.
   * 
   * @return the arrowPos the arrow position of Hunter
   */
  public Point getArrowPos() {
    return arrowPos;
  }

  /**
   * Set the arrow Position of this Hunter.
   * 
   * @param arrowPos the arrowPos to set
   */
  public void setArrowPos(Point arrowPos) {
    this.arrowPos = arrowPos;
  }
}
