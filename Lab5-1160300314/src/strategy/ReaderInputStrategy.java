package strategy;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReaderInputStrategy implements InputStrategy {

  private InputStreamReader reader;
  private long lineNumber;
  private String filePath;

  /**
   * Use reader strategy read from file.
   *
   * @param fileName non-null file path
   * @throws IOException if there is no such file
   */
  public ReaderInputStrategy(String fileName) throws IOException {
    this.filePath = fileName;
    lineNumber = 0;
    reader = new InputStreamReader(new FileInputStream(fileName), "utf-8");
  }

  @Override
  public String getFilePath() {
    return this.filePath;
  }

  @Override
  public String readLine() throws IOException {
    StringBuilder stringBuilder = new StringBuilder();
    char c;
    int i;
    while ((i = reader.read()) != -1) {
      c = (char) i;
      if (c == '\n' || c == '\r') {
        break;
      }
      stringBuilder.append(c);
    }
    lineNumber++;
    return i == -1 ? null : stringBuilder.toString();
  }

  @Override
  public long getLineNumber() {
    return lineNumber;
  }

  @Override
  public void close() throws IOException {
    reader.close();
  }
}
