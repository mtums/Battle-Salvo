package cs3500.pa03.controller;

import static cs3500.pa03.view.ConsoleView.displayBoard;
import static cs3500.pa03.view.ConsoleView.displayOpponentBoard;

import cs3500.pa03.Player;
import cs3500.pa03.model.AIPlayer;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.HumanPlayer;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.view.ConsoleView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the controller for the game. Handles the relationship between model and view
 */
public class GameController {
  ConsoleView view;
  Player player1;
  Player player2;
  Board p1Board;
  Board p2Board;

  /**
   * Instantiates a GameController object with all its fields
   * @param game The Game object being passed in
   * @param view The ConsoleView object being passed in
   */

  /**
   * Instantiates a GameController object with a ConsoleView object
   * @param view ConsoleView object that allows this GameController to receive user input
   */
  public GameController(ConsoleView view) {
    this.view = view;
  }

  /**
   * Starts a game of BattleSalvio. Gathers needed information to start the game from the user.
   * Uses gathered info from the view to give to the model and establish the starting boards for
   * both users.
   */
  public void startGame() {
    view.displayMessage("Welcome to BattleSalvio!\n");
    int height;
    do {
      view.displayMessage("Please enter the height of the board (must be in the range [6, 15] inclusive): ");
      height = view.getUserInput();
      if ((height < 6) || height > 15) {
        view.displayMessage("Invalid height! Range is [6, 15] inclusive.");
      }
    } while ((height < 6) || (height > 15));
    int width;
    do {
      view.displayMessage("Please enter the width of the board (must be in the range [6, 15] inclusive): ");
      width = view.getUserInput();
      if ((width < 6) || width > 15) {
        view.displayMessage("Invalid height! Range is [6, 15] inclusive.");
      }
    } while ((width < 6) || (width > 15));

    p1Board = new Board(height, width);
    p2Board = new Board(height, width);
    player1 = new HumanPlayer(p1Board);
    player2 = new AIPlayer(p2Board);
    view.displayMessage("Please enter the amount of each ship you would like.\n" +
        "The total number of ships on the board may not exceed " + Math.min(height, width));
    int numCarrier;
    int numBattleship;
    int numDestroyer;
    int numSubmarine;
    int totalShips;
    int minDimension;
    do {
      view.displayMessage("\nNumber of Carriers:");
      numCarrier = view.getUserInput();
      view.displayMessage("\nNumber of Battleships:");
      numBattleship = view.getUserInput();
      view.displayMessage("\nNumber of Destroyers:");
      numDestroyer = view.getUserInput();
      view.displayMessage("\nNumber of Submarines:");
      numSubmarine = view.getUserInput();
      totalShips = numCarrier + numBattleship + numDestroyer + numSubmarine;
      minDimension = Math.min(height, width);
      if (totalShips > minDimension) {
        view.displayMessage("Invalid ship configuration! Total number of ships cannot exceed the lowest dimension of the board.");
      } else if (numCarrier < 1 || numBattleship < 1 || numDestroyer < 1 || numSubmarine < 1) {
        view.displayMessage("Invalid ship configuration! You must have at least 1 of each ship type.");
      }
    } while (numCarrier < 1 || numBattleship < 1 || numDestroyer < 1 || numSubmarine < 1 || totalShips > minDimension);
    Map<ShipType, Integer> shipConfiguration = new HashMap<>();
    shipConfiguration.put(ShipType.CARRIER, numCarrier);
    shipConfiguration.put(ShipType.BATTLESHIP, numBattleship);
    shipConfiguration.put(ShipType.DESTROYER, numDestroyer);
    shipConfiguration.put(ShipType.SUBMARINE, numSubmarine);
    p1Board.ships = player1.setup(height, width, shipConfiguration);
    p2Board.ships = player2.setup(height, width, shipConfiguration);
    view.displayMessage("Here is your board. S means there's a ship, H means there's a hit, M means there's a miss, O means empty: ");
    displayBoard(p1Board);
    view.displayMessage("\nHere is your opponent's board. H means you've hit one of their ships, M means you've missed: ");
    displayOpponentBoard(p2Board);
    this.updateGame();
  }

  /**
   * Handles the game during play. Receives shots from user and updates both board's accordingly.
   * Also handles the end of the game
   */
  public void updateGame() {
    boolean isGameOver = false;
    while (!isGameOver) {
      List<Coord> shotsAgainstP2 = player1.takeShots();
      List<Coord> shotsAgainstP1 = player2.takeShots();

      for (Coord coord : shotsAgainstP2) {
        p2Board.receiveShot(coord);
      }
      for (Coord coord : shotsAgainstP1) {
        p1Board.receiveShot(coord);
      }

      view.displayMessage("Here is your board after your opponent's shots: ");
      displayBoard(p1Board);
      view.displayMessage("\n");
      view.displayMessage("Here is your opponent's board after your shots: ");
      displayOpponentBoard(p2Board);
      view.displayMessage("\n");

      if (p1Board.areAllShipsSunk() || p2Board.areAllShipsSunk()) {
        isGameOver = true;
      }
    }

    GameResult result;
    String reason;

    if (p1Board.areAllShipsSunk() && p2Board.areAllShipsSunk()) {
      result = GameResult.TIE;
      reason = "Both players' ships are sunk!";
    } else if (p1Board.areAllShipsSunk()) {
      result = GameResult.LOSE;
      reason = "All of player 1's ships are sunk!";
    } else {
      result = GameResult.WIN;
      reason = "All of player 2's ships are sunk!";
    }
    player1.endGame(result, reason);
    player2.endGame(result, reason);
  }
}

