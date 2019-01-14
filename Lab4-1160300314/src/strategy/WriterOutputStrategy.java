package strategy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class WriterOutputStrategy implements OutputStrategy {

  private OutputStreamWriter writer;

  public WriterOutputStrategy(String filePath) throws IOException {
    writer = new OutputStreamWriter(new FileOutputStream(filePath), "utf-8");
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
