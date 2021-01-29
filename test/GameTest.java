
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import controller.Game;

/**
 * Game Controller Class Test.
 */
public class GameTest {
  private StringBuffer testInput;
  private StringBuffer testOutput;
  private Game testGame;
  
  /**
   * Initiation of the Test Case.
   */
  @Before
  public void setUp() {
    testInput = new StringBuffer();
    testOutput = new StringBuffer();
    // Game(int rows, int columns, int numOfRemainingWalls, int numOfPits, int
    // numOfBats, int startingIdx, int startingNumArrow)
    testGame = new Game(10, 10, 3, 2, 2, 5, 3, false);
  }

  /**
   * Test if MOVE_COMMAND state at Controller works.
   */
  @Test
  public void testMOVE_COMMAND() {
    testInput.append("M");
    testGame.streamIn(testInput.toString());
    testOutput.append("Where to?");
    assertEquals(testOutput.toString(), testGame.streamOut()); 
  }
  
  /**
   * Test if GET_INPUT_SHOOT_TO state at Controller works.
   */
  @Test
  public void testSHOOT_COMMAND() {
    testInput.append("S");
    testGame.streamIn(testInput.toString());
    testOutput.append("No. of caves (1-5)?");
    assertEquals(testOutput.toString(), testGame.streamOut()); 
  }
  
  /**
   * Test if GET_INPUT_MOVE_TO state at Controller works.
   */
  @Test
  public void testGET_INPUT_MOVE_TO() {
    testInput.append("M");
    testGame.streamIn(testInput.toString());
    testInput = new StringBuffer();
    testInput.append("10");
    testGame.streamIn(testInput.toString());
    testOutput.append("Not valid cave index move to");
    testOutput.append("\nWhere to?");
    assertEquals(testOutput.toString(), testGame.streamOut()); 
  }
  
  /**
   * Test if GET_INPUT_NUM_CAVES at Controller works.
   */
  @Test
  public void testGET_INPUT_NUM_CAVES() {
    testInput.append("S");
    testGame.streamIn(testInput.toString());
    testInput = new StringBuffer();
    testInput.append("5");
    testGame.streamIn(testInput.toString());
    testOutput.append("Toward cave?");
    assertEquals(testOutput.toString(), testGame.streamOut()); 
  }
  
  /**
   * Test if GET_INPUT_SHOOT_TO at Controller works.
   */
  @Test
  public void testGET_INPUT_SHOOT_TO() {
    testInput.append("S");
    testGame.streamIn(testInput.toString());
    testInput = new StringBuffer();
    testInput.append("5");
    testGame.streamIn(testInput.toString());
    testInput = new StringBuffer();
    testInput.append("30");
    testGame.streamIn(testInput.toString());
    testOutput.append("Not valid cave index shoot towards");
    testOutput.append("\nToward cave?");
    assertEquals(testOutput.toString(), testGame.streamOut());
  }
}
