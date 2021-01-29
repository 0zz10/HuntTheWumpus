package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.Game;

/**
 * This class represents a control panel containing all the buttons for the Hunt
 * The Wumpus Game. Move Buttons and Shoot Buttons are adding Action Listener
 * using lambda function. This control panel is also allows to disable buttons
 * when game ends.
 * 
 * @author Daniel Zhou
 */
public class ButtonPanel extends JPanel {
  private JPanel movePanel;
  private JPanel shootPanel;
  private JButton moveHunter;
  private JButton shootArrow;
  private JButton moveNorth;
  private JButton moveSouth;
  private JButton moveWest;
  private JButton moveEast;
  private JButton shootNorth;
  private JButton shootSouth;
  private JButton shootWest;
  private JButton shootEast;
  private Game game;
  
  /**
   * Construct this ButtonPanel and placed to the top left.
   */
  public ButtonPanel(Game game) {
    this.game = game;
    this.movePanel = new JPanel();
    this.shootPanel = new JPanel();
    // set moveButtons textView as String
    this.moveHunter = new JButton("Move");
    this.moveNorth = new JButton();
    this.moveNorth.setIcon(new ImageIcon("res/images/moveUp.png"));
    this.moveSouth = new JButton();
    this.moveSouth.setIcon(new ImageIcon("res/images/moveDown.png"));
    this.moveWest = new JButton();
    this.moveWest.setIcon(new ImageIcon("res/images/moveLeft.png"));
    this.moveEast = new JButton();
    this.moveEast.setIcon(new ImageIcon("res/images/moveRight.png"));

    // set moveButtons unFocusable in order to interact with keyboard lister
    this.moveHunter.setFocusable(false);
    this.moveNorth.setFocusable(false);
    this.moveSouth.setFocusable(false);
    this.moveWest.setFocusable(false);
    this.moveEast.setFocusable(false);

    // set shootButtons textView as String
    this.shootArrow = new JButton("Shoot");
    this.shootNorth = new JButton();
    this.shootNorth.setIcon(new ImageIcon("res/images/arrowUp.png"));
    this.shootSouth = new JButton();
    this.shootSouth.setIcon(new ImageIcon("res/images/arrowDown.png"));
    this.shootWest = new JButton();
    this.shootWest.setIcon(new ImageIcon("res/images/arrowLeft.png"));
    this.shootEast = new JButton();
    this.shootEast.setIcon(new ImageIcon("res/images/arrowRight.png"));

    // set shootButtons unFocusable in order to interact with keyboard lister
    this.shootArrow.setFocusable(false);
    this.shootNorth.setFocusable(false);
    this.shootSouth.setFocusable(false);
    this.shootWest.setFocusable(false);
    this.shootEast.setFocusable(false);

    setupButtonLayout();

    this.setSize(320, 640);

    this.setLocation(0, 0);
    // moveHolder and shootHolder 2 panels vertical
    this.setLayout(new GridLayout(2, 0));
    this.add(movePanel);
    this.add(shootPanel);
  }
  
  /**
   * This methods sets up the layout of 6 x 3 buttons with GridLayout(2, 0) for
   * movePanel/ shootPanel, and GridLayout(3, 3) buttons for each Panel.
   * Also implement ActionListener reversely to the order of click.
   */
  private void setupButtonLayout() {

    // *------Move Button ------*
    movePanel.setPreferredSize(new Dimension(320, 320));
    // 3 x 3 grid with only 4 enabled buttons
    movePanel.setLayout(new GridLayout(3, 3));
    movePanel.setBackground(Color.LIGHT_GRAY);
    movePanel.add(new EmptyButton());
    movePanel.add(moveNorth);
    movePanel.add(new EmptyButton());
    movePanel.add(moveWest);
    movePanel.add(moveHunter);
    movePanel.add(moveEast);
    movePanel.add(new EmptyButton());
    movePanel.add(moveSouth);
    movePanel.add(new EmptyButton());
    moveHunter.addActionListener(e -> JOptionPane.showMessageDialog(null,
        "Click a direction to MOVE.\nOr Press any Arrow Key for direction to MOVE"));

    System.out.println(game.streamOut());
    moveNorth.addActionListener(e -> game.streamIn(Integer.toString(game.moveToIndex("NORTH"))));
    moveNorth.addActionListener(e -> game.streamIn("M"));

    System.out.println(game.streamOut());
    moveSouth.addActionListener(e -> game.streamIn(Integer.toString(game.moveToIndex("SOUTH"))));
    moveSouth.addActionListener(e -> game.streamIn("M"));

    System.out.println(game.streamOut());
    moveWest.addActionListener(e -> game.streamIn(Integer.toString(game.moveToIndex("WEST"))));
    moveWest.addActionListener(e -> game.streamIn("M"));

    System.out.println(game.streamOut());
    moveEast.addActionListener(e -> game.streamIn(Integer.toString(game.moveToIndex("EAST"))));
    moveEast.addActionListener(e -> game.streamIn("M"));

    // *------Shoot Button ------*
    shootPanel.setPreferredSize(new Dimension(320, 320));
    // 3 x 3 grid with only 4 enabled buttons
    shootPanel.setLayout(new GridLayout(3, 3));
    shootPanel.setBackground(Color.LIGHT_GRAY);
    shootPanel.add(new EmptyButton());
    shootPanel.add(shootNorth);
    shootPanel.add(new EmptyButton());
    shootPanel.add(shootWest);
    shootPanel.add(shootArrow);
    shootPanel.add(shootEast);
    shootPanel.add(new EmptyButton());
    shootPanel.add(shootSouth);
    shootPanel.add(new EmptyButton());
    shootArrow.addActionListener(e -> JOptionPane.showMessageDialog(null,
        "Click a direction to SHOOT.\nOr Press key W, S, A, D for direction to SHOOT"));

    // using addActionListener is in reversed order to execute
    shootNorth.addActionListener(e -> System.out.println(game.streamOut()));
    shootNorth.addActionListener(e -> game.streamIn(Integer.toString(game.shootToIndex("NORTH"))));
    shootNorth.addActionListener(e -> game.streamIn("1"));
    shootNorth.addActionListener(e -> game.streamIn("S"));

    shootWest.addActionListener(e -> System.out.println(game.streamOut()));
    shootWest.addActionListener(e -> game.streamIn(Integer.toString(game.shootToIndex("WEST"))));
    shootWest.addActionListener(e -> game.streamIn("1"));
    shootWest.addActionListener(e -> game.streamIn("S"));

    shootEast.addActionListener(e -> System.out.println(game.streamOut()));
    shootEast.addActionListener(e -> game.streamIn(Integer.toString(game.shootToIndex("EAST"))));
    shootEast.addActionListener(e -> game.streamIn("1"));
    shootEast.addActionListener(e -> game.streamIn("S"));

    shootSouth.addActionListener(e -> System.out.println(game.streamOut()));
    shootSouth.addActionListener(e -> game.streamIn(Integer.toString(game.shootToIndex("SOUTH"))));
    shootSouth.addActionListener(e -> game.streamIn("1"));
    shootSouth.addActionListener(e -> game.streamIn("S"));
  }

  /**
   * This method updates the button states whether it is enabled.
   */
  public void enableButtons(boolean state) {
    moveHunter.setEnabled(state);
    moveNorth.setEnabled(state);
    moveSouth.setEnabled(state);
    moveWest.setEnabled(state);
    moveEast.setEnabled(state);

    shootArrow.setEnabled(state);
    shootNorth.setEnabled(state);
    shootSouth.setEnabled(state);
    shootWest.setEnabled(state);
    shootEast.setEnabled(state);
  }

}