package org.openjfx;

public class Knight {
  private int currentRow;

  public int getCurrentRow() {
    return currentRow;
  }

  private int currentColumn;

  public int getCurrentColumn() {
    return currentColumn;
  }

  public Knight() {
    this.currentRow = 0;
    this.currentColumn = 0;
  }

  // move the knight to the next position
  public void move(int nextRow, int nextColumn) {
    this.currentRow = nextRow;
    this.currentColumn = nextColumn;
  }
}
