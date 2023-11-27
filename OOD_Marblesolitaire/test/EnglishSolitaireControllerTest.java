
import Marblesolitaire.controller.MarbleSolitaireController;
import Marblesolitaire.controller.MarbleSolitaireControllerImpl;
import Marblesolitaire.model.hw02.EnglishSolitaireModel;
import Marblesolitaire.model.hw02.MarbleSolitaireModel;
import Marblesolitaire.view.MarbleSolitaireTextView;
import Marblesolitaire.view.MarbleSolitaireView;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test the methods and exception in MarbleSolitaireController.
 */

public class EnglishSolitaireControllerTest {

  MarbleSolitaireController controller;
  MarbleSolitaireModel model;
  MarbleSolitaireView view;
  Readable readable;
  ByteArrayOutputStream outContent = new ByteArrayOutputStream();


  @Before
  public void setUp() throws Exception {
    model = new EnglishSolitaireModel();
    view = new MarbleSolitaireTextView(model, new PrintStream(outContent));
  }


  @Test(expected = IllegalArgumentException.class)
  public void constructorThrowsExceptionNullModel_Test() {
    new MarbleSolitaireControllerImpl(null, view, readable);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorThrowsExceptionNullView_Test() {
    new MarbleSolitaireControllerImpl(model, null, readable);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorThrowsExceptionNullReader_Test() {
    new MarbleSolitaireControllerImpl(model, view, null);
  }

  @Test(expected = IllegalStateException.class)
  public void incompleteInput_Test() {
    readable = new InputStreamReader(new ByteArrayInputStream("1 2 3".getBytes()));
    controller = new MarbleSolitaireControllerImpl(model, view, readable);
    controller.playGame();
  }

  @Test(expected = IllegalStateException.class)
  public void BadInput_Test() {
    readable = new InputStreamReader(new ByteArrayInputStream("a b".getBytes()));
    controller = new MarbleSolitaireControllerImpl(model, view, readable);
    controller.playGame();
  }

  @Test
  public void playThrowsExceptionInvalidInput_Test() {
    readable = new InputStreamReader(new ByteArrayInputStream("a q".getBytes()));
    controller = new MarbleSolitaireControllerImpl(model, view, readable);
    controller.playGame();
    assertEquals(32, model.getScore());
  }

  @Test(expected = IllegalStateException.class)
  public void appendableCannotWork() {
    Appendable appendable = new FakeAppendable();
    view = new MarbleSolitaireTextView(model, appendable);
    readable = new InputStreamReader(new ByteArrayInputStream("a q".getBytes()));
    controller = new MarbleSolitaireControllerImpl(model, view, readable);
    controller.playGame();
  }



  @Test
  public void playQuit_Test() {
    readable = new InputStreamReader(new ByteArrayInputStream("q".getBytes()));
    controller = new MarbleSolitaireControllerImpl(model, view, readable);
    controller.playGame();
    String expected = "Game quit!\n" +
            "State of game when quit:\n" +
            "    O O O\n" +
            "    O O O\n" +
            "O O O O O O O\n" +
            "O O O _ O O O\n" +
            "O O O O O O O\n" +
            "    O O O\n" +
            "    O O O\n" +
            "Score: 32\n";
    assertTrue(outContent.toString().endsWith(expected));
    assertEquals(32, model.getScore());
  }


  @Test
  public void playBadInput1_Test() {
    readable = new InputStreamReader(new ByteArrayInputStream("1 2 3 4 q ".getBytes()));
    controller = new MarbleSolitaireControllerImpl(model, view, readable);
    controller.playGame();
    String expected = "Invalid move, Play again. No marble at from position!";
    assertTrue(outContent.toString().contains(expected));
    assertEquals(32, model.getScore());
  }


  @Test
  public void playBadInput2_Test() {
    readable = new InputStreamReader(new ByteArrayInputStream("1 2 3 -5 q".getBytes()));
    controller = new MarbleSolitaireControllerImpl(model, view, readable);
    controller.playGame();
    assertEquals(32, model.getScore());
  }

  @Test
  public void playBadInput3_Test() {
    readable = new InputStreamReader(new ByteArrayInputStream("2 3 4 3 q ".getBytes()));
    controller = new MarbleSolitaireControllerImpl(model, view, readable);
    controller.playGame();
    String expected = "Invalid move, Play again. To position is not empty!";
    assertTrue(outContent.toString().contains(expected));
    assertEquals(32, model.getScore());
  }


  @Test
  public void playInvalidMove1_Test() {
    readable = new InputStreamReader(new ByteArrayInputStream("4 2 4 2 q".getBytes()));
    controller = new MarbleSolitaireControllerImpl(model, view, readable);
    controller.playGame();
    String expected = "Invalid move, Play again.";
    assertTrue(outContent.toString().contains(expected));
    assertEquals(32, model.getScore());
  }


  @Test
  public void playQuitAfterValidMove_Test() {
    readable = new StringReader("2 4 4 4 q");
    controller = new MarbleSolitaireControllerImpl(model, view, readable);
    controller.playGame();
    System.out.println(outContent.toString());
    assertEquals(31, model.getScore());
  }

  @Test
  public void quitAfterOneLegalMoveButIncompleteSecondMove() {
    readable = new InputStreamReader(new ByteArrayInputStream("2 4 4 4 4 6 q".getBytes()));
    controller = new MarbleSolitaireControllerImpl(model, view, readable);
    controller.playGame();
    System.out.println(outContent.toString());
    assertEquals(31, model.getScore());
  }

  @Test
  public void quitAfterOneMoveIgnoreMoveAfterQuit() {
    readable = new InputStreamReader(new ByteArrayInputStream("2 4 4 4 q 1 2 3".getBytes()));
    controller = new MarbleSolitaireControllerImpl(model, view, readable);
    controller.playGame();
    System.out.println(outContent.toString());
    assertEquals(31, model.getScore());
  }

  @Test
  public void skipTheInvalidInput() {
    readable = new InputStreamReader(new ByteArrayInputStream("2 4 a s 4 4 q".getBytes()));
    controller = new MarbleSolitaireControllerImpl(model, view, readable);
    controller.playGame();
    System.out.println(outContent.toString());
    assertEquals(31, model.getScore());
  }

  @Test(expected = IllegalStateException.class)
  public void notEnoughInputForFinishingGame_Test() {
    readable = new InputStreamReader(new ByteArrayInputStream("2 4 4 4".getBytes()));
    controller = new MarbleSolitaireControllerImpl(model, view, readable);
    controller.playGame();
  }

  @Test
  public void playFinish_Test() {
    String input = "4 2 4 4 " +
            "2 3 4 3 " +
            "4 4 4 2 " +
            "3 1 3 3 " +
            "2 4 4 4 " +
            "3 6 3 4 " +
            "1 5 3 5 " +
            "1 3 1 5 " +
            "4 5 2 5 " +
            "1 5 3 5 " +
            "3 4 3 6 " +
            "3 7 3 5 " +
            "5 4 3 4 " +
            "3 4 3 6 " +
            "5 7 3 7 " +
            "3 7 3 5 " +
            "5 6 5 4 " +
            "5 3 5 5 " +
            "4 1 4 3 " +
            "3 3 5 3 " +
            "6 5 4 5 " +
            "3 5 5 5 " +
            "5 2 5 4 " +
            "5 5 5 3 " +
            "6 3 6 5 " +
            "7 5 5 5 " +
            "7 3 7 5 ";
    readable = new InputStreamReader(new ByteArrayInputStream(input.getBytes()));
    controller = new MarbleSolitaireControllerImpl(model, view, readable);
    controller.playGame();
    String expected = "Game over!\n" +
            "    _ _ _\n" +
            "    _ _ _\n" +
            "_ _ _ _ _ _ _\n" +
            "_ _ _ _ _ O _\n" +
            "O _ O _ O _ _\n" +
            "    _ _ _\n" +
            "    _ _ O\n" +
            "Score: 5\n";
    assertTrue(outContent.toString().endsWith(expected));
    assertEquals(5, model.getScore());
  }

  @Test
  public void ErrorMoveAfterGameOver_Test() {
    String input = "4 2 4 4 " +
            "2 3 4 3 " +
            "4 4 4 2 " +
            "3 1 3 3 " +
            "2 4 4 4 " +
            "3 6 3 4 " +
            "1 5 3 5 " +
            "1 3 1 5 " +
            "4 5 2 5 " +
            "1 5 3 5 " +
            "3 4 3 6 " +
            "3 7 3 5 " +
            "5 4 3 4 " +
            "3 4 3 6 " +
            "5 7 3 7 " +
            "3 7 3 5 " +
            "5 6 5 4 " +
            "5 3 5 5 " +
            "4 1 4 3 " +
            "3 3 5 3 " +
            "6 5 4 5 " +
            "3 5 5 5 " +
            "5 2 5 4 " +
            "5 5 5 3 " +
            "6 3 6 5 " +
            "7 5 5 5 " +
            "7 3 7 5 " +
            "1 2 3 4";
    readable = new InputStreamReader(new ByteArrayInputStream(input.getBytes()));
    controller = new MarbleSolitaireControllerImpl(model, view, readable);
    controller.playGame();
    String expected = "Game over!\n" +
            "    _ _ _\n" +
            "    _ _ _\n" +
            "_ _ _ _ _ _ _\n" +
            "_ _ _ _ _ O _\n" +
            "O _ O _ O _ _\n" +
            "    _ _ _\n" +
            "    _ _ O\n" +
            "Score: 5\n";
    assertTrue(outContent.toString().endsWith(expected));
    assertEquals(5, model.getScore());
  }

  @Test
  public void InvalidMoveAfterGameOver_Test() {
    String input = "4 2 4 4 " +
            "2 3 4 3 " +
            "4 4 4 2 " +
            "3 1 3 3 " +
            "2 4 4 4 " +
            "3 6 3 4 " +
            "1 5 3 5 " +
            "1 3 1 5 " +
            "4 5 2 5 " +
            "1 5 3 5 " +
            "3 4 3 6 " +
            "3 7 3 5 " +
            "5 4 3 4 " +
            "3 4 3 6 " +
            "5 7 3 7 " +
            "3 7 3 5 " +
            "5 6 5 4 " +
            "5 3 5 5 " +
            "4 1 4 3 " +
            "3 3 5 3 " +
            "6 5 4 5 " +
            "3 5 5 5 " +
            "5 2 5 4 " +
            "5 5 5 3 " +
            "6 3 6 5 " +
            "7 5 5 5 " +
            "7 3 7 5 " +
            "1 2 3 ";
    readable = new InputStreamReader(new ByteArrayInputStream(input.getBytes()));
    controller = new MarbleSolitaireControllerImpl(model, view, readable);
    controller.playGame();
    String expected = "Game over!\n" +
            "    _ _ _\n" +
            "    _ _ _\n" +
            "_ _ _ _ _ _ _\n" +
            "_ _ _ _ _ O _\n" +
            "O _ O _ O _ _\n" +
            "    _ _ _\n" +
            "    _ _ O\n" +
            "Score: 5\n";
    assertTrue(outContent.toString().endsWith(expected));
    assertEquals(5, model.getScore());
  }
}