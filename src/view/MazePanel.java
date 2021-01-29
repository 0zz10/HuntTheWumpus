package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import controller.Game;
import model.Cell;

/**
 * This class represents a maze panel and keeps updating as any changes in Maze
 * setting or Hunter/Arrow position. All the visited Element will be shown and
 * also the graph of each cell.
 * 
 * @author Daniel Zhou
 */
public class MazePanel extends JPanel {
  private Game game;
  private Cell[][] gameMap;


  /**
   * Construct this MazePanel, is resizable according to the rows and columns.
   * 
   * @param currentGame if game restart the Panel will be updated
   */
  public MazePanel(Game currentGame) {
    this.game = currentGame;
    this.gameMap = game.getGameMap();
    this.setSize(64 * game.getRows(), 64 * game.getColumns());
    // right side of the GraphicView
    this.setLocation(320, 0);
    this.setBackground(Color.WHITE);
  }

  /**
   * Import the Image object from designated file location.
   * 
   * @param imageLocation the file location.
   * @return the image loaded internally to the getterImage method.
   */
  private Image importImage(String imageLocation) {
    try {
      return ImageIO.read(new File(imageLocation));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Get the Ground Image from designated file location.
   * 
   * @return the Image object can read by MazePanel
   */
  public Image getGroundImage(Cell cell) {
    return importImage("res/images/emptyRoom.png");
  }

  /**
   * Get the Cell Image from designated file location. Check different
   * orientations according to the x coordinate and y coordinate of connected
   * neighbor cells.
   * 
   * @return the Image object can read by MazePanel
   */
  public Image getCellImage(Cell cell) {
    // get sumX and sumY are the indicator to show the directions
    int sumX = 0;
    int sumY = 0;

    for (Cell neighouberCell : cell.getNeighouberCellsSet()) {
      sumX = sumX + neighouberCell.getX();
      sumY = sumY + neighouberCell.getY();
    }

    if (cell.getNeighouberCellsSet().size() == 0) {
      return importImage("res/images/roombase-0.png");
    }
    // one-way cave in four directions
    else if (cell.getNeighouberCellsSet().size() == 1) {
      if ((sumX == cell.getX()) && (sumY < cell.getY())) { // west
        return importImage("res/images/roombase-1-W.png");
      } else if ((sumX < cell.getX()) && (sumY == cell.getY())) { // north
        return importImage("res/images/roombase-1-N.png");
      } else if ((sumX > cell.getX()) && (sumY == cell.getY())) { // south
        return importImage("res/images/roombase-1-S.png");
      } else {
        return importImage("res/images/roombase-1-E.png"); // east
      }
    }
    // two-way tunnel in four directions
    else if (cell.getNeighouberCellsSet().size() == 2) {
      boolean sameX = true;
      boolean sameY = true;
      for (Cell neighouberCell : cell.getNeighouberCellsSet()) {
        // boolean is used for find status of straight hallway
        sameX = neighouberCell.getX() == cell.getX() ? true : false;
        sameY = neighouberCell.getY() == cell.getY() ? true : false;
      }
      // Straight tunnel
      if ((sumX == cell.getX() * 2) && (sumY == cell.getY() * 2)) {
        if (sameX) {
          return importImage("res/images/hallway-WE.png");// Straight tunnel WE
        } else if (sameY) {
          return importImage("res/images/hallway-NS.png");// Straight tunnel NS
        } else {
          return null;
        }
      } else if ((sumX > cell.getX() * 2) && (sumY < cell.getY() * 2)) { // crooked tunnel SW
        return importImage("res/images/hallway-SW.png");
      } else if ((sumX > cell.getX() * 2) && (sumY > cell.getY() * 2)) { // crooked tunnel SE
        return importImage("res/images/hallway-SE.png");
      } else if ((sumX < cell.getX() * 2) && (sumY < cell.getY() * 2)) { // crooked tunnel NW
        return importImage("res/images/hallway-NW.png");
      } else if ((sumX < cell.getX() * 2) && (sumY > cell.getY() * 2)) { // crooked tunnel NE
        return importImage("res/images/hallway-NE.png");
      } else {
        return null;
      }
    }
    // three-way cave in four directions
    else if (cell.getNeighouberCellsSet().size() == 3) {
      if ((sumX == cell.getX() * 3) && (sumY > cell.getY() * 3)) { // west missing
        return importImage("res/images/roombase-3-W.png");
      } else if ((sumX > cell.getX() * 3) && (sumY == cell.getY() * 3)) { // north missing
        return importImage("res/images/roombase-3-N.png");
      } else if ((sumX == cell.getX() * 3) && (sumY < cell.getY() * 3)) { // east missing
        return importImage("res/images/roombase-3-E.png");
      } else {
        return importImage("res/images/roombase-3-S.png"); // south missing
      }
    }
    // four-way cave in four directions
    else if (cell.getNeighouberCellsSet().size() == 4) {
      return importImage("res/images/roombase-4.png");
    }
    return null;
  }

  /**
   * Get the Element Image from designated file location. Check different flags of
   * of current cell has and reprint the ElementImage to MazePanel.
   * 
   * @return the Image object can read by MazePanel
   */
  public Image getElementImage(Cell cell) {
    if (cell.isWumpus()) {
      return importImage("res/images/wumpus.png");
    }
    if (cell.isPit()) {
      return importImage("res/images/slime-pit.png");
    }

    if (cell.isBlood()) {
      return importImage("res/images/wumpus-nearby.png");
    }
    if (cell.isDraft()) {
      return importImage("res/images/slime-pit-nearby.png");
    }
    if (cell.isBat()) {
      return importImage("res/images/superbat.png");
    } else {
      return null;
    }
  }

  /**
   * Get the Special Edition Image from designated file location. Check different
   * flags of current cell has and reprint the SpecialImage to MazePanel.
   * 
   * @return the Image object from file.
   */
  public Image getSpecialImage(Cell cell) {
    if (cell.isWumpus()) {
      return importImage("res/images/santa.png");
    }
    if (cell.isPit()) {
      return importImage("res/images/tree.png");
    }

    if (cell.isBlood()) {
      return importImage("res/images/present.png");
    }
    if (cell.isDraft()) {
      return importImage("res/images/present.png");
    }
    if (cell.isBat()) {
      return importImage("res/images/sock.png");
    } else {
      return null;
    }
  }

  /**
   * This Methods is override to be updated images as the game proceeds. Get the
   * Hunter/Arrow/Reindeer Images from designated file location. Check flags of
   * each in the gameMaze and draw the ElementImage to MazePanel as 2D graph.
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Point curPosition;
    Cell cell;

    Image hunterOne = new ImageIcon("res/images/hunter.png").getImage();
    Image hunterTwo = new ImageIcon("res/images/hunter2.png").getImage();
    Image arrowOne = new ImageIcon("res/images/target.png").getImage();
    Image arrowTwo = new ImageIcon("res/images/target2.png").getImage();
    Image reindeerOne = new ImageIcon("res/images/reindeer-1.png").getImage();
    Image reindeerTwo = new ImageIcon("res/images/reindeer-2.png").getImage();

    Graphics2D g2d = (Graphics2D) g;
    for (int row = 0; row < game.getRows(); row++) {
      for (int col = 0; col < game.getColumns(); col++) {

        curPosition = new Point(row, col);
        cell = gameMap[curPosition.x][curPosition.y];

        g2d.drawImage(getGroundImage(cell), col * 64, row * 64, this);

        if (game.isGodMode()) {
          g2d.drawImage(getCellImage(cell), col * 64, row * 64, this);

          if (game.isSpecialEdition()) {
            g2d.drawImage(getSpecialImage(cell), col * 64, row * 64, this);
          } else {
            g2d.drawImage(getElementImage(cell), col * 64, row * 64, this);
          }
        }

        if (curPosition.equals(game.getHunterOne().getArrowPos())) {
          g2d.drawImage(arrowOne, col * 64, row * 64, this);
        }
        if (game.isTwoPlayers() && curPosition.equals(game.getHunterTwo().getArrowPos())) {
          g2d.drawImage(arrowTwo, col * 64, row * 64, this);
        }

        if (cell.isVisited()) {
          g2d.drawImage(getCellImage(cell), col * 64, row * 64, this);
          if (game.isSpecialEdition()) {
            g2d.drawImage(getSpecialImage(cell), col * 64, row * 64, this);
          } else {
            g2d.drawImage(getElementImage(cell), col * 64, row * 64, this);
          }
          if (curPosition.equals(game.getHunterOne().getCurrentPos())) {
            g2d.drawImage(hunterOne, col * 64, row * 64, this);
            if (game.isSpecialEdition()) {
              g2d.drawImage(reindeerOne, col * 64, row * 64, this);
            }
          }
          if (game.isTwoPlayers() && curPosition.equals(game.getHunterTwo().getCurrentPos())) {
            g2d.drawImage(hunterTwo, col * 64, row * 64, this);
            if (game.isSpecialEdition()) {
              g2d.drawImage(reindeerTwo, col * 64, row * 64, this);
            }
          }
        }
      }
    }
  }
}
