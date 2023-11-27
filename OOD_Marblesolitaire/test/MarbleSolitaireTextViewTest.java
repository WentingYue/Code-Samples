

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

import Marblesolitaire.model.hw02.EnglishSolitaireModel;
import Marblesolitaire.view.MarbleSolitaireTextView;
import Marblesolitaire.view.MarbleSolitaireView;

import static org.junit.Assert.assertEquals;

/**
 * Test the methods in MarbleSolitaireTextView.
 */
public class MarbleSolitaireTextViewTest {
  EnglishSolitaireModel model;
  StringWriter destination;
  MarbleSolitaireView view;

  @Before
  public void setUp() throws Exception {
    model = new EnglishSolitaireModel(3, 3, 3);
    destination = new StringWriter();
    view = new MarbleSolitaireTextView(model, destination);

  }

  @Test(expected = IllegalArgumentException.class)
  public void nullModel_Test() {
    new MarbleSolitaireTextView(null, destination);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullAppendable_Test() {
    new MarbleSolitaireTextView(model, null);
  }

  @Test
  public void toString_Test() {
    String output =
            "    O O O\n" +
                    "    O O O\n" +
                    "O O O O O O O\n" +
                    "O O O _ O O O\n" +
                    "O O O O O O O\n" +
                    "    O O O\n" +
                    "    O O O";
    assertEquals(output, view.toString());

    model.move(3, 1, 3, 3);
    String output2 =
            "    O O O\n" +
                    "    O O O\n" +
                    "O O O O O O O\n" +
                    "O _ _ O O O O\n" +
                    "O O O O O O O\n" +
                    "    O O O\n" +
                    "    O O O";
    assertEquals(output2, view.toString());
  }

  @Test
  public void renderBoard_Test() throws IOException {
    String output =
            "    O O O\n" +
                    "    O O O\n" +
                    "O O O O O O O\n" +
                    "O O O _ O O O\n" +
                    "O O O O O O O\n" +
                    "    O O O\n" +
                    "    O O O\n";
    view.renderBoard();
    assertEquals(output, destination.toString());
  }

  @Test
  public void renderMessage_Test() throws IOException {
    view.renderMessage("test");
    assertEquals("test", destination.toString());
  }

  @Test(expected = IOException.class)
  public void NotWorkingAppendable_Test1() throws IOException {
    Appendable appendable = new FakeAppendable();
    view = new MarbleSolitaireTextView(model, appendable);
    view.renderMessage("test");
  }
}