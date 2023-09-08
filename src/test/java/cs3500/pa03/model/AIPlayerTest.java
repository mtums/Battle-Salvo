package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AIPlayerTest {
  AIPlayer aip1;
  AIPlayer aip2;
  Board board1;
  Board board2;
  Ship ship1;
  Ship ship2;
  List<Coord> coordsList1;

  List<Coord> coordsList2;
  List<Ship> shipList;

  @BeforeEach
  void setup() {
    coordsList1 = new ArrayList<>();
    coordsList1.add(new Coord(1, 0));
    coordsList1.add(new Coord(2, 0));
    coordsList1.add(new Coord(3, 0));

    coordsList2 = new ArrayList<>();
    coordsList2.add(new Coord(3, 3));
    coordsList2.add(new Coord(3, 4));
    coordsList2.add(new Coord(3, 5));

    Ship ship1 = new Ship(ShipType.SUBMARINE, coordsList1);
    Ship ship2 = new Ship(ShipType.SUBMARINE, coordsList2);
    shipList = new ArrayList<>();
    shipList.add(ship1);
    shipList.add(ship2);
    board1 = new Board(10, 10);
    board1.ships = shipList;
    aip1 = new AIPlayer(board1);

    board2 = new Board(8, 8);
    aip2 = new AIPlayer(board2);
  }

  /**
   * Tests that the name method returns the name of the player
   */
  @Test
  void testName() {
    AIPlayer p1 = new AIPlayer(null,"AI1");
    AIPlayer p2 = new AIPlayer(null, "AI2");
    assertEquals("AI1", p1.name());
    assertEquals("AI2", p2.name());
  }

  /**
   * Tests the take shots method by checking that a list of coords is produced
   * after takeShots is run on a player
   */
  @Test
  void takeShots() {
    Board board = new Board(10, 10);
    AIPlayer aiPlayer = new AIPlayer(board);

    Map<ShipType, Integer> specifications = new LinkedHashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    specifications.put(ShipType.BATTLESHIP, 2);

    List<Ship> ships = aiPlayer.setup(10, 10, specifications);

    List<Coord> shots = aiPlayer.takeShots();

    int expectedShotCount = (int) ships.stream().filter(ship -> !ship.isSunk()).count();
    assertEquals(expectedShotCount, shots.size());

    for (Coord shot : shots) {
      //assertFalse(shot.isHit); // The shot should not be a hit cell
      //assertFalse(shot.hasMiss); // The shot should not be a miss cell
      //assertFalse(shot.hasShip); // The shot should not be a ship cell
    }
  }

  /**
   * Tests the reportDamage method by checking that the hasShip fields of the shpis are true
   */
  @Test
  void reportDamage() {
    Ship ship = new Ship(ShipType.SUBMARINE, coordsList1);
    List<Ship> list = new ArrayList<>();
    list.add(ship);
    Board board = new Board(10, 10, list, new Coord[10][10]);
    AIPlayer player = new AIPlayer(board);
    coordsList1.get(0).hasShip = true;
    coordsList1.get(1).hasShip = true;
    coordsList1.get(2).hasShip = true;
    assertEquals(3, player.reportDamage(coordsList1).size());
  }

  /**
   * Tests that the game ends and displays the correct messages
   */
  @Test
  void testEndGame() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    System.setOut(printStream);

    // Create a HumanPlayer object
    AIPlayer player = new AIPlayer(null, "ai");

    // Test different game results and reasons
    player.endGame(GameResult.WIN, "You sank all enemy ships!");
    String expectedOutput = "";
    assertEquals(expectedOutput, outputStream.toString());
    outputStream.reset();

    player.endGame(GameResult.LOSE, "All your ships have been sunk!");
    expectedOutput = "";
    assertEquals(expectedOutput, outputStream.toString());
    outputStream.reset();

    player.endGame(GameResult.TIE, "The game ended in a draw.");
    expectedOutput = "";
    assertEquals(expectedOutput, outputStream.toString());
    outputStream.reset();

    System.setOut(System.out);
  }
}