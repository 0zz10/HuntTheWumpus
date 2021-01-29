package view;

import java.awt.Color;

import javax.swing.JButton;

/**
 * This class represents an Empty Button which can not be clicked during game,
 * also the color is Light Gray.
 * 
 * @author Daniel Zhou
 */
public class EmptyButton extends JButton {
  
  /**
   * Instantiate this EmptyButton.
   */
  public EmptyButton() {
    this.setText("");
    this.setBackground(Color.LIGHT_GRAY);
    this.setFocusable(false);
    this.setEnabled(false);
  }
}
