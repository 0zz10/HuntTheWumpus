

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import model.Cell;
import model.DisjointSets;

/**
 * Test the Disjoint Sets class.
 */
public class DisjointSetsTest {
  private DisjointSets testSet;
  Cell c1 = new Cell(1, 2);
  Cell c2 = new Cell(2, 3);
  Cell c3 = new Cell(3, 5);

  /**
   * Test if union method works.
   */
  @Test
  public void testUnion() {
    testSet = new DisjointSets(4, 6);
    testSet.union(c1, c2);
    testSet.union(c1, c3);
    assertEquals(true, testSet.isConnected(c1, c3));
    assertEquals(true, testSet.isConnected(c2, c1));
    assertEquals(true, testSet.isConnected(c2, c3));
  }
  
  /**
   * Test if find method works.
   */
  @Test
  public void testFind() {
    testSet = new DisjointSets(4, 6);
    // before union
    assertEquals(c1.toString(), testSet.find(c1).toString());
    assertEquals(c2.toString(), testSet.find(c2).toString());
    assertEquals(c3.toString(), testSet.find(c3).toString());
    testSet.union(c1, c2);
    testSet.union(c2, c3);
    // after union
    assertEquals(c1.toString(), testSet.find(c1).toString());
    assertEquals(c1.toString(), testSet.find(c2).toString());
    assertEquals(c1.toString(), testSet.find(c3).toString());
  }

  /**
   * Test if isConnected method works.
   */
  @Test
  public void testIsConnected() {
    testSet = new DisjointSets(4, 6);
    // before union
    assertFalse(testSet.isConnected(c1, c2));
    assertFalse(testSet.isConnected(c1, c3));
    assertFalse(testSet.isConnected(c2, c3));
    testSet.union(c1, c2);
    testSet.union(c2, c3);
    // after union
    assertTrue(testSet.isConnected(c1, c2));
    assertTrue(testSet.isConnected(c1, c3));
    assertTrue(testSet.isConnected(c2, c3));
  }

}
