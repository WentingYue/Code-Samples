package Marblesolitaire.view;

import Marblesolitaire.model.hw02.MarbleSolitaireModelState;

/**
 * Triangle solitaire text view. Showing the Triangle solitaire board
 * and receive message from controller then showing the movement.
 */
public class TriangleSolitaireTextView extends DefaultSolitaireTextView {

  /**
   * Constructor for TriangleSolitaireTextView.
   * @param model view the given model
   */

  public TriangleSolitaireTextView(MarbleSolitaireModelState model) {
    this(model, System.out);
  }

  /**
   * Constructor for TriangleSolitaireTextView.
   * @param model view the given model
   * @param destination transmitting destination
   */

  public TriangleSolitaireTextView(MarbleSolitaireModelState model, Appendable destination) {
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
      // leading space of current row
      for (int k = 0; k < size - i - 1; k++) {
        sb.append(" ");
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