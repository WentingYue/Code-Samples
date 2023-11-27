package Marblesolitaire.model.hw04;

/**
 * Represent the EuropeanSolitaireModel which is type of MarbleSolitaireModel.
 * Model enable player to move the Solitaire. The default arm thickness is 3.
 */
public class EuropeanSolitaireModel extends AbstractSolitaireModel {

  /**
   * creates an octagonal board whose sides have length 3,
   * with the empty slot in the center of the board.
   */
  public EuropeanSolitaireModel() {
    this(3);
  }

  /**
   * creates a game with the specified side length,
   * and the empty slot in the center of the board.
   *
   * @param sideLength the arm thickness
   */
  public EuropeanSolitaireModel(int sideLength) {
    this(sideLength, 3 * (sideLength - 1) / 2, 3 * (sideLength - 1) / 2);
  }

  /**
   * specify the initial position of the empty slot,
   * in a board of default size 3.
   */
  public EuropeanSolitaireModel(int sRow, int sCol) {
    this(3, sRow, sCol);
  }

  /**
   * specify the size of the board and the initial position of the empty slot.
   *
   * @param sideLength the arm thickness
   * @param sRow         row of the empty slot
   * @param sCol         column of the empty slot
   */
  public EuropeanSolitaireModel(int sideLength, int sRow, int sCol) {
    if (sideLength <= 0 || sideLength % 2 == 0) {
      throw new IllegalArgumentException("the arm thickness should be a positive odd number");
    }
    this.size = sideLength * 3 - 2;
    grids = new SlotState[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (i < sideLength - 1) {
          if (j >= sideLength - 1 - i && j <= size - sideLength + i) {
            grids[i][j] = SlotState.Marble;
          } else {
            grids[i][j] = SlotState.Invalid;
          }
        } else if (i > size - sideLength) {
          if (j >= sideLength - (size - i) && j <= size - (sideLength - (size - i) + 1)) {
            grids[i][j] = SlotState.Marble;
          } else {
            grids[i][j] = SlotState.Invalid;
          }
        } else if (i >= sideLength - 1 && i <= size - sideLength) {
          grids[i][j] = SlotState.Marble;
        }
      }
    }
    setState(sRow, sCol, SlotState.Empty);
  }
}
