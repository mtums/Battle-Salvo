package cs3500.pa03.model;

import static cs3500.pa03.model.Ship.checkIfSunk;

import cs3500.pa03.view.ConsoleView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a BattleSalvio Board
 */
public class Board {
  public int height;
  public int width;
  public List<Ship> ships;
  public Coord[][] cells;

  /**
   * Instantiates a Board object with all of its fields
   * @param height the height of the board
   * @param width the width of the baord
   * @param ships the Ships on this board
   * @param cells the 2D array that represents the grid of this board
   */
  public Board(int height, int width, List<Ship> ships, Coord[][] cells) {
    this.height = height;
    this.width = width;
    this.ships = ships;
    this.cells = new Coord[height][width];
    initializeCells();
  }

  /**
   * Instantiates an empty board
   * @param height the height of the grid
   * @param width the width of the grid
   */
  //Constructor for Board without any ships placed yet. An empty board of size height*width
  public Board(int height, int width) {
    this.height = height;
    this.width = width;
    this.ships = new ArrayList<>();
    this.cells = new Coord[height][width];
    initializeCells();
  }

  //Add more constructors as needed

  /**
   * Initializes an empty 2D array of cells with the instantiated board's height and width
   */
  void initializeCells() {
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        cells[row][col] = new Coord(row, col);
      }
    }
  }

  /**
   * Checks if a cell has been hit (meaning it has a ship and has received a shot)
   * @param row row index of the 2D array
   * @param col column index of the 2D array
   * @return true if this cell has been hit
   */
  public boolean isCellHit(int row, int col) {
    Coord cell = cells[row][col];
    return cell.isHit;
  }

  /**
   * Checks if this cell has a Ship on it
   * @param row the row index to be checked
   * @param col the column index to be checked
   * @return true if this cell has a Ship on it
   */
  public boolean isCellOccupied(int row, int col) {
    Coord cell = cells[row][col];
    return cell.hasShip;
  }

  /**
   * Checks if a cell has a miss (has received a shot but does not have a ship)
   * @param row row index to be checked
   * @param col column index to be checked
   * @return true if the cell has received a shot but has no ships
   */
  public boolean doesCellHaveMiss(int row, int col) {
    Coord cell = cells[row][col];
    return cell.hasMiss;
  }

  /**
   * Places a Ship randomly onto a Board
   * @param ship the ship to be placed
   */
  public void placeShip(Ship ship, Random random) {
    List<Coord> shipCoords = new ArrayList<>();
    boolean canFit = false;

    while (!canFit) {
      int startX = random.nextInt(width - ship.size + 1);
      int startY = random.nextInt(height - ship.size + 1);
      int direction = random.nextInt(2); //0 is vertical, 1 is horizontal
      canFit = true;

      for (int i = 0; i < ship.size; i++) {
        int currentX = startX;
        int currentY = startY;

        if (direction == 0) {
          currentY += i;
        } else {
          currentX += i;
        }

        if (currentX < 0
            || currentX > width
            || currentY < 0
            || currentY > height
            || cells[currentY][currentX].hasShip) {
          canFit = false;
          shipCoords.clear();
        }
        shipCoords.add(cells[currentY][currentX]);
      }
    }

    if (canFit) {
      for (Coord coord : shipCoords) {
        coord.hasShip = true;
      }
      ship.location = shipCoords;
      ships.add(ship);
    } else {
      shipCoords.clear();
    }
  }

  /**
   * Changes a cell's data if that cell has been hit depending on what that cell contains
   * @param coord the Coord cell to be checked
   */
  public void receiveShot(Coord coord) {
    Coord cell = cells[coord.getX()][coord.getY()];

    if (cell.hasShip) {
      cell.isHit = true;
      cell.hasShip = false;
      cell.hasMiss = false;
    } else if (!cell.hasShip) {
      cell.hasMiss = true;
      cell.hasShip = false;
      cell.isHit = false;
    }
  }

  /**
   * Checks if all the ships on a board have been sunk
   * @return true if all ships on a board have been sunk
   */
  public boolean areAllShipsSunk() {
    List<Ship> sunkenShipsOnBoard = new ArrayList<>();
    for (Ship ship : ships) {
      checkIfSunk(ship);
      if (ship.isSunk()) {
        sunkenShipsOnBoard.add(ship);
      }
    }
    ships.removeAll(sunkenShipsOnBoard);
    return ships.isEmpty();
  }
}

