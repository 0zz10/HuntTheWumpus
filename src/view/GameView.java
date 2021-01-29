package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import controller.Game;

/**
 * This class represents a GUI view for the Hunt The Wumpus Game. This view
 * implements KeyLister to catch keyboard command, as well as adding the graphic
 * panel. It also implements JMenu Lister to allow user interactions at this
 * view.
 * 
 * @author Daniel Zhou
 */
public class GameView extends JFrame implements KeyListener {
  private JPanel currentPanel;
  private GraphicPanel graphicPanel;
  private Game game;

  /**
   * Instantiate the Game Frame.
   */
  public GameView() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setUpInitialGame();
    // size JView = JPanel + JMenu. JMenu = 50
    this.setSize(320 + 64 * game.getColumns(), 64 * game.getRows() + 50);
    this.setResizable(true);
    this.setLocationRelativeTo(null);
    this.setTitle("Hunt The Wumpus");
    setupMenus();
    setUpPanels();
    this.addKeyListener(this);
    // set focus on keyboard
    this.setFocusable(true);
    this.setBackground(Color.LIGHT_GRAY);
  }

  /**
   * This method instantiates all the JMenu items for this Game Frame. Adding
   * Action Lister to the MenuItems.
   */
  private void setupMenus() {
    JMenu option = new JMenu("Options");
    JMenu newGame = new JMenu("New Game");
    option.add(newGame);
    JMenuItem exitGame = new JMenuItem("Quit");
    option.add(exitGame);
    JMenuItem newGameSameSetup = new JMenuItem("Same Setup");
    newGame.add(newGameSameSetup);
    JMenuItem newGameRandom = new JMenuItem("Random Setup");
    newGame.add(newGameRandom);

    JMenuItem mode = new JMenu("Mode");
    JMenuItem onePlayer = new JMenuItem("1 Player");
    mode.add(onePlayer);
    JMenuItem twoPlayer = new JMenuItem("2 Player");
    mode.add(twoPlayer);

    JMenuItem difficulty = new JMenu("Difficulty");
    JMenuItem easy = new JMenuItem("Easy");
    difficulty.add(easy);
    JMenuItem medium = new JMenuItem("Medium");
    difficulty.add(medium);
    JMenuItem hard = new JMenuItem("Hard");
    difficulty.add(hard);
    JMenuItem hell = new JMenuItem("Hell");
    difficulty.add(hell);

    JMenuBar menuBar = new JMenuBar();
    this.setJMenuBar(menuBar);
    menuBar.add(option);
    menuBar.add(mode);
    menuBar.add(difficulty);

    MenuItemListener menuListener = new MenuItemListener();
    newGameSameSetup.addActionListener(menuListener);
    newGameRandom.addActionListener(menuListener);
    exitGame.addActionListener(menuListener);
    easy.addActionListener(menuListener);
    medium.addActionListener(menuListener);
    hard.addActionListener(menuListener);
    hell.addActionListener(menuListener);
    onePlayer.addActionListener(menuListener);
    twoPlayer.addActionListener(menuListener);
  }

  /**
   * This method instantiates the GraphicPanel which contains the Button Panel and
   * Maze Panel.
   */
  private void setUpPanels() {
    graphicPanel = new GraphicPanel(game);
    game.addObserver(graphicPanel);
    setViewTo(graphicPanel);
  }

  /**
   * This Method sets up the first game. All variables will be modified each time
   * game reset with different settings. Hunter starts at index 11 for the initial
   * GUI.
   */
  private void setUpInitialGame() {
    int rows = 10;
    int columns = 10;
    int numOfRemainingWalls = 5;
    int numOfPits = 3;
    int numOfBats = 3;
    int startingIdx = 15;
    int startingNumArrow = 5;
    boolean isTwoPlayer = true;
    game = new Game(rows, columns, numOfRemainingWalls, numOfPits, numOfBats, startingIdx,
        startingNumArrow, isTwoPlayer);
  }

  /**
   * This Class implements the ActionListener from JMenu of this GameView.
   * 
   * @param e ActionEvent Performed by JMenu
   */
  private class MenuItemListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      String text = ((JMenuItem) e.getSource()).getText();
      if (text.equals("Same Setup")) {
        // instantiate game with same settings
        game.start();
        // reprint view
        setViewTo(graphicPanel);
      }

      if (text.equals("Random Setup")) {
        // randomize the Game random Seed
        game.setSeedRandomGame(new Random().nextLong());
        game.start();
        // reprint view
        setViewTo(graphicPanel);
      }

      if (text.equals("Quit")) {
        System.exit(0);
      }

      if (text.equals("1 Player")) {
        game.setTwoPlayers(false);
        game.start();
        System.out.println(game.streamOut());
        // reprint view
        setViewTo(graphicPanel);
      }

      if (text.equals("2 Player")) {
        game.setTwoPlayers(true);
        game.start();
        System.out.println(game.streamOut());
        // reprint view
        setViewTo(graphicPanel);
      }

      if (text.equals("Easy")) {
        game.setRows(10);
        game.setColumns(10);
        game.setNumOfRemainingWalls(1);
        game.setNumOfBats(1);
        game.setNumOfPits(1);
        game.getHunter().setNumberOfArrows(20);
        game.start();
        // reprint view
        setViewTo(graphicPanel);
        JOptionPane.showMessageDialog(null,
            "Easy Game:\nthis Maze has 10 rows, 10 columns, "
                + "1 remaining wall, 1 supper bat, and "
                + "1 pit\nHunter is placed at Cell #15 with 20 arrows"
                + "\n\nEnjoy your hunt!",
            "Easy Game Generated", JOptionPane.INFORMATION_MESSAGE);
        System.out.println(game.streamOut());
      }

      if (text.equals("Medium")) {
        game.setRows(10);
        game.setColumns(10);
        game.setNumOfRemainingWalls(10);
        game.setNumOfBats(3);
        game.setNumOfPits(3);
        game.getHunter().setNumberOfArrows(10);
        game.start();
        // reprint view
        setViewTo(graphicPanel);
        JOptionPane.showMessageDialog(null,
            "Medium Game:\nthis Maze has 10 rows, 10 columns, "
                + "10 remaining wall, 3 supper bat, and "
                + "3 pit\nHunter is placed at Cell #15 with 10 arrows"
                + "\n\nEnjoy your hunt!",
            "Medium Game Generated", JOptionPane.INFORMATION_MESSAGE);
        System.out.println(game.streamOut());
      }

      if (text.equals("Hard")) {
        game.setRows(8);
        game.setColumns(8);
        game.setNumOfRemainingWalls(5);
        game.setNumOfBats(3);
        game.setNumOfPits(3);
        game.getHunter().setNumberOfArrows(5);
        game.start();
        // reprint view
        setViewTo(graphicPanel);
        JOptionPane.showMessageDialog(null,
            "Hard Game:\nthis Maze has 8 rows, 8 columns, "
                + "5 remaining wall, 3 supper bat, and "
                + "3 pit\nHunter is placed at Cell #15 with 5 arrows"
                + "\n\nEnjoy your hunt!",
            "Hard Game Generated", JOptionPane.INFORMATION_MESSAGE);
        System.out.println(game.streamOut());
      }

      if (text.equals("Hell")) {
        game.setRows(5);
        game.setColumns(5);
        game.setNumOfRemainingWalls(5);
        game.setNumOfBats(2);
        game.setNumOfPits(2);
        game.getHunter().setNumberOfArrows(2);
        game.start();
        setViewTo(graphicPanel);
        JOptionPane.showMessageDialog(null,
            "Hell Game:\nthis Maze has 5 rows, 5 columns, "
                + "5 remaining wall, 2 supper bat, and "
                + "2 pit\nHunter is placed at Cell #15 with 2 arrows"
                + "\n\nTrust your gut!",
            "Hell Game Generated", JOptionPane.INFORMATION_MESSAGE);
        System.out.println(game.streamOut());
      }
    }
  }

  /**
   * This is called when the view needs to switch different JPanel with repaint
   * and validate function.
   * 
   * @param newView The JPanel to Switch.
   */
  private void setViewTo(JPanel newView) {
    if (currentPanel != null) {
      remove(currentPanel);
    }
    currentPanel = newView;
    add(currentPanel);
    currentPanel.repaint();
    validate();
  }

  /**
   * This is called when the view detects that a key has been typed. Enable Shoot
   * Keys from keyboard in this method, and updating with Controller.
   * 
   * @param e KeyEvent
   */
  @Override
  public void keyTyped(KeyEvent e) {
    switch (e.getKeyChar()) {
      // 'W' Key - Shoot NORTH
      case 'w':
        game.streamIn("S");
        game.streamIn("1");
        game.streamIn(Integer.toString(game.shootToIndex("NORTH")));
        System.out.println(game.streamOut());
        break;
      // 'S' Key - Shoot SOUTH
      case 's':
        game.streamIn("S");
        game.streamIn("1");
        game.streamIn(Integer.toString(game.shootToIndex("SOUTH")));
        System.out.println(game.streamOut());
        break;
  
      // 'D' Key - Shoot EAST
      case 'd':
        game.streamIn("S");
        game.streamIn("1");
        game.streamIn(Integer.toString(game.shootToIndex("EAST")));
        System.out.println(game.streamOut());
        break;
  
      // 'A' Key - Shoot WEST
      case 'a':
        game.streamIn("S");
        game.streamIn("1");
        game.streamIn(Integer.toString(game.shootToIndex("WEST")));
        System.out.println(game.streamOut());
        break;
      default:
        System.out.println("");
    }
  }

  /**
   * This is called when the view detects that a key has been pressed. Enable Move
   * Keys from keyboard in this method, and updating with Controller.
   * 
   * @param e KeyEvent
   */
  @Override
  public void keyPressed(KeyEvent e) {
    // Arrow Key - Move WEST
    if (e.getKeyCode() == 37) {
      game.streamIn("M");
      game.streamIn(Integer.toString(game.moveToIndex("WEST")));
      System.out.println(game.streamOut());
    }
    // Arrow Key - Move UP
    if (e.getKeyCode() == 38) {
      game.streamIn("M");
      game.streamIn(Integer.toString(game.moveToIndex("NORTH")));
      System.out.println(game.streamOut());
    }
    // Arrow Key RIGHT - Move EAST
    if (e.getKeyCode() == 39) {
      game.streamIn("M");
      game.streamIn(Integer.toString(game.moveToIndex("EAST")));
      System.out.println(game.streamOut());

    }
    // Arrow Key DOWN - Move SOUTH
    if (e.getKeyCode() == 40) {
      game.streamIn("M");
      game.streamIn(Integer.toString(game.moveToIndex("SOUTH")));
      System.out.println(game.streamOut());
    }
  }

  /**
   * This is called when the view detects that a key has been released. Enable
   * Shortcut Keys in this methods.
   * 
   * @param e KeyEvent
   */
  @Override
  public void keyReleased(KeyEvent e) {
    if (e.getKeyChar() == 'q') {
      System.exit(0);
    }
    // ShortCut G: God Mode
    if (e.getKeyChar() == 'g') {
      game.setGodMode(true);
      setViewTo(graphicPanel);
    }
    // ShortCut X: Merry Xmas Mode
    if (e.getKeyChar() == 'x') {
      game.setSpecialEdition(true);
      setViewTo(graphicPanel);
    }
  }
}
