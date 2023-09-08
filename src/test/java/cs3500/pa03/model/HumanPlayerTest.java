package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HumanPlayerTest {
  HumanPlayer hp1;
  HumanPlayer hp2;
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
    hp1 = new HumanPlayer("Bob", board1);

    board2 = new Board(8, 8);
    hp2 = new HumanPlayer("Joe", board2);
  }

  HumanPlayer humanPlayer = new HumanPlayer();
  HumanPlayer humanp1 = new HumanPlayer("Bob", board2, null, null);

  @Test
  void name() {
    assertEquals("Bob", hp1.name());
    assertEquals("Joe", hp2.name());
  }

  /**
   * Tests the takeShots method by using sample input to see that those shots get returned in the
   * list produced by the takeShots method
   */
/*  @Test
  void testTakeShots() {
    StringReader mockInput = new StringReader("0 0\n1 1\n2 2\n");
    Board board = new Board(10, 10);
    HumanPlayer player = new HumanPlayer("Player 1", board, mockInput, System.out);

    Map<ShipType, Integer> shipSpecifications = new HashMap<ShipType, Integer>();
    shipSpecifications.put(ShipType.BATTLESHIP, 5);

    // Place ships on the board
    List<Ship> ships = player.setup(10, 10, shipSpecifications);

    // Simulate opponent's shots
    List<Coord> opponentShots = Arrays.asList(
        new Coord(0, 0),
        new Coord(1, 1),
        new Coord(2, 2)
    );

    // Set up a mock input for the player

    // Call takeShots() and assert the returned shots
    List<Coord> shots = player.takeShots();
    assertEquals(opponentShots, shots);
  }*/

  /**
   * tests that reportDamage filters the coords that got hit
   */
  @Test
  void testReportDamage() {
    Ship ship = new Ship(ShipType.SUBMARINE, coordsList1);
    List<Ship> list = new ArrayList<>();
    list.add(ship);
    Board board = new Board(10, 10, list, new Coord[10][10]);
    HumanPlayer player = new HumanPlayer(board);
    coordsList1.get(0).hasShip = true;
    coordsList1.get(1).hasShip = true;
    coordsList1.get(2).hasShip = true;
    assertEquals(3, player.reportDamage(coordsList1).size());
  }

  /**
   * Tests that the game ends properly
   */
  @Test
  void testEndGame() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    System.setOut(printStream);

    HumanPlayer humanPlayer = new HumanPlayer("Player1", null);

    humanPlayer.endGame(GameResult.WIN, "You sank all enemy ships!");
    String expectedOutput = "Congratulations! You won the game!\nReason: You sank all enemy ships!\n";
    assertEquals(expectedOutput, outputStream.toString());
    outputStream.reset();

    humanPlayer.endGame(GameResult.LOSE, "All your ships have been sunk!");
    expectedOutput = "You lost the game. Better luck next time!\nReason: All your ships have been sunk!\n";
    assertEquals(expectedOutput, outputStream.toString());
    outputStream.reset();

    humanPlayer.endGame(GameResult.TIE, "The game ended in a draw.");
    expectedOutput = "The game ended in a tie.\nReason: The game ended in a draw.\n";
    assertEquals(expectedOutput, outputStream.toString());
    outputStream.reset();

    System.setOut(System.out);
  }
}
