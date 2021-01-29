
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import model.Cell;
import model.Wall;

/**
 * Wall Class Test.
 */
public class WallTest {
  private Cell testCellOne;
  private Cell testCellTwo;
  private Wall testWall;
  
  /**
   * Initiation of the Test Case.
   */
  @Before
  public void setUp() {
    testCellOne = new Cell(0, 10);
    testCellTwo = new Cell(1, 10);
    testWall = new Wall(testCellOne, testCellTwo);
  }

  /**
   * Test if constructor works.
   */
  @Test
  public void testConstuctor() {
    assertEquals(testCellOne, testWall.getCellOne());
    assertEquals(testCellTwo, testWall.getCellTwo());
  }
  
  /**
   * Test if Cell getter works.
   */
  @Test
  public void testCellGetter() {
    assertEquals(0, testWall.getCellOne().getX());
    assertEquals(10, testWall.getCellOne().getY());
    assertEquals(1, testWall.getCellTwo().getX());
    assertEquals(10, testWall.getCellTwo().getY());
  }

  /**
   * Test if setter and getter works.
   */
  @Test
  public void testSet() {
    Cell testCell = new Cell(9, 9);
    testWall.setCellOne(testCell);
    assertEquals(testCell, testWall.getCellOne());
    testWall.setCellTwo(testCell);
    assertEquals(testCell, testWall.getCellTwo());
  }
}
