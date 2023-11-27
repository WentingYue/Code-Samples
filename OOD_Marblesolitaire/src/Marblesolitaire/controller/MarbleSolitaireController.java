package Marblesolitaire.controller;

/**
 * a controller for this game.
 */
public interface MarbleSolitaireController {

  /**
   * play a new game of Marble Solitaire.
   * @throws IllegalStateException if unable to successfully read input or transmit output
   */
  void playGame() throws IllegalStateException;

}
