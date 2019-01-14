package strategy;

import java.io.IOException;
import org.junit.Test;

public class BufferStrategyTest {

  @Test
  public void testBuffer() {
    InputStrategy in;
    OutputStrategy out;
    try {
      in = new BufferInputStrategy("src/txt/test.txt");
      out = new BufferOutputStrategy("src/txt/testOut.txt");

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