package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {
  Ship ship1;
  Ship ship2;
  Ship ship3;

  List<Coord> coordsList1;
  List<Coord> coordsList2;
  List<Coord> coordsList3;
  List<Ship> shipList;
  Board testBoard;

  @BeforeEach
  void setup() {
    coordsList1 = new ArrayList<>();
    coordsList1.add(new Coord(0, 0));
    coordsList1.add(new Coord(0, 1));
    coordsList1.add(new Coord(0, 2));
    coordsList1.add(new Coord(0, 3));

    coordsList2 = new ArrayList<>();
    coordsList2.add(new Coord(1, 0));
    coordsList2.add(new Coord(2, 0));
    coordsList2.add(new Coord(3, 0));
    coordsList2.add(new Coord(4, 0));
    coordsList2.add(new Coord(5, 0));

    coordsList3 = new ArrayList<>();
    coordsList3.add(new Coord(3, 3));
    coordsList3.add(new Coord(3, 4));
    coordsList3.add(new Coord(3, 5));

    ship1 = new Ship(ShipType.DESTROYER, coordsList1);
    ship2 = new Ship(ShipType.BATTLESHIP, coordsList2);
    ship3 = new Ship(ShipType.SUBMARINE, coordsList3);

    shipList = new ArrayList<>();
    shipList.add(ship1);
    shipList.add(ship2);
    shipList.add(ship3);

    Coord[][] cells = new Coord[6][6];

    testBoard = new Board(6, 6, shipList, cells);
  }

  //Still need to find a way to test for exceptions, which will require using tests for random

  @Test
  void testIsCellHit() {
    assertFalse(testBoard.isCellHit(0, 0));
    testBoard.cells[0][0].isHit = true;
    assertTrue(testBoard.isCellHit(0, 0));
  }

  @Test
  void testIsCellOccupied() {
    assertFalse(testBoard.isCellOccupied(1, 1));
    testBoard.cells[1][1].hasShip = true;
    assertTrue(testBoard.isCellOccupied(1, 1));
  }

  @Test
  void testDoesCellHaveMiss() {
    assertFalse(testBoard.doesCellHaveMiss(2, 2));
    testBoard.cells[2][2].hasMiss = true;
    assertTrue(testBoard.doesCellHaveMiss(2, 2));
  }

  /**
   * Tests that placeShip places a ship onto a board
   */
  @Test
  void testPlaceShip() {
    Ship newShip = new Ship(ShipType.SUBMARINE);
    Random seed = new Random(12345);
    testBoard.placeShip(newShip, seed);
    assertEquals(4, testBoard.ships.size());

  }

  /**
   * Tests that the receiveShot method produces the correct mutation on the Coord objects
   */
  @Test
  void testReceiveShot() {
    Random seed = new Random(1);
    int height = 10;
    int width = 10;
    Board board = new Board(height, width);
    Ship ship = new Ship(ShipType.SUBMARINE); // Assuming ship size of 3
    board.placeShip(ship, seed);

    // Create a coordinate to represent the shot
    Coord shotCoord = new Coord(0, 5);

    // Verify that the cell initially has a ship
    assertTrue(board.isCellOccupied(0, 5));

    // Receive the shot
    board.receiveShot(shotCoord);

    // Verify that the ship is hit and the cell no longer has a ship
    assertTrue(board.isCellHit(0, 5));
    assertFalse(board.isCellOccupied(0, 5));
    assertFalse(board.doesCellHaveMiss(0, 5));

  }

  /**
   * Tests that all ships are sunk once all their Coords have been hit
   */
  @Test
  void testAreAllShipsSunk() {
    Random seed = new Random(1);
    // Create a board with dimensions 10x10
    Board board = new Board(10, 10);

    // Create ships of different sizes
    Ship ship1 = new Ship(ShipType.SUBMARINE);
    Ship ship2 = new Ship(ShipType.DESTROYER);

    // Place ships on the board
    board.placeShip(ship1, seed);
    board.placeShip(ship2, seed);

    // Fire shots on specific coordinates
    board.receiveShot(new Coord(0, 5));
    board.receiveShot(new Coord(1, 5));
    board.receiveShot(new Coord(2, 5));

    // Check the state of the board after shots
    assertFalse(board.areAllShipsSunk()); // ship1 is hit, but ship2 is not sunk

    board.receiveShot(new Coord(6, 0));
    board.receiveShot(new Coord(7, 0));
    board.receiveShot(new Coord(8, 0));
    board.receiveShot(new Coord(9, 0));

    // Check the state of the board after shots
    assertTrue(board.areAllShipsSunk());
  }
}
