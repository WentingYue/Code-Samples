import Marblesolitaire.model.hw02.MarbleSolitaireModelState;
import Marblesolitaire.model.hw04.TriangleSolitaireModel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test the methods and exception in TriangleSolitaireModel.
 */

public class TriangleSolitaireModelTest {

  TriangleSolitaireModel model;

  @Before
  public void setUp() throws Exception {
    model = new TriangleSolitaireModel(3, 0, 0);
  }


  @Test(expected = IllegalArgumentException.class)
  public void constructorThrowsExceptionIllegalSideLength_Test() {
    new TriangleSolitaireModel(0);
  }


  @Test(expected = IllegalArgumentException.class)
  public void constructorThrowsExceptionIllegalHole_Test() {
    new TriangleSolitaireModel(3, 0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void twoArgumentsConstructorThrowsExceptionIllegalHole_Test() {
    new TriangleSolitaireModel(0,1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorIllegalSideLength() {
    new TriangleSolitaireModel(-1, 3, 3);
  }


  @Test(expected = IllegalArgumentException.class)
  public void moveThrowsExceptionNegative_Test() {
    model.move(-1, -1, -1, -1);
  }


  @Test(expected = IllegalArgumentException.class)
  public void moveThrowsExceptionInvalidMove_Test() {
    model.move(1, 0, 1, 1);
  }


  @Test(expected = IllegalArgumentException.class)
  public void moveThrowsExceptionNotEmpty_Test() {
    model.move(2, 0, 2, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveFromEmpty() {
    model.move(0, 0, 2, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveJumpOverEmpty() {
    model.move(2, 0, 0, 0);
    model.move(2, 2, 2, 0);
    model.move(2, 0, 2, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveJumpOverTwo() {
    model = new TriangleSolitaireModel(4, 0, 0);
    model.move(3, 0, 0, 0);
  }

  @Test
  public void constructorWithoutParameter_Test() {
    TriangleSolitaireModel model = new TriangleSolitaireModel();
    assertEquals(5, model.getBoardSize());
  }

  @Test
  public void constructorWithOneParameter_Test() {
    TriangleSolitaireModel model = new TriangleSolitaireModel(9);
    assertEquals(9, model.getBoardSize());
  }

  @Test
  public void constructorWithTwoParameters_Test() {
    TriangleSolitaireModel model = new TriangleSolitaireModel(1, 1);
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(1, 1));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(0, 0));
  }

  @Test
  public void constructorWithThreeParameters_Test() {
    TriangleSolitaireModel model = new TriangleSolitaireModel(3, 1, 1);
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(1, 1));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(0, 0));
  }

  @Test
  public void moveAlongLeftSide_Test() {
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(0, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(1, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(2, 0));
    model.move(2, 0, 0, 0);
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(0, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(1, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(2, 0));
  }

  @Test
  public void moveAlongRightSide_Test() {
    model.move(2, 2, 0, 0);
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(0, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(1, 1));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(2, 2));
  }

  @Test
  public void moveAlongBottomSide_Test() {
    model.move(2, 2, 0, 0);
    model.move(2, 0, 2, 2);
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(2, 2));
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, model.getSlotAt(0, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(1, 1));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(2, 0));
    assertEquals(MarbleSolitaireModelState.SlotState.Empty, model.getSlotAt(2, 1));
  }

  @Test
  public void isGameOver_Test() {
    assertEquals(false, model.isGameOver());
    model.move(2, 2, 0, 0);
    model.move(2, 0, 2, 2);
    model.move(0, 0, 2, 0);
    assertEquals(true, model.isGameOver());
  }

  @Test
  public void getBoardSize_Test() {

    assertEquals(3, model.getBoardSize());
    model = new TriangleSolitaireModel(9, 5, 5);
    assertEquals(9, model.getBoardSize());
  }

  @Test
  public void getScore_Test() {
    assertEquals(5, model.getScore());
    model.move(2, 2, 0, 0);
    assertEquals(4, model.getScore());
    model.move(2, 0, 2, 2);
    assertEquals(3, model.getScore());
  }
}