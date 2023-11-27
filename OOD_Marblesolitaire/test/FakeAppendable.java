import java.io.IOException;

/**
 * It is the Appendable for testing IOException.
 */
public class FakeAppendable implements Appendable {
  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException("cannot write");
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException("cannot write");
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException("cannot write");
  }
}
