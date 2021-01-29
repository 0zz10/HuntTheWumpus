package controller;

import java.awt.Point;
import java.util.HashSet;
import java.util.Observable;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import model.Cell;
import model.Hunter;
import model.RoomMaze;

/**
 * This class represents a controller for the Hunt The Wumpus Game. This
 * controller takes input from the user using StringBuffer, and navigate the
 * hunter's position of this game, also enables to shoot arrow towards given
 * direction. Fields in rows, columns, numOfRemainingWalls, numOfPits,
 * numOfBats, ,startingIdx, and startingNumArrow are passing from the
 * constructor, which is enabled by the command line argumnent from the driver.
 * 
 * @author Daniel Zhou
 */
public class Game extends Observable implements IController {
  private StringBuffer input; // Holds user input command
  private StringBuffer output; // Holds generated text output
  private int rows;
  private int columns;
  private int numOfRemainingWalls;
  private int startingIdx;
  private int numOfPits;
  private int numOfBats;
  private int startingNumArrow;
  private Hunter hunter;
  private Hunter hunterOne;
  private Hunter hunterTwo;
  private Cell[][] gameMap;
  private RoomMaze roomMaze;
  private String currentState;
  private int numCavePass;
  private boolean isTwoPlayers;
  private long seedRandomGame;
  Random random = new Random(seedRandomGame);
  private boolean isGodMode;
  private boolean isSpecialEdition;

  private final String GET_INPUT_ACTION = "GET_INPUT_ACTION";
  private final String GET_INPUT_NUM_CAVES = "GET_INPUT_NUM_CAVES";
  private final String GET_INPUT_SHOOT_TO = "GET_INPUT_SHOOT_TO";
  private final String GET_INPUT_MOVE_TO = "GET_INPUT_MOVE_TO";

  private final String MOVE_COMMAND = "M";
  private final String SHOOT_COMMAND = "S";

  /**
   * Construct a Game Controller defined by user. rows/ columns/
   * numOfRemainingWalls will be validated with exception through maze
   * constructor.
   * 
   * @param rows                total rows of this maze
   * @param columns             total columns of this maze
   * @param numOfRemainingWalls total numOfRemainingWalls of this maze
   * @param numOfPits           total number of pits
   * @param numOfBats           total number of bats
   * @param startingIdx         starting index
   * @param startingNumArrow    starting number of arrows
   * @param isTwoPlayers        true if 2 player mode
   * @throws IllegalArgumentException if attributes are invalid
   */
  public Game(int rows, int columns, int numOfRemainingWalls, int numOfPits, int numOfBats,
      int startingIdx, int startingNumArrow, boolean isTwoPlayers) throws IllegalArgumentException {
    if (numOfPits < 0 || numOfPits > rows * columns - 1 - numOfBats) {
      throw new IllegalArgumentException("invalid number of Pits");
    }
    if (numOfBats < 0 || numOfBats > rows * columns - 1 - numOfPits) {
      throw new IllegalArgumentException("invalid number of bats");
    }
    if (startingIdx < 1 || startingIdx > rows * columns) {
      throw new IllegalArgumentException("invalid starting index");
    }
    if (startingNumArrow < 0) {
      throw new IllegalArgumentException("invalid number of arrows to start");
    }
    this.rows = rows;
    this.columns = columns;
    this.numOfRemainingWalls = numOfRemainingWalls;
    this.numOfPits = numOfPits;
    this.numOfBats = numOfBats;
    this.startingIdx = startingIdx;
    this.startingNumArrow = startingNumArrow;
    this.isTwoPlayers = isTwoPlayers;
    output = new StringBuffer();
    input = new StringBuffer();
    start();
  }

