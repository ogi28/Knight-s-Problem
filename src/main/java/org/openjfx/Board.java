package org.openjfx;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Board {
  private final Square[][] board = new Square[8][8];
  private final GridPane pane = new GridPane();
  private final Knight knight = new Knight();
  private final ArrayList<int[]> knightMoves = new ArrayList<>();
  private int knightCounter = 0; // The no of times the knight has moved

  public Board() {
    Color color;
    for (int row = 0; row < 8; row++) {
      for (int column = 0; column < 8; column++) {
        if ((row + column) % 2 == 0) {
          color = Color.BLACK;
        } else {
          color = Color.GRAY;
        }

        final int finalRow = row;
        final int finalColumn = column;

        this.board[row][column] = new Square(color, e -> {
          if (knightMoves.size() == 0 || isMovable(getKnightCurrentPosition(), new int[] { finalRow, finalColumn })) {
            // reset the board color except for where we've been.
            resetBoardColor();
            ArrayList<int[]> possibleMoves = getPossibleMoves(finalRow, finalColumn);
            // TODO: exit the game here.
            if (possibleMoves.size() == 0) {
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Game Over");
              alert.setHeaderText("Game Over");
              alert.setContentText("Game over, quitting now!");
              alert.showAndWait();
              Platform.exit();
              System.exit(0);

            }
            highlightPossibleMoves(possibleMoves); // highlight all possible moves
            if (this.knightCounter != 0) {
              removeKnightFromPrevious(getPreviousPosition()[0], getPreviousPosition()[1]);
            }
            addKnightToCurrent(finalRow, finalColumn); // add the knight to the current position on the board
            highlightHeuristicMoves(getMoveScores()); // highlight heuristic moves after the knight is moved
          } else {
            invalidMove(); // show alert box
          }
        });
        this.pane.add(this.board[row][column].getRect(), column, row);
      }
    }

  }

  private void addKnightToCurrent(int row, int column) {
    this.knightMoves.add(new int[] { row, column });
    this.knight.move(row, column);
    this.knightCounter++;
    this.pane.add(new Label("" + this.knightCounter), column, row);
    this.board[row][column].setImage(new Image("file:src/resources/knight2.png"));
  }

  private void removeKnightFromPrevious(int row, int column) {
    this.board[row][column].highlight(Color.RED);
  }

  public GridPane getPane() {
    return pane;
  }

  /**
   * Display error message for invalid move
   */
  private void invalidMove() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Invalid Move");
    alert.setHeaderText("Invalid Move");
    alert.setContentText("You can't move there!");
    alert.showAndWait();
  }

  /**
   * Get all possible moves from the current position of the knight
   * @param row
   * @param column
   */
  private ArrayList<int[]> getPossibleMoves(int row, int column) {
    ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
    for (int i = column - 2; i <= column + 2; i++) {
      for (int j = row - 2; j <= row + 2; j++) {
        if (isMovable(new int[] { row, column }, new int[] { j, i })) {
          possibleMoves.add(new int[] { j, i });
        }
      }
    }
    return possibleMoves;
  }

  /**
   * Highlight all possible moves with green
   * @param possibleMoves
   */
  private void highlightPossibleMoves(ArrayList<int[]> possibleMoves) {
    for (int[] position : possibleMoves) {
      this.board[position[0]][position[1]].highlight(Color.GREEN);
    }
  }

  /**
   * Remove all highlight colors and knight image from previous move
   */
  private void resetBoardColor() {
    for (int row = 0; row < 8; row++) {
      for (int column = 0; column < 8; column++) {
        if (!includesPosition(new int[] { row, column })) {
          this.board[row][column].revertColor();
        }
      }
    }
  }

  // check if the next position is valid
  // check if the next position is already visited
  // check if the next position is reachable from the current position
  // return true if the next position is valid
  // return false if the next position is invalid
  // public boolean isMovable(int nextRow, int nextColumn) {
  private boolean isMovable(int[] currentPosition, int[] nextPosition) {
    // check if the next position is in range of the board
    // or
    // check if the next position is reachable from the current position
    if (!isInBoardRange(nextPosition[0], nextPosition[1]) || includesPosition(nextPosition)) {
      return false;
    }

    if (currentPosition[0] == nextPosition[0] || currentPosition[1] == nextPosition[1]) {
      return false;
    }

    if (Math.abs(currentPosition[0] - nextPosition[0])
        + Math.abs(currentPosition[1] - nextPosition[1]) == 3) {
      return true;
    }

    return false;
  }

  /**
   * Check whether the knight has been there
   * @param array
   * @return
   */
  private boolean includesPosition(int[] array) {
    for (int[] move : knightMoves) {
      if (move[0] == array[0] && move[1] == array[1]) {
        return true;
      }
    }
    return false;
  }

  /**
   * Check if the next move is in bound of the board
   * @param row
   * @param column
   * @return
   */
  private boolean isInBoardRange(int row, int column) {
    return row >= 0 && row <= 7 && column >= 0 && column <= 7;
  }

  /**
   * Return previous position of the knight
   * @return
   */
  private int[] getPreviousPosition() {
    return knightMoves.get(knightMoves.size() - 1);
  }

  /**
   * Return move score for each possible move from the knight's current position
   * @return
   */
  private int[][] getMoveScores() {
    int[][] moveScores = new int[8][8];

    for (int row = 0; row <= 7; row++) {
      for (int column = 0; column <= 7; column++) {
        ArrayList<int[]> possibleMoves = getPossibleMoves(row, column);
        if (isMovable(new int[] { this.knight.getCurrentRow(), this.knight.getCurrentColumn() },
            new int[] { row, column })) {
          moveScores[row][column] = (possibleMoves.size());
        } else {
          moveScores[row][column] = 9999;
        }
      }
    }

    return moveScores;
  }

  /**
   * Highlight heuristic moves with blue
   * @param moveScores
   */
  private void highlightHeuristicMoves(int[][] moveScores) {
    int min = Integer.MAX_VALUE;
    int[] knightPosition = getKnightCurrentPosition();
    for (int row = 0; row <= 7; row++) {
      for (int column = 0; column <= 7; column++) {
        if (moveScores[row][column] < min && isMovable(knightPosition, new int[] { row, column })) {
          min = moveScores[row][column];
        }
      }
    }

    for (int row = 0; row <= 7; row++) {
      for (int column = 0; column <= 7; column++) {
        if (moveScores[row][column] == min && isMovable(knightPosition, new int[] { row, column })) {
          this.board[row][column].highlight(Color.BLUE);
        }
      }
    }
  }

  /**
   * Return knight's current position
   * @return
   */
  private int[] getKnightCurrentPosition() {
    return new int[] { this.knight.getCurrentRow(), this.knight.getCurrentColumn() };
  }
}
