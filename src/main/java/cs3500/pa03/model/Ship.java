package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single Ship in BattleSalvio
 */
public class Ship {
  ShipType type;
  int size;
  List<Coord> location;
  boolean isSunk;

  /**
   * Instantiates a Ship with all of its fields
   * @param type from the ShipType enum, the type of BattleSalvio ship
   * @param size the size of the ship
   * @param location the coordinates of the ship on a board
   * @param isSunk true if all coordinates of this ship have been hit
   */
  public Ship(ShipType type, int size, List<Coord> location, boolean isSunk) {
    this.type = type;
    this.size = type.getShipTypeSize();
    this.location = location;
    this.isSunk = false;
  }

  /**
   * Instantiates a ship with just its type and location
   * @param type from the ShipType enum, the type of BattleSalvio ship
   * @param location the coordinates of the ship on a board
   */
  public Ship(ShipType type, List<Coord> location) {
    this.type = type;
    this.size = type.getShipTypeSize();
    this.location = location;
    this.isSunk = false;
  }

  /**
   * Instantiates a Ship before it has been placed onto a board
   * @param type from the ShipType enum, the type of BattleSalvio ship
   */
  //constructor for a Ship before it's placed
  public Ship(ShipType type) {
    this.type = type;
    this.size = type.getShipTypeSize();
    this.location = new ArrayList<>();
    this.isSunk = false;
  }

  //Add more constructors as needed

  /**
   * Returns the value of this Ship's isSunk field
   * @return true if the ship has sunk
   */
  public boolean isSunk() {
    return this.isSunk;
  }

  /**
   * Processes a hit on a Ship. If the Coord for the hit matches a Coord on this ship, the Coord's
   * metadata is changed to accomodate for being hit
   * @param coord the Coord of the hit
   */
  public void hit(Coord coord) {
    for (Coord c: location) {
      if ((c.getX() == coord.getX() && c.getY() == coord.getY())) {
        coord.isHit = true;
        c.isHit = true;
        coord.hasShip = false;
        c.hasShip = false;
        coord.hasMiss = false;
        c.hasMiss = false;
      }
    }
    checkIfSunk(this);
  }

  /**
   * Checks if a ship has been hit on all of its coordinates
   * @param ship the ship to be checked
   */
  public static void checkIfSunk(Ship ship) {
    for (Coord coords : ship.location) {
      if (!coords.isHit) {
        ship.isSunk = false;
        return;
      }
    }
    ship.isSunk = true;
  }
}