  /**
   * initialized the game controller. Modified: Adding notifyObservers() to this
   * method, callback from View when restarting the game.
   */
  @Override
  public void start() {
    random.setSeed(seedRandomGame);
    roomMaze = new RoomMaze(rows, columns, numOfRemainingWalls, seedRandomGame);
    gameMap = roomMaze.getGameMap();
    this.isGodMode = false;
    this.isSpecialEdition = false;
    buildPits(numOfPits);
    buildBats(numOfBats);
    buildWumpus();
    buildHunter();
    currentState = GET_INPUT_ACTION;
    showCurrentCave();
    checkBats();
    askShootOrMove();
    setChanged();
    notifyObservers();
  }

  /**
   * Takes input from the view as BufferedReader String. Modified: passing the
   * inputString to this method for further validations.
   */
  @Override
  public void streamIn(final String inputString) {
    output.setLength(0);
    input.setLength(0);
    input.append(inputString);
    update(input.toString());
  }

  /**
   * Generates output and print to Text View as String.
   */
  @Override
  public String streamOut() {
    return output.toString();
  }

  /**
   * Termination condition. Move to Wumpus/ Move to Pit/ Use up the arrows/ Shoot
   * at Wumpus. Modified: Sub-termination status are implements to pass the view
   * exact result as checking by boolean methods.
   */
  public boolean isEnd() {
    return (isEaten() || isFallen() || isOutOfArrows() || isGameWon());
  }

  /**
   * Scenario when either Hunter is eaten by the Wumpus.
   */
  public boolean isEaten() {
    Cell current = gameMap[hunter.getCurrentPos().x][hunter.getCurrentPos().y];
    if (current.isWumpus()) {
      System.out.println(
          "\nChomp, chomp, chomp, thanks for feeding the Wumpus!\n" + "Better luck next time\n");
      return true;
    }
    return false;
  }

  /**
   * Scenario when either Hunter move to pit.
   */
  public boolean isFallen() {
    Cell current = gameMap[hunter.getCurrentPos().x][hunter.getCurrentPos().y];
    if (current.isPit()) {
      System.out
          .println("\nThe cave contains a bottomless pit!\n" 
              + "You fall screaming into the void.\n");
      return true;
    }
    return false;
  }

  /**
   * Scenario when either Hunter used up the arrows.
   */
  public boolean isOutOfArrows() {
    if (hunter.getNumberOfArrows() <= 0) {
      System.out.println("\nYou used up all your arrows!\n" + "Game Over\n");
      return true;
    }
    return false;
  }

  /**
   * Scenario when either Hunter Shoot at Wumpus and win the game.
   */
  public boolean isGameWon() {
    Cell arrowPosition = gameMap[hunter.getArrowPos().x][hunter.getArrowPos().y];
    if (arrowPosition.isWumpus()) {
      System.out.println("\nHee hee hee, you got the wumpus!\n" 
          + "Next time you won't be so lucky\n");
      return true;
    }
    return false;
  }

