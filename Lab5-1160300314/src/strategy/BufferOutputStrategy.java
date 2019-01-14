package strategy;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class BufferOutputStrategy implements OutputStrategy {

  private OutputStreamWriter writer;
  private BufferedWriter bufferedWriter;
  private String filePath;

  /**
   * Using buffer as the input strategy.
   *
   * @param filePath the file path of inputting.
   * @throws IOException if there is no such file on the disk.
   */
  public BufferOutputStrategy(String filePath) throws IOException {
    this.filePath = filePath;
    writer = new OutputStreamWriter(new FileOutputStream(filePath), "utf-8");
    bufferedWriter = new BufferedWriter(writer);
  }

  @Override
  public String getFilePath() {
    return this.filePath;
  }

  @Override
  public void write(String string) throws IOException {
    bufferedWriter.write(string);
  }

  @Override
  public void close() throws IOException {
    bufferedWriter.flush();
    bufferedWriter.close();
  }
}
