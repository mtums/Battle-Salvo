package cs3500.pa03.model;

import cs3500.pa03.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents an AI opponent for the game BattleSalvio
 */
public class AIPlayer implements Player {
  String name;
  Board board;

  /**
   * Instantiates an AIPlayer object with all of its fields
   * @param board the board being passed into the AIPlayer
   * @param name the name of the AIPlayer
   */
  public AIPlayer(Board board, String name) {
    this.board = board;
    this.name = name;
  }


  /**
   * Instantiates an AIPlayer object with only a board
   * @param board the board being passed into this AIPlayer
   */
  public AIPlayer(Board board) {
    this.board = board;
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
        board.placeShip(newShip, new Random());

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
    Random random = new Random();
    int shipCount = 0;
    List<Coord> shots = new ArrayList<>();
    for (Ship ship : board.ships) {
      if (!ship.isSunk) {
        shipCount++;
      }
    }

    while (shots.size() < shipCount) {
      int row = random.nextInt(board.height);
      int col = random.nextInt(board.width);

      Coord cell = board.cells[row][col];
      if (!cell.isHit && !cell.hasMiss && !shots.contains(cell)) {
        shots.add(cell);
      }
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
        break;
      case LOSE:
        break;
      case TIE:
        break;
    }
  }
}

