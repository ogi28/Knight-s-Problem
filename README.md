# Knight-s-Problem
![](https://github.com/ogi28/Knight-s-Problem/blob/main/Screenshots/Gameplay.gif)

## Description
This project aims to provide a visually engaging and interactive solution to the classic [Knight's tour problem](https://en.wikipedia.org/wiki/Knight%27s_tour) using a JavaFX graphical user interface (GUI). This project implements various heuristics and algorithms to efficiently solve the problem and find the most optimal path for the Knight to reach its destination on a chessboard. This project not only provides a fun and game-like way to solve the problem but also demonstrate a practical application of heuristics in problem-solving. The project also gives an opportunity to explore the potential of JavaFX in developing interactive and graphical user interfaces.

## Requirements
Maven

## Running it
Clone the project, then in your favorite terminal copy paste:
```
mvn clean javafx:run
```

## Gameplay
Click anywhere on the board to begin.
Green tile means the Knight can move to the tile, however that tile is not the optimal play.
Blue tile means the Knight can move to the tile, and that is the best possible move(s).
Red tile means the Knight has already been to the tile, and can not move there anymore.
