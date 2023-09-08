package cs3500.pa03;

import static cs3500.pa03.view.ConsoleView.displayBoard;

import cs3500.pa03.controller.GameController;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.view.ConsoleView;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    System.out.println("Hello from Battle Salvo - PA03 Template Repo");

    ConsoleView view = new ConsoleView();
    GameController gc = new GameController(view);
    gc.startGame();
  }
}

