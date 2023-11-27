package Marblesolitaire.model.hw04;

import Marblesolitaire.model.hw02.MarbleSolitaireModel;

import java.text.MessageFormat;

/**
 * Abstract MarbleSolitaireModel.
 * Pull up the methods which can be commonly used in different models.
 */
public abstract class AbstractSolitaireModel implements MarbleSolitaireModel {

  /**
   * This enum represents the direction of a movement.
   */
  enum Direction { Up, Down, Left, Right }

  /**
   * Size of board.
   */
  protected int size;
  /**
   * Board data representing states of each grid.
   */
  protected SlotState[][] grids;

  /**
   * set specific position to new state.
   * @param sRow position row
   * @param sCol position column
   * @param slotState new state
   */
  protected void setState(int sRow, int sCol, SlotState slotState) {
    if (sRow >= size || sCol >= size
            || sRow < 0 || sCol < 0
            || grids[sRow][sCol] == SlotState.Invalid) {
      throw new IllegalArgumentException(MessageFormat.format(
              "Invalid cell position ({0},{1})",
              sRow, sCol));
    } else {
      grids[sRow][sCol] = slotState;
    }
  }

  /**
   * check if the movement is legal.
   * @param fromRow from row
   * @param fromCol from column
   * @param toRow to row
   * @param toCol to column
   */
  protected void checkMove(int fromRow, int fromCol, int toRow, int toCol) {
    if (fromRow < 0 || fromRow >= size || fromCol < 0 || fromCol >= size
            || toRow < 0 || toRow >= size || toCol < 0 || toCol >= size) {
      throw new IllegalArgumentException("Out of board!");
    }
    if (grids[fromRow][fromCol] != SlotState.Marble) {
      throw new IllegalArgumentException("No marble at from position!");
    }
    if (grids[toRow][toCol] != SlotState.Empty ) {
      throw new IllegalArgumentException("To position is not empty!");
    }
  }

  @Override
  public boolean isGameOver() {
    for (int i = 0; i < grids.length; i++) {
      for (int j = 0; j < grids[i].length; j++) {
        if (movable(i, j)) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * default move with vertical and horizontal directions.
   * @param fromRow the row number of the position to be moved from
   *                (starts at 0)
   * @param fromCol the column number of the position to be moved from
   *                (starts at 0)
   * @param toRow   the row number of the position to be moved to
   *                (starts at 0)
   * @param toCol   the column number of the position to be moved to
   *                (starts at 0)
   * @throws IllegalArgumentException breaking rules
   */
  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {
    // check
    checkMove(fromRow, fromCol, toRow, toCol);

    int rowGap = Math.abs(fromRow - toRow);
    int colGap = Math.abs(fromCol - toCol);
    if ((rowGap == 0 && colGap == 2) || (rowGap == 2 && colGap == 0)) {
      int treadStoneRow = (fromRow + toRow) / 2;
      int treadStoneCol = (fromCol + toCol) / 2;
      if (grids[treadStoneRow][treadStoneCol] != SlotState.Marble) {
        throw new IllegalArgumentException("no marble between from grid and to grid");
      }
      grids[toRow][toCol] = SlotState.Marble;
      grids[fromRow][fromCol] = SlotState.Empty;
      grids[treadStoneRow][treadStoneCol] = SlotState.Empty;
    } else {
      throw new IllegalArgumentException("invalid move direction");
    }
  }

  /**
   * Default method for judge if a position can make movements.
   * @param row row
   * @param col col
   * @return true if any movement can be proceeded
   */
  protected boolean movable(int row, int col) {
    if (row >= 2 && movableWithDirection(row, col, Direction.Up)) {
      return true;
    }
    if (row < size - 2 && movableWithDirection(row, col, Direction.Down)) {
      return true;
    }
    if (col >= 2 && movableWithDirection(row, col, Direction.Left)) {
      return true;
    }
    return col < size - 2 && movableWithDirection(row, col, Direction.Right);
  }

  private boolean movableWithDirection(int row, int col, Direction direction) {
    if (grids[row][col] != SlotState.Marble) {
      return false;
    }
    switch (direction) {
      case Up:
        return grids[row - 1][col] == SlotState.Marble && grids[row - 2][col] == SlotState.Empty;
      case Right:
        return grids[row][col + 1] == SlotState.Marble && grids[row][col + 2] == SlotState.Empty;
      case Down:
        return grids[row + 1][col] == SlotState.Marble && grids[row + 2][col] == SlotState.Empty;
      case Left:
        return grids[row][col - 1] == SlotState.Marble && grids[row][col - 2] == SlotState.Empty;
      default:
        return false;
    }
  }

  @Override
  public int getBoardSize() {
    return size;
  }

  @Override
  public SlotState getSlotAt(int row, int col) throws IllegalArgumentException {
    return grids[row][col];
  }

  @Override
  public int getScore() {
    int score = 0;
    for (int i = 0; i < grids.length; i++) {
      for (int j = 0; j < grids[i].length; j++) {
        if (grids[i][j] == SlotState.Marble) {
          score++;
        }
      }
    }
    return score;
  }
}