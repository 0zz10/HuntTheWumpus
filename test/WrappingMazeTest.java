
import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import model.WrappingMaze;

/**
 * Test the RoomMaze class.
 * 
 * @author Daniel Zhou
 */
public class WrappingMazeTest {
  private static WrappingMaze testMaze;
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
    numOfRemainingWalls = 3;
    testMaze = new WrappingMaze(rows, columns, numOfRemainingWalls);
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
    testMaze.printMaze();
    System.out.println(testMaze.getRemainingWallList());
  }
}