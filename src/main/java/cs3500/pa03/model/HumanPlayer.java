package cs3500.pa03.model;

import cs3500.pa03.Player;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents a single human player in a game of BattleSalvo.
 */
public class HumanPlayer implements Player {
  String name;
  public Board board;
  Readable input;
  Appendable output;

  /**
   * Instantiates a HumanPlayer object with all of its fields
   * @param name the name of this HumanPlayer
   * @param board this HumanPlayer's board
   * @param input input from the user
   * @param output output from this HumanPlayer
   */
  public HumanPlayer(String name, Board board, Readable input, Appendable output) {
    this.name = name;
    this.board = board;
    this.input = new StringReader("");
    this.output = new OutputStreamWriter(System.out);
  }

  public HumanPlayer() {
    this.input = new InputStreamReader(System.in);
    this.output = new OutputStreamWriter(System.out);
  }

  public HumanPlayer(String name, Board board) {
    this.name = name;
    this.board = board;
    this.input = new InputStreamReader(System.in);
    this.output = new OutputStreamWriter(System.out);
  }

  public HumanPlayer(Board board) {
    this.board = board;
    this.input = new InputStreamReader(System.in);
    this.output = new OutputStreamWriter(System.out);
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return this.name;
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height the height of the board, range: [6, 15] inclusive
   * @param width the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    Map<ShipType, Integer> shipConfiguration = new HashMap<>();
    List<Ship> shipList = new ArrayList<>();
    Coord[][] cells = new Coord[height][width];

    for (Map.Entry<ShipType, Integer> entry : specifications.entrySet()) {
      for (int i = 0; i < entry.getValue(); i++) {
        Ship newShip = new Ship(entry.getKey());
        shipList.add(newShip);
        board.placeShip(newShip, new Random(1));

      }
    }
    return shipList;
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    int shipCount = 0;
    List<Coord> shots = new ArrayList<>();
    for (Ship ship : board.ships) {
      if (!ship.isSunk) {
        shipCount++;
      }
    }

    Scanner scanner = new Scanner(input);

    System.out.println("Please enter " + shipCount + " shots: ");

    for (int i = 1; i <= shipCount; i++) {
      System.out.println("Shot number " + i + ":");

      int row = -1;
      int col = -1;
      boolean validInput = false;

      while (!validInput) {
        try {
          row = scanner.nextInt();
          col = scanner.nextInt();
          if (row >= 0 && row < board.height && col >= 0 && col < board.width) {
            validInput = true;
          } else {
            System.out.println("Invalid coordinates! Please enter coordinates within the board's dimensions.");
          }
        } catch (InputMismatchException e) {
          System.out.println("Invalid input! Please enter valid coordinates.");
          scanner.nextLine(); // Consume invalid input
        }
      }
      shots.add(new Coord(row, col));
    }
    return shots;
  }

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   * ship on this board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> shotsThatHit = new ArrayList<>();
    for (Coord coord : opponentShotsOnBoard) {
      if (coord.hasShip) {
        coord.isHit = true;
        shotsThatHit.add(coord);
      } else if (!coord.hasShip) {
        coord.hasMiss = true;
      }
    }
    return shotsThatHit;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
    switch (result) {
      case WIN:
        System.out.println("Congratulations! You won the game!");
        break;
      case LOSE:
        System.out.println("You lost the game. Better luck next time!");
        break;
      case TIE:
        System.out.println("The game ended in a tie.");
        break;
    }

    System.out.println("Reason: " + reason);
  }
}


