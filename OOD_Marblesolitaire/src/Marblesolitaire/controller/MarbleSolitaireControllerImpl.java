package Marblesolitaire.controller;

import java.io.IOException;
import java.util.Scanner;

import Marblesolitaire.model.hw02.MarbleSolitaireModel;
import Marblesolitaire.view.MarbleSolitaireView;

/**
 * A controller implementation for this game.
 * Controller takes in the model, view and input. Scanner the player's input to showing
 * the move, quit or game over text. It's used for player to play the game and transmit
 * the move to the text view for printing out the current board.
 */
public class MarbleSolitaireControllerImpl implements MarbleSolitaireController {

  private MarbleSolitaireModel model;
  private MarbleSolitaireView view;
  private Scanner scanner;

  private int fromRow = -1;
  private int fromColumn = -1;
  private int toRow = -1;
  private int toColumn = -1;

  /**
   * instantiate a new game controller.
   * When Calling the controller,player's input would be recorded and processed.
   * @param model model
   * @param view  output view
   * @param input read input
   * @throws IllegalArgumentException failed to read input or transmit output
   */

  public MarbleSolitaireControllerImpl(MarbleSolitaireModel model,
                                       MarbleSolitaireView view,
                                       Readable input) throws IllegalArgumentException {

    if (model == null) {
      throw new IllegalArgumentException("model can't be null");
    }
    if (view == null) {
      throw new IllegalArgumentException("view can't be null");
    }
    if (input == null) {
      throw new IllegalArgumentException("input can't be null");
    }
    this.model = model;
    this.view = view;
    this.scanner = new Scanner(input);
  }

  /**
   * Render the board.
   * @throws IOException exception
   */

  private void showBoard() throws IOException {
    view.renderBoard();
    view.renderMessage("Score: " + model.getScore() + "\n");
  }

  /**
   * Get the input for the move then plat the move.
   * @param number input number
   * @throws IOException exception
   */

  private void getMove(int number) throws IOException {
    if (fromRow == -1) {
      fromRow = number - 1;
    } else if (fromColumn == -1) {
      fromColumn = number - 1;
    } else if (toRow == -1) {
      toRow = number - 1;
    } else if (toColumn == -1) {
      toColumn = number - 1;
      try {
        model.move(fromRow, fromColumn, toRow, toColumn);
      } catch (IllegalArgumentException e) {
        view.renderMessage("Invalid move, Play again. " + e.getMessage() + "\n");
      }

      // reset movement data
      fromRow = -1;
      fromColumn = -1;
      toRow = -1;
      toColumn = -1;
    }
  }

  @Override
  public void playGame() throws IllegalStateException {
    try {
      while (scanner.hasNext()) {
        if (model.isGameOver()) {
          break;
        }
        if (fromRow == -1 && fromColumn == -1 && toRow == -1 && toColumn == -1) {
          // show game board before input
          this.showBoard();
        }
        String input = scanner.next();

        if ("q".equals(input.toLowerCase()) || "quit".equals(input.toLowerCase())) {
          // user quitting game by input "q" or "Q"
          view.renderMessage("Game quit!\n");
          view.renderMessage("State of game when quit:\n");
          this.showBoard();
          return;
        }

        int number = 0;
        try {
          number = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
          // do nothing
        }
        if (number <= 0) {
          continue;
        }

        this.getMove(number);
      }

      if (model.isGameOver()) {
        view.renderMessage("Game over!\n");
        this.showBoard();
      } else {
        throw new IllegalStateException("runs out of inputs");
      }

    } catch (Exception runtimeException) {
      throw new IllegalStateException(runtimeException.getMessage());
    }
  }
}
