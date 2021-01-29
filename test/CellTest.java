
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import model.Cell;

/**
 * Cell Class Test.
 */
public class CellTest {
  private Cell testCell;

  /**
   * Initiation of the Test Case.
   */
  @Before
  public void setUp() throws Exception {
    int x = 2;
    int y = 3;
    testCell = new Cell(2, 3);
  }

  /**
   * Test if getter x for constructor works.
   */
  @Test
  public void testGetX() {
    assertEquals(2, testCell.getX());
  }

  /**
   * Test if getter y for constructor works.
   */
  @Test
  public void testGetY() {
    assertEquals(3, testCell.getY());
  }

  /**
   * Test if setter x works.
   */
  @Test
  public void testSetX() {
    testCell.setX(10);
    assertEquals(10, testCell.getX());
  }

  /**
   * Test if setter y works.
   */
  @Test
  public void testSetY() {
    testCell.setY(20);
    assertEquals(20, testCell.getY());
  }

  /**
   * Test if setter and getter index works.
   */
  @Test
  public void testIndex() {
    testCell.setIndex(20);
    assertEquals(20, testCell.getIndex());
  }

  /**
   * Test if setter and getter for cave works.
   */
  @Test
  public void testCave() {
    testCell.setCave(true);
    assertEquals(true, testCell.isCave());
    testCell.setCave(false);
    assertEquals(false, testCell.isCave());
  }

  /**
   * Test if setter and getter for Wupums works.
   */
  @Test
  public void testWupums() {
    testCell.setWumpus(true);
    assertEquals(true, testCell.isWumpus());
    testCell.setWumpus(false);
    assertEquals(false, testCell.isWumpus());
  }

  /**
   * Test if setter and getter for Bat works.
   */
  @Test
  public void testBat() {
    testCell.setBat(true);
    assertEquals(true, testCell.isBat());
    testCell.setBat(false);
    assertEquals(false, testCell.isBat());
  }

  /**
   * Test if setter and getter for Pit works.
   */
  @Test
  public void testPit() {
    testCell.setPit(true);
    assertEquals(true, testCell.isPit());
    testCell.setPit(false);
    assertEquals(false, testCell.isPit());
  }

  /**
   * Test if setter and getter for Blood works.
   */
  @Test
  public void testBlood() {
    testCell.setBlood(true);
    assertEquals(true, testCell.isBlood());
    testCell.setBlood(false);
    assertEquals(false, testCell.isBlood());
  }
}
