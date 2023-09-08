package cs3500.pa03.model;

/**
 * Represents a coordinate on the BattleSalvio board
 */
public class Coord {
  public int x;
  public int y;
  public boolean isHit;
  public boolean hasShip;
  public boolean hasMiss;

  /**
   * Instantiates a Coord object with all of its classes
   * @param x the x-position of this Coord
   * @param y the y-position of this Coord
   * @param isHit true if this Coord has been hit (meaning it has a Ship and has been hit)
   * @param hasShip true if this Coord has a Ship on it
   * @param hasMiss true if a shot was taken on this Coord, but this Coord does not have a Ship
   */
  public Coord(int x, int y, boolean isHit, boolean hasShip, boolean hasMiss) {
    this.x = x;
    this.y = y;
    this.isHit = isHit;
    this.hasShip = hasShip;
    this.hasMiss = hasMiss;
  }

  /**
   * Instantiates a Coord object with just the x and y coordinates
   * @param x the x-position of this Coord
   * @param y the y-position of this Coord
   */
  public Coord(int x, int y) {
    this.x = x;
    this.y =y;
    this.isHit = false;
    this.hasShip = false;
    this.hasMiss = false;
  }

  /**
   * Gets the x of this Coord
   * @return the x position of this Coord as an int
   */
  public int getX() {
    return x;
  }

  /**
   * Gets the y of this Coord
   * @return the y position of this Coord as an int
   */
  public int getY() {
    return y;
  }
}

