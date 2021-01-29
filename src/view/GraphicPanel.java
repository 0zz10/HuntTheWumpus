package view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.Game;

/**
 * This class represents a Graphic Panel which combines ButtonPanel and
 * MazePanel together. this Graphic Panel is updated in GameView with option to
 * reset.
 * 
 * @author Daniel Zhou
 */
public class GraphicPanel extends JPanel implements Observer {
  private Game game;
  private ButtonPanel buttonPanel;
  private MazePanel mazePanel;

  /**
   * Instantiate this GraphicPanel.
   * @param currentGame if game restart the Panel will be updated
   */
  public GraphicPanel(Game currentGame) {
    this.game = currentGame;
    this.setPreferredSize(null);
    buttonPanel = new ButtonPanel(game);
    this.add(buttonPanel);
    mazePanel = new MazePanel(game);
    this.add(mazePanel);
    mazePanel.repaint();
    this.setLayout(null);
  }

  /**
   * This method provides access to reprint the MazePanel when GraphicPanel is
   * updated.
   */
  private void updateMazePanel() {
    this.remove(mazePanel);
    mazePanel = new MazePanel(game);
    this.add(mazePanel);
    mazePanel.repaint();
  }

  /**
   * This method overrides updating GraphicPanel with status of the game. If Game
   * ends, disables all Button from Button Panel, revealing all map information.
   * Also prompts new dialog window to provide reason that game ends.
   */
  @Override
  public void update(Observable o, Object arg) {
    updateMazePanel();
    buttonPanel.enableButtons(true);
    if (game.isEnd()) {
      game.getRoomMaze().revealGameMap();
      buttonPanel.enableButtons(false);
      if (game.isSpecialEdition()) {
        JOptionPane.showMessageDialog(null,
            "Thanks for Watching\n\nWith best wishes for a Merry Christmas"
            + "\nand a Happy New Year!\n",
            "Merry Christmas", JOptionPane.INFORMATION_MESSAGE);
      } else {
        if (game.isEaten()) {
          JOptionPane.showMessageDialog(null,
              "Chomp, chomp, chomp, thanks for feeding the Wumpus!\nBetter luck next time\n",
              "Game Over", JOptionPane.INFORMATION_MESSAGE);
        } else if (game.isFallen()) {
          JOptionPane.showMessageDialog(null,
              "The cave contains a bottomless pit!\nYou fall screaming into the void.\n",
              "Game Over", JOptionPane.INFORMATION_MESSAGE);
        } else if (game.isOutOfArrows()) {
          JOptionPane.showMessageDialog(null, "You used up all your arrows!\n", "Game Over",
              JOptionPane.INFORMATION_MESSAGE);
        } else if (game.isGameWon()) {
          JOptionPane.showMessageDialog(null,
              "Hee hee hee, you got the wumpus!\nNext time you won't be so lucky\n", "Game Win",
              JOptionPane.INFORMATION_MESSAGE);
        }
      }
    }
  }
}
