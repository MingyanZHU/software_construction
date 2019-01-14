package strategy;

import java.io.IOException;
import org.junit.Test;

public class StreamStrategyTest {

  @Test
  public void testStream() {
    InputStrategy in;
    OutputStrategy out;

    try {
      in = new StreamInputStrategy("src/txt/test.txt");
      out = new StreamOutputStrategy("src/txt/testOut.txt");

      StringBuilder stringBuilder = new StringBuilder();
      String string;
      while ((string = in.readLine()) != null) {
        stringBuilder.append(in.getLineNumber() + " " + string + "\n");
      }

      out.write(stringBuilder.toString());

      in.close();
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
