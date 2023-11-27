import Marblesolitaire.model.hw04.TriangleSolitaireModel;
import Marblesolitaire.view.MarbleSolitaireTextView;
import Marblesolitaire.view.TriangleSolitaireTextView;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

/**
 * Test the methods in TriangleSolitaireTextView.
 */
public class TriangleSolitaireTextViewTest {
  TriangleSolitaireModel model;
  StringWriter destination;
  TriangleSolitaireTextView view;

  @Before
  public void setUp() throws Exception {
    model = new TriangleSolitaireModel(3);
    destination = new StringWriter();
    view = new TriangleSolitaireTextView(model, destination);

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
    String output = "  _\n" +
            " O O\n" +
            "O O O";
    assertEquals(output, view.toString());

    model.move(2, 0, 0, 0);
    String output2 = "  O\n" +
            " _ O\n" +
            "_ O O";
    assertEquals(output2, view.toString());
  }

  @Test
  public void renderBoard_Test() throws IOException {
    String output = "  _\n" +
            " O O\n" +
            "O O O\n";
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
    view = new TriangleSolitaireTextView(model, appendable);
    view.renderMessage("test");
  }
}