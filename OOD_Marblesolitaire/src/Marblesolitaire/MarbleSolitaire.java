package Marblesolitaire;

import Marblesolitaire.controller.MarbleSolitaireController;
import Marblesolitaire.controller.MarbleSolitaireControllerImpl;
import Marblesolitaire.model.hw02.EnglishSolitaireModel;
import Marblesolitaire.model.hw02.MarbleSolitaireModel;
import Marblesolitaire.model.hw04.EuropeanSolitaireModel;
import Marblesolitaire.model.hw04.TriangleSolitaireModel;
import Marblesolitaire.view.TriangleSolitaireTextView;
import Marblesolitaire.view.MarbleSolitaireTextView;
import Marblesolitaire.view.MarbleSolitaireView;

import java.io.InputStreamReader;
import java.util.Objects;

/**
 * Entry point for marble game.
 */
public final class MarbleSolitaire {

  /**
   * Start a game.
   * @param args args
   */
  public static void main(String[] args) {
    String modelType = args[0];
    int size = 0;
    int row = 0;
    int col = 0;
    for (int i = 0; i < args.length; i++) {
      if (Objects.equals(args[i], "-size")) {
        size = Integer.parseInt(args[i + 1]);
      }
      if (Objects.equals(args[i], "-hole")) {
        row = Integer.parseInt(args[i + 1]);
        col = Integer.parseInt(args[i + 2]);
      }
    }
    // initialize
    MarbleSolitaireModel model = initializeModel(modelType, size, row, col);
    MarbleSolitaireView view;
    if (Objects.equals(modelType, "triangular")) {
      view = new TriangleSolitaireTextView(model, System.out);
    } else {
      view = new MarbleSolitaireTextView(model, System.out);
    }
    Readable readable = new InputStreamReader(System.in);

    // start a game
    MarbleSolitaireController controller = new MarbleSolitaireControllerImpl(model,
            view,
            readable);
    controller.playGame();
  }

  // initialize model by model type
  private static MarbleSolitaireModel initializeModel(String type, int size, int row, int col) {
    MarbleSolitaireModel model;
    switch (type) {
      case "english":
        if (size > 0 && row > 0 && col > 0) {
          model = new EnglishSolitaireModel(size, row, col);
        } else if (size > 0) {
          model = new EnglishSolitaireModel(size);
        } else if (row > 0 && col > 0) {
          model = new EnglishSolitaireModel(row, col);
        } else {
          model = new EnglishSolitaireModel();
        }
        break;
      case "european":
        if (size > 0 && row > 0 && col > 0) {
          model = new EuropeanSolitaireModel(size, row, col);
        } else if (size > 0) {
          model = new EuropeanSolitaireModel(size);
        } else if (row > 0 && col > 0) {
          model = new EuropeanSolitaireModel(row, col);
        } else {
          model = new EuropeanSolitaireModel();
        }
        break;
      case "triangular":
        if (size > 0 && row > 0 && col > 0) {
          model = new TriangleSolitaireModel(size, row, col);
        } else if (size > 0) {
          model = new TriangleSolitaireModel(size);
        } else if (row > 0 && col > 0) {
          model = new TriangleSolitaireModel(row, col);
        } else {
          model = new TriangleSolitaireModel();
        }
        break;
      default:
        return null;
    }
    return model;
  }
}