  /**
   * Update the status of controller in response of input.
   */
  @Override
  public void update(String currentCommand) {
    switch (currentState) {
      case GET_INPUT_ACTION:
        if (currentCommand.equals(MOVE_COMMAND)) {
          output.append("Where to?");
          currentState = GET_INPUT_MOVE_TO;
        } else if (currentCommand.equals(SHOOT_COMMAND)) {
          output.append("No. of caves (1-5)?");
          currentState = GET_INPUT_NUM_CAVES;
        } else {
          output.append("invalid command for Shoot or Move, input S or M");
          showCurrentCave();
          askShootOrMove();
        }
        break;
      case GET_INPUT_MOVE_TO:
        if (!isValidNextIndex(currentCommand)) {
          output.append("Not valid cave index move to");
          output.append("\nWhere to?");
          break;
        }
        int moveToHunterX = (Integer.valueOf(currentCommand) - 1) / columns;
        int moveToHunterY = (Integer.valueOf(currentCommand) - 1) % columns;
        hunter.setCurrentPos(new Point(moveToHunterX, moveToHunterY));
        Cell currentCave = gameMap[hunter.getCurrentPos().x][hunter.getCurrentPos().y];
        currentCave.setVisited(true);
        if (hunter.equals(hunterOne) && isTwoPlayers) {
          hunter = hunterTwo;
          output.append("\n\n** Switch to Player 2 **\n");
          showCurrentCave();
          checkBats();
        } else {
          hunter = hunterOne;
          showCurrentCave();
          checkBats();
        }
        askShootOrMove();
        currentState = GET_INPUT_ACTION;
        break;
  
      case GET_INPUT_NUM_CAVES:
        if (!isValidNumCavesPass(currentCommand)) {
          output.append("Not valid num of caves pass");
          output.append("\nNo. of caves (1-5)?");
          break;
        }
        numCavePass = Integer.valueOf(currentCommand);
        output.append("Toward cave?");
        currentState = GET_INPUT_SHOOT_TO;
        break;
  
      case GET_INPUT_SHOOT_TO:
        if (!isValidNextIndex(currentCommand)) {
          output.append("Not valid cave index shoot towards");
          output.append("\nToward cave?");
          break;
        }
        hunter.setNumberOfArrows(hunter.getNumberOfArrows() - 1);
        int shootToArrowX = (Integer.valueOf(currentCommand) - 1) / columns;
        int shootToArrowY = (Integer.valueOf(currentCommand) - 1) % columns;
        if (numCavePass == 1) {
          hunter.setArrowPos(new Point(shootToArrowX, shootToArrowY));
        } else {
          // shootWumpus(numCavePass);
          // output.append("\nHunter are at: " + hunter.toString());
          // output.append("\nNothing Happened ");
        }
        // when game is not end, arrow not hit target
        output.append(
            "\nMissed." + "\nYour has " + hunter.getNumberOfArrows() + " more arrows to Shoot\n");
        currentState = GET_INPUT_ACTION;
        if (hunter.equals(hunterOne) && isTwoPlayers) {
          hunter = hunterTwo;
          output.append("\n\n** Switch to Player 2 **\n");
        } else {
          hunter = hunterOne;
        }
        showCurrentCave();
        checkBats();
        askShootOrMove();
        break;
      default:
        output.append("Looking for the input");
    }
    setChanged();
    notifyObservers();
  }

