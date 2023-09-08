package cs3500.pa03.model;

import static cs3500.pa03.model.Ship.checkIfSunk;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipTest {
  Ship ship1;
  Ship ship2;

  List<Coord> coordsList1;
  List<Coord> coordsList2;

  Coord testCoord4;

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

    ship1 = new Ship(ShipType.DESTROYER, coordsList1);

    ship2 = new Ship(ShipType.BATTLESHIP, coordsList2);
  }

  /**
   * Tests that the isSunk method returns what is should return
   */
  @Test
  void testIsSunk() {
    assertFalse(ship1.isSunk());
    assertFalse(ship2.isSunk());

    ship1.isSunk = true;
    ship2.isSunk = true;

    assertTrue(ship1.isSunk());
    assertTrue(ship2.isSunk());
  }

  /**
   * Tests that a Ship may be hit correctly
   */
  @Test
  void testHit() {
    Coord testCoord1 = ship1.location.get(0); //This Coord is 0,0
    assertEquals(0, ship1.location.get(0).x);
    assertEquals(0, ship1.location.get(0).y);
    Coord testCoord2 = new Coord(3, 5); //This Coord is not on ship1 nor ship2
    Coord testCoord3 = ship2.location.get(3); //This Coord is 4,0
    assertEquals(4, ship2.location.get(3).x);
    assertEquals(0, ship2.location.get(3).y);
    Coord testCoord4 = new Coord(5, 0); //This Coord is not on ship1 but is on ship2

    assertFalse(testCoord1.isHit);
    ship1.hit(testCoord1);
    assertTrue(testCoord1.isHit);

    assertFalse(testCoord2.isHit);
    ship1.hit(testCoord2);
    assertFalse(testCoord2.isHit);

    assertFalse(testCoord4.isHit);
    ship1.hit(testCoord4);
    assertFalse(testCoord4.isHit);

    assertFalse(testCoord3.isHit);
    ship2.hit(testCoord3);
    assertTrue(testCoord3.isHit);

    assertFalse(testCoord4.isHit);
    ship2.hit(testCoord4);
    assertTrue(testCoord4.isHit);

    ship2.hit(new Coord(3, 0));
    assertTrue(ship2.location.get(2).isHit);

    assertFalse(ship2.isSunk);
    ship2.hit(new Coord(1, 0));
    ship2.hit(new Coord(2, 0));
    ship2.hit(new Coord(3, 0));
    assertTrue(ship2.isSunk);
  }

  /**
   * Tests if the check method for if a ship is sunk works correctly
   */
  @Test
  void testCheckIfSunk() {
    checkIfSunk(ship1);
    assertFalse(ship1.isSunk);
    ship1.hit(new Coord(0,0));
    ship1.hit(new Coord(0,1));
    ship1.hit(new Coord(0,2));
    ship1.hit(new Coord(0,3));

    checkIfSunk(ship1);
    assertTrue(ship1.isSunk);

    checkIfSunk(ship2);
    assertFalse(ship2.isSunk);
    ship2.hit(new Coord(1,0));
    ship2.hit(new Coord(2,0));
    ship2.hit(new Coord(3,0));
    ship2.hit(new Coord(4,0));
    ship2.hit(new Coord(5,0));

    checkIfSunk(ship2);
    assertTrue(ship2.isSunk);
  }
}
