package cs3500.pa03.view;

import cs3500.pa03.model.Board;
import cs3500.pa03.model.Coord;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

/**
 * Represents the view portion of this game
 */
public class ConsoleView {
  public Readable input;
  Appendable output;

  /**
   * Instantiates a ConsoleView object with a Readable input stream and an appendable output stream
   */
  public ConsoleView() {
    this.input = new InputStreamReader(System.in);
    this.output = new OutputStreamWriter(System.out);
  }

  /**
   * Displays a message to the user
   * @param message message to be displayed in the console
   */
  public void displayMessage(String message) {
    System.out.println(message);
  }

  /**
   * Displays a supplied Board as it is. Does not change anything on the Board.
   * @param board The Board to be displayed to the console
   */
  public static void displayBoard(Board board) {
    Coord[][] cells = new Coord[board.height][board.width];

    //Initialize the 2D array with current Coord objects
    for (int row = 0; row < board.height; row++) {
      for (int col = 0; col < board.width; col++) {
        boolean isCellHit = board.isCellHit(row, col);
        boolean isCellOccupied = board.isCellOccupied(row, col);
        boolean doesCellHaveMiss = board.doesCellHaveMiss(row, col);
        cells[row][col] = new Coord(row, col, isCellHit, isCellOccupied, doesCellHaveMiss);
      }
    }

    //Print the initialized 2D array
    for (int row = 0; row < board.height; row++) {
      for (int col = 0; col < board.width; col++) {
        Coord cell = cells[row][col];
        String symbol;
        if (cell.isHit) {
          symbol = "H";
        } else if (cell.hasShip) {
          symbol = "S";
        } else if (cell.hasMiss) {
          symbol = "M";
        } else {
          symbol = "O";
        }
        System.out.print(symbol + " ");
      }
      System.out.println();
    }
  }

  /**
   * Displays the opponent's board, which doesn't show Ship locations, only hits and misses
   * @param board The opponent's board
   */
  public static void displayOpponentBoard(Board board) {
    Coord[][] cells = new Coord[board.height][board.width];

    //Initialize the 2D array with current Coord objects
    for (int row = 0; row < board.height; row++) {
      for (int col = 0; col < board.width; col++) {
        boolean isCellHit = board.isCellHit(row, col);
        boolean isCellOccupied = board.isCellOccupied(row, col);
        boolean doesCellHaveMiss = board.doesCellHaveMiss(row, col);
        cells[row][col] = new Coord(row, col, isCellHit, isCellOccupied, doesCellHaveMiss);
      }
    }

    //Print the initialized 2D array
    for (int row = 0; row < board.height; row++) {
      for (int col = 0; col < board.width; col++) {
        Coord cell = cells[row][col];
        String symbol;
        if (cell.isHit) {
          symbol = "H";
        } else if (cell.hasMiss) {
          symbol = "M";
        } else {
          symbol = "O";
        }
        System.out.print(symbol + " ");
      }
      System.out.println();
    }
  }


  /**
   * Gathers user input
   * @return a single int from the user's input
   */
  public int getUserInput() {
    Scanner scanner = new Scanner(input);
    return scanner.nextInt();
  }
}


