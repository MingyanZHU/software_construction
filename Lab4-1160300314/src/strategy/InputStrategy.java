package strategy;

import java.io.IOException;

public interface InputStrategy {

  /**
   * Read file by each line.
   *
   * @return the whole line with "\n"
   * @throws IOException if there is some IO errors.
   */
  public String readLine() throws IOException;

  /**
   * Get current number of line.
   *
   * @return line number
   */
  public long getLineNumber();

  /**
   * Close the stream.
   */
  public void close() throws IOException;
}
