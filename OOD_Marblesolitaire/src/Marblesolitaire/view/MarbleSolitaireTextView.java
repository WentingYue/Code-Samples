package Marblesolitaire.view;

import Marblesolitaire.model.hw02.MarbleSolitaireModelState;

/**
 * Marble solitaire text view.
 * Print the Marble solitaire board in a text form.
 */
public class MarbleSolitaireTextView extends DefaultSolitaireTextView {

  /**
   * Constructor for MarbleSolitaireTextView.
   * @param model view the given model
   */

  public MarbleSolitaireTextView(MarbleSolitaireModelState model) {
    this(model, System.out);
  }

  /**
   * Constructor for MarbleSolitaireTextView.
   * @param model view the given model
   * @param destination transmitting destination
   */

  public MarbleSolitaireTextView(MarbleSolitaireModelState model, Appendable destination) {
    super(model, destination);
  }

  @Override
  public String toString() {
    int size = model.getBoardSize();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < size; i++) {
      int lastMarbleIndex = -1;
      // find the last marble in a row
      for (int j = size - 1; j >= 0; j--) {
        MarbleSolitaireModelState.SlotState slotState = model.getSlotAt(i, j);
        if (slotState == MarbleSolitaireModelState.SlotState.Marble
                || slotState == MarbleSolitaireModelState.SlotState.Empty) {
          lastMarbleIndex = j;
          break;
        }
      }
      for (int j = 0; j <= lastMarbleIndex; j++) {
        MarbleSolitaireModelState.SlotState slotState = model.getSlotAt(i, j);
        if (slotState == MarbleSolitaireModelState.SlotState.Marble) {
          sb.append("O");
        } else if (slotState == MarbleSolitaireModelState.SlotState.Empty) {
          sb.append("_");
        } else if (slotState == MarbleSolitaireModelState.SlotState.Invalid) {
          sb.append(" ");
        }
        if (j < lastMarbleIndex) {
          sb.append(" ");
        }
      }
      if (i != size - 1) {
        sb.append("\n");
      }
    }
    return sb.toString();
  }
}