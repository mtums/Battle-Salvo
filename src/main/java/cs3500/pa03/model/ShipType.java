package cs3500.pa03.model;

/**
 * Represents each type of BattleSalvio ship
 */
public enum ShipType {
  CARRIER(6),
  BATTLESHIP(5),
  DESTROYER(4),
  SUBMARINE(3);

  private int shipTypeSize;

  /**
   * private constructor for the ShipType
   * @param shipTypeSize the size of each type of ship
   */
  private ShipType(int shipTypeSize) {
    this.shipTypeSize = shipTypeSize;
  }

  /**
   * Gets the size of a ShipType
   * @return the size of the ShipType as an int
   */
  public int getShipTypeSize() {
    return shipTypeSize;
  }
}
