package strategy;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class StreamInputStrategy implements InputStrategy {

  private long lineNumber;
  private DataInputStream dataInputStream;
  private String filePath;

  /**
   * Use stream strategy read from file.
   *
   * @param fileName non-null file path
   * @throws IOException if there is no such file
   */
  public StreamInputStrategy(String fileName) throws IOException {
    this.filePath = fileName;
    lineNumber = 0;
    dataInputStream = new DataInputStream(new FileInputStream(fileName));
  }

  @Override
  public String getFilePath() {
    return filePath;
  }

  @Override
  @SuppressWarnings("deprecation")
  public String readLine() throws IOException {
    lineNumber++;
    return dataInputStream.readLine();
  }

  @Override
  public long getLineNumber() {
    return lineNumber;
  }

  @Override
  public void close() throws IOException {
    dataInputStream.close();
  }
}
