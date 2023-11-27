package Marblesolitaire.model.hw02;

import Marblesolitaire.model.hw04.AbstractSolitaireModel;

/**
 * Represent the EnglishSolitaireModel which is type of MarbleSolitaireModel.
 */
public class EnglishSolitaireModel extends AbstractSolitaireModel {

  /**
   * Initialize the game board of arm thickness 3 with the empty slot at the center.
   */
  public EnglishSolitaireModel() {
    this(3);
  }

  /**
   * Initialize the game board.
   * so that the arm thickness is 3 and the empty slot is at the position (sRow, sCol)
   */
  public EnglishSolitaireModel(int sRow, int sCol) {
    this(3, sRow, sCol);
  }

  /**
   * Initialize a game board with the empty slot at the center.
   *
   * @param armThickness the arm thickness
   */
  public EnglishSolitaireModel(int armThickness) {
    this(armThickness, 3 * (armThickness - 1) / 2, 3 * (armThickness - 1) / 2);
  }

  /**
   * Initialize a game board.
   *
   * @param armThickness the arm thickness
   * @param sRow         row of the empty slot
   * @param sCol         column of the empty slot
   */
  public EnglishSolitaireModel(int armThickness, int sRow, int sCol) {
    if (armThickness <= 0 || armThickness % 2 == 0) {
      throw new IllegalArgumentException("the arm thickness should be a positive odd number");
    }
    this.size = armThickness * 3 - 2;
    grids = new SlotState[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (j >= armThickness - 1 && j <= size - armThickness) {
          grids[i][j] = SlotState.Marble;
        } else if (i >= armThickness - 1 && i <= size - armThickness) {
          grids[i][j] = SlotState.Marble;
        } else {
          grids[i][j] = SlotState.Invalid;
        }
      }
    }
    setState(sRow, sCol, SlotState.Empty);
  }

}