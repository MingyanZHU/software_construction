package strategy;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class BufferInputStrategy implements InputStrategy {

  private BufferedReader bufferedReader;
  private InputStreamReader inputStreamReader;
  private long lineNumber;
  private String filePath;

  /**
   * Use buffer strategy read from file.
   *
   * @param fileName non-null file path
   * @throws IOException if there is no such file
   */
  public BufferInputStrategy(String fileName) throws IOException {
    this.filePath = fileName;
    lineNumber = 0;
    inputStreamReader = new InputStreamReader(new FileInputStream(fileName), "utf-8");
    bufferedReader = new BufferedReader(inputStreamReader);
  }

  @Override
  public String getFilePath() {
    return this.filePath;
  }

  @Override
  public String readLine() throws IOException {
    lineNumber++;
    return bufferedReader.readLine();
  }

  @Override
  public long getLineNumber() {
    return lineNumber;
  }

  @Override
  public void close() throws IOException {
    bufferedReader.close();
    inputStreamReader.close();
  }
}
