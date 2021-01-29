package model;

import java.util.Arrays;

/**
 * This class is implemented to represent Disjoint Set Data Structure.
 * 
 * @author Daniel Zhou
 */
public class DisjointSets {
  private int rows;
  private int columns;
  private int[] rank;
  private Cell[] parent;

  /**
   * construct a disjoint set.
   * 
   * @param rows total rows of this disjoint sets
   * @param columns total columns of this disjoint sets
   */
  public DisjointSets(int rows, int columns) {
    this.rows = rows;
    this.columns = columns;
    int size = rows * columns;
    this.rank = new int[size];
    this.parent = new Cell[size];
    buildSet();
  }

  private void buildSet() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        int index = columns * i + j;
        Cell p = new Cell(i, j);
        parent[index] = p;
      }
    }
  }

  /**
   * find method recursively traverses the parent array until hit a node who is
   * parent of itself.
   * 
   * @param cell the target Cell.
   * @return the set contains target node
   */
  public Cell find(Cell cell) {
    int index = columns * cell.getX() + cell.getY();
    // If i is the parent of itself
    if (parent[index] == cell) {
      return cell;
    } else {
      // Recursively find the representative.
      Cell result = find(parent[index]);
      parent[index] = result;
      return result;
    }
  }

  /**
   * Union two sets containing target i and target j.
   * 
   * @param i value at first set
   * @param j value at second set
   */
  public void union(Cell i, Cell j) {
    Cell irep = find(i);
    Cell jrep = find(j);
    int iIdx = columns * i.getX() + i.getY();
    int jIdx = columns * j.getX() + j.getY();
    int irank = rank[iIdx];
    int jrank = rank[jIdx];
    // if belongs same set, no union
    if (irep.equals(jrep)) {
      return;
    }
    // union i to j set
    if (irank < jrank) {
      this.parent[iIdx] = jrep;
    }
    // else union j to i set
    else if (irank > jrank) {
      this.parent[jIdx] = irep;
    }
    // same rank, union j to i and update rank of i
    else {
      this.parent[jIdx] = irep;
      rank[iIdx]++;
    }
  }

  /**
   * Find if two nodes are connected.
   * 
   * @param i value at first set
   * @param j value at second set
   */
  public boolean isConnected(Cell i, Cell j) {
    return this.find(i) == this.find(j);
  }

  @Override
  public String toString() {
    return "DisjointSets [rows=" + rows + ", columns=" + columns + ", parent="
        + Arrays.toString(parent) + "]";
  }

}
