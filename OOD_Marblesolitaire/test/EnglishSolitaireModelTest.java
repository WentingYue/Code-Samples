
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import Marblesolitaire.model.hw02.EnglishSolitaireModel;
import Marblesolitaire.model.hw02.MarbleSolitaireModelState;

import static org.junit.Assert.assertEquals;

/**
 * Test the methods and exception in EnglishSolitaireModel.
 */

public class EnglishSolitaireModelTest {

  EnglishSolitaireModel model;

  @Before
  public void setUp() throws Exception {
    model = new EnglishSolitaireModel(3, 3, 3);
  }

  @Rule
  public Timeout timeout = Timeout.seconds(1);


  @Test(expected = IllegalArgumentException.class)
  public void constructorThrowsExceptionIllegalThickness_Test() {
    new EnglishSolitaireModel(6);
  }


  @Test(expected = IllegalArgumentException.class)
  public void constructorThrowsExceptionIllegalCenter_Test() {
    new EnglishSolitaireModel(3, 1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void twoArgumentsConstructorThrowsExceptionIllegalCenter_Test() {
    new EnglishSolitaireModel(1,1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorNotLegalArm() {
    new EnglishSolitaireModel(-1, 3, 3);
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
    EnglishSolitaireModel model = new EnglishSolitaireModel();
    assertEquals(7, model.getBoardSize());
  }

  @Test
  public void constructorWithOneParameter_Test() {
    EnglishSolitaireModel model = new EnglishSolitaireModel(5);
    assertEquals(13, model.getBoardSize());
  }

  @Test
  public void constructorWithTwoParameters_Test() {
    EnglishSolitaireModel model = new EnglishSolitaireModel(4, 3);
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(4, 3));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(3, 3));
  }

  @Test
  public void constructorWithThreeParameters_Test() {
    EnglishSolitaireModel model = new EnglishSolitaireModel(3, 4, 3);
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
    assertEquals(true, model.isGameOver());
  }

  @Test
  public void getBoardSize_Test() {

    assertEquals(7, model.getBoardSize());
    model = new EnglishSolitaireModel(5, 5, 5);
    assertEquals(13, model.getBoardSize());
  }

  @Test
  public void getScore_Test() {
    assertEquals(32, model.getScore());
    model.move(1, 3, 3, 3);
    assertEquals(31, model.getScore());
    model.move(2, 5, 2, 3);
    assertEquals(30, model.getScore());
  }
}