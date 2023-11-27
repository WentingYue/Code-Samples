import Marblesolitaire.model.hw02.MarbleSolitaireModelState;
import Marblesolitaire.model.hw04.EuropeanSolitaireModel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test the methods and exception in EuropeanSolitaireModel.
 */

public class EuropeanSolitaireModelTest {

  EuropeanSolitaireModel model;

  @Before
  public void setUp() throws Exception {
    model = new EuropeanSolitaireModel(3, 3, 3);
  }


  @Test(expected = IllegalArgumentException.class)
  public void constructorThrowsExceptionIllegalSideLength_Test() {
    new EuropeanSolitaireModel(6);
  }


  @Test(expected = IllegalArgumentException.class)
  public void constructorThrowsExceptionIllegalHole_Test() {
    new EuropeanSolitaireModel(3, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void twoArgumentsConstructorThrowsExceptionIllegalHole_Test() {
    new EuropeanSolitaireModel(0,0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorIllegalSideLength() {
    new EuropeanSolitaireModel(-1, 3, 3);
  }


  @Test(expected = IllegalArgumentException.class)
  public void moveThrowsExceptionNegative_Test() {
    model.move(-1, -1, -1, -1);
  }


  @Test(expected = IllegalArgumentException.class)
  public void moveThrowsExceptionInvalidMove_Test() {
    model.move(2, 1, 2, 2);
  }


  @Test(expected = IllegalArgumentException.class)
  public void moveThrowsExceptionNotEmpty_Test() {
    model.move(2, 3, 0, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveFromEmpty() {
    model.move(5, 3, 3, 3);
    model.move(2, 3, 4, 3);
    model.move(2, 3, 4, 3);
    model.move(3, 3, 5, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveJumpOverEmpty() {
    model.move(5, 3, 3, 3);
    model.move(6, 3, 4, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveJumpOverTwo() {
    model.move(5, 3, 3, 3);
    model.move(4, 0, 4, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveJumpOverNotHorizontalOrVertical() {
    model.move(5, 3, 3, 3);
    model.move(2, 5, 4, 3);
  }


  @Test
  public void constructorWithoutParameter_Test() {
    EuropeanSolitaireModel model = new EuropeanSolitaireModel();
    assertEquals(7, model.getBoardSize());
  }

  @Test
  public void constructorWithOneParameter_Test() {
    EuropeanSolitaireModel model = new EuropeanSolitaireModel(5);
    assertEquals(13, model.getBoardSize());
  }

  @Test
  public void constructorWithTwoParameters_Test() {
    EuropeanSolitaireModel model = new EuropeanSolitaireModel(4, 3);
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(4, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(3, 3));
  }

  @Test
  public void constructorWithThreeParameters_Test() {
    EuropeanSolitaireModel model = new EuropeanSolitaireModel(3, 4, 3);
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(4, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(3, 3));
  }

  @Test
  public void move_Test() {
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(3, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(4, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(5, 3));
    model.move(5, 3, 3, 3);
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(3, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(4, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(5, 3));
  }

  @Test
  public void moveSameColumn_Test() {
    model.move(1, 3, 3, 3);
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(3, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(1, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(2, 3));
  }

  @Test
  public void moveSameRowMoveLeft_Test() {
    model.move(3, 5, 3, 3);
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(3, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(3, 5));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(3, 4));
  }

  @Test
  public void moveSameRowMoveRight_Test() {
    model.move(3, 1, 3, 3);
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(3, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(3, 1));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(3, 2));
  }


  @Test
  public void isGameOver_Test() {
    assertEquals(false, model.isGameOver());
    model.move(5, 3, 3, 3);
    assertEquals(false, model.isGameOver());
    model.move(2, 3, 4, 3);
    model.move(3, 5, 3, 3);
    model.move(3, 2, 3, 4);
    model.move(3, 0, 3, 2);
    model.move(0, 3, 2, 3);
    model.move(1, 1, 3, 1);
    model.move(4, 1, 2, 1);
    model.move(1, 5, 3, 5);
    model.move(5, 5, 5, 3);
    model.move(4, 3, 4, 1);
    model.move(5, 2, 5, 4);
    model.move(5, 1, 3, 1);
    model.move(4, 5, 2, 5);
    model.move(3, 2, 3, 0);
    model.move(1, 2, 3, 2);
    model.move(2, 0, 2, 2);
    model.move(4, 0, 2, 0);
    model.move(2, 2, 4, 2);
    model.move(2, 4, 2, 2);
    model.move(2, 6, 2, 4);
    model.move(4, 6, 2, 6);
    assertEquals(true, model.isGameOver());
  }

  @Test
  public void getBoardSize_Test() {

    assertEquals(7, model.getBoardSize());
    model = new EuropeanSolitaireModel(5, 5, 5);
    assertEquals(13, model.getBoardSize());
  }

  @Test
  public void getScore_Test() {
    assertEquals(36, model.getScore());
    model.move(1, 3, 3, 3);
    assertEquals(35, model.getScore());
    model.move(2, 5, 2, 3);
    assertEquals(34, model.getScore());
  }
}