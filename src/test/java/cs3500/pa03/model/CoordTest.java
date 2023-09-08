package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoordTest {
  Coord coord1;
  Coord coord2;
  @BeforeEach
  void setup() {
    coord1 = new Coord(1, 2);
    coord2 = new Coord(2, 3);
  }

  @Test
  void testGetX() {
    assertEquals(1, coord1.getX());
    assertEquals(2, coord1.getY());
  }

  @Test
  void testGetY() {
    assertEquals(2, coord2.getX());
    assertEquals(3, coord2.getY());
  }
}