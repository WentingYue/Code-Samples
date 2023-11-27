import Marblesolitaire.controller.MarbleSolitaireControllerImpl;
import Marblesolitaire.model.hw02.MarbleSolitaireModel;
import Marblesolitaire.model.hw02.EnglishSolitaireModel;
import Marblesolitaire.model.hw04.TriangleSolitaireModel;
import Marblesolitaire.view.MarbleSolitaireTextView;

/**
 * Do not modify this file. This file should compile correctly with your code!
 */
public class Hw04TypeChecks {

  /**
   * main method.
   * @param args args
   */
  public static void main(String[] args) {
    Readable rd = null;
    Appendable ap = null;
    helperMarble(new EnglishSolitaireModel(), rd, ap);

    helperTriangle(new TriangleSolitaireModel(3, 3), rd, ap);
  }

  private static void helperMarble(MarbleSolitaireModel model,
                                   Readable rd, Appendable ap) {
    new MarbleSolitaireControllerImpl(model,
            new MarbleSolitaireTextView(model,ap),rd);
  }

  private static void helperTriangle(MarbleSolitaireModel model,
                                     Readable rd, Appendable ap) {
    new MarbleSolitaireControllerImpl(model,
            new MarbleSolitaireTextView(model,ap),rd);
  }

}
