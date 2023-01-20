/**
 * Student 1: Ogulcan Tayhan - 104
 * Student 2: Nghi Phuong Huynh Pham - 101412203
 */
package org.openjfx;

import javafx.scene.shape.Rectangle;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

/**
 * Represents each square move on the board
 */
public class Square {
  private Rectangle rect;

  private Color initialColor;
  private final double squareSide = 100;

  /**
   * Constructor for the Square class.
   * 
   * @param color   The color of the square.
   * @param handler The function to be called when a square is clicked.
   */
  /**
   * Initialize each square as a single move.
   * Add even listener to every move
   * @param color
   * @param handler
   */
  public Square(Color color, EventHandler<MouseEvent> handler) {
    this.rect = new Rectangle(squareSide, squareSide);
    this.initialColor = color;
    this.rect.setFill(color);
    this.rect.setOnMouseClicked(handler);
  }

  /**
   * Highlight all possible moves
   * @param color
   */
  public void highlight(Color color) {
    this.rect.setFill(color);
  }

  /**
   * Set the color for every other move on a row with a different color
   */
  public void revertColor() {
    this.rect.setFill(this.initialColor);
  }

  /**
   * Set image for the current move
   * @param image
   */
  public void setImage(Image image) {
    this.rect.setFill(new javafx.scene.paint.ImagePattern(image));
  }

  public Rectangle getRect() {
    return rect;
  }
}
