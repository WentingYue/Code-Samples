import Marblesolitaire.model.hw02.MarbleSolitaireModel;
import Marblesolitaire.controller.MarbleSolitaireControllerImpl;
import Marblesolitaire.model.hw02.EnglishSolitaireModel;
import Marblesolitaire.view.MarbleSolitaireTextView;

/**
 * Do not modify this file. This file should compile correctly with your code!
 */
public class Hw03TypeChecks {

  /**
   * All public methods need a Javadoc comment explaining their purpose.
   * @param args args
   */
  public static void main(String[] args) {
    Readable rd = null;
    Appendable ap = null;
    helper(new EnglishSolitaireModel(), rd, ap);
    helper(new EnglishSolitaireModel(3, 3), rd, ap);
  }

  private static void helper(
          MarbleSolitaireModel model,
          Readable rd,Appendable ap) {
    new MarbleSolitaireControllerImpl(model,
            new MarbleSolitaireTextView(model,ap),rd);
  }

}
