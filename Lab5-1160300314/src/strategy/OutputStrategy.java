package strategy;

import java.io.IOException;

public interface OutputStrategy {

  /**
   * Get the path of file outputting.
   *
   * @return file path
   */
  public String getFilePath();

  /**
   * Write string in file.
   *
   * @param string the output string.
   * @throws IOException if there is no such file.
   */
  public void write(String string) throws IOException;

  /**
   * Close the stream.
   */
  public void close() throws IOException;
}
