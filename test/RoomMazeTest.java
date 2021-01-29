
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import model.RoomMaze;

/**
 * Test the RoomMaze class.
 * 
 * @author Daniel Zhou
 */
public class RoomMazeTest {
  private static RoomMaze testMaze;
  private static int rows;
  private static int columns;
  private static int numOfRemainingWalls;
  
  /**
   * Initiation of the Test Case.
   */
  @BeforeClass
  public static void setUp() throws Exception {
    rows = 4;
    columns = 4;
    numOfRemainingWalls = 1;
    testMaze = new RoomMaze(rows, columns, numOfRemainingWalls, 0);
  }

  /**
   * Test if setDeadline method throws an IllegalArgumentException 
   * when rows, columns, and numOfRemainingWalls is invalid.
   */
  @Test
  public void testInvalidConstructor() {
    try {
      RoomMaze testInvalidDeadline = new RoomMaze(-10, 10, 1, 0);
      fail();
    } 
    catch (IllegalArgumentException e) {
      // Continue, do nothing
    }
    try {
      RoomMaze testInvalidDeadline = new RoomMaze(10, -10, 1, 0);
      fail();
    } 
    catch (IllegalArgumentException e) {
      // Continue, do nothing
    }
    try {
      RoomMaze testInvalidDeadline = new RoomMaze(10, 10, -1, 0);
      fail();
    } 
    catch (IllegalArgumentException e) {
      // Continue, do nothing
    }
    try {
      RoomMaze testInvalidDeadline = new RoomMaze(10, 10, 100, 0);
      fail();
    } 
    catch (IllegalArgumentException e) {
      // Continue, do nothing
    }
  }
  
  /**
   * Test if Kruskal algorithm works for removing expected number of walls.
   */
  @Test
  public void testListSize() {
    int expectedWalls = rows * (columns - 1) + (rows - 1) * columns;
    int expectedBrokenWalls = expectedWalls - numOfRemainingWalls;
    assertEquals(numOfRemainingWalls, testMaze.getRemainingWallList().size());
    assertEquals(expectedBrokenWalls, testMaze.getBreakedWallSet().size());
  }

}
