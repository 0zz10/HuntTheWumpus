
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import model.Hunter;

/**
 * Hunter Class Test.
 */
public class HunterTest {
  private Hunter testHunter;
  private Point testPoint;
  
  /**
   * Initiation of the Test Case.
   */
  @Before
  public void setUp() {
    testPoint = new Point(10, 20);
    testHunter = new Hunter(testPoint);
  }

  /**
   * Test if getCurrentPos for constructor works.
   */
  @Test
  public void testGetCurrentPos() {
    assertEquals(testPoint, testHunter.getCurrentPos());
    assertEquals(10, testHunter.getCurrentPos().x);
    assertEquals(20, testHunter.getCurrentPos().y);
  }
  
  /**
   * Test if constructor throws an IllegalArgumentException 
   * when starting position is invalid.
   */
  @Test
  public void testNegativeStarting() {
    try {
      testHunter = new Hunter(new Point(-10, 10));
      fail();
    } 
    catch (IllegalArgumentException e) {
      // Continue, do nothing
    }
    try {
      testHunter = new Hunter(new Point(10, -10));
      fail();
    }
    catch (IllegalArgumentException e) {
      // Continue, do nothing
    }
  }
  
  /**
   * Test if getter and setter for number of arrows works.
   */
  @Test
  public void testNumberOfArrows() {
    int finalCount = 100;
    for (int i = 0; i < finalCount; i++) {
      testHunter.setNumberOfArrows(i);
      assertEquals(i, testHunter.getNumberOfArrows());
    } 
  }

}
