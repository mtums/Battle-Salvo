package cs3500.pa03.view;

import static org.junit.jupiter.api.Assertions.*;

import cs3500.pa03.model.Board;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class ConsoleViewTest {

  /**
   * Tests that displayMessage displays a given message to the console
   */
  @Test
  void testDisplayMessage() {
    // Create a new ConsoleView instance
    ConsoleView consoleView = new ConsoleView();

    // Redirect the output stream to a ByteArrayOutputStream to capture the output
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    // Test the displayMessage() method
    String message = "Hello, world!";
    consoleView.displayMessage(message);

    // Get the captured output from the ByteArrayOutputStream
    String output = outputStream.toString().trim();

    // Assert that the captured output matches the expected message
    assertEquals(message, output);
  }

  /**
   * Tests that displayBoard accurately displays a board
   */
  @Test
  void testDisplayBoard() {
    Board board = new Board(7, 7);
    board.cells[0][0].isHit = true;
    board.cells[1][0].hasShip = true;
    board.cells[2][0].hasMiss = true;
    ConsoleView view = new ConsoleView();
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    // Test the displayBoard() method
    view.displayBoard(board);

    // Get the captured output from the ByteArrayOutputStream
    String output = outputStream.toString().trim();

    String expectedOutput =
        "H O O O O O O \n" +
            "S O O O O O O \n" +
            "M O O O O O O \n" +
            "O O O O O O O \n" +
            "O O O O O O O \n" +
            "O O O O O O O \n" +
            "O O O O O O O";

    assertEquals(expectedOutput, output);
  }

  /**
   * Tests that displayOpponentBoard accurately displays an oppontent's board
   */
  @Test
  void testDisplayOpponentBoard() {
    Board board = new Board(7, 7);
    board.cells[0][0].isHit = true;
    board.cells[1][0].hasShip = true;
    board.cells[2][0].hasMiss = true;
    ConsoleView view = new ConsoleView();
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    // Test the displayBoard() method
    view.displayOpponentBoard(board);

    // Get the captured output from the ByteArrayOutputStream
    String output = outputStream.toString().trim();

    String expectedOutput =
        "H O O O O O O \n" +
            "O O O O O O O \n" +
            "M O O O O O O \n" +
            "O O O O O O O \n" +
            "O O O O O O O \n" +
            "O O O O O O O \n" +
            "O O O O O O O";

    assertEquals(expectedOutput, output);
  }

  /**
   * Tests that this method stores user input correctly
   */
  @Test
  void testGetUserInput() {
    // Create a new ConsoleView instance
    ConsoleView consoleView = new ConsoleView();

    // Create a mock user input
    String userInput = "42\n";

    // Redirect the input stream to the mock user input
    InputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
    consoleView.input = new InputStreamReader(inputStream);

    // Test the getUserInput() method
    int result = consoleView.getUserInput();

    // Assert that the returned value matches the expected input
    assertEquals(42, result);
  }
}
