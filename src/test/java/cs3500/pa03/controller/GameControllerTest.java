package cs3500.pa03.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cs3500.pa03.view.ConsoleView;
import org.junit.jupiter.api.Test;

class GameControllerTest {

  /**
   * Tests that all expected output is shown to the console for the startGame method
   */
  @Test
  void startGame() {
    ConsoleView mockView = mock(ConsoleView.class);

    when(mockView.getUserInput())
        .thenReturn(10) //board height
        .thenReturn(9) //board width
        .thenReturn(1) //number of carriers
        .thenReturn(1) //number of battleships
        .thenReturn(2) //number of destroyers
        .thenReturn(2); //number of submarines

    GameController gameController = spy(new GameController(mockView));
    doNothing().when(gameController).updateGame();

    gameController.startGame();
    verify(mockView).displayMessage("Welcome to BattleSalvio!\n");
    verify(mockView).displayMessage("Please enter the height of the board (must be in the range [6, 15] inclusive): ");
    verify(mockView).displayMessage("Please enter the width of the board (must be in the range [6, 15] inclusive): ");
    verify(mockView).displayMessage("Please enter the amount of each ship you would like.\n" +
        "The total number of ships on the board may not exceed 9");
    verify(mockView).displayMessage("\nNumber of Carriers:");
    verify(mockView).displayMessage("\nNumber of Battleships:");
    verify(mockView).displayMessage("\nNumber of Destroyers:");
    verify(mockView).displayMessage("\nNumber of Submarines:");
    verify(mockView).displayMessage("Here is your board. S means there's a ship, H means there's a hit, M means there's a miss, O means empty: ");
    verify(mockView).displayMessage("\nHere is your opponent's board. H means you've hit one of their ships, M means you've missed: ");
  }

  /**
   * Tests the updateGame method by ensuring each method is called the right amount of times
   */
/*  @Test
  void updateGame() {
    ConsoleView consoleView = mock(ConsoleView.class);
    Player player1 = mock(HumanPlayer.class);
    Player player2 = mock(AIPlayer.class);
    Board p1Board = mock(Board.class);
    Board p2Board = mock(Board.class);

    // Set up the mock objects
    List<Coord> shotsAgainstP2 = Arrays.asList(new Coord(0, 0), new Coord(1, 1));
    List<Coord> shotsAgainstP1 = Arrays.asList(new Coord(2, 2), new Coord(3, 3));

    when(player1.takeShots())
        .thenReturn(shotsAgainstP2)
        .thenReturn(Collections.emptyList()); // Return an empty list for subsequent calls
    when(player2.takeShots())
        .thenReturn(shotsAgainstP1)
        .thenReturn(Collections.emptyList()); // Return an empty list for subsequent calls
    when(p1Board.areAllShipsSunk()).thenReturn(false, true);
    when(p2Board.areAllShipsSunk()).thenReturn(false, true);

    GameController gameController = new GameController(consoleView);
    gameController.player1 = player1;
    gameController.player2 = player2;
    gameController.p1Board = p1Board;
    gameController.p2Board = p2Board;

    // Redirect the input stream to simulate user input
    String userInput = "4 2\n";
    InputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
    consoleView.input = new InputStreamReader(inputStream);

    // Execute the method under test
    gameController.updateGame();

    // Verify that the expected methods were called and messages were displayed
    verify(player1, times(2)).takeShots();
    verify(player2, times(2)).takeShots();
    verify(p1Board, times(2)).receiveShot(any(Coord.class));
    verify(p2Board, times(2)).receiveShot(any(Coord.class));
    verify(consoleView, times(8)).displayMessage(anyString());
    verify(consoleView, times(2)).displayBoard(p1Board);
    verify(consoleView, times(2)).displayOpponentBoard(p2Board);
  }*/

  /**
   * tests startGame with invalid responses
   */
/*  @Test
  void startGameInvalidResponses() {
    ConsoleView mockView = mock(ConsoleView.class);

    // Configure mockView to return invalid responses for board height and width
    when(mockView.getUserInput())
        .thenReturn(5) // Invalid height
        .thenReturn(16)// Invalid width
        .thenReturn(1)
        .thenReturn(1)
        .thenReturn(2)
        .thenReturn(2);

    GameController gameController = spy(new GameController(mockView));
    doNothing().when(gameController).updateGame();

    gameController.startGame();

    // Verify that the invalid response prompts are displayed
    verify(mockView).displayMessage("Invalid height! Range is [6, 15] inclusive.");
    verify(mockView).displayMessage("Invalid width! Range is [6, 15] inclusive.");

    //verify(mockView, times(2)).displayMessage("Invalid height! Range is [6, 15] inclusive.");
    //verify(mockView, times(2)).displayMessage("Invalid width! Range is [6, 15] inclusive.");
  }*/
}