  /**
   * Error Handler of Controller for MoveTo or ShootTo index. Check the if input
   * of Next Cave is valid.
   */
  private boolean isValidNextIndex(final String input) {
    int nextCaveIndex;
    try {
      nextCaveIndex = Integer.valueOf(input);
    } catch (NumberFormatException e) {
      return false;
    }
    Cell currentCell = gameMap[hunter.getCurrentPos().x][hunter.getCurrentPos().y];
    for (Cell leadToCave : currentCell.getLeadToCavesSet()) {
      if (nextCaveIndex == leadToCave.getIndex()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Error Handler of Controller for number of Cave. Check the if input of number
   * of Cave Pass(1-5) is valid.
   */
  private boolean isValidNumCavesPass(final String input) {
    try {
      numCavePass = Integer.valueOf(input);
      return (numCavePass > 0 && numCavePass < 6);
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Append output string as showing current position.
   */
  public void showCurrentCave() {
    Cell currentCave = gameMap[hunter.getCurrentPos().x][hunter.getCurrentPos().y];
    currentCave.setVisited(true);
    checkSmell();
    output.append("\nYou are in " + currentCave);
    output.append("\nTunnel Leads to: " + currentCave.getLeadToCavesSet());
  }

  /**
   * Append output string as ask for GET_INPUT_ACTION status.
   */
  public void askShootOrMove() {
    output.append("\nShoot or Move (S-M)?");
  }

  /**
   * Take input of starting cave idx for hunter and instantiate this hunter
   * object. Initialize two hunters if in 2P mode.
   */
  private void buildHunter() {
    int startingPosX = (startingIdx - 1) / columns;
    int startingPosY = (startingIdx - 1) % columns;
    Point currentPos = new Point(startingPosX, startingPosY);
    while (!gameMap[currentPos.x][currentPos.y].isCave()) {
      System.out.println("Not a valid Cave, start at a random Cave position");
      currentPos = new Point(random.nextInt(rows), random.nextInt(columns));
    }
    hunterOne = new Hunter(currentPos);
    hunterOne.setNumberOfArrows(startingNumArrow);
    if (isTwoPlayers) {
      hunterTwo = new Hunter(currentPos);
      hunterTwo.setNumberOfArrows(startingNumArrow);
    }
    hunter = hunterOne;
  }

  /**
   * Take input of number of pits and set the pit locations randomly, also update
   * neighbour caves as draft.
   */
  private void buildPits(int numOfPits) {
    for (int i = 0; i < numOfPits; i++) {
      Point pitPosition = new Point(random.nextInt(rows), random.nextInt(columns));
      while (!gameMap[pitPosition.x][pitPosition.y].isCave()) {
        pitPosition = new Point(random.nextInt(rows), random.nextInt(columns));
      }
      gameMap[pitPosition.x][pitPosition.y].setPit(true);
      placeDraft(pitPosition);
    }
  }

  /**
   * Take input of number of bats and set the bat locations randomly.
   */
  private void buildBats(int numOfBats) {
    for (int i = 0; i < numOfBats; i++) {
      Point batPosition = new Point(random.nextInt(rows), random.nextInt(columns));
      while (!gameMap[batPosition.x][batPosition.y].isCave()) {
        batPosition = new Point(random.nextInt(rows), random.nextInt(columns));
      }
      gameMap[batPosition.x][batPosition.y].setBat(true);
    }
  }

  /**
   * Set the wumpus locations randomly and update neighbour caves as blood.
   */
  private void buildWumpus() {
    Point wumpusLocation = new Point(random.nextInt(rows), random.nextInt(columns));
    while (!gameMap[wumpusLocation.x][wumpusLocation.y].isCave()) {
      wumpusLocation = new Point(random.nextInt(rows), random.nextInt(columns));
    }
    gameMap[wumpusLocation.x][wumpusLocation.y].setWumpus(true);
    placeBoold(wumpusLocation);
  }

  /**
   * Set model.Cell object as blood.
   */
  private void placeBoold(Point wumpusLocation) {
    Set<Cell> wupusLeadToCavesSet = gameMap[wumpusLocation.x][wumpusLocation.y].getLeadToCavesSet();
    for (Cell bloodCaves : wupusLeadToCavesSet) {
      bloodCaves.setBlood(true);
    }
  }

  /**
   * Set model.Cell object as draft.
   */
  private void placeDraft(Point pitLocation) {
    Set<Cell> pitLeadToCavesSet = gameMap[pitLocation.x][pitLocation.y].getLeadToCavesSet();
    for (Cell draftCaves : pitLeadToCavesSet) {
      draftCaves.setDraft(true);
    }
  }

  /**
   * Check if super bat locates at current cave, 50% chance to avoid or randomly
   * put another cave.
   */
  public void checkBats() {
    Cell current = gameMap[hunter.getCurrentPos().x][hunter.getCurrentPos().y];
    if (current.isBat()) {
      // randomly choose 0 or 1
      int ifGrab = random.nextInt(2);
      if (ifGrab == 0) {
        output.append("\nWhoa -- you successfully duck superbats that try to grab you");
      } else {
        int randomPosX = random.nextInt(rows);
        int randomPosY = random.nextInt(columns);
        // reroll if in same bat cave or in tunnel
        while ((randomPosX == current.getX()) && randomPosY == current.getY()
            && !gameMap[randomPosX][randomPosY].isCave()) {
          randomPosX = random.nextInt(rows);
          randomPosY = random.nextInt(columns);
        }
        output.append("\nSnatch -- you are grabbed by superbats and ...");
        Point randomPos = new Point(randomPosX, randomPosY);
        hunter.setCurrentPos(randomPos);
      }
      showCurrentCave();
    }
  }

  /**
   * Check if blood or draft locates at current cave, append output if smells
   * anything.
   */
  private void checkSmell() {
    Cell current = gameMap[hunter.getCurrentPos().x][hunter.getCurrentPos().y];
    if (current.isBlood() && current.isDraft()) {
      output.append("\nTough choice! You smell a Wumpus and" + " you feel a cold wind blowing");
    } else if (current.isBlood()) {
      output.append("\nYou smell a Wumpus!");
    } else if (current.isDraft()) {
      output.append("\nYou feel a cold wind blowing!");
    }
  }

  /**
   * Shoot Wumpus in given distance, return true if arrowPos reaches Wumpus cave.
   */
  private boolean shootWumpus(int numCavePass) {
    hunter.setNumberOfArrows(hunter.getNumberOfArrows() - 1);
    Set<Cell> arrowSet = new HashSet<>();
    for (int numPass = 1; numPass < numCavePass; numPass++) {
      arrowSet = new HashSet<>();
      Cell currentArrow = gameMap[hunter.getArrowPos().x][hunter.getArrowPos().y];
      for (Cell leadsToCaves : currentArrow.getLeadToCavesSet()) {
        hunter.getArrowPos().x = leadsToCaves.getX();
        hunter.getArrowPos().y = leadsToCaves.getY();
        arrowSet.add(gameMap[hunter.getArrowPos().x][hunter.getArrowPos().y]);
      }
    }
    for (Cell arrowDesinations : arrowSet) {
      if (arrowDesinations.isWumpus()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Getter the gameMap of this Game.
   * 
   * @return the gameMap 2-D array
   */
  public Cell[][] getGameMap() {
    return gameMap;
  }

  /**
   * Getter hunter of this Game.
   * 
   * @return the hunter the hunter object
   */
  public Hunter getHunter() {
    return hunter;
  }

  /**
   * Takes MoveTo Direction Command and generate inStream StringBuilder to the
   * controller. For example if the command is EAST, this method will first check
   * if the eastCell in a cave, if it is a tunnel, then find the next cave index
   * through the tunnel from linkedCaveNotLinkedCellIndex, and return this index
   * as the available moveToEastIdx to EAST.
   */
  public Integer moveToIndex(String moveToDirection) {
    Cell currentCell = this.getGameMap()[this.getHunter().getCurrentPos().x][this.getHunter()
        .getCurrentPos().y];
    Set<Integer> linkedCaveIndexs = currentCell.getLeadToCavesSet().stream().map(c -> c.getIndex())
        .collect(Collectors.toSet());

    linkedCaveIndexs.add(currentCell.getIndex());

    Integer moveToEastIdx = currentCell.getY() < columns - 1 ? currentCell.getIndex() + 1
        : currentCell.getIndex();
    Integer moveToWestIdx = currentCell.getY() > 0 ? currentCell.getIndex() - 1
        : currentCell.getIndex();
    Integer moveToNorthIdx = currentCell.getX() > 0 ? currentCell.getIndex() - columns
        : currentCell.getIndex();
    Integer moveToSouthIdx = currentCell.getX() < rows - 1 ? currentCell.getIndex() + columns
        : currentCell.getIndex();

    Set<Integer> linkedCellIndexs = new HashSet<>();
    linkedCellIndexs.add(moveToEastIdx);
    linkedCellIndexs.add(moveToWestIdx);
    linkedCellIndexs.add(moveToNorthIdx);
    linkedCellIndexs.add(moveToSouthIdx);

    Integer linkedCaveNotLinkedCellIndex = currentCell.getIndex();

    for (Integer integer : linkedCaveIndexs) {
      if (!linkedCellIndexs.contains(integer)) {
        linkedCaveNotLinkedCellIndex = integer;
        break;
      }
    }

    if (moveToDirection.equals("EAST")) {
      if (linkedCaveIndexs.contains(moveToEastIdx)) {
        return moveToEastIdx;
      } else {
        Cell eastCell = this.getGameMap()[currentCell.getX()][currentCell.getY() + 1];
        if (!eastCell.isCave()) {
          eastCell.setVisited(true);
        }
        return linkedCaveNotLinkedCellIndex;
      }

    }

    else if (moveToDirection.equals("WEST")) {
      if (linkedCaveIndexs.contains(moveToWestIdx)) {
        return moveToWestIdx;
      } else {
        Cell westCell = this.getGameMap()[currentCell.getX()][currentCell.getY() - 1];
        if (!westCell.isCave()) {
          westCell.setVisited(true);
        }
        return linkedCaveNotLinkedCellIndex;
      }
    }

    else if (moveToDirection.equals("NORTH")) {
      if (linkedCaveIndexs.contains(moveToNorthIdx)) {
        return moveToNorthIdx;
      } else {
        Cell northCell = this.getGameMap()[currentCell.getX() - 1][currentCell.getY()];
        if (!northCell.isCave()) {
          northCell.setVisited(true);
        }
        return linkedCaveNotLinkedCellIndex;
      }
    }

    else if (moveToDirection.equals("SOUTH")) {
      if (linkedCaveIndexs.contains(moveToSouthIdx)) {
        return moveToSouthIdx;
      } else {
        Cell southCell = this.getGameMap()[currentCell.getX() + 1][currentCell.getY()];
        if (!southCell.isCave()) {
          southCell.setVisited(true);
        }
        return linkedCaveNotLinkedCellIndex;
      }
    } else {
      return currentCell.getIndex();
    }
  }

  /**
   * Takes ShootTo Direction Command and generate inStream StringBuilder to the
   * controller. For example if the command is SOUTH, this method will first reset
   * the location of Arrow with same location of Hunter, then update the direction
   * to the next cave of shootToEastIdx index in the maze.
   */
  public Integer shootToIndex(String shooToDirection) {
    Cell currentArrow = this.getGameMap()[this.getHunter().getCurrentPos().x][this.getHunter()
        .getCurrentPos().y];
    Set<Integer> linkedCaveIndexs = currentArrow.getLeadToCavesSet().stream().map(c -> c.getIndex())
        .collect(Collectors.toSet());

    linkedCaveIndexs.add(currentArrow.getIndex());

    Integer shootToEastIdx = currentArrow.getY() < columns - 1 ? currentArrow.getIndex() + 1
        : currentArrow.getIndex();
    Integer shootToWestIdx = currentArrow.getY() > 0 ? currentArrow.getIndex() - 1
        : currentArrow.getIndex();
    Integer shootToNorthIdx = currentArrow.getX() > 0 ? currentArrow.getIndex() - columns
        : currentArrow.getIndex();
    Integer shootToSouthIdx = currentArrow.getX() < rows - 1 ? currentArrow.getIndex() + columns
        : currentArrow.getIndex();

    Set<Integer> linkedCellIndexs = new HashSet<>();
    linkedCellIndexs.add(shootToEastIdx);
    linkedCellIndexs.add(shootToWestIdx);
    linkedCellIndexs.add(shootToNorthIdx);
    linkedCellIndexs.add(shootToSouthIdx);
    Integer linkedCaveNotLinkedCellIndex = currentArrow.getIndex();

    for (Integer integer : linkedCaveIndexs) {
      if (!linkedCellIndexs.contains(integer)) {
        linkedCaveNotLinkedCellIndex = integer;
        break;
      }
    }
    if (shooToDirection.equals("EAST")) {
      if (linkedCaveIndexs.contains(shootToEastIdx)) {
        return shootToEastIdx;
      } else {
        return linkedCaveNotLinkedCellIndex;
      }
    }
    else if (shooToDirection.equals("WEST")) {
      if (linkedCaveIndexs.contains(shootToWestIdx)) {
        return shootToWestIdx;
      } else {
        return linkedCaveNotLinkedCellIndex;
      }
    } else if (shooToDirection.equals("NORTH")) {
      if (linkedCaveIndexs.contains(shootToNorthIdx)) {
        return shootToNorthIdx;
      } else {
        return linkedCaveNotLinkedCellIndex;
      }
    } else if (shooToDirection.equals("SOUTH")) {
      if (linkedCaveIndexs.contains(shootToSouthIdx)) {
        return shootToSouthIdx;
      } else {
        return linkedCaveNotLinkedCellIndex;
      }
    } else {
      return currentArrow.getIndex();
    }
  }

  /**
   * Getter for rows.
   * 
   * @return the rows rows
   */
  public int getRows() {
    return rows;
  }

  /**
   * Setter for rows.
   * 
   * @param rows the rows to set
   */
  public void setRows(int rows) {
    this.rows = rows;
  }

  /**
   * Getter for columns.
   * 
   * @return the columns columns
   */
  public int getColumns() {
    return columns;
  }

  /**
   * Setter for columns.
   * 
   * @param columns the columns to set
   */
  public void setColumns(int columns) {
    this.columns = columns;
  }

  /**
   * Getter for RemainingWalls.
   * 
   * @return the numOfRemainingWalls RemainingWalls
   */
  public int getNumOfRemainingWalls() {
    return numOfRemainingWalls;
  }

  /**
   * Setter for RemainingWalls.
   * 
   * @param numOfRemainingWalls the numOfRemainingWalls to set
   */
  public void setNumOfRemainingWalls(int numOfRemainingWalls) {
    this.numOfRemainingWalls = numOfRemainingWalls;
  }

  /**
   * Getter for numOfPits.
   * 
   * @return the numOfPits numOfPits
   */
  public int getNumOfPits() {
    return numOfPits;
  }

  /**
   * Setter for numOfPits.
   * 
   * @param numOfPits the numOfPits to set
   */
  public void setNumOfPits(int numOfPits) {
    this.numOfPits = numOfPits;
  }

  /**
   * Getter for numOfBats.
   * 
   * @return the numOfBats numOfBats
   */
  public int getNumOfBats() {
    return numOfBats;
  }

  /**
   * Setter for numOfBats.
   * 
   * @param numOfBats the numOfBats to set
   */
  public void setNumOfBats(int numOfBats) {
    this.numOfBats = numOfBats;
  }

  public boolean isTwoPlayers() {
    return isTwoPlayers;
  }

  public void setTwoPlayers(boolean isTwoPlayers) {

    this.isTwoPlayers = isTwoPlayers;

  }

  /**
   * Get Position of hunterOne.
   * 
   * @return the hunterOne in this game
   */
  public Hunter getHunterOne() {
    return hunterOne;
  }

  /**
   * Get Position of hunterTwo.
   * 
   * @return the hunterTwo in this game
   */
  public Hunter getHunterTwo() {
    return hunterTwo;
  }

  /**
   * Getter the randomSeed.
   * 
   * @return the seedRandomGame
   */
  public long getSeedRandomGame() {
    return seedRandomGame;
  }

  /**
   * Setter the randomSeed.
   * 
   * @param seedRandomGame the seedRandomGame to set
   */
  public void setSeedRandomGame(long seedRandomGame) {
    this.seedRandomGame = seedRandomGame;
  }

  /**
   * Getter for RoomMaze.
   * 
   * @return the roomMaze Room Maze
   */
  public RoomMaze getRoomMaze() {
    return roomMaze;
  }

  /**
   * Getter for isGodMode.
   * 
   * @return the isGodMode God Mode flag
   */
  public boolean isGodMode() {
    return isGodMode;
  }

  /**
   * Setter for isGodMode.
   * 
   * @param isGodMode the isGodMode to set
   */
  public void setGodMode(boolean isGodMode) {
    this.isGodMode = isGodMode;
  }

  /**
   * Getter for isSpecialEdition flag.
   * 
   * @return the isSpecialEdition flag
   */
  public boolean isSpecialEdition() {
    return isSpecialEdition;
  }

  /**
   * Setter for isSpecialEdition flag.
   * 
   * @param isSpecialEdition the isSpecialEdition to set
   */
  public void setSpecialEdition(boolean isSpecialEdition) {
    this.isSpecialEdition = isSpecialEdition;
  }
}
