package model;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a cell entity which can be created by passing in x
 * coordinate and y coordinate. In this class, Cell has also fields: status
 * flag, neighbor Cells Set, and leadToCaves Set. getters and setters are
 * created so that neighbor Cells Set and leadToCaves Set can be updated by the
 * other class, also apply for the status flags: isCave, pit, bat, wumpus,
 * blood, and draft. 
 * Updated: 1.adding isVisited flag to update Image Panel
 * 2.GetImage method for view to access the right component icons.
 * 
 * @author Daniel Zhou
 */
public class Cell {
  private int x;
  private int y;
  private int index;
  private Set<Cell> neighouberCellsSet;
  private Set<Cell> leadToCavesSet;

  private boolean isCave; // True if the cell is a Cave. False if a Tunnel
  private boolean pit; // True if the cave contains a pit
  private boolean bat; // True if the cave contains a "super bat"
  private boolean wumpus; // True if the cave contains the Wumpus
  private boolean blood; // True if the cave next to Wumpus
  private boolean draft; // True if the cave next to pit
  private boolean isVisited; // True if the cell is visited

  /**
   * Construct the Cell object given the x coordinate and y coordinate.
   * 
   * @param x x coordinate of this cell
   * @param y y coordinate of this cell
   * @throws IllegalArgumentException if either coordinate is invalid
   */
  public Cell(int x, int y) throws IllegalArgumentException {
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("invalid position for a Cell");
    }
    this.x = x;
    this.y = y;
    this.neighouberCellsSet = new HashSet<>();
    this.leadToCavesSet = new HashSet<>();
  }

  /**
   * Get the x coordinate of this cell.
   * @return the x x coordinate of this cell
   */
  public int getX() {
    return x;
  }

  /**
   * Set the x coordinate of this cell.
   * @param x the x to set
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * Get the y coordinate of this cell.
   * @return the y y coordinate of this cell
   */
  public int getY() {
    return y;
  }

  /**
   * Set the y coordinate of this cell.
   * @param y the y to set
   */
  public void setY(int y) {
    this.y = y;
  }

  /**
   * Get isCave status of this cell.
   * @return the isCave Cave flag
   */
  public boolean isCave() {
    return isCave;
  }

  /**
   * Set isCave status of this cell.
   * @param isCave the isCave to set
   */
  public void setCave(boolean isCave) {
    this.isCave = isCave;
  }

  /**
   * Get the set of neighbor cells.
   * @return the neighouberCellsSet the set of neighbor cells
   */
  public Set<Cell> getNeighouberCellsSet() {
    return neighouberCellsSet;
  }

  /**
   * Get index of this cell.
   * @return the index of this cell
   */
  public int getIndex() {
    return index;
  }

  /**
   * Set the index of this cell.
   * @param index the index to set
   */
  public void setIndex(int index) {
    this.index = index;
  }

  /**
   * Get the set of leadToCaves cells.
   * @return the leadToCavesSet the set of leadToCaves cells
   */
  public Set<Cell> getLeadToCavesSet() {
    return leadToCavesSet;
  }

  /**
   * Get pit status of this cell.
   * @return the pit pit status
   */
  public boolean isPit() {
    return pit;
  }

  /**
   * Set pit status of this cell.
   * @param pit the pit to set
   */
  public void setPit(boolean pit) {
    this.pit = pit;
  }

  /**
   * Get bat status of this cell.
   * @return the bat bat status
   */
  public boolean isBat() {
    return bat;
  }

  /**
   * Set bat status of this cell.
   * @param bat the bat to set
   */
  public void setBat(boolean bat) {
    this.bat = bat;
  }

  /**
   * Get wumpus status of this cell.
   * @return the wumpus wumpus status
   */
  public boolean isWumpus() {
    return wumpus;
  }

  /**
   * Set wumpus status of this cell.
   * @param wumpus the wumpus to set
   */
  public void setWumpus(boolean wumpus) {
    this.wumpus = wumpus;
  }

  /**
   * Get blood status of this cell.
   * @return the blood blood status
   */
  public boolean isBlood() {
    return blood;
  }

  /**
   * Set blood status of this cell.
   * @param blood the blood to set
   */
  public void setBlood(boolean blood) {
    this.blood = blood;
  }

  /**
   * Get draft status of this cell.
   * @return the draft draft status
   */
  public boolean isDraft() {
    return draft;
  }

  /**
   * Set draft status of this cell.
   * @param draft the draft to set
   */
  public void setDraft(boolean draft) {
    this.draft = draft;
  }


  /**
   * Get isVisited status of this cell.
   * @return the isVisited
   */
  public boolean isVisited() {
    return isVisited;
  }

  /**
   * Set isVisited status of this cell.
   * @param isVisited the isVisited to set
   */
  public void setVisited(boolean isVisited) {
    this.isVisited = isVisited;
  }
  
  @Override
  public String toString() {
    if (this.isCave) {
      return String.format("Cave %d", this.getIndex());
    } else {
      return String.format("Tunnel %d", this.getIndex());
    }
  }  
}
