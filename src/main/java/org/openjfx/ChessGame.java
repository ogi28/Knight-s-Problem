package org.openjfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class ChessGame extends Application {

  @Override
  public void start(Stage primaryStage) {
    // Create a GridPane

    Board board = new Board();
    GridPane pane = board.getPane();

    // Create a scene and place it in the stage
    Scene scene = new Scene(pane);
    primaryStage.setTitle("Bonus Assignment");
    primaryStage.setScene(scene); // Place in scene in the stage
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
