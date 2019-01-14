package strategy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class WriterOutputStrategy implements OutputStrategy {

  private OutputStreamWriter writer;
  private String filePath;

  public WriterOutputStrategy(String filePath) throws IOException {
    this.filePath = filePath;
    writer = new OutputStreamWriter(new FileOutputStream(filePath), "utf-8");
  }

  @Override
  public String getFilePath() {
    return this.filePath;
  }

  @Override
  public void write(String string) throws IOException {
    writer.write(string);
  }

  @Override
  public void close() throws IOException {
    writer.flush();
    writer.close();
  }
}
