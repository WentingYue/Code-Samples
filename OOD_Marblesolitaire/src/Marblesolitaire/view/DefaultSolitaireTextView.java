package Marblesolitaire.view;

import Marblesolitaire.model.hw02.MarbleSolitaireModelState;

import java.io.IOException;

/**
 * Default solitaire text view.
 * Has render message and render board for receiving message from the player's move.
 */
public class DefaultSolitaireTextView implements MarbleSolitaireView {

  protected MarbleSolitaireModelState model;

  protected Appendable destination;

  /**
   * Constructor for DefaultSolitaireTextView.
   * @param model view the given model
   */

  public DefaultSolitaireTextView(MarbleSolitaireModelState model) {
    this(model, System.out);
  }

  /**
   * Constructor for TriangleSolitaireTextView.
   * @param model view the given model
   * @param destination transmitting destination
   */

  public DefaultSolitaireTextView(MarbleSolitaireModelState model, Appendable destination) {
    if (model == null) {
      throw new IllegalArgumentException("model can't be null");
    }
    if (destination == null) {
      throw new IllegalArgumentException("destination can't be null");
    }
    this.model = model;
    this.destination = destination;
  }

  @Override
  public void renderBoard() throws IOException {
    try {
      destination.append(this.toString() + "\n");
    } catch (Exception e) {
      throw new IOException(e.getMessage());
    }
  }

  @Override
  public void renderMessage(String message) throws IOException {
    try {
      destination.append(message);
    } catch (Exception e) {
      throw new IOException(e.getMessage());
    }
  }
}