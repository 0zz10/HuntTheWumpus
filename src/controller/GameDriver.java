package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import view.GameView;

/**
 * This class represents a driver which launches the controller and takes
 * keyboard input, and outputs the state of the game to the screen.
 * 
 * @author Daniel Zhou
 */
public class GameDriver {

  /**
   * Main driver method to execute the program with command line arguments and can
   * be further implemented in jar file. First command line argument to tell if
   * GUI is needed to be generated. command line argument --gui or --text to
   * proceed the game. otherwise the program should display a suitable error
   * message and quit.
   */
  public static void main(String[] args) {
    System.out.println("Welcome to Hunt The Wumpus Game!\n*** Final Version ***");
    if (args[0].equals("--gui")) {
      GameView gameGUI = new GameView();
      gameGUI.setVisible(true);
    } else if (args[0].equals("--text")) {
      boolean continueToPlay = true;
      // continueToPlay gives the option to restart this game.
      while (continueToPlay) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-- Game Mode configuration --\n1-Player or 2-Player? (1 or 2)");
        int numOfPlayers = scanner.nextInt();
        boolean isTwoPlayer = numOfPlayers == 2 ? true : false;

        System.out.println("-- Map property configuration --");
        System.out.println("How many rows of this maze? ");
        int rows = scanner.nextInt();

        System.out.println("How many columns of this maze? ");
        int columns = scanner.nextInt();

        System.out.println("How many remaining walls of this maze? ");
        int numOfRemainingWalls = scanner.nextInt();

        System.out.println("Preferable cave index to start? ");
        System.out.println("Choose any index between 1 and " + (rows * columns));
        int startingIdx = scanner.nextInt();

        System.out.println("-- Difficulty configuration --");
        System.out.println("How many pits located at this map? ");
        int numOfPits = scanner.nextInt();

        System.out.println("How many superbats located at this map? ");
        int numOfBats = scanner.nextInt();

        System.out.println("Preferable number of arrows to start? ");
        int startingNumArrow = scanner.nextInt();

        System.out.println("Game synchronizing...\n");

        Game game = new Game(rows, columns, numOfRemainingWalls, numOfPits, numOfBats, startingIdx,
            startingNumArrow, isTwoPlayer);

        BufferedReader streamInMsg = new BufferedReader(new InputStreamReader(System.in));
        // access to the isEnd status
        while (!game.isEnd()) {
          String streamOutMsg = game.streamOut();
          System.out.println(streamOutMsg);
          try {
            String input = streamInMsg.readLine();
            game.streamIn(input);
          } catch (IOException e) {
            System.out.println("IO exception, restart process");
          }
        }
        scanner = new Scanner(System.in);
        System.out.println("Play Again? (Y/N)");
        String nextGame = scanner.nextLine();
        continueToPlay = nextGame.equals("Y");
      }
    } else {
      System.out.println("command line input error, should be either --gui or --text.");
      System.exit(0);
    }
  }
}
