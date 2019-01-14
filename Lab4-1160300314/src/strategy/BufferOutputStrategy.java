package strategy;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class BufferOutputStrategy implements OutputStrategy {

  private OutputStreamWriter writer;
  private BufferedWriter bufferedWriter;

  public BufferOutputStrategy(String filePath) throws IOException {
    writer = new OutputStreamWriter(new FileOutputStream(filePath), "utf-8");
    bufferedWriter = new BufferedWriter(writer);
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
