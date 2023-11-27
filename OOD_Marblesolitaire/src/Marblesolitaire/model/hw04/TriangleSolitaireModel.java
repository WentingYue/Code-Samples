package Marblesolitaire.model.hw04;

/**
 * Represent the TriangleSolitaireModel which is type of MarbleSolitaireModel.
 * Model enable player to move the Solitaire. The default arm thickness is 5.
 */
public class TriangleSolitaireModel extends AbstractSolitaireModel {

  /**
   * creates a triangle board whose sides have dimensions 5,
   * with the empty slot in the center of the board.
   */
  public TriangleSolitaireModel() {
    this(5);
  }

  /**
   * creates a game with the specified side length,
   * and the empty slot at (0, 0).
   *
   * @param dimensions the dimensions
   */
  public TriangleSolitaireModel(int dimensions) {
    this(dimensions, 0, 0);
  }

  /**
   * specify the initial position of the empty slot,
   * in a board of default dimensions 5.
   */
  public TriangleSolitaireModel(int row, int col) {
    this(5, row, col);
  }

  /**
   * specify the size of the board and the initial position of the empty slot.
   *
   * @param dimensions  dimension of this board
   * @param row         row of the empty slot
   * @param col         column of the empty slot
   */
  public TriangleSolitaireModel(int dimensions, int row, int col) {
    if (dimensions < 0) {
      throw new IllegalArgumentException("dimension should be a positive number");
    }
    this.size = dimensions;
    grids = new SlotState[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (j < i + 1) {
          grids[i][j] = SlotState.Marble;
        } else {
          grids[i][j] = SlotState.Invalid;
        }
      }
    }
    setState(row, col, SlotState.Empty);
  }

  @Override
  protected boolean movable(int row, int col) {
    if (grids[row][col] != SlotState.Marble) {
      return false;
    }
    if (row - 2 >= 0 && grids[row - 1][col] == SlotState.Marble
            && grids[row - 2][col] == SlotState.Empty) {
      return true;
    } else if (row + 2 < size && grids[row + 1][col] == SlotState.Marble
            && grids[row + 2][col] == SlotState.Empty) {
      return true;
    } else if (col - 2 >= 0 && grids[row][col - 1] == SlotState.Marble
            && grids[row][col - 2] == SlotState.Empty) {
      return true;
    } else if (col + 2 < size && grids[row][col + 1] == SlotState.Marble
            && grids[row][col + 2] == SlotState.Empty) {
      return true;
    } else if (row - 2 >= 0 && col - 2 >= 0 && grids[row - 1][col - 1] == SlotState.Marble
            && grids[row - 2][col - 2] == SlotState.Empty) {
      return true;
    } else if (row + 2 < size && col + 2 < size) {
      return grids[row + 1][col + 1] == SlotState.Marble
              && grids[row + 2][col + 2] == SlotState.Empty;
    } else {
      return false;
    }
  }

  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol)
          throws IllegalArgumentException {
    checkMove(fromRow, fromCol, toRow, toCol);

    int treadStoneRow;
    int treadStoneCol;
    if (fromRow == toRow && Math.abs(fromCol - toCol) == 2) {
      // movement on horizontal direction
      treadStoneRow = fromRow;
      treadStoneCol = (fromCol + toCol) / 2;
    } else if (fromCol == toCol && Math.abs(fromRow - toRow) == 2) {
      // movement on vertical direction
      treadStoneCol = fromCol;
      treadStoneRow = (fromRow + toRow) / 2;
    } else if (fromRow - toRow == 2 && fromCol - toCol == 2) {
      treadStoneRow = (fromRow + toRow) / 2;
      treadStoneCol = (fromCol + toCol) / 2;
    } else if (fromRow - toRow == -2 && fromCol - toCol == -2) {
      treadStoneRow = (fromRow + toRow) / 2;
      treadStoneCol = (fromCol + toCol) / 2;
    } else {
      throw new IllegalArgumentException("invalid move direction");
    }
    if (grids[treadStoneRow][treadStoneCol] != SlotState.Marble) {
      throw new IllegalArgumentException("no marble between from grid and to grid");
    }
    grids[toRow][toCol] = SlotState.Marble;
    grids[fromRow][fromCol] = SlotState.Empty;
    grids[treadStoneRow][treadStoneCol] = SlotState.Empty;
  }
}
